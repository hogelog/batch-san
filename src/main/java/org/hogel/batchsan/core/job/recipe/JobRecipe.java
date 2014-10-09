package org.hogel.batchsan.core.job.recipe;

import com.google.common.base.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hogel.config.Config;
import org.hogel.config.InvalidConfigException;
import org.hogel.config.annotation.Attribute;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class JobRecipe extends Config {
    public static String OPTION_JOB_RECIPE_ID = "job_recipe_id";

    private final Yaml yaml = new Yaml();

    @Attribute
    String job;

    @Attribute(loader = OptionsLoader.class)
    Options options;

    @Attribute
    List<Object> params;

    public JobRecipe() {
    }

    public static JobRecipe loadRecipe(String recipe) throws InvalidConfigException {
        JobRecipe jobRecipe = new JobRecipe();
        jobRecipe.load(recipe);
        return jobRecipe;
    }

    public Optional<Long> getJobRecipeId() {
        if (options == null) {
            return Optional.absent();
        }
        int jobRecipeId = options.getInt(OPTION_JOB_RECIPE_ID, 0);
        if (jobRecipeId == 0) {
            return Optional.absent();
        } else {
            return Optional.of(Long.valueOf(jobRecipeId));
        }
    }

    public void setJobRecipeId(long jobRecipeId) {
        if (options == null) {
            options = new Options();
        }
        options.put(OPTION_JOB_RECIPE_ID, jobRecipeId);
    }

    public String toRecipeString() {
        Map<String, Object> recipeMap = new LinkedHashMap<>(3);
        recipeMap.put("job", job);
        recipeMap.put("options", options);
        recipeMap.put("params", params);
        return yaml.dump(recipeMap);
    }
}
