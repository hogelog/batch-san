package org.hogel.batchsan.config;

import lombok.Data;
import org.hogel.config.Config;
import org.hogel.config.annotation.Attribute;
import org.hogel.config.annotation.IntDefaultValue;

@Data
public class SampleConfig extends Config {
    @Attribute
    int sleep;

    @Attribute
    @IntDefaultValue(3)
    int retry;
}
