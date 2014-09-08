package org.hogel.batchsan;

import com.google.inject.Inject;
import lombok.Data;
import org.hogel.batchsan.recipe.JobRecipe;
import org.hogel.batchsan.recipe.Options;

import java.util.List;

@Data
public abstract class BatchJob {
    private final JobRecipe recipe;

    private final Options options;

    private final List<Object> params;

    @Inject
    public BatchJob(JobRecipe recipe) {
        this.recipe = recipe;
        options = recipe.getOptions();
        params = recipe.getParams();
    }


    public abstract JobResult run() throws Exception;
}
