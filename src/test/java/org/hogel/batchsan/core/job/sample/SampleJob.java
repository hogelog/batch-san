package org.hogel.batchsan.core.job.sample;

import com.google.common.base.Joiner;
import org.hogel.batchsan.core.annotation.BatchJobName;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.BasicJobResult;
import org.hogel.batchsan.core.job.result.JobResult;

@BatchJobName("sample")
public class SampleJob extends BatchJob {
    public SampleJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        int timeout = options.getInt("timeout");
        return BasicJobResult.success(String.format("timeout: %d, params: [%s]", timeout, Joiner.on(',').join(params)));
    }
}
