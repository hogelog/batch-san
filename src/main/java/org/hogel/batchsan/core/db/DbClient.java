package org.hogel.batchsan.core.db;

import com.google.common.base.Optional;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DbClient {
    protected QueryRunner queryRunner = new QueryRunner();

    public int update(Connection connection, String sql, Object... params) throws SQLException {
        return queryRunner.update(connection, sql, params);
    }

    public long updateAndLastInsertId(Connection connection, String sql, Object... params) throws SQLException {
        int updates = queryRunner.update(connection, sql, params);
        if (updates == 1) {
            return lastInsertId(connection);
        }
        return 0;
    }

    public long lastInsertId(Connection connection) throws SQLException {
        return ((Number) query(connection, "SELECT LAST_INSERT_ID();")[0]).longValue();
    }

    public <T> Optional<T> query(Connection connection, String sql, Class<T> klass, Object... params) throws SQLException {
        return Optional.fromNullable(queryRunner.query(connection, sql, new BeanHandler<>(klass), params));
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
