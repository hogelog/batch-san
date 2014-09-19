package org.hogel.batchsan.core;

public class BatchRedisKey {
    private final BatchConfig config;

    public BatchRedisKey(BatchConfig config) {
        this.config = config;
    }

    public String queue() {
        return _name("job_queue");
    }

    private String _name(String name) {
        StringBuilder builder = new StringBuilder(config.getRedisNamespace());
        return builder.append(':').append(name).toString();
    }
}
