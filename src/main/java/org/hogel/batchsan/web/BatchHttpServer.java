package org.hogel.batchsan.web;

import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.web.rs.BatchHttpApplication;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

public class BatchHttpServer {
    public static final String ATTR_BATCH_CONFIG = "batch_config";
    public static final String ATTR_BATCH_JOB_MANAGER = "batch_job_manager";
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

        BatchJobManager manager = new BatchJobManager(config);
        deploymentInfo.addServletContextAttribute(ATTR_BATCH_CONFIG, config);
        deploymentInfo.addServletContextAttribute(ATTR_BATCH_JOB_MANAGER, manager);
        deploymentInfo.addServletContextAttribute(ATTR_BATCH_INJECTOR, manager.getInjector());

        server.deploy(deploymentInfo);
    }

    public void start() {
        server.start(serverBulder);
    }
}
