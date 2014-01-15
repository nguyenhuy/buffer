package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.Configuration;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class ConfigurationAvailableEvent {
    private Configuration configuration;

    public ConfigurationAvailableEvent(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
