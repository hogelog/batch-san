package org.hogel.batchsan.core;

import com.google.inject.Binder;
import com.google.inject.Module;

public class BatchConfigModule implements Module {
    private final BatchConfig batchConfig;

    public BatchConfigModule(BatchConfig batchConfig) {
        this.batchConfig = batchConfig;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(BatchConfig.class).toInstance(batchConfig);
    }
}
