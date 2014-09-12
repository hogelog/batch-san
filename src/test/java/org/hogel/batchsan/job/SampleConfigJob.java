package org.hogel.batchsan.job;

import com.google.inject.Inject;
import org.hogel.batchsan.config.SampleConfig;
import org.hogel.batchsan.job.recipe.JobRecipe;
import org.hogel.batchsan.job.result.BasicJobResult;
import org.hogel.batchsan.job.result.JobResult;

public class SampleConfigJob extends BatchJob {

    @Inject
    SampleConfig config;

    public SampleConfigJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        return BasicJobResult.success("sleep: " + config.getSleep() + ", retry: " + config.getRetry());
    }
}
