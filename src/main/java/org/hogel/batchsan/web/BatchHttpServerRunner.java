package org.hogel.batchsan.web;

public class BatchHttpServerRunner {
    public static void main(String[] args) {
        BatchHttpServer server = new BatchHttpServer("localhost", 8080);
        server.start();
    }
}
