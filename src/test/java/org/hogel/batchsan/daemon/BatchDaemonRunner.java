package org.hogel.batchsan.daemon;

import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.config.InvalidConfigException;

public class BatchDaemonRunner {
    public static void main(String[] args) throws InvalidConfigException {
        BatchConfig batchConfig = new BatchConfig();
        batchConfig.load("databaseUrl: 'jdbc:h2:./target/development'");

        BatchJobManager batchJobManager = new BatchJobManager(batchConfig);
        BatchDaemon daemon = new BatchDaemon(batchJobManager);

        daemon.start();
    }
}
