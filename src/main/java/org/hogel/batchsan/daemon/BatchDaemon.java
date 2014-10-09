package org.hogel.batchsan.daemon;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchJobManager;
import org.hogel.batchsan.core.db.dao.JobRecipeLogDao;
import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.queue.JobQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchDaemon {
    private static final Logger LOG = LoggerFactory.getLogger(BatchDaemon.class);

    @Inject
    BatchConfig config;

    @Inject
    JobRecipeLogDao jobRecipeLogDao;

    @Inject
    JobQueue jobQueue;

    private final BatchJobManager batchJobManager;

    private final ExecutorService jobExecutor;

    public BatchDaemon(BatchJobManager batchJobManager) {
        this.batchJobManager = batchJobManager;

        Injector injector = batchJobManager.getInjector();
        injector.injectMembers(this);

        jobExecutor = Executors.newFixedThreadPool(config.getJobWorkers());
    }

    public void start() {
        while (true) {
            try {
                final Optional<String> recipe = jobQueue.dequeue();
                if (recipe.isPresent()) {
                    jobExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            executeJob(recipe.get());
                        }
                    });
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public void executeJob(final String recipe) {
        try {
            JobRecipe jobRecipe = JobRecipe.loadRecipe(recipe);
            BatchJob batchJob = batchJobManager.createBatchJob(jobRecipe);
            JobRecipeLogRecord logRecord = jobRecipeLogDao.create(jobRecipe);
            try {
                batchJob.run();
                logRecord.setStatus(JobRecipeLogRecord.SUCCESS);
                jobRecipeLogDao.update(logRecord);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                logRecord.setStatus(JobRecipeLogRecord.FAILURE);
                jobRecipeLogDao.update(logRecord);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
