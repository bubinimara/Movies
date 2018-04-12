package com.github.bubinimara.movies.app.ui.activity.details;

import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.ui.IView;

/**
 * Created by davide.
 */
public interface DetailsView extends IView {
    long getMovieId();
    void showDetails(MovieModel movieModel);
}
