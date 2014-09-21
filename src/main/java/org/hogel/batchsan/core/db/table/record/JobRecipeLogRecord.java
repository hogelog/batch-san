package org.hogel.batchsan.core.db.table.record;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobRecipeLogRecord extends AbstractRecord {
    private long id;

    private String job;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
