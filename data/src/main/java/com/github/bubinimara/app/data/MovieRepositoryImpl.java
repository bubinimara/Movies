package com.github.bubinimara.app.data;

import com.github.bubinimara.app.data.cache.InMemoryCache;
import com.github.bubinimara.app.data.entity.PageMovieEntity;
import com.github.bubinimara.app.data.entity.mapper.MovieMapper;
import com.github.bubinimara.app.data.entity.mapper.PageMovieMapper;
import com.github.bubinimara.app.data.net.ApiTmb;
import com.github.bubinimara.app.domain.Movie;
import com.github.bubinimara.app.domain.PageMovie;
import com.github.bubinimara.app.domain.repository.MovieRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public class MovieRepositoryImpl implements MovieRepository {
    private InMemoryCache<PageMovieEntity> cache;
    private ApiTmb apiTmb;

    @Inject
    public MovieRepositoryImpl(ApiTmb apiTmb) {
        this.apiTmb = apiTmb;
        this.cache = new InMemoryCache<>();
    }

    public MovieRepositoryImpl(ApiTmb apiTmb, InMemoryCache<PageMovieEntity> cache) {
        this.apiTmb = apiTmb;
        this.cache = cache;
    }

    @Override
    public Observable<PageMovie> getMostPopularMovies(String language,int pageNumber) {
        String keyForCache = "getMostPopularMovies_" +language + pageNumber;
        Observable<PageMovieEntity> deferApiCall = Observable.defer(() -> apiTmb.getMostPopularVideos(language, pageNumber));
        return buildChainObservable(keyForCache, deferApiCall);
    }

    @Override
    public Observable<PageMovie> searchMovie(String language, String searchTerm, int pageNumber) {
        String keyForCache = "search_for_term_"+language+pageNumber+searchTerm;
        Observable<PageMovieEntity> deferApiCall = Observable.defer(() -> apiTmb.searchForVideos(language,searchTerm, pageNumber));
        return buildChainObservable(keyForCache, deferApiCall);
    }

    @Override
    public Observable<Movie> findMovieById(String language, long movieId) {
        return apiTmb.findMovieById(movieId,language).map(MovieMapper::transform);
    }

    private Observable<PageMovie> buildChainObservable(String keyForCache, Observable<PageMovieEntity> deferApiCall) {
        return cache.get(keyForCache)
                .switchIfEmpty(deferApiCall
                        .doOnNext(n->cache.put(keyForCache,n)))
                .map(PageMovieMapper::transform);
    }
}
