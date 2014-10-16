package org.hogel.batchsan.core.guice;

import com.google.common.eventbus.EventBus;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.j256.ormlite.support.ConnectionSource;
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
        binder.bind(BatchRedisKey.class).toInstance(new BatchRedisKey(batchConfig));
        binder.bind(ConnectionSource.class).toProvider(ConnectionSourceProvider.class).in(Singleton.class);
        binder.bind(EventBus.class).toInstance(new EventBus("batchsan"));
    }
}
