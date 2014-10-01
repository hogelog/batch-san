package org.hogel.batchsan.web.rs.resource;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import org.hogel.batchsan.core.db.table.JobRecipeTable;
import org.hogel.batchsan.core.db.table.record.JobRecipeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/job_recipe")
@Produces("text/html")
public class JobRecipeResource extends BatchHttpResource {
    private static final Logger LOG = LoggerFactory.getLogger(JobRecipeResource.class);

    private JobRecipeTable jobRecipeTable = new JobRecipeTable();

    @GET
    public String index() throws SQLException, IOException {
        StringBuilder builder = new StringBuilder("## job recipes");
        try (Connection connection = getDbConnection()) {
            List<JobRecipeRecord> jobRecipeRecords = jobRecipeTable.getAll(connection);
            Map<String, Object> params = ImmutableMap.of("jobRecipes", (Object) jobRecipeRecords);
            return template("job_recipe/index", params);
        }
    }

    @GET
    @Path("/{id}")
    public String show(@PathParam("id") long id) throws IOException, SQLException {
        try (Connection connection = getDbConnection()) {
            Optional<JobRecipeRecord> jobRecipeRecord = jobRecipeTable.get(connection, id);
            if (jobRecipeRecord.isPresent()) {
                Map<String, Object> params = ImmutableMap.of("jobRecipe", (Object) jobRecipeRecord.get());
                return template("job_recipe/show", params);
            } else {
                return template("404");
            }
        }
    }
}
