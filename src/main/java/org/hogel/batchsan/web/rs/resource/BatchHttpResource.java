package org.hogel.batchsan.web.rs.resource;

import com.google.inject.Injector;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.web.BatchHttpServer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

public abstract class BatchHttpResource {
    protected static final TemplateEngine TEMPLATE = new TemplateEngine();

    static {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        TEMPLATE.setTemplateResolver(templateResolver);
    }

    @Inject @Context
    protected HttpServletRequest request;

    @Inject @Context
    protected HttpServletResponse response;

    @Inject @Context
    protected ServletContext context;

    protected Connection getDbConnection() throws SQLException {
        return getDbConnections().getConnection();
    }

    protected BoneCP getDbConnections() {
        return getInjector().getInstance(BoneCP.class);
    }

    @SuppressWarnings("unchecked")
    protected Injector getInjector() {
        return (Injector) context.getAttribute(BatchHttpServer.ATTR_BATCH_INJECTOR);
    }

    protected String template(String templateName, Map<String, Object> params) {
        WebContext webContext = new WebContext(request, response, context, Locale.JAPAN, params);
        return TEMPLATE.process(templateName, webContext);
    }
}
