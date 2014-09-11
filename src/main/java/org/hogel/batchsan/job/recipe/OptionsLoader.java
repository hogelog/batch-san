package org.hogel.batchsan.job.recipe;

import org.hogel.config.loader.AttributeLoader;
import org.hogel.config.loader.InvalidAttributeException;

import java.lang.annotation.Annotation;
import java.util.Map;

public class OptionsLoader implements AttributeLoader<Options> {
    @Override
    public Options load(Annotation[] annotations, Object source) throws InvalidAttributeException {
        return new Options((Map<String, Object>) source);
    }
}
