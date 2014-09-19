package org.hogel.batchsan.web.rs.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/job")
public class JobResource {
    @GET
    @Produces("text/html")
    public String jobs() {
        return "listing jobs";
    }

    @GET
    @Produces("text/html")
    @Path("/{jobName}")
    public String job(@PathParam("jobName") String jobName) {
        return "show job detail: " + jobName;
    }
}
