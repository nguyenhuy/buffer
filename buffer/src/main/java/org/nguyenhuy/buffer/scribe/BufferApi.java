package org.nguyenhuy.buffer.scribe;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class BufferApi extends DefaultApi20{
    private static final String AUTHORIZATION_URL = "https://bufferapp.com/oauth2/authorize" +
            "?client_id=%s&redirect_uri=%s&response_type=code";

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.bufferapp.com/1/oauth2/token.json";
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config) {
        return String.format(AUTHORIZATION_URL, config.getApiKey(), OAuthEncoder.encode(config.getCallback()));
    }
}
