package org.hogel.batchsan.web;

import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchConfigModule;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.config.InvalidConfigException;

public class BatchHttpServerRunner {
    public static void main(String[] args) throws InvalidConfigException {
        BatchConfig batchConfig = new BatchConfig();
        batchConfig.load("{}");

        BatchJobManager batchJobManager = new BatchJobManager(new BatchConfigModule(batchConfig));

        BatchHttpServer server = new BatchHttpServer(batchJobManager);
        server.start();
    }
}
