package org.nguyenhuy.buffer.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.fragment.PendingUpdatesFragment;
import org.nguyenhuy.buffer.fragment.SentUpdatesFragment;
import org.nguyenhuy.buffer.module.ForActivity;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class UpdatesFragmentsPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    @Inject
    UpdatesFragmentsPagerAdapter(@ForActivity Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PendingUpdatesFragment();
            case 1:
                return new SentUpdatesFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.title_fragment_pending_updates);
            case 1:
                return context.getString(R.string.title_fragment_pending_sent);
            default:
                return null;
        }
    }
}
