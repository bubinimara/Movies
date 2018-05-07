package com.github.bubinimara.movies.app.ui.fragment.details;

import com.github.bubinimara.movies.domain.cases.GetConfiguration;
import com.github.bubinimara.movies.domain.cases.GetMovieDetails;
import com.github.bubinimara.movies.domain.cases.GetSimilarMovies;
import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.model.mapper.MovieModelMapper;
import com.github.bubinimara.movies.app.rx.SimpleDisposableObserver;
import com.github.bubinimara.movies.app.ui.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by davide.
 */
public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView>{

    private final GetConfiguration getConfiguration;
    private final GetMovieDetails getMovieDetails;
    private final GetSimilarMovies getSimilarMovies;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public MovieDetailsPresenter(GetConfiguration getConfiguration, GetMovieDetails getMovieDetails, GetSimilarMovies getSimilarMovies) {
        this.getConfiguration = getConfiguration;
        this.getMovieDetails = getMovieDetails;
        this.getSimilarMovies = getSimilarMovies;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void viewShowed() {
        super.viewShowed();
        compositeDisposable.add(executeMovieDetails(view.getMovieId()));
        compositeDisposable.add(executeSimilarMovies(view.getMovieId()));
    }


    @Override
    public void viewHidden() {
        super.viewHidden();
        compositeDisposable.clear();
    }


    private Disposable executeSimilarMovies(long movieId){
        return Observable.zip(
                getConfiguration.toObservable(null),
                getSimilarMovies.toObservable(new GetSimilarMovies.Params(movieId,1)),
                ((configuration, pageMovie) -> MovieModelMapper.transform(pageMovie.getMovies(),configuration)))
                .subscribeWith(new SimilarDisposable());
    }

    private Disposable executeMovieDetails(long movieId) {
        return Observable.zip(
                getConfiguration.toObservable(null),
                getMovieDetails.toObservable(new GetMovieDetails.Params(movieId)),
                ((configuration, movie) -> MovieModelMapper.transform(movie,configuration))
                )
                .subscribeWith(new MovieDisposable());

    }

    class MovieDisposable extends SimpleDisposableObserver<MovieModel> {
        @Override
        public void onNext(MovieModel movieModel) {
            super.onNext(movieModel);
            view.setTitle(movieModel.getTitle());
            view.setOverview(movieModel.getOverview());
        }
    }

    private class SimilarDisposable extends SimpleDisposableObserver<List<MovieModel>>{
        @Override
        public void onNext(List<MovieModel> movieModels) {
            super.onNext(movieModels);
            view.showSimilarMovie(movieModels);
        }
    }
}
