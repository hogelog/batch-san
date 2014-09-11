package org.hogel.batchsan.recipe;

import lombok.Data;
import org.hogel.config.Config;
import org.hogel.config.annotation.Attribute;

import java.util.List;

@Data
public class JobRecipe extends Config {
    @Attribute
    String job;

    @Attribute(loader = OptionsLoader.class)
    Options options;

    @Attribute
    List<Object> params;

    public JobRecipe() {
    }
}
