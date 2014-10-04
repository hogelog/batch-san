package org.hogel.batchsan.core.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.j256.ormlite.db.MysqlDatabaseType;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.hogel.batchsan.core.BatchConfig;

import java.sql.SQLException;

public class ConnectionSourceProvider implements Provider<ConnectionSource> {
    @Inject
    BatchConfig batchConfig;

    @Override
    public ConnectionSource get() {
        try {
            return new JdbcPooledConnectionSource(
                batchConfig.getDatabaseUrl(),
                batchConfig.getDatabaseUsername(),
                batchConfig.getDatabasePassword(),
                new MysqlDatabaseType()
            );
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
