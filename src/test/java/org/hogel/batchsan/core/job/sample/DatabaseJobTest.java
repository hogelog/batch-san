package org.hogel.batchsan.core.job.sample;

import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.TestBatchConfig;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.JobResult;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DatabaseJobTest {
    private TestBatchConfig config;

    private BatchJobManager jobManager;

    private JobRecipe recipe;

    @Before
    public void setUp() throws Exception {
        config = new TestBatchConfig();
        config.load("sleep: 3000");
        jobManager = new BatchJobManager(config);
        jobManager.registerJobClass(DatabaseJob.class);

        recipe = new JobRecipe();
        recipe.load("job: database");
    }

    @Test
    public void run() throws Exception {
        DatabaseJob job = jobManager.createBatchJob(recipe);
        JobResult result = job.run();

        assertThat(result.getBody(), is("count: 0"));
    }
}
