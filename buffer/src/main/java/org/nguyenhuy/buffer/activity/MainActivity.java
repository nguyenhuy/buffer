package org.nguyenhuy.buffer.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.adapter.ProfileAdapter;
import org.nguyenhuy.buffer.controller.AccessTokenController;
import org.nguyenhuy.buffer.controller.ConfigurationController;
import org.nguyenhuy.buffer.controller.ProfilesController;
import org.nguyenhuy.buffer.event.*;
import org.nguyenhuy.buffer.model.configuration.Service;
import org.nguyenhuy.buffer.model.user.Profile;
import org.nguyenhuy.buffer.module.ForActivity;
import org.nguyenhuy.buffer.module.MainActivityModule;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Inject
    ConfigurationController configurationController;
    @Inject
    @ForActivity
    JobManager jobManager;
    @Inject
    AccessTokenController accessTokenController;
    @Inject
    ProfilesController profilesController;
    @Inject
    Provider<ProfileAdapter> profileAdapterProvider;
    private int loadingCounter;
    private ProfileAdapter profileAdapter;
    private Map<String, String> serviceIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        jobManager.start();
        configurationController.onStart();
        profilesController.onStart();
        configurationController.load();
        profilesController.load();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        ActionBar actionBar = getActionBar();
        if (actionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST) {
            outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                    actionBar.getSelectedNavigationIndex());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        jobManager.stop();
        configurationController.onStop();
        profilesController.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_log_out:
                logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        getFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        return true;
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{
                new MainActivityModule(this)
        };
    }

    @Subscribe
    public void onAccessTokenChanged(AccessTokenChangedEvent event) {
        if (accessTokenController.isAvailable()) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Subscribe
    public void onGettingConfiguration(GettingConfigurationEvent event) {
        showLoadingIndicator();
    }

    @Subscribe
    public void onGotConfiguration(GotConfigurationEvent event) {
        if (event.getSource() == DataSource.NETWORK) {
            hideLoadingIndicator();
        }
        // Construct a map of service names and and service icon URLs.
        serviceIcons = new HashMap<String, String>();
        Map<String, Service> services = event.getConfiguration().getServices();
        for (Map.Entry<String, Service> entry : services.entrySet()) {
            String serviceName = entry.getKey();
            Service service = entry.getValue();
            serviceIcons.put(serviceName, service.getIcon());
        }
        if (profileAdapter != null) {
            profileAdapter.setServiceIcons(serviceIcons);
        }
    }

    @Subscribe
    public void onFailedToGetConfiguration(FailedToGetConfigurationEvent event) {
        Toast.makeText(this, R.string.prompt_failed_to_get_configuration, Toast.LENGTH_LONG)
                .show();
        hideLoadingIndicator();
    }

    @Subscribe
    public void onGettingProfiles(GettingProfilesEvent event) {
        showLoadingIndicator();
    }

    @Subscribe
    public void onGotProfilesController(GotProfilesEvent event) {
        setUpDropDownList(event.getProfiles());
        if (event.getSource() == DataSource.NETWORK) {
            hideLoadingIndicator();
        }
    }

    @Subscribe
    public void onFailedToGetProfiles(FailedToGetProfilesEvent event) {
        Toast.makeText(this, R.string.prompt_failed_to_get_profiles, Toast.LENGTH_LONG)
                .show();
        hideLoadingIndicator();
    }

    private void logout() {
        configurationController.clear();
        profilesController.clear();
        accessTokenController.clear();
        // Expect to receive AccessTokenChangedEvent after this point, so LoginActivity
        // will be started.
    }

    private void setUpDropDownList(List<Profile> profiles) {
        profileAdapter = profileAdapterProvider.get();
        profileAdapter.addAll(profiles);
        if (serviceIcons != null) {
            profileAdapter.setServiceIcons(serviceIcons);
        }

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(profileAdapter, this);
    }

    private void showLoadingIndicator() {
        ++loadingCounter;
        if (loadingCounter == 1) {
            setProgressBarIndeterminateVisibility(true);
        }
    }

    private void hideLoadingIndicator() {
        --loadingCounter;
        if (loadingCounter == 0) {
            setProgressBarIndeterminateVisibility(false);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
