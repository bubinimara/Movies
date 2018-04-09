package com.github.bubinimara.app.domain.repository;

import com.github.bubinimara.app.domain.PageMovie;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
//TODO: Let return only list of movies
public interface MovieRepository {
    Observable<PageMovie> getMostPopularMovies(String language,int pageNumber);

    Observable<PageMovie> searchMovie(String isoCode, String searchTerm, int pageNumber);
}
