package org.hogel.batchsan.web.utils;

import de.neuland.jade4j.Jade4J;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JadeUtils {
    private static final String TEMPLATE_PREFIX = "/templates/";
    private static final String TEMPLATE_SUFFIX = ".jade";

    public static String template(String templateName) throws IOException {
        String resourceName = TEMPLATE_PREFIX + templateName + TEMPLATE_SUFFIX;
        return Jade4J.render(JadeUtils.class.getResource(resourceName).getFile(), Collections.<String, Object>emptyMap());
    }

    public static String template(String templateName, Map<String, Object> params) throws IOException {
        String resourceName = TEMPLATE_PREFIX + templateName + TEMPLATE_SUFFIX;
        return Jade4J.render(JadeUtils.class.getResource(resourceName).getFile(), params);
    }
}
