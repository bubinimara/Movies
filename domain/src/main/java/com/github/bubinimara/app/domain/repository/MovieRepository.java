package com.github.bubinimara.app.domain.repository;

import com.github.bubinimara.app.domain.PageMovie;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
//TODO: Let return only list of movies
public interface MovieRepository {
    Observable<PageMovie> getMostPopularMovies(int pageNumber);

    Observable<PageMovie> searchMovie(String searchTerm, int pageNumber);
}
