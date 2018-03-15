package com.github.bubinimara.movies.data;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.data.cache.InMemoryCache;
import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.data.entity.TmbApiResponse;
import com.github.bubinimara.movies.data.net.ApiClient;
import com.github.bubinimara.movies.data.net.ApiTmb;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class RepositoryImpl implements Repository {
    private InMemoryCache<List<MovieEntity>> cache;
    private ApiTmb apiTmb;
    String apiKey =  BuildConfig.TMDB_API_KEY;
    String language = "en-US";

    public RepositoryImpl() {
        cache = new InMemoryCache<>();
        ApiClient client = new ApiClient();
        apiTmb = client.getApiTmb();

    }

    public RepositoryImpl(InMemoryCache cache, ApiTmb apiTmb) {
        this.cache = cache;
        this.apiTmb = apiTmb;
    }

    @Override
    public Observable<List<MovieEntity>> getMostPopularMovies(int pageNumber) {
        String keyForCache = "getMostPopularMovies_" + pageNumber;
        Observable<List<MovieEntity>> moviesFromCache = cache.getMoviesFromCache(keyForCache);
        if(moviesFromCache!=null){
            return moviesFromCache;
        }

        return apiTmb.getMostPopularVideos(apiKey,language,pageNumber)
                .map(TmbApiResponse::getResults).doOnNext(m->cache.put(keyForCache,m));
    }

    @Override
    public Observable<List<MovieEntity>> searchMovie(String searchTerm, int pageNumber) {
        return apiTmb.searchForVideos(apiKey,language,searchTerm,pageNumber)
                .map(TmbApiResponse::getResults);
    }
}
