package com.github.bubinimara.app.data;

import com.github.bubinimara.app.data.cache.InMemoryCache;
import com.github.bubinimara.app.data.entity.MovieEntity;
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
    private InMemoryCache<PageMovieEntity> pageMovieCache;
    private InMemoryCache<MovieEntity> movieEntityCache;
    private ApiTmb apiTmb;

    @Inject
    public MovieRepositoryImpl(ApiTmb apiTmb) {
        this.apiTmb = apiTmb;
        this.pageMovieCache = new InMemoryCache<>();
        this.movieEntityCache = new InMemoryCache<>();
    }

    public MovieRepositoryImpl(ApiTmb apiTmb, InMemoryCache<PageMovieEntity> pageMovieCache) {
        this.apiTmb = apiTmb;
        this.pageMovieCache = pageMovieCache;
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
    public Observable<PageMovie> getSimilarMovies(String language, long movieId, int page) {
        // TODO: add cache
        return apiTmb.getSimilarMovies(movieId,language,page).map(PageMovieMapper::transform);
    }

    @Override
    public Observable<Movie> findMovieById(String language, long movieId) {
        String keyForCache = "movie_details_"+language+movieId;
        return movieEntityCache.get(keyForCache)
                .switchIfEmpty(Observable.defer(
                        ()->apiTmb.getMovieById(movieId,language)
                                .doOnNext(movie ->  movieEntityCache.put(keyForCache,movie))
                )).map(MovieMapper::transform);
    }

    private Observable<PageMovie> buildChainObservable(String keyForCache, Observable<PageMovieEntity> deferApiCall) {
        return pageMovieCache.get(keyForCache)
                .switchIfEmpty(deferApiCall
                        .doOnNext(n-> pageMovieCache.put(keyForCache,n)))
                .map(PageMovieMapper::transform);
    }
}
