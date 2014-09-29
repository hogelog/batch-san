package org.hogel.batchsan.web.rs.resource;

import com.google.inject.Injector;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.web.BatchHttpServer;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BatchHttpResource {
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
}
