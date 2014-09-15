package org.hogel.batchsan.web.rs;

import org.hogel.batchsan.web.rs.resource.RootResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class BatchHttpApplication extends Application {
    private static final Set<Class<?>> APPLICATION_CLASSES = new HashSet<>();

    static {
        APPLICATION_CLASSES.add(RootResource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return APPLICATION_CLASSES;
    }
}
