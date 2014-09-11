package org.hogel.batchsan.job;

import org.hogel.batchsan.BatchJobManager;
import org.hogel.batchsan.config.SampleConfig;
import org.hogel.batchsan.guice.SampleConfigModule;
import org.hogel.batchsan.job.recipe.JobRecipe;
import org.hogel.batchsan.job.result.JobResult;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SampleConfigJobTest {
    private SampleConfig config;

    private BatchJobManager jobManager;

    private JobRecipe recipe;

    @Before
    public void setUp() throws Exception {
        config = new SampleConfig();
        config.load("sleep: 3000");
        jobManager = new BatchJobManager(new SampleConfigModule(config));
        jobManager.registerJobClass(SampleConfigJob.class);

        recipe = new JobRecipe();
        recipe.load(Paths.get("src/test/resources/sample-config-recipe.yaml"));
    }

    @Test
    public void run() throws Exception {
        SampleConfigJob job = jobManager.createBatchJob(recipe);
        JobResult result = job.run();

        assertThat(result.getBody(), is("sleep: 3000, retry: 3"));
    }
}
