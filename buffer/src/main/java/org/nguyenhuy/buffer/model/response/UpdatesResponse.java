package org.nguyenhuy.buffer.model.response;

import org.nguyenhuy.buffer.model.user.Update;

import java.util.List;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class UpdatesResponse {
    private int total;
    private List<Update> updates;

    public int getTotal() {
        return total;
    }

    public List<Update> getUpdates() {
        return updates;
    }
}
