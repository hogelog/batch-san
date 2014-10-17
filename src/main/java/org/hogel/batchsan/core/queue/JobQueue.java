package org.hogel.batchsan.core.queue;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchRedisKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

@Singleton
public class JobQueue {
    private static final Logger LOG = LoggerFactory.getLogger(JobQueue.class);

    private final BatchConfig config;

    private final BatchRedisKey redisKey;

    private final JedisPool jedisPool;

    @Inject
    public JobQueue(BatchConfig config, BatchRedisKey redisKey) {
        this.config = config;
        this.redisKey = redisKey;
        jedisPool = new JedisPool(new JedisPoolConfig(), config.getRedisHost(), config.getRedisPort(), config.getRedisTimeout());
        LOG.info("Redis: connect to {}:{}", config.getRedisHost(), config.getRedisPort());
    }

    public void enqueue(String jobRecipe) {
        final String queueName = redisKey.queue();
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(queueName, jobRecipe);
            LOG.info("enqueue {}: {}", queueName, jobRecipe);
        }
    }

    public Optional<String> dequeue() {
        final String queueName = redisKey.queue();
        final int timeout = config.getRedisTimeout();
        try (Jedis jedis = jedisPool.getResource()) {
            List<String> jobMessage = jedis.brpop(timeout, queueName);
            if (jobMessage == null) {
                return Optional.absent();
            }
            LOG.info("dequeue {}: {}", queueName, jobMessage);

            return Optional.of(jobMessage.get(1));
        }
    }
}
