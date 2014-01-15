package org.nguyenhuy.buffer.api;

import org.nguyenhuy.buffer.model.Configuration;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public interface BufferService {
    @GET("/info/configuration.json")
    Configuration getConfiguration(@Query("access_token") String accessToken);
}
