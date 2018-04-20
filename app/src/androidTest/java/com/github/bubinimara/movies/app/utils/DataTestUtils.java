package com.github.bubinimara.movies.app.utils;

import com.github.bubinimara.movies.domain.Movie;
import com.github.bubinimara.movies.domain.PageMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davide.
 */
public class DataTestUtils {
    private static final int PAGE_LIST_SIZE = 33;

    private static final Long PREFIX_ID = 0L;
    private static final String PREFIX_OVERVIEW = "overview_";
    private static final String PREFIX_POSTER_PATH = "poster_path_";
    private static final String PREFIX_RELEASE_DATA = "release_data_";
    private static final String PREFIX_TITLE = "title_";

    public static PageMovie createPageMovie(int page){
        PageMovie pageMovie = new PageMovie ();
        pageMovie.setPage(page);
        pageMovie.setMovies(createListMovie());
        return pageMovie;
    }

    private static List<Movie> createListMovie() {
        List<Movie> movies = new ArrayList<>(PAGE_LIST_SIZE);
        for (int i = 0; i < PAGE_LIST_SIZE; i++) {
            movies.add(createMovieFromSuffix(i));
        }
        return movies;

    }

    private static Movie createMovieFromSuffix(int suffix) {
        Movie movie = new Movie();
        movie.setId(PREFIX_ID+suffix);
        movie.setTitle(PREFIX_TITLE+suffix);
        movie.setOverview(PREFIX_OVERVIEW+suffix);
        movie.setPosterPath(PREFIX_POSTER_PATH+suffix);
        movie.setReleaseDate(PREFIX_RELEASE_DATA+suffix);
        return movie;
    }
}
