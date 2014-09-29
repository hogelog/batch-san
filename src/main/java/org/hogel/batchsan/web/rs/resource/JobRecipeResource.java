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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/job_recipe")
public class JobRecipeResource extends BatchHttpResource {
    private static final Logger LOG = LoggerFactory.getLogger(JobRecipeResource.class);

    private JobRecipeTable jobRecipeTable = new JobRecipeTable();

    @GET
    @Produces("text/html")
    public String jobRecipes() throws SQLException {
        StringBuilder builder = new StringBuilder("## job recipes");
        try (Connection connection = getDbConnection()) {
            List<JobRecipeRecord> jobRecipeRecords = jobRecipeTable.getAll(connection);
            Map<String, Object> params = ImmutableMap.of("job_recipes", (Object) jobRecipeRecords);
            return template("job_recipe", params);
        }
    }

    @GET
    @Produces("text/html")
    @Path("/{id}")
    public String jobRecipe(@PathParam("id") long id) {
        try (Connection connection = getDbConnection()) {
            Optional<JobRecipeRecord> jobRecipeRecord = jobRecipeTable.get(connection, id);
            if (jobRecipeRecord.isPresent()) {
                return jobRecipeRecord.get().getRecipe();
            } else {
                return "not found";
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return e.getMessage();
        }
    }
}
