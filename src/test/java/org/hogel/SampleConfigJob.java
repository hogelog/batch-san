package org.hogel;

import com.google.inject.Inject;
import org.hogel.batchsan.BasicJobResult;
import org.hogel.batchsan.BatchJob;
import org.hogel.batchsan.JobResult;
import org.hogel.batchsan.annotation.BatchJobName;
import org.hogel.batchsan.recipe.JobRecipe;
import org.hogel.config.SampleConfig;

@BatchJobName("sample_config")
public class SampleConfigJob extends  BatchJob {

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
