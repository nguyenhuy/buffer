package org.nguyenhuy.buffer.model.configuration;

import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class Service {
    private static final int DEFAULT_ICON_SIZE = 64;

    private Map<String, Type> types;
    private Map<String, String> urls;

    public Map<String, Type> getTypes() {
        return types;
    }

    public Map<String, String> getUrls() {
        return urls;
    }

    public String getIcon() {
        return getIcon(DEFAULT_ICON_SIZE);
    }

    public String getIcon(int iconSize) {
        if (types != null || types.size() > 0) {
            Type[] typeArray = types.values().toArray(new Type[types.size()]);
            return typeArray[0].getIcons().get(iconSize);
        }
        return null;
    }
}
