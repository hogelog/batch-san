package org.hogel.batchsan.daemon;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.BatchRedisKey;
import org.hogel.batchsan.core.db.table.JobRecipeLogTable;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchDaemon {
    private static final Logger LOG = LoggerFactory.getLogger(BatchDaemon.class);

    @Inject
    BatchConfig config;

    @Inject
    BatchRedisKey batchRedisKey;

    @Inject
    BoneCP connections;

    @Inject
    JobRecipeLogTable jobRecipeLogTable;

    private final BatchJobManager batchJobManager;

    private final JedisPool jedisPool;

    private final ExecutorService jobExecutor;

    public BatchDaemon(BatchJobManager batchJobManager) {
        this.batchJobManager = batchJobManager;

        Injector injector = batchJobManager.getInjector();
        injector.injectMembers(this);

        jedisPool = new JedisPool(new JedisPoolConfig(), config.getRedisHost(), config.getRedisPort(), config.getRedisTimeout());
        jobExecutor = Executors.newFixedThreadPool(config.getJobWorkers());
    }

    public void start() {
        while (true) {
            final String queueName = batchRedisKey.queue();
            final int timeout = config.getRedisTimeout();
            try(Jedis jedis = jedisPool.getResource()) {
                List<String> jobMessage = jedis.brpop(timeout, queueName);
                if (jobMessage == null) {
                    continue;
                }
                LOG.info("{}: {}", queueName, jobMessage);

                final String recipe = jobMessage.get(1);
                jobExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        executeJob(recipe);
                    }
                });
            }
        }
    }

    public void executeJob(final String recipe) {
        try {
            JobRecipe jobRecipe = JobRecipe.loadRecipe(recipe);
            BatchJob batchJob = batchJobManager.createBatchJob(jobRecipe);
            try (Connection connection = connections.getConnection()) {
                long logId = jobRecipeLogTable.insertLog(connection, jobRecipe.getJob());
                try {
                    batchJob.run();
                    jobRecipeLogTable.updateLogStatus(connection, logId, JobRecipeLogTable.SUCCESS);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                    jobRecipeLogTable.updateLogStatus(connection, logId, JobRecipeLogTable.FAILURE);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
