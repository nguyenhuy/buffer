package org.nguyenhuy.buffer.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import org.nguyenhuy.buffer.model.user.Update;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class UpdatesAdapter extends ArrayAdapter<Update> {

    public UpdatesAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1);
    }
}
