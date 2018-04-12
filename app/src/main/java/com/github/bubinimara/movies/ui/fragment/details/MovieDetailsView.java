package com.github.bubinimara.movies.ui.fragment.details;

import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.ui.IView;

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
