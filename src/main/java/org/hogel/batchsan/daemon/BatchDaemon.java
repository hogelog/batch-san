package org.hogel.batchsan.daemon;

import com.google.inject.Module;
import org.hogel.batchsan.BatchJobManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class BatchDaemon {
    private final BatchJobManager batchJobManager;

    private final JedisPool jedisPool;

    private JedisPubSub jedisListener = new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {

        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {

        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {

        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {

        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {

        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {

        }
    };

    public BatchDaemon(Module... modules) {
        batchJobManager = new BatchJobManager(modules);
        jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");
    }

    public void start() {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.subscribe(jedisListener);
        }
    }
}
