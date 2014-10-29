package org.hogel.batchsan.core.db.dao;

import com.google.inject.Inject;
import org.hogel.batchsan.core.db.table.record.S3BackupRecord;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class S3BackupDaoTest extends DaoTestCase {
    @Inject
    S3BackupDao dao;

    @Override
    public void before() throws Exception {
        super.before();
        dao.truncate();
    }

    @Test
    public void 日本語テスト() throws Exception {
        S3BackupRecord newRecord = new S3BackupRecord("bucket", "s3/日本語.zip", "local/日本語.zip", 100);
        assertThat(dao.create(newRecord), is(1));
        S3BackupRecord readRecord = dao.queryForId(1L);
        assertThat(readRecord.getS3Bucket(), is("bucket"));
        assertThat(readRecord.getS3Path(), is("s3/日本語.zip"));
        assertThat(readRecord.getLocalPath(), is("local/日本語.zip"));
        assertThat(readRecord.getSize(), is(100));
    }
}
