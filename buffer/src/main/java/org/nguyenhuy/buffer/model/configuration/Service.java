package org.nguyenhuy.buffer.model.configuration;

import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class Service {
    private Map<String, Type> types;
    private Map<String, String> urls;

    public Map<String, Type> getTypes() {
        return types;
    }

    public Map<String, String> getUrls() {
        return urls;
    }
}
