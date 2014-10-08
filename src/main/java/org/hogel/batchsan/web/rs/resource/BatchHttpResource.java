package org.hogel.batchsan.web.rs.resource;

import com.google.inject.Injector;
import org.hogel.batchsan.web.BatchHttpServer;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

public abstract class BatchHttpResource {
    @Inject @Context
    protected HttpServletRequest request;

    @Inject @Context
    protected HttpServletResponse response;

    @Inject @Context
    protected ServletContext context;

    protected <T> T getInstance(Class<T> instanceClass) {
        return getInjector().getInstance(instanceClass);
    }

    @SuppressWarnings("unchecked")
    protected Injector getInjector() {
        return (Injector) context.getAttribute(BatchHttpServer.ATTR_BATCH_INJECTOR);
    }
}
