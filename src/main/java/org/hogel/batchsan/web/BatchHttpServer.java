package org.hogel.batchsan.web;

import io.undertow.Undertow;
import org.hogel.batchsan.web.rs.BatchHttpApplication;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

public class BatchHttpServer {
    public BatchHttpServer(String host, int port) {
        UndertowJaxrsServer server = new UndertowJaxrsServer();
        Undertow.Builder serverBulder = Undertow.builder().addHttpListener(port, host);

        server.deploy(BatchHttpApplication.class);

        server.start(serverBulder);
    }

    public void start() {
    }
}
