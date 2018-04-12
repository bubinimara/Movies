package com.github.bubinimara.movies.data.entity.mapper;

import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.domain.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide.
 */

public class MovieMapper {
    public static List<Movie> transform(List<MovieEntity> movieEntities){
        List<Movie> movies = new ArrayList<>(movieEntities.size());
        for (int i = 0; i < movieEntities.size(); i++) {
            movies.add(transform(movieEntities.get(i)));
        }
        return movies;
    }
    public static Movie transform(MovieEntity movieEntity){
        Movie movie = new Movie();
        movie.setId(movieEntity.getId());
        movie.setTitle(movieEntity.getTitle());
        movie.setOverview(movieEntity.getOverview());
        movie.setPosterPath(movieEntity.getPoster_path());
        movie.setReleaseDate(movieEntity.getRelease_date());
        return movie;
    }
}
