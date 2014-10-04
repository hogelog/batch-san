package org.hogel.batchsan.core.db.table.record;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hogel.batchsan.core.db.dao.JobRecipeDao;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "job_recipe", daoClass = JobRecipeDao.class)
public class JobRecipeRecord implements BatchDatabaseRecord {
    @DatabaseField(generatedId = true)
    private long id;

    @NonNull
    @DatabaseField(canBeNull = false)
    private String recipe;

    @DatabaseField(canBeNull = false, readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(canBeNull = false, readOnly = true)
    private Timestamp updatedAt;
}
