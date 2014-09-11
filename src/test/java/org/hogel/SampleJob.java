package org.hogel;

import com.google.common.base.Joiner;
import org.hogel.batchsan.BasicJobResult;
import org.hogel.batchsan.BatchJob;
import org.hogel.batchsan.JobResult;
import org.hogel.batchsan.annotation.BatchJobName;
import org.hogel.batchsan.recipe.JobRecipe;
import org.hogel.batchsan.recipe.Options;

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
