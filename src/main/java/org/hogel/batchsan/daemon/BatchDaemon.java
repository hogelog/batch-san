package org.hogel.batchsan.daemon;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.BatchRedisKey;
import org.hogel.batchsan.core.db.dao.JobRecipeLogDao;
import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
    JobRecipeLogDao jobRecipeLogDao;

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
                JobRecipeLogRecord logRecord = jobRecipeLogDao.create(jobRecipe);
                try {
                    batchJob.run();
                    jobRecipeLogDao.update(logRecord.getId(), JobRecipeLogRecord.SUCCESS);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                    jobRecipeLogDao.update(logRecord.getId(), JobRecipeLogRecord.FAILURE);
                }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
