package org.hogel.batchsan.core.job.sample;

import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.JobResult;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SampleJobTest {
    private BatchConfig config;

    private BatchJobManager jobManager;

    private JobRecipe recipe;

    @Before
    public void setUp() throws Exception {
        config = new BatchConfig();
        config.load(Collections.EMPTY_MAP);

        jobManager = new BatchJobManager(config);
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
