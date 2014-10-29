package org.hogel.batchsan.core.db.dao;

import com.google.inject.Inject;
import com.j256.ormlite.support.ConnectionSource;
import org.hogel.batchsan.core.db.table.record.S3BackupRecord;

import java.sql.SQLException;

public class S3BackupDao extends BatchDatabaseDao<S3BackupRecord, Long> {
    @Inject
    public S3BackupDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, S3BackupRecord.class);
    }
}
