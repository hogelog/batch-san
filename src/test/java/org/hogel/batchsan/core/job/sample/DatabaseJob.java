package org.hogel.batchsan.core.job.sample;

import com.google.inject.Inject;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.core.db.DbClient;
import org.hogel.batchsan.core.job.BatchJob;
import org.hogel.batchsan.core.job.recipe.JobRecipe;
import org.hogel.batchsan.core.job.result.BasicJobResult;
import org.hogel.batchsan.core.job.result.JobResult;

import java.sql.Connection;

public class DatabaseJob extends BatchJob {
    @Inject
    BoneCP connections;

    @Inject
    DbClient dbClient;

    public DatabaseJob(JobRecipe recipe) {
        super(recipe);
    }

    @Override
    public JobResult run() throws Exception {
        try (Connection connection = connections.getConnection()) {
            dbClient.update(connection, "CREATE TABLE IF NOT EXISTS hoge (id INT);");
            long count = (long) dbClient.query(connection, "SELECT COUNT(1) FROM hoge;")[0];
            dbClient.update(connection, "DROP TABLE hoge;");
            return BasicJobResult.success("count: " + count);
        }
    }
}
