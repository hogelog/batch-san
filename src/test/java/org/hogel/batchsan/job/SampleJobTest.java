package org.hogel.batchsan.job;

import org.hogel.batchsan.BatchJobManager;
import org.hogel.batchsan.job.recipe.JobRecipe;
import org.hogel.batchsan.job.result.JobResult;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SampleJobTest {
    private BatchJobManager jobManager;

    private JobRecipe recipe;

    @Before
    public void setUp() throws Exception {
        jobManager = new BatchJobManager();
        jobManager.registerJobClass(SampleJob.class);

        recipe = new JobRecipe();
        recipe.load(Paths.get("src/test/resources/sample-recipe.yaml"));
    }

    @Test
    public void run() throws Exception {
        SampleJob job = jobManager.createBatchJob(recipe);
        JobResult result = job.run();

        assertThat(result.getBody(), is("timeout: 1000, params: [10,20]"));
    }
}
