package org.hogel.batchsan.core.db.table;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.TestBatchConfig;
import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class JobRecipeLogTableTest {
    private TestBatchConfig config;

    @Inject
    JobRecipeLogTable jobRecipeLogTable;

    @Inject
    BoneCP connections;

    @Before
    public void before() throws Exception {
        config = new TestBatchConfig();
        config.load("sleep: 3000");
        BatchJobManager jobManager = new BatchJobManager(config);
        Injector injector = jobManager.getInjector();
        injector.injectMembers(this);
    }

    @Test
    public void writeRead() throws Exception {
        try (Connection connection = connections.getConnection()) {
            long id = jobRecipeLogTable.insertLog(connection, "test");
            JobRecipeLogRecord record = jobRecipeLogTable.get(connection, id);
            assertThat(record.getJob(), is("test"));
            assertThat(record.getCreated_at(), is(notNullValue()));
        }
    }
}
