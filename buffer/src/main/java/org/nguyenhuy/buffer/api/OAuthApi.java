package org.nguyenhuy.buffer.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.exceptions.OAuthException;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.JsonTokenExtractor;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class OAuthApi extends DefaultApi20 {
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

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }

    @Override
    public AccessTokenExtractor getAccessTokenExtractor() {
        // Scribe's JsonTokenExtractor uses regex to extract the token,
        // thus it doesn't remove escaped slash and makes the token malformed.
        return new TokenExtractor();
    }

    @Override
    public OAuthService createService(OAuthConfig config) {
        return new OAuthServiceImpl(this, config);
    }

    private static final class TokenExtractor implements AccessTokenExtractor {

        @Override
        public Token extract(String s) {
            Preconditions.checkEmptyString(s, "Cannot extract a token from a null or empty String");
            try {
                JSONObject jsonObject = new JSONObject(s);
                String accessToken = jsonObject.getString("access_token");
                return new Token(accessToken, "", s);
            } catch (JSONException e) {
                throw new OAuthException("Cannot extract an acces token. Response was: " + s);
            }
        }
    }
}
