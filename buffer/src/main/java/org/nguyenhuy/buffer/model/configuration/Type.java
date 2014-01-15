package org.nguyenhuy.buffer.model.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class Type {
    @SerializedName("character_limit")
    private int charLimit;
    private Map<Integer, String> icons;
    private String name;
    @SerializedName("schedule_limit")
    private int scheduleLimit;
    @SerializedName("supported_interactions")
    private List<String> interactions;

    public int getCharLimit() {
        return charLimit;
    }

    public Map<Integer, String> getIcons() {
        return icons;
    }

    public String getName() {
        return name;
    }

    public int getScheduleLimit() {
        return scheduleLimit;
    }

    public List<String> getInteractions() {
        return interactions;
    }
}
