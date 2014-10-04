package org.hogel.batchsan.core.db.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.hogel.batchsan.core.db.table.record.BatchDatabaseRecord;

import java.sql.SQLException;

public abstract class BatchDatabaseDao<T extends BatchDatabaseRecord, ID> extends BaseDaoImpl<T, ID> {
    public BatchDatabaseDao(ConnectionSource connectionSource, Class<T> recordClass) throws SQLException {
        super(connectionSource, recordClass);
    }

    public void truncate() throws SQLException {
        TableUtils.clearTable(connectionSource, getDataClass());
    }
}
