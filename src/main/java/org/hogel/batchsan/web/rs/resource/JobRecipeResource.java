package org.hogel.batchsan.web.rs.resource;

import com.google.common.collect.ImmutableMap;
import org.hogel.batchsan.core.db.dao.JobRecipeDao;
import org.hogel.batchsan.core.db.table.record.JobRecipeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/job_recipe")
@Produces("text/html")
public class JobRecipeResource extends BatchHttpResource {
    private static final Logger LOG = LoggerFactory.getLogger(JobRecipeResource.class);

    @GET
    public String index() throws SQLException, IOException {
        JobRecipeDao dao = getInstance(JobRecipeDao.class);
        List<JobRecipeRecord> jobRecipeRecords = dao.queryForAll();
        Map<String, Object> params = ImmutableMap.of("jobRecipes", (Object) jobRecipeRecords);
        return template("job_recipe/index", params);
    }

    @GET
    @Path("/{id}")
    public String show(@PathParam("id") long id) throws IOException, SQLException {
        JobRecipeDao dao = getInstance(JobRecipeDao.class);
        JobRecipeRecord jobRecipeRecord = dao.queryForId(id);
        if (jobRecipeRecord != null) {
            Map<String, Object> params = ImmutableMap.of("jobRecipe", (Object) jobRecipeRecord);
            return template("job_recipe/show", params);
        } else {
            return template("404");
        }
    }
}
