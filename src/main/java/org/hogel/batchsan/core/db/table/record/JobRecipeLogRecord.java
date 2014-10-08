package org.hogel.batchsan.core.db.table.record;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hogel.batchsan.core.db.dao.JobRecipeLogDao;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "job_recipe_log", daoClass = JobRecipeLogDao.class)
public class JobRecipeLogRecord implements BatchDatabaseRecord {
    public static final int PROGRESS = 0;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    @DatabaseField(generatedId = true)
    private long id;

    @NonNull
    @DatabaseField(canBeNull = false)
    private String job;

    @DatabaseField(columnName = "job_recipe_id")
    private Long jobRecipeId;

    @DatabaseField
    private int status;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;

    public String getStatusName() {
        switch (status) {
            case PROGRESS:
                return "progress";
            case SUCCESS:
                return "success";
            case FAILURE:
                return "failure";
        }
        return "unknown";
    }

    public boolean isProgress() {
        return status == PROGRESS;
    }

    public boolean isSuccess() {
        return status == SUCCESS;
    }

    public boolean isFailure() {
        return status == FAILURE;
    }
}
