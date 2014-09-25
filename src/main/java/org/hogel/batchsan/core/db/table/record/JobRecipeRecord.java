package org.hogel.batchsan.core.db.table.record;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobRecipeRecord {
    private long id;

    private String recipe;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
