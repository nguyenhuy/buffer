package org.nguyenhuy.buffer.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class Update {
    public static final String STATUS_PENDING = "buffer";
    public static final String STATUS_SENT = "sent";

    private String id;
    @SerializedName("created_at")
    private long createdDate;
    @SerializedName("due_at")
    private long dueDate;
    private String day;
    @SerializedName("due_time")
    private String due_time;
    @SerializedName("profile_id")
    private String profileId;
    @SerializedName("profile_service")
    private String profileService;
    /**
     * Status of the update. Should be either {@link #STATUS_PENDING}
     * or {@link #STATUS_SENT}.
     */
    private String status;
    private String text;
    @SerializedName("text_formatted")
    private String formattedText;
    @SerializedName("user_id")
    private String userId;
    private String via;

    @Override
    public String toString() {
        return formattedText;
    }
}
