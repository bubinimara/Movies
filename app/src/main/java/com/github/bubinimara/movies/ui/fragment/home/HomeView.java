package com.github.bubinimara.movies.ui.fragment.home;

import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.ui.IView;

import java.util.Collection;

/**
 * Created by davide.
 */

public interface HomeView extends IView {

    @interface Errors{
        int UNKNOWN = 0;
    }
    void showMovies(Collection<MovieModel> movies);
    void showDetailsView(MovieModel movieModel);
    void showError(@Errors int type);
}
