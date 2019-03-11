package com.github.bubinimara.movies.app.ui.fragment.home;

import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.ui.IView;

import java.util.Collection;

/**
 * Created by davide.
 */

public interface HomeView extends IView {

    @interface Errors{
        int UNKNOWN = 0;
    }

    int getCurrentPage();

    void showMovies(Collection<MovieModel> movies);
    void showDetailsView(MovieModel movieModel);

    void showProgress();
    void hideProgress();
    boolean isLoading();

    void showError(@Errors int type);
    void hideError();
}
