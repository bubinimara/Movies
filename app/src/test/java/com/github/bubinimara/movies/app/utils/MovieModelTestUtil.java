package com.github.bubinimara.movies.app.utils;

import com.github.bubinimara.movies.app.model.MovieModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by davide.
 */
public class MovieModelTestUtil {

    public static List<MovieModel> createMovies(int size) {
        List<MovieModel> movies = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            MovieModel m = new MovieModel();
            m.setId(i);
            m.setTitle(String.valueOf(i));
            m.setImageUrl(String.valueOf(i));
            m.setOverview(String.valueOf(i));
            m.setYear(String.valueOf(i));
            movies.add(m);
        }
        return movies;
    }
}
