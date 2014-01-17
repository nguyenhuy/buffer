package org.nguyenhuy.buffer.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/17/14.
 */
public class Profile {
    private String avatar;
    @SerializedName("created_date")
    private long createdDate;
    @SerializedName("default")
    private boolean isDefault;
    @SerializedName("formatted_username")
    private String formattedUsername;
    private String id;
    // "schedules" is not supported (yet)
    private String service;
    @SerializedName("service_id")
    private String serviceId;
    @SerializedName("service_username")
    private String serviceUsername;
    private Map<String, Integer> statistics;
    @SerializedName("team_members")
    private List<String> teamMembers;
    private String timezone;
    @SerializedName("user_id")
    private String userId;

    public String getAvatar() {
        return avatar;
    }

    public String getFormattedUsername() {
        return formattedUsername;
    }

    public String getService() {
        return service;
    }
}
