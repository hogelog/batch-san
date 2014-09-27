package org.hogel.batchsan.daemon;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.TestBatchConfig;
import org.hogel.batchsan.core.db.table.JobRecipeLogTable;
import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BatchDaemonTest {
    @Inject
    JobRecipeLogTable jobRecipeLogTable;

    @Inject
    BoneCP connections;

    private TestBatchConfig config;

    private BatchJobManager batchJobManager;

    @Before
    public void before() throws Exception {
        config = new TestBatchConfig();
        config.load("{}");

        batchJobManager = new BatchJobManager(config);

        Injector injector = batchJobManager.getInjector();
        injector.injectMembers(this);
    }

    @Test
    public void test() throws Exception {
        BatchDaemon daemon = new BatchDaemon(batchJobManager);
        try (Connection connection = connections.getConnection()) {
            jobRecipeLogTable.truncate(connection);

            daemon.executeJob("job: nop");

            List<JobRecipeLogRecord> jobRecipeLogs = jobRecipeLogTable.getAll(connection);
            assertThat(jobRecipeLogs.get(0).getJob(), is("nop"));
            assertThat(jobRecipeLogs.get(0).getStatus(), is(JobRecipeLogTable.SUCCESS));
        }
    }

    @Test
    public void runWithJobRecipeId() throws Exception {
        BatchDaemon daemon = new BatchDaemon(batchJobManager);
        try (Connection connection = connections.getConnection()) {
            jobRecipeLogTable.truncate(connection);

            daemon.executeJob("{job: nop, options: {job_recipe_id: 10}}");

            List<JobRecipeLogRecord> jobRecipeLogs = jobRecipeLogTable.getAll(connection);
            assertThat(jobRecipeLogs.get(0).getJob(), is("nop"));
            assertThat(jobRecipeLogs.get(0).getStatus(), is(JobRecipeLogTable.SUCCESS));
            assertThat(jobRecipeLogs.get(0).getJob_recipe_id(), is(10L));
        }
    }
}
