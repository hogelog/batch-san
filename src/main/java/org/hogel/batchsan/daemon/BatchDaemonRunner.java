package org.hogel.batchsan.daemon;

import org.hogel.batchsan.BatchJobManager;

public class BatchDaemonRunner {
    public static void main(String[] args) {
        BatchJobManager batchJobManager = new BatchJobManager();
        BatchDaemon daemon = BatchDaemon
            .builder()
            .jedis("localhost")
            .build(batchJobManager);

        daemon.start();
    }
}
