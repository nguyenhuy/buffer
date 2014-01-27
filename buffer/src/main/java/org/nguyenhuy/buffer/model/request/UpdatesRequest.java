package org.nguyenhuy.buffer.model.request;

/**
 * Created by nguyenthanhhuy on 1/27/14.
 */
public class UpdatesRequest implements Comparable<UpdatesRequest> {
    private String profileId;
    private String status;
    private int page;
    private int count;

    public UpdatesRequest(String profileId, String status, int page, int count) {
        this.profileId = profileId;
        this.status = status;
        this.page = page;
        this.count = count;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(UpdatesRequest another) {
        if (this == another) {
            return 0;
        }

        int result = profileId.compareTo(another.profileId);
        if (result != 0) {
            return result;
        }

        result = status.compareTo(another.status);
        if (result != 0) {
            return result;
        }

        result = page - another.page;
        if (page != 0) {
            return result;
        }

        return count - another.count;
    }
}