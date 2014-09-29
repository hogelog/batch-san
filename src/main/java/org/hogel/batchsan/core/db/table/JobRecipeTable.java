package org.hogel.batchsan.core.db.table;

import com.google.common.base.Optional;
import org.hogel.batchsan.core.db.table.record.JobRecipeRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JobRecipeTable extends BatchDatabaseTable {
    public Optional<JobRecipeRecord> get(Connection connection, long id) throws SQLException {
        return query(connection, "SELECT * FROM `job_recipe` WHERE `id` = ? LIMIT 1;", JobRecipeRecord.class, id);
    }

    public List<JobRecipeRecord> getAll(Connection connection) throws SQLException {
        return queryAll(connection, "SELECT * FROM `job_recipe`;", JobRecipeRecord.class);
    }

    public void truncate(Connection connection) throws SQLException {
        update(connection, "TRUNCATE TABLE `job_recipe`;");
    }
}
