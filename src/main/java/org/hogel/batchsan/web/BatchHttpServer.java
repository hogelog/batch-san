package org.hogel.batchsan.web;

import com.google.inject.Guice;
import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.guice.BatchBasicModule;
import org.hogel.batchsan.web.rs.BatchHttpApplication;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

public class BatchHttpServer {
    public static final String ATTR_BATCH_CONFIG = "batch_config";
    public static final String ATTR_BATCH_INJECTOR = "batch_injector";

    private final UndertowJaxrsServer server;
    private final Undertow.Builder serverBulder;

    public BatchHttpServer(BatchConfig config) {
        server = new UndertowJaxrsServer();
        serverBulder = Undertow
            .builder()
            .addHttpListener(config.getWebPort(), config.getWebHost())
        ;

        DeploymentInfo deploymentInfo = server.undertowDeployment(BatchHttpApplication.class);
        deploymentInfo.setDeploymentName("BatchHttpServer");
        deploymentInfo.setContextPath("/");

        deploymentInfo.addServletContextAttribute(ATTR_BATCH_CONFIG, config);
        deploymentInfo.addServletContextAttribute(ATTR_BATCH_INJECTOR, Guice.createInjector(new BatchBasicModule(config)));

        server.deploy(deploymentInfo);
    }

    public void start() {
        server.start(serverBulder);
    }
}
