package org.hogel.batchsan.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hogel.config.annotation.Attribute;
import org.hogel.config.annotation.StringDefaultValue;

@Data
@EqualsAndHashCode(callSuper = false)
public class TestBatchConfig extends BatchConfig {
    @Attribute
    @StringDefaultValue("jdbc:mysql://localhost/batchsan_test")
    String databaseUrl;

    @Attribute
    @StringDefaultValue("batchsan-test")
    String redisNamespace;
}
