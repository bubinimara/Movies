package com.github.bubinimara.movies.fragment.home;

import com.github.bubinimara.movies.IView;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.Collection;

/**
 * Created by davide.
 */

public interface HomeView extends IView {
    void showMovies(Collection<MovieModel> movies, int page);
}
