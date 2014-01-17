package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.configuration.Configuration;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class GotConfigurationEvent {
    private Configuration configuration;

    public GotConfigurationEvent(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
