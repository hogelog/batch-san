package org.hogel.batchsan.core.db.dao;

import com.google.inject.Inject;
import org.hogel.batchsan.core.db.table.record.JobRecipeLogRecord;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class JobRecipeLogDaoTest extends DaoTestCase {
    @Inject
    JobRecipeLogDao jobRecipeLogDao;

    @Test
    public void writeRead() throws Exception {
        long id = jobRecipeLogDao.create("test").getId();
        JobRecipeLogRecord record = jobRecipeLogDao.queryForId(id);
        assertThat(record.getJob(), is("test"));
        assertThat(record.getCreatedAt(), is(notNullValue()));
    }
}
