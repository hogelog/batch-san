package org.hogel.batchsan.web.rs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class RootResource {
    @GET
    @Produces("text/plain")
    public String root() {
        return "Hello, JAX-RS";
    }
}
