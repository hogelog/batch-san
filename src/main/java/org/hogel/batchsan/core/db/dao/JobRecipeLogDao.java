package org.hogel.batchsan.core.db.dao;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.j256.ormlite.support.ConnectionSource;
import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.hogel.batchsan.core.job.recipe.JobRecipe;

import java.sql.SQLException;

public class JobRecipeLogDao extends BatchDatabaseDao<JobRecipeLogRecord, Long>  {
    public static final int PROGRESS = 0;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    @Inject
    public JobRecipeLogDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, JobRecipeLogRecord.class);
    }

    public JobRecipeLogRecord create(String job) throws SQLException {
        JobRecipeLogRecord record = new JobRecipeLogRecord(job);
        create(record);
        return record;
    }

    public JobRecipeLogRecord create(JobRecipe jobRecipe) throws SQLException {
        JobRecipeLogRecord record = new JobRecipeLogRecord(jobRecipe.getJob());
        Optional<Long> jobRecipeId = jobRecipe.getJobRecipeId();
        if (jobRecipeId.isPresent()) {
            record.setJobRecipeId(jobRecipeId.get());
        }
        create(record);
        return record;
    }

    public void update(long id, int status) throws SQLException {
        JobRecipeLogRecord record = new JobRecipeLogRecord();
        record.setId(id);
        record.setStatus(status);
        update(record);
    }
}
