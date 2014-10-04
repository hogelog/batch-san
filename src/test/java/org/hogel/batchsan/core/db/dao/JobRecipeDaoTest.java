package org.hogel.batchsan.core.db.dao;

import com.google.inject.Inject;
import org.hogel.batchsan.core.db.table.record.JobRecipeRecord;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class JobRecipeDaoTest extends DaoTestCase {
    @Inject
    JobRecipeDao jobRecipeDao;

    @Test
    public void read() throws Exception {
        JobRecipeRecord record = jobRecipeDao.queryForId(0L);
        assertThat(record, is(nullValue()));
    }
}
