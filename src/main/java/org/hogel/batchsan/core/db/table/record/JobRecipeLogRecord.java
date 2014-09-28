package org.hogel.batchsan.core.db.table.record;

import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
public class JobRecipeLogRecord {
    private long id;

    private String job;

    private Long job_recipe_id;

    private int status;

    @Getter
    private Timestamp created_at;

    private Timestamp updated_at;
}
