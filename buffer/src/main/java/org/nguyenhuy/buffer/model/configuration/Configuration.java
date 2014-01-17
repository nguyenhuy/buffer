package org.nguyenhuy.buffer.model.configuration;

import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class Configuration {
    private Media media;
    private Map<String, Service> services;

    public Map<String, Service> getServices() {
        return services;
    }
}
