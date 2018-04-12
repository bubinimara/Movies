package com.github.bubinimara.movies.ui.activity.details;

import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.ui.IView;

/**
 * Created by davide.
 */
public interface DetailsView extends IView {
    long getMovieId();
    void showDetails(MovieModel movieModel);
}
