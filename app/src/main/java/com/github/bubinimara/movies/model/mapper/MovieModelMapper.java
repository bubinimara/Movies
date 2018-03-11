package com.github.bubinimara.movies.model.mapper;

import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide.
 */

public class MovieModelMapper {
    public static List<MovieModel> transform(List<MovieEntity> movieEntities) {
        List<MovieModel> movies = new ArrayList<>();
        for (MovieEntity e :
                movieEntities) {
            movies.add(transform(e));

        }
        return movies;
    }

    public static MovieModel transform(MovieEntity movieEntities) {
        MovieModel movieModel = new MovieModel();
        movieModel.setTitle(movieEntities.getTitle());
        return movieModel;
    }
}
