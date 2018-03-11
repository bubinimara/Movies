package com.github.bubinimara.movies.data;

import com.github.bubinimara.movies.data.entity.MovieEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public interface Repository {
    Observable<List<MovieEntity>> getMostPopularMovies(int pageNumber);
    Observable<List<MovieEntity>> searchMovie(String searchTerm,int pageNumber);
}
