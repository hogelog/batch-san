package org.hogel.config;

import lombok.Data;
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
