package org.nguyenhuy.buffer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.model.user.Update;

import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class PendingUpdatesAdapter extends ArrayAdapter<Update> {
    private LayoutInflater layoutInflater;

    public PendingUpdatesAdapter(Context context) {
        super(context, 0);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_pending_update,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.formattedText
                    = (TextView) convertView.findViewById(R.id.textview_formatted_text);
            viewHolder.dueDateText
                    = (TextView) convertView.findViewById(R.id.textview_date);
            viewHolder.mediaThumbnail
                    = (ImageView) convertView.findViewById(R.id.imageview_media_thumbnail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Update update = getItem(position);
        viewHolder.formattedText.setText(update.getFormattedText());
        viewHolder.dueDateText.setText(update.getDay() + " " + update.getDueTime());

        Map<String, String> medias = update.getMedias();
        if (medias == null || !medias.containsKey(Update.MEDIA_KEY_PICTURE)) {
            Picasso.with(getContext()).cancelRequest(viewHolder.mediaThumbnail);
            viewHolder.mediaThumbnail.setVisibility(View.GONE);
        } else {
            Picasso.with(getContext()).load(medias.get(Update.MEDIA_KEY_PICTURE))
                    .into(viewHolder.mediaThumbnail);
            viewHolder.mediaThumbnail.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView formattedText;
        TextView dueDateText;
        ImageView mediaThumbnail;
    }
}
