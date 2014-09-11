package org.hogel.batchsan.job;

import com.google.common.base.Joiner;
import org.hogel.batchsan.annotation.BatchJobName;
import org.hogel.batchsan.job.recipe.JobRecipe;
import org.hogel.batchsan.job.recipe.Options;
import org.hogel.batchsan.job.result.BasicJobResult;
import org.hogel.batchsan.job.result.JobResult;

@BatchJobName("sample")
public class SampleJob extends BatchJob {
    int count = 0;

    public SampleJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        String params = String.format("[%s]", Joiner.on(',').join(getParams()));
        Options options = getOptions();
        int timeout = options.getInt("timeout");
        return BasicJobResult.success("timeout: " + timeout + ", params: " + params);
    }
}
