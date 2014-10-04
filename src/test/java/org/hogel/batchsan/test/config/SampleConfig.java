package org.hogel.batchsan.test.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.config.annotation.Attribute;
import org.hogel.config.annotation.IntDefaultValue;

@Data
@EqualsAndHashCode(callSuper = false)
public class SampleConfig extends BatchConfig {
    @Attribute
    int sleep;

    @Attribute
    @IntDefaultValue(3)
    int retry;
}
