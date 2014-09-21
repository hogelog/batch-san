package org.hogel.batchsan.core.job;

import com.google.inject.Inject;
import lombok.Data;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.recipe.Options;
import org.hogel.batchsan.core.job.result.JobResult;

import java.util.List;

@Data
public abstract class BatchJob {
    protected final JobRecipe recipe;

    protected final Options options;

    protected final List<Object> params;

    @Inject
    protected BatchConfig config;

    @Inject
    public BatchJob(JobRecipe recipe) {
        this.recipe = recipe;
        options = recipe.getOptions();
        params = recipe.getParams();
    }

    public abstract JobResult run() throws Exception;
}
