package org.hogel.batchsan.core.job.sample;

import com.google.inject.Inject;
import org.hogel.batchsan.core.db.dao.JobRecipeLogDao;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.BasicJobResult;
import org.hogel.batchsan.core.job.result.JobResult;

public class JobRecipeLogTestJob extends BatchJob {
    @Inject
    JobRecipeLogDao dao;

    public JobRecipeLogTestJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        dao.truncate();
        dao.create(recipe.getJob());
        long count = dao.countOf();
        return BasicJobResult.success("count: " + count);
    }
}
