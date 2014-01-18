package org.nguyenhuy.buffer.api;

import org.nguyenhuy.buffer.model.configuration.Configuration;
import org.nguyenhuy.buffer.model.response.UpdatesResponse;
import org.nguyenhuy.buffer.model.user.Profile;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public interface BufferService {
    @GET("/info/configuration.json")
    Configuration getConfiguration(@Query("access_token") String accessToken);

    @GET("/profiles.json")
    List<Profile> getProfiles(@Query("access_token") String accessToken);

    @GET("/profiles/{id}/updates/pending.json")
    UpdatesResponse getPendingUpdates(@Path("id") String id,
                                      @Query("access_token") String accessToken,
                                      @Query("page") int page,
                                      @Query("count") int count);

    @GET("/profiles/{id}/updates/sent.json")
    UpdatesResponse getSentUpdates(@Path("id") String id,
                                   @Query("access_token") String accessToken,
                                   @Query("page") int page,
                                   @Query("count") int count);
}
