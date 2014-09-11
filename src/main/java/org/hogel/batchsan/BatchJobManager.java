package org.hogel.batchsan;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.Getter;
import org.hogel.batchsan.annotation.BatchJobName;
import org.hogel.batchsan.job.BatchJob;
import org.hogel.batchsan.job.recipe.JobRecipe;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class BatchJobManager {

    private final Map<String, Class<? extends BatchJob>> jobClasses = new HashMap<>();

    @Getter
    private final Injector injector;

    public BatchJobManager(Module... modules) {
        this.injector = Guice.createInjector(modules);
    }

    public synchronized void registerJobClass(Class<? extends BatchJob> jobClass) {
        BatchJobName jobNameAnnotation = jobClass.getAnnotation(BatchJobName.class);
        String name = jobNameAnnotation.value();
        if (jobClasses.containsKey(name)) {
            throw new IllegalArgumentException(String.format("%s is already registered job", name));
        }
        jobClasses.put(name, jobClass);
    }

    public synchronized void clearJobClasses() {
        jobClasses.clear();
    }

    @SuppressWarnings("unchecked")
    public <T extends BatchJob> T createBatchJob(JobRecipe recipe) {
        Class<? extends BatchJob> jobClass = jobClasses.get(recipe.getJob());
        try {
            Constructor<? extends BatchJob> constructor = jobClass.getConstructor(JobRecipe.class);
            BatchJob batchJob = constructor.newInstance(recipe);
            injector.injectMembers(batchJob);
            return (T) batchJob;
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
