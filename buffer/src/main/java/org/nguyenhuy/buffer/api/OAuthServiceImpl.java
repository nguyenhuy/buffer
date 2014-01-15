package org.nguyenhuy.buffer.api;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class OAuthServiceImpl extends OAuth20ServiceImpl {
    private DefaultApi20 api;
    private OAuthConfig config;

    public OAuthServiceImpl(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
        this.api = api;
        this.config = config;
    }

    public Token getAccessToken(Token requestToken, Verifier verifier)
    {
        OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addBodyParameter(OAuthConstants.CODE, verifier.getValue());
        request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        request.addBodyParameter("grant_type", "authorization_code");
        if(config.hasScope()) request.addBodyParameter(OAuthConstants.SCOPE, config.getScope());
        Response response = request.send();
        return api.getAccessTokenExtractor().extract(response.getBody());
    }
}
