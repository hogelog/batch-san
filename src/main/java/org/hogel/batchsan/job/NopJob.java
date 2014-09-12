package org.hogel.batchsan.job;

import org.hogel.batchsan.job.recipe.JobRecipe;
import org.hogel.batchsan.job.result.BasicJobResult;
import org.hogel.batchsan.job.result.JobResult;

public class NopJob extends BatchJob {
    public NopJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        return BasicJobResult.success("");
    }
}
