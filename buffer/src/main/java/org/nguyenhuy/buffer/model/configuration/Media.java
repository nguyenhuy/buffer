package org.nguyenhuy.buffer.model.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class Media {
    @SerializedName("picture_filetypes")
    private List<String> pictureFileTypes;
    @SerializedName("picture_size_max")
    private int maxPictureSize;
    @SerializedName("picture_size_min")
    private int minPictureSize;

    public List<String> getPictureFileTypes() {
        return pictureFileTypes;
    }

    public int getMaxPictureSize() {
        return maxPictureSize;
    }

    public int getMinPictureSize() {
        return minPictureSize;
    }
}
