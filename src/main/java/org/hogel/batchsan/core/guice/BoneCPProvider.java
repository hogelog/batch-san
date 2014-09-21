package org.hogel.batchsan.core.guice;

import com.google.inject.Inject;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.hogel.batchsan.core.BatchConfig;

import javax.inject.Provider;
import java.sql.SQLException;

public class BoneCPProvider implements Provider<BoneCP> {
    @Inject
    BatchConfig config;

    @Override
    public BoneCP get() {
        try {
            Class.forName(config.getDatabaseDriver());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        BoneCPConfig boneCpConfig = new BoneCPConfig();
        boneCpConfig.setJdbcUrl(config.getDatabaseUrl());
        boneCpConfig.setUsername(config.getDatabaseUsername());
        boneCpConfig.setPassword(config.getDatabasePassword());
        try {
            return new BoneCP(boneCpConfig);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
