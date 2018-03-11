package com.github.bubinimara.movies.fragment.search;

import com.github.bubinimara.movies.IView;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.List;

/**
 * Created by davide.
 */

public interface SearchView extends IView {
    @interface Errors {

        int UNKNOWN = 0;
    }
    void showMovies(List<MovieModel> movieModels, int currentPageNumber);
    void showEmptyMovies();

    void showError(@Errors int error);
}
