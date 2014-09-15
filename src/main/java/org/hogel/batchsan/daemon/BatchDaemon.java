package org.hogel.batchsan.daemon;

import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.config.InvalidConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchDaemon {
    private static final Logger LOG = LoggerFactory.getLogger(BatchDaemon.class);

    private static final int DEFAULT_QUEUE_TIMEOUT_SEC = 10;

    private static final int DEFAULT_WORKER_COUNT = 5;

    public static class Builder {
        private String jedisHost = "localhost";

        private int jedisPort = Protocol.DEFAULT_PORT;

        private int jedisTimeout = Protocol.DEFAULT_TIMEOUT;

        private String queueName = "job_queue";

        private int worker = DEFAULT_WORKER_COUNT;

        public Builder jedis(String host) {
            jedisHost = host;
            return this;
        }

        public Builder jedis(String host, int port) {
            jedisHost = host;
            jedisPort = port;
            return this;
        }

        public Builder jedis(String host, int port, int timeout) {
            jedisHost = host;
            jedisPort = port;
            jedisTimeout = timeout;
            return this;
        }

        public Builder queueName(String name) {
            queueName = name;
            return this;
        }

        public Builder worker(int worker) {
            this.worker = worker;
            return this;
        }

        public BatchDaemon build(BatchJobManager batchJobManager) {
            return new BatchDaemon(batchJobManager, jedisHost, jedisPort, jedisTimeout, queueName, worker);
        }
    }

    private final BatchJobManager batchJobManager;

    private final JedisPool jedisPool;

    private final String queueName;

    private final ExecutorService jobExecutor;

    private BatchDaemon(BatchJobManager batchJobManager, String host, int port, int timeout, String queueName, int worker) {
        this.batchJobManager = batchJobManager;
        this.queueName = queueName;
        jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
        jobExecutor = Executors.newFixedThreadPool(worker);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void start() {
        while (true) {
            try(Jedis jedis = jedisPool.getResource()) {
                List<String> jobMessage = jedis.brpop(DEFAULT_QUEUE_TIMEOUT_SEC, queueName);
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
