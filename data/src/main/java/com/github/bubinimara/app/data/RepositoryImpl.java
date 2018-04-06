package com.github.bubinimara.app.data;

import com.github.bubinimara.app.data.cache.InMemoryCache;
import com.github.bubinimara.app.data.entity.PageMovieEntity;
import com.github.bubinimara.app.data.entity.mapper.PageMovieMapper;
import com.github.bubinimara.app.data.net.ApiTmb;
import com.github.bubinimara.app.domain.PageMovie;
import com.github.bubinimara.app.domain.repository.Repository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
//TODO: language should be get from api
public class RepositoryImpl implements Repository {
    private InMemoryCache<PageMovieEntity> cache;
    private ApiTmb apiTmb;
    String language = "en-US";

    @Inject
    public RepositoryImpl(ApiTmb apiTmb) {
        this.apiTmb = apiTmb;
        this.cache = new InMemoryCache<>();
    }

    public RepositoryImpl( ApiTmb apiTmb, InMemoryCache<PageMovieEntity> cache) {
        this.apiTmb = apiTmb;
        this.cache = cache;
    }

    @Override
    public Observable<PageMovie> getMostPopularMovies(int pageNumber) {
        String keyForCache = "getMostPopularMovies_" + pageNumber;
        Observable<PageMovieEntity> deferApiCall = Observable.defer(() -> apiTmb.getMostPopularVideos(language, pageNumber));
        return buildChainObservable(keyForCache, deferApiCall);
    }

    @Override
    public Observable<PageMovie> searchMovie(String searchTerm, int pageNumber) {
        String keyForCache = "search_for_term_"+pageNumber+searchTerm;
        Observable<PageMovieEntity> deferApiCall = Observable.defer(() -> apiTmb.searchForVideos(language,searchTerm, pageNumber));
        return buildChainObservable(keyForCache, deferApiCall);
    }

    private Observable<PageMovie> buildChainObservable(String keyForCache, Observable<PageMovieEntity> deferApiCall) {
        return cache.get(keyForCache)
                .switchIfEmpty(deferApiCall
                        .doOnNext(n->cache.put(keyForCache,n)))
                .map(PageMovieMapper::transform);
    }
}
