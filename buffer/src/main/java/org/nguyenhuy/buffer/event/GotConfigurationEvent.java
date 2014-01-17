package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.configuration.Configuration;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class GotConfigurationEvent {
    private DataSource source;
    private Configuration configuration;

    public GotConfigurationEvent(DataSource source, Configuration configuration) {
        this.source = source;
        this.configuration = configuration;
    }

    public DataSource getSource() {
        return source;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
