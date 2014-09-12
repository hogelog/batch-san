package org.hogel.batchsan;

import com.google.common.base.CaseFormat;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.Getter;
import org.hogel.batchsan.annotation.BatchJobName;
import org.hogel.batchsan.job.BatchJob;
import org.hogel.batchsan.job.NopJob;
import org.hogel.batchsan.job.recipe.JobRecipe;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BatchJobManager {

    public static final Class<? extends BatchJob>[] BASIC_JOB_CLASSES = new Class[] {
        NopJob.class,
    };

    private static final Pattern JOB_CLASS_SUFFIX_PATTERN = Pattern.compile("_job$");

    private final Map<String, Class<? extends BatchJob>> jobClasses = new HashMap<>();

    @Getter
    private final Injector injector;

    public BatchJobManager(Module... modules) {
        this(Guice.createInjector(modules));
    }

    public BatchJobManager(List<Module> modules) {
        this(Guice.createInjector(modules));
    }

    public BatchJobManager(Injector injector) {
        this.injector = injector;
        for (Class<? extends BatchJob> basicJobClass : BASIC_JOB_CLASSES) {
            registerJobClass(basicJobClass);
        }
    }

    public synchronized void registerJobClass(Class<? extends BatchJob> jobClass) {
        String name;
        BatchJobName jobNameAnnotation = jobClass.getAnnotation(BatchJobName.class);
        if (jobNameAnnotation != null) {
            name = jobNameAnnotation.value();
        } else {
            String className = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, jobClass.getSimpleName());
            name = JOB_CLASS_SUFFIX_PATTERN.matcher(className).replaceFirst("");
        }
        if (jobClasses.containsKey(name)) {
            throw new IllegalArgumentException(String.format("%s is already registered job", name));
        }
        jobClasses.put(name, jobClass);
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
