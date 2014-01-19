package org.nguyenhuy.buffer.model.user;

import android.text.Spanned;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class Update {
    public static final String STATUS_PENDING = "buffer";
    public static final String STATUS_SENT = "sent";

    public static final String MEDIA_KEY_THUMBNAIL = "thumbnail";
    public static final String MEDIA_KEY_PICTURE = "picture";

    private String id;
    @SerializedName("created_at")
    private long createdDate;
    @SerializedName("due_at")
    private long dueDate;
    private String day;
    @SerializedName("due_time")
    private String dueTime;
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
    @SerializedName("media")
    private Map<String, String> medias;

    private transient Spanned styledText;

    public String getFormattedText() {
        return formattedText;
    }

    public String getDay() {
        return day;
    }

    public String getDueTime() {
        return dueTime;
    }

    public Map<String, String> getMedias() {
        return medias;
    }

    public Spanned getStyledText() {
        return styledText;
    }

    public void setStyledText(Spanned styledText) {
        this.styledText = styledText;
    }
}
