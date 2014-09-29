package org.hogel.batchsan.core.db.table;

import com.google.common.base.Optional;
import com.google.inject.Injector;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.TestBatchConfig;
import org.hogel.batchsan.core.db.table.record.JobRecipeRecord;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.sql.Connection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JobRecipeTableTest {
    private TestBatchConfig config;

    @Inject
    JobRecipeTable jobRecipeTable;

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
    public void read() throws Exception {
        try (Connection connection = connections.getConnection()) {
            Optional<JobRecipeRecord> record = jobRecipeTable.get(connection, 0);
            assertThat(record.isPresent(), is(false));
        }
    }
}
