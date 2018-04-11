package com.github.bubinimara.app.ui.fragment.search;

import com.github.bubinimara.app.model.MovieModel;
import com.github.bubinimara.app.ui.IView;

import java.util.List;

/**
 * Created by davide.
 */

public interface SearchView extends IView {
    @interface Errors {

        int UNKNOWN = 0;
    }

    String getSearchTerm();
    int getPageNumber();

    void showEmptyMovies();

    void showMovies(List<MovieModel> movieModels);

    void showError(@Errors int error);
}
