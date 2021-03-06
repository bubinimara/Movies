package com.github.bubinimara.movies.app.model.mapper;

import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.Movie;
import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.domain.PageMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide.
 */

public class MovieModelMapper {

    public static List<MovieModel> transform(PageMovie pageMovie, Configuration configuration) {
        List<Movie> movieEntities = pageMovie.getMovies();
        List<MovieModel> movies = new ArrayList<>(movieEntities.size());
        for (int i = 0;i<movieEntities.size();i++) {
            Movie e = movieEntities.get(i);
            MovieModel movieModel = transform(e, configuration);
            //movieModel.setTitle(movieModel.getTitle() + " "+pageMovie.getPage());
            movies.add(movieModel);

        }
        return movies;
    }

    public static List<MovieModel> transform(List<Movie> movieEntities, Configuration configuration) {
        List<MovieModel> movies = new ArrayList<>(movieEntities.size());
        for (int i = 0;i<movieEntities.size();i++) {
            Movie e = movieEntities.get(i);
            movies.add(transform(e,configuration));

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
        if(images == null){
            return null;
        }
        if(images.getPoster_sizes() == null || images.getPoster_sizes().isEmpty()){
            return null;
        }
        if(movie.getPosterPath() == null || movie.getPosterPath().isEmpty()){
            return null;
        }

        String img_size = images.getPoster_sizes().get(images.getPoster_sizes().size() - 1);
        return images.getBase_url()+img_size+movie.getPosterPath();

    }

    private static String getFormattedDate(String release_date) {
        if(release_date == null || release_date.isEmpty()){
            return "";
        }
        int indexOf = release_date.indexOf('-');
        if(indexOf <0 || indexOf > release_date.length()){
            return "";
        }
        return release_date.substring(0, indexOf);
    }
}
