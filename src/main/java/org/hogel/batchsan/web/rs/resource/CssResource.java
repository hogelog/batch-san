package org.hogel.batchsan.web.rs.resource;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.InputStream;

@Path("/css")
@Produces("text/css")
public class CssResource extends BatchHttpResource {
    private static final String CSS_PREFIX = "/css/";

    @GET
    @Path("/{path}")
    public String get(@PathParam("path") String path) throws IOException {
        try (InputStream cssStream = getClass().getResourceAsStream(CSS_PREFIX + path)) {
            return IOUtils.toString(cssStream, "UTF-8");
        }
    }
}
