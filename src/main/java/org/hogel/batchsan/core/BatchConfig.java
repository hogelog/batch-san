package org.hogel.batchsan.core;

import lombok.Data;
import org.hogel.config.Config;
import org.hogel.config.annotation.Attribute;
import org.hogel.config.annotation.IntDefaultValue;
import org.hogel.config.annotation.StringDefaultValue;

@Data
public class BatchConfig extends Config {
    @Attribute
    @StringDefaultValue("localhost")
    String redisHost;

    @Attribute
    @IntDefaultValue(6379)
    int redisPort;

    @Attribute
    @IntDefaultValue(10)
    int redisTimeout;

    @Attribute
    @StringDefaultValue("batchsan")
    String redisNamespace;

    @Attribute
    @IntDefaultValue(5)
    int jobWorkers;

    @Attribute
    @StringDefaultValue("localhost")
    String webHost;

    @Attribute
    @IntDefaultValue(8080)
    int webPort;

    @Attribute
    @StringDefaultValue("org.h2.Driver")
    String databaseDriver;

    @Attribute
    @StringDefaultValue("jdbc:h2:./batch")
    String databaseUrl;

    @Attribute
    @StringDefaultValue("username")
    String databaseUsername;

    @Attribute
    @StringDefaultValue("password")
    String databasePassword;
}
