package org.hogel.batchsan.web.rs;

import org.hogel.batchsan.web.rs.resource.CssResource;
import org.hogel.batchsan.web.rs.resource.JobRecipeResource;
import org.hogel.batchsan.web.rs.resource.RootResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class BatchHttpApplication extends Application {
    public static final Set<Class<?>> RESOURCE_CLASSES = new HashSet<>();

    static {
        RESOURCE_CLASSES.add(RootResource.class);
        RESOURCE_CLASSES.add(JobRecipeResource.class);
        RESOURCE_CLASSES.add(CssResource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return RESOURCE_CLASSES;
    }
}
