package org.hogel.batchsan.core.job.sample;

import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.TestBatchConfig;
import org.hogel.batchsan.core.job.BatchJob;
import org.junit.Before;

public abstract class JobTestCase {
    protected TestBatchConfig config;

    protected BatchJobManager jobManager;

    @Before
    public void setUp() throws Exception {
        config = new TestBatchConfig();
        config.load("{}");
        jobManager = new BatchJobManager(config);
        jobManager.registerJobClass(getJobClass());
    }

    public abstract Class<? extends BatchJob> getJobClass();
}
