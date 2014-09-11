package org.hogel.batchsan.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.hogel.batchsan.config.SampleConfig;

public class SampleConfigModule implements Module {
    private final SampleConfig config;

    public SampleConfigModule(SampleConfig config) {
        this.config = config;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(SampleConfig.class).toInstance(config);
    }
}
