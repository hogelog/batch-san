package org.hogel.batchsan.core.job.sample;

import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.BasicJobResult;
import org.hogel.batchsan.core.job.result.JobResult;
import org.hogel.batchsan.test.config.SampleConfig;

public class SampleConfigJob extends BatchJob {
    public SampleConfigJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        SampleConfig config = (SampleConfig) super.config;
        return BasicJobResult.success("sleep: " + config.getSleep() + ", retry: " + config.getRetry());
    }
}
