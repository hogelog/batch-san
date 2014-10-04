package org.hogel.batchsan.web.rs.resource;

import com.google.inject.Injector;
import de.neuland.jade4j.Jade4J;
import org.hogel.batchsan.web.BatchHttpServer;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public abstract class BatchHttpResource {
    private static final String TEMPLATE_PREFIX = "/templates/";
    private static final String TEMPLATE_SUFFIX = ".jade";

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

    protected String template(String templateName) throws IOException {
        String resourceName = TEMPLATE_PREFIX + templateName + TEMPLATE_SUFFIX;
        return Jade4J.render(getClass().getResource(resourceName).getFile(), Collections.<String, Object>emptyMap());
    }

    protected String template(String templateName, Map<String, Object> params) throws IOException {
        String resourceName = TEMPLATE_PREFIX + templateName + TEMPLATE_SUFFIX;
        return Jade4J.render(getClass().getResource(resourceName).getFile(), params);
    }
}
