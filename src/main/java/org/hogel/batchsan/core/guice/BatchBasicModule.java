package org.hogel.batchsan.core.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.jolbox.bonecp.BoneCP;
import org.hogel.batchsan.core.BatchConfig;
import org.hogel.batchsan.core.BatchRedisKey;

public class BatchBasicModule implements Module {
    private final BatchConfig batchConfig;

    public BatchBasicModule(BatchConfig batchConfig) {
        this.batchConfig = batchConfig;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(BatchConfig.class).toInstance(batchConfig);
        binder.bind(BoneCP.class).toProvider(BoneCPProvider.class).in(Singleton.class);
        binder.bind(BatchRedisKey.class).toInstance(new BatchRedisKey(batchConfig));
    }
}
