package org.hogel.batchsan.daemon;

import com.google.inject.Injector;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.BatchRedisKey;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.config.InvalidConfigException;
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


    private final BatchJobManager batchJobManager;

    private final BatchConfig config;

    private final BatchRedisKey batchRedisKey;

    private final JedisPool jedisPool;

    private final ExecutorService jobExecutor;

    public BatchDaemon(BatchJobManager batchJobManager) {
        this.batchJobManager = batchJobManager;

        Injector injector = batchJobManager.getInjector();
        config = injector.getInstance(BatchConfig.class);

        batchRedisKey = new BatchRedisKey(config);

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

                String recipe = jobMessage.get(1);
                executeJob(recipe);
            }
        }
    }

    private void executeJob(final String recipe) {
        jobExecutor.execute(new Runnable() {
            @Override
            public void run() {
                JobRecipe jobRecipe = new JobRecipe();
                try {
                    jobRecipe.load(recipe);
                } catch (InvalidConfigException e) {
                    LOG.error("Invalid job recipe: {}", recipe);
                    return;
                }

                BatchJob batchJob = batchJobManager.createBatchJob(jobRecipe);
                try {
                    batchJob.run();
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        });
    }
}
