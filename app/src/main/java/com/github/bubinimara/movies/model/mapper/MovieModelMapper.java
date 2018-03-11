package com.github.bubinimara.movies.model.mapper;

import com.github.bubinimara.movies.data.entity.MovieEntity;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide.
 */

public class MovieModelMapper {
    // todo: retrieve it from configuration api call
    private static String BASE_URL_IMAGE= "https://image.tmdb.org/t/p/w500/";

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
        movieModel.setOverview(movieEntities.getOverview());
        movieModel.setYear(movieEntities.getRelease_date());
        movieModel.setImageUrl(BASE_URL_IMAGE+movieEntities.getPoster_path());
        return movieModel;
    }
}
