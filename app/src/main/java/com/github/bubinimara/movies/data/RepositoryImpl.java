package com.github.bubinimara.movies.data;

import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.data.mock.MockRepo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class RepositoryImpl implements Repository {
    private MockRepo mockRepo;

    public RepositoryImpl() {
        mockRepo = new MockRepo();
    }

    @Override
    public Observable<List<MovieEntity>> getMostPopularMovies(int pageNumber) {
        return mockRepo.getMostPopularMovies(pageNumber);
    }

    @Override
    public Observable<List<MovieEntity>> searchMovie(String searchTerm, int pageNumber) {
        return mockRepo.searchMovie(searchTerm,pageNumber);
    }
}
