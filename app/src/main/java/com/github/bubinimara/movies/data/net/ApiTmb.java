package com.github.bubinimara.movies.data.net;

import com.github.bubinimara.movies.data.entity.TmbApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by davide.
 */

public interface ApiTmb {

    @GET("3/movie/popular")
    Observable<TmbApiResponse> getMostPopularVideos(@Query("api_key") String api_key, @Query("language") String language,@Query("page") int page);

    @GET("3/search/movie")
    Observable<TmbApiResponse> searchForVideos(@Query("api_key") String api_key,
                                               @Query("language") String language,
                                               @Query("query") String searchTerm,
                                               @Query("page") int page);
}
