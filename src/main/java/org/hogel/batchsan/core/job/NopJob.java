package org.hogel.batchsan.core.job;

import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.BasicJobResult;
import org.hogel.batchsan.core.job.result.JobResult;

public class NopJob extends BatchJob {
    public NopJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        return BasicJobResult.success("");
    }
}
