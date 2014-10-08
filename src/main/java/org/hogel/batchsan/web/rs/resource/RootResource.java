package org.hogel.batchsan.web.rs.resource;

import org.hogel.batchsan.web.utils.JadeUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;

@Path("/")
@Produces("text/html")
public class RootResource extends BatchHttpResource {
    @GET
    public String root() throws IOException {
        return JadeUtils.template("root");
    }
}
