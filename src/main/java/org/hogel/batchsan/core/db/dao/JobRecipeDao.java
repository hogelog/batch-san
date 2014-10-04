package org.hogel.batchsan.core.db.dao;

import com.google.inject.Inject;
import com.j256.ormlite.support.ConnectionSource;
import org.hogel.batchsan.core.db.table.record.JobRecipeRecord;

import java.sql.SQLException;

public class JobRecipeDao extends BatchDatabaseDao<JobRecipeRecord, Long> {
    @Inject
    public JobRecipeDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, JobRecipeRecord.class);
    }
}
