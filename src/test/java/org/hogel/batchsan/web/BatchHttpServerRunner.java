package org.hogel.batchsan.web;

import org.hogel.batchsan.core.BatchConfig;
import org.hogel.config.InvalidConfigException;

public class BatchHttpServerRunner {
    public static void main(String[] args) throws InvalidConfigException {
        BatchConfig batchConfig = new BatchConfig();
        batchConfig.load("{}");

        BatchHttpServer server = new BatchHttpServer(batchConfig);
        server.start();
    }
}
