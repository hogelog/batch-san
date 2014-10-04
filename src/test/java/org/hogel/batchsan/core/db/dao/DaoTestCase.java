package org.hogel.batchsan.core.db.dao;

import com.google.inject.Injector;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.TestBatchConfig;
import org.junit.Before;

public abstract class DaoTestCase {
    protected TestBatchConfig config;

    @Before
    public void before() throws Exception {
        config = new TestBatchConfig();
        config.load("{}");
        BatchJobManager jobManager = new BatchJobManager(config);
        Injector injector = jobManager.getInjector();
        injector.injectMembers(this);
    }
}
