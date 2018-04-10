package com.github.bubinimara.app.model.mapper;

import com.github.bubinimara.app.domain.Configuration;
import com.github.bubinimara.app.domain.Movie;
import com.github.bubinimara.app.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide.
 */

public class MovieModelMapper {

    // todo: to rename
    public static List<MovieModel> transformConf(List<Movie> movieEntities,Configuration configuration) {
        List<MovieModel> movies = new ArrayList<>(movieEntities.size());
        for (int i = 0;i<movieEntities.size();i++) {
            Movie e = movieEntities.get(i);
            movies.add(transform(e,configuration));

        }
        return movies;
    }
    // todo: to remove
    public static List<MovieModel> transform(List<Movie> movieEntities) {
        List<MovieModel> movies = new ArrayList<>(movieEntities.size());
        for (int i = 0;i<movieEntities.size();i++) {
            Movie e = movieEntities.get(i);
            movies.add(transform(e,null));

        }
        return movies;
    }

    public static MovieModel transform(Movie movieEntities, Configuration configuration) {
        MovieModel movieModel = new MovieModel();
        movieModel.setId(movieEntities.getId());
        movieModel.setTitle(movieEntities.getTitle());
        movieModel.setOverview(movieEntities.getOverview());
        movieModel.setYear(getFormattedDate(movieEntities.getReleaseDate()));
        movieModel.setImageUrl(getBestImageUrl(movieEntities,configuration));
        return movieModel;
    }

    private static String getBestImageUrl(Movie movie, Configuration configuration) {
        if(configuration == null){
            return null;
        }
        Configuration.Image images = configuration.getImages();
        if(images == null || images.getPoster_sizes().isEmpty()){
            return null;
        }
        if(movie.getPosterPath() == null || movie.getPosterPath().isEmpty()){
            return null;
        }

        return images.getBase_url()+images.getPoster_sizes().get(0)+movie.getPosterPath();

    }

    private static String getFormattedDate(String release_date) {
        if(release_date.isEmpty()){
            return release_date;
        }
        int indexOf = release_date.indexOf('-');
        if(indexOf <0 || indexOf > release_date.length()){
            return release_date;
        }
        return release_date.substring(0, indexOf);
    }
}
