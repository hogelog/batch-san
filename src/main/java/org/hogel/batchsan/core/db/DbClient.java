package org.hogel.batchsan.core.db;

import com.google.inject.Inject;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DbClient {
    @Inject
    protected QueryRunner queryRunner;

    public int update(Connection connection, String sql, Object... params) throws SQLException {
        return queryRunner.update(connection, sql, params);
    }

    public long updateAndIdentity(Connection connection, String sql, Object... params) throws SQLException {
        int updates = queryRunner.update(connection, sql, params);
        if (updates == 1) {
            return identity(connection);
        }
        return 0;
    }

    public long identity(Connection connection) throws SQLException {
        return (long) query(connection, "CALL IDENTITY();")[0];
    }

    public <T> T query(Connection connection, String sql, Class<T> klass, Object... params) throws SQLException {
        return queryRunner.query(connection, sql, new BeanHandler<>(klass), params);
    }

    public Object[] query(Connection connection, String sql, Object... params) throws SQLException {
        return queryRunner.query(connection, sql, new ArrayHandler(), params);
    }

    public <T> List<T> queryAll(Connection connection, String sql, Class<T> klass, Object... params) throws SQLException {
        return queryRunner.query(connection, sql, new BeanListHandler<>(klass), params);
    }

    public List<Object[]> queryAll(Connection connection, String sql, Object... params) throws SQLException {
        return queryRunner.query(connection, sql, new ArrayListHandler(), params);
    }
}
