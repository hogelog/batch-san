package org.hogel.batchsan.core.db.table.record;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hogel.batchsan.core.db.dao.S3BackupDao;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "s3_backup", daoClass = S3BackupDao.class)
public class S3BackupRecord implements BatchDatabaseRecord {

    @DatabaseField(generatedId = true)
    private long id;

    @NonNull
    @DatabaseField(canBeNull = false, columnName = "s3_bucket")
    private String s3Bucket;

    @NonNull
    @DatabaseField(canBeNull = false, columnName = "s3_path")
    private String s3Path;

    @NonNull
    @DatabaseField(canBeNull = false, columnName = "local_path")
    private String localPath;

    @NonNull
    @DatabaseField
    private int size;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;
}
