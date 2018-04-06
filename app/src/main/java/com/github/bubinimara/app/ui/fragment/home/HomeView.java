package com.github.bubinimara.app.ui.fragment.home;

import com.github.bubinimara.app.ui.IView;
import com.github.bubinimara.app.model.MovieModel;

import java.util.Collection;

/**
 * Created by davide.
 */

public interface HomeView extends IView {

    @interface Errors{
        int UNKNOWN = 0;
    }
    void showMovies(Collection<MovieModel> movies);
    void showError(@Errors int type);
}
