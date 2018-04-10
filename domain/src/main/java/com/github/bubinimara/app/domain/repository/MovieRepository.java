package com.github.bubinimara.app.domain.repository;

import com.github.bubinimara.app.domain.Movie;
import com.github.bubinimara.app.domain.PageMovie;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
//TODO: Let return only list of movies
public interface MovieRepository {
    Observable<PageMovie> getMostPopularMovies(String language,int pageNumber);

    Observable<PageMovie> searchMovie(String language, String searchTerm, int pageNumber);

    Observable<PageMovie> getSimilarMovies(String language,long movieId, int page);
    Observable<Movie> getMovieById(String language, long movieId);
}
