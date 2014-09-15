package org.hogel.batchsan.test.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.hogel.batchsan.test.config.SampleConfig;

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
