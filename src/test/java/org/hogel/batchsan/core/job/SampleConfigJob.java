package org.hogel.batchsan.core.job;

import com.google.inject.Inject;
import org.hogel.batchsan.test.config.SampleConfig;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.BasicJobResult;
import org.hogel.batchsan.core.job.result.JobResult;

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
