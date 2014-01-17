package org.nguyenhuy.buffer.adapter;

import android.content.Context;
import android.graphics.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.model.user.Profile;
import org.nguyenhuy.buffer.module.ForActivity;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by nguyenthanhhuy on 1/17/14.
 */
public class ProfileAdapter extends ArrayAdapter<Profile> {
    private LayoutInflater layoutInflater;
    /**
     * A map contains URL of icon for supported services. Keys are service names
     * and values are URLs.
     */
    private Map<String, String> serviceIcons;

    @Inject
    ProfileAdapter(@ForActivity Context context, LayoutInflater layoutInflater) {
        super(context, 0);
        this.layoutInflater = layoutInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent, R.layout.spinner_item_profile);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent, R.layout.spinner_dropdown_item_profile);
    }

    public void setServiceIcons(Map<String, String> serviceIcons) {
        this.serviceIcons = serviceIcons;
        notifyDataSetChanged();
    }

    private View getView(int position, View convertView, ViewGroup parent, int layout) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.profile_avatar);
            viewHolder.serviceIcon = (ImageView) convertView.findViewById(R.id.profile_service_icon);
            viewHolder.username = (TextView) convertView.findViewById(R.id.profile_username);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Profile profile = getItem(position);
        String service = profile.getService();
        viewHolder.username.setText(profile.getFormattedUsername());
        Transformation transformation = new RoundedCornerTransformation();
        Picasso.with(getContext()).load(profile.getAvatar())
                .transform(transformation)
                .into(viewHolder.avatar);
        if (serviceIcons != null && serviceIcons.containsKey(service)) {
            Picasso.with(getContext()).load(serviceIcons.get(service))
                    .transform(transformation)
                    .into(viewHolder.serviceIcon);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView avatar;
        ImageView serviceIcon;
        TextView username;
    }

    private static class RoundedCornerTransformation implements Transformation {
        private static final int CORNER_RADIUS = 5;

        @Override
        public Bitmap transform(Bitmap bitmap) {
            // Original source: http://ruibm.com/?p=184
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = CORNER_RADIUS;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();
            return output;
        }

        @Override
        public String key() {
            return "rounded_corner";
        }
    }
}
