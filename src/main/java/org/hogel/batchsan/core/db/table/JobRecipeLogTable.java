package org.hogel.batchsan.core.db.table;

import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.hogel.batchsan.core.job.recipe.JobRecipe;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JobRecipeLogTable extends BatchDatabaseTable {
    public static final int PROGRESS = 0;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    public long insertLog(Connection connection, JobRecipe jobRecipe) throws SQLException {
        String job = jobRecipe.getJob();
        return updateAndIdentity(
            connection,
            "INSERT INTO `job_recipe_log` (`job`, `created_at`, `updated_at`) VALUES (?, NOW(), NOW())",
            job
        );
    }

    public void updateLogStatus(Connection connection, long logId, int status) throws SQLException {
        update(
            connection,
            "UPDATE `job_recipe_log` SET `status` = ? WHERE `id` = ?",
            logId,
            status
        );
    }

    public JobRecipeLogRecord get(Connection connection, long id) throws SQLException {
        return query(connection, "SELECT * FROM `job_recipe_log` WHERE `id` = ? LIMIT 1;", JobRecipeLogRecord.class, id);
    }

    public List<JobRecipeLogRecord> getAll(Connection connection) throws SQLException {
        return queryAll(connection, "SELECT * FROM `job_recipe_log`;", JobRecipeLogRecord.class);
    }

    public void truncate(Connection connection) throws SQLException {
        update(connection, "TRUNCATE TABLE `job_recipe_log`;");
    }
}
