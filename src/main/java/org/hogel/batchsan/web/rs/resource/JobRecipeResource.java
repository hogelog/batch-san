package org.hogel.batchsan.web.rs.resource;

import com.google.common.collect.ImmutableMap;
import org.hogel.batchsan.core.db.dao.JobRecipeDao;
import org.hogel.batchsan.core.db.dao.JobRecipeLogDao;
import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.hogel.batchsan.core.db.table.record.JobRecipeRecord;
import org.hogel.batchsan.web.utils.JadeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/job_recipe")
@Produces("text/html;charset=UTF-8")
public class JobRecipeResource extends BatchHttpResource {
    private static final Logger LOG = LoggerFactory.getLogger(JobRecipeResource.class);

    @GET
    public String index() throws SQLException, IOException {
        JobRecipeDao dao = getInstance(JobRecipeDao.class);
        List<JobRecipeRecord> jobRecipeRecords = dao.queryForAll();
        Map<String, Object> params = ImmutableMap.of("jobRecipes", (Object) jobRecipeRecords);
        return JadeUtils.template("job_recipe/index", params);
    }

    @GET
    @Path("/add")
    public String add() throws IOException {
        return JadeUtils.template("job_recipe/add");
    }

    @GET
    @Path("/{id}")
    public String show(@PathParam("id") long id) throws IOException, SQLException {
        JobRecipeDao dao = getInstance(JobRecipeDao.class);
        JobRecipeRecord jobRecipeRecord = dao.queryForId(id);
        if (jobRecipeRecord == null) {
            return JadeUtils.template("404");
        }
        JobRecipeLogDao logDao = getInstance(JobRecipeLogDao.class);
        List<JobRecipeLogRecord> jobRecipeLogRecords = logDao.queryForJobRecipeId(id);

        Map<String, Object> params = ImmutableMap.of(
            "jobRecipe", (Object) jobRecipeRecord,
            "jobRecipeLogs", (Object) jobRecipeLogRecords
        );
        return JadeUtils.template("job_recipe/show", params);
    }

    @POST
    public void create(@FormParam("name") String name, @FormParam("recipe") String recipe) throws SQLException, IOException {
        JobRecipeDao dao = getInstance(JobRecipeDao.class);
        JobRecipeRecord jobRecipeRecord = new JobRecipeRecord(name, recipe);
        dao.create(jobRecipeRecord);
        response.sendRedirect("/job_recipe/" + jobRecipeRecord.getId());
    }

    @GET
    @Path("/{id}/edit")
    public String edit(@PathParam("id") long id) throws IOException, SQLException {
        JobRecipeDao dao = getInstance(JobRecipeDao.class);
        JobRecipeRecord jobRecipeRecord = dao.queryForId(id);
        if (jobRecipeRecord == null) {
            return JadeUtils.template("404");
        }
        Map<String, Object> params = ImmutableMap.of("jobRecipe", (Object) jobRecipeRecord);
        return JadeUtils.template("job_recipe/edit", params);
    }

    @POST
    @Path("/{id}/update")
    public void update(
        @PathParam("id") long id,
        @FormParam("name") String name,
        @FormParam("recipe") String recipe
    ) throws SQLException, IOException {
        JobRecipeDao dao = getInstance(JobRecipeDao.class);
        JobRecipeRecord jobRecipeRecord = dao.queryForId(id);
        jobRecipeRecord.setName(name);
        jobRecipeRecord.setRecipe(recipe);
        dao.update(jobRecipeRecord);
        response.sendRedirect("/job_recipe/" + id);
    }

    @POST
    @Path("/{id}/launch")
    public void launch(@PathParam("id") long id) throws SQLException, IOException {
//        JobRecipeDao dao = getInstance(JobRecipeDao.class);
//        JobRecipeRecord jobRecipeRecord = dao.queryForId(id);
//        dao.update(jobRecipeRecord);
//        jobQueue.postJob(JobRecipe.fromRecord(jobRecipeRecord));
        response.sendRedirect("/job_recipe/" + id);
    }
}
