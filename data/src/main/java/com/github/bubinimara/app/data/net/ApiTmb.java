package com.github.bubinimara.app.data.net;

import com.github.bubinimara.app.data.entity.ConfigurationEntity;
import com.github.bubinimara.app.data.entity.MovieEntity;
import com.github.bubinimara.app.data.entity.PageMovieEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by davide.
 */
// TODO: Handle api version
public interface ApiTmb {

    @GET("3/movie/popular")
    Observable<PageMovieEntity> getMostPopularVideos(@Query("language") String language,
                                                     @Query("page") int page);

    @GET("3/search/movie")
    Observable<PageMovieEntity> searchForVideos(@Query("language") String language,
                                                @Query("query") String searchTerm,
                                                @Query("page") int page);

    @GET("3/movie/{id}")
    Observable<MovieEntity> findMovieById(@Path("id") long movieId,@Query("language") String language);

    @GET("3/configuration")
    Observable<ConfigurationEntity> getConfiguration();
}
