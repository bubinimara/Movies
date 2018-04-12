package com.github.bubinimara.movies.app.ui.fragment.details;

import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.ui.IView;

import java.util.List;

/**
 * Created by davide.
 */
public interface MovieDetailsView extends IView {
    long getMovieId();

    void setOverview(String o);

    void showSimilarMovie(List<MovieModel> movieModels);

    void setTitle(String title);
}
