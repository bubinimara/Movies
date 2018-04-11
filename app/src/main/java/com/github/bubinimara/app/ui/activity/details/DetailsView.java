package com.github.bubinimara.app.ui.activity.details;

import com.github.bubinimara.app.model.MovieModel;
import com.github.bubinimara.app.ui.IView;

/**
 * Created by davide.
 */
public interface DetailsView extends IView {
    long getMovieId();
    void showDetails(MovieModel movieModel);
}
