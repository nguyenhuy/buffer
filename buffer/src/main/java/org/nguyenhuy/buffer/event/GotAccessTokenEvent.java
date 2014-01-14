package org.nguyenhuy.buffer.event;

import org.scribe.model.Token;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class GotAccessTokenEvent {
    private Token token;

    public GotAccessTokenEvent(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
