package org.hogel.batchsan.core.job.sample;

import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.JobResult;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JobRecipeLogTestJobTest extends JobTestCase {
    private JobRecipe recipe;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        recipe = new JobRecipe();
        recipe.load("job: job_recipe_log_test");
    }

    @Override
    public Class<? extends BatchJob> getJobClass() {
        return JobRecipeLogTestJob.class;
    }

    @Test
    public void run() throws Exception {
        JobRecipeLogTestJob job = jobManager.createBatchJob(recipe);
        JobResult result = job.run();

        assertThat(result.getBody(), is("count: 1"));
    }
}
