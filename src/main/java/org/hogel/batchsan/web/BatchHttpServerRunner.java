package org.hogel.batchsan.web;

import org.hogel.batchsan.core.BatchJobManager;

public class BatchHttpServerRunner {
    public static void main(String[] args) {
        BatchJobManager batchJobManager = new BatchJobManager();
        BatchHttpServer server = new BatchHttpServer("localhost", 8080);
        server.start();
//        server.start();
    }
}
