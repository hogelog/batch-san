package org.hogel.batchsan.web;

import com.google.inject.Injector;
import io.undertow.Undertow;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.web.rs.BatchHttpApplication;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

public class BatchHttpServer {
    private final UndertowJaxrsServer server;
    private final Undertow.Builder serverBulder;

    public BatchHttpServer(BatchJobManager batchJobManager) {
        Injector injector = batchJobManager.getInjector();
        BatchConfig config = injector.getInstance(BatchConfig.class);

        server = new UndertowJaxrsServer();
        serverBulder = Undertow.builder().addHttpListener(config.getWebPort(), config.getWebHost());

        server.deploy(BatchHttpApplication.class);
    }

    public void start() {
        server.start(serverBulder);
    }
}
