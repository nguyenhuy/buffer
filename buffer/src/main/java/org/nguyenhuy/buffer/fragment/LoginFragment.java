package org.nguyenhuy.buffer.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.nguyenhuy.buffer.R;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class LoginFragment extends Fragment {

    public interface Delegate {
        void login();
    }

    private Delegate delegate;

    public LoginFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        delegate = (Delegate) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.login();
            }
        });
    }
}
