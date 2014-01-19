package org.nguyenhuy.buffer.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.api.ApiConstants;
import org.nguyenhuy.buffer.delegate.InjectDelegate;
import org.nguyenhuy.buffer.event.FailedToGetAccessTokenEvent;
import org.nguyenhuy.buffer.event.GotAccessTokenEvent;
import org.nguyenhuy.buffer.job.GetAccessTokenJob;
import org.nguyenhuy.buffer.module.ForActivity;
import org.nguyenhuy.buffer.util.LogUtils;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class OAuthFragment extends Fragment {

    public interface Delegate extends InjectDelegate {
        void oAuthFailed();

        void oAuthSuccess(String accessToken);
    }

    private Delegate delegate;
    private WebView webView;
    private View progressContainer;
    private TextView progressText;
    @Inject
    Bus bus;
    @Inject
    @ForActivity
    JobManager jobManager;
    @Inject
    ApiConstants apiConstants;
    @Inject
    OAuthService oAuthService;

    public OAuthFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        delegate = (Delegate) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_oauth, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.webview);
        progressContainer = view.findViewById(R.id.progress_container);
        progressText = (TextView) view.findViewById(R.id.progress_text);
        // Request focus for the webview, otherwise soft keyboard won't be
        // showed for text boxes.
        webView.requestFocus(View.FOCUS_DOWN);
        webView.setWebViewClient(new WebViewClient() {
            private boolean isLoadingPage;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.v("Should override: " + url);
                if (!url.startsWith(apiConstants.getCallback())) {
                    return false;
                }

                isLoadingPage = false;
                Uri uri = Uri.parse(url);
                String error = uri.getQueryParameter("error");
                if (!TextUtils.isEmpty(error)) {
                    delegate.oAuthFailed();
                } else {
                    showProgress(true, true);
                    String code = uri.getQueryParameter("code");
                    Verifier verifier = new Verifier(code);
                    GetAccessTokenJob job = new GetAccessTokenJob(verifier);
                    jobManager.addJobInBackground(job);
                }

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.v("Page started: " + url);
                showProgress(true, false);
                isLoadingPage = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtils.v("Page finished: " + url);
                if (isLoadingPage) {
                    showProgress(false, false);
                    isLoadingPage = false;
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressText.setText(newProgress + "%");
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(false);
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        delegate.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
        webView.loadUrl(oAuthService.getAuthorizationUrl(null));
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    private void showProgress(boolean loading, boolean intermediate) {
        if (loading) {
            progressContainer.setVisibility(View.VISIBLE);
            progressText.setVisibility(intermediate ? View.GONE : View.VISIBLE);
            webView.setVisibility(View.GONE);
        } else {
            progressContainer.setVisibility(View.GONE);
            progressText.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onGotAccessToken(GotAccessTokenEvent event) {
        delegate.oAuthSuccess(event.getToken().getToken());
    }

    @Subscribe
    public void onFailedToGetAccessToken(FailedToGetAccessTokenEvent token) {
        delegate.oAuthFailed();
    }
}
