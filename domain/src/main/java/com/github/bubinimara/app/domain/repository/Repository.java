package com.github.bubinimara.app.domain.repository;

import com.github.bubinimara.app.domain.Movie;
import com.github.bubinimara.app.domain.PageMovie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public interface Repository {
    Observable<PageMovie> getMostPopularMovies(int pageNumber);

    Observable<PageMovie> searchMovie(String searchTerm, int pageNumber);
}
