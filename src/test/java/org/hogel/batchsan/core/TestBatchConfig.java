package org.hogel.batchsan.core;

import lombok.Data;
import org.hogel.config.annotation.Attribute;
import org.hogel.config.annotation.StringDefaultValue;

@Data
public class TestBatchConfig extends BatchConfig {
    @Attribute
    @StringDefaultValue("jdbc:h2:./target/test")
    String databaseUrl;

    @Attribute
    @StringDefaultValue("batchsan-test")
    String redisNamespace;
}
