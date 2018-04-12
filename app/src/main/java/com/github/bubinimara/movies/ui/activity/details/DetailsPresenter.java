package com.github.bubinimara.movies.ui.activity.details;

import com.github.bubinimara.movies.domain.cases.GetConfiguration;
import com.github.bubinimara.movies.domain.cases.GetMovieDetails;
import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.model.mapper.MovieModelMapper;
import com.github.bubinimara.movies.rx.SimpleDisposableObserver;
import com.github.bubinimara.movies.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by davide.
 */
public class DetailsPresenter extends BasePresenter<DetailsView> {

    private final GetMovieDetails getMovieDetails;
    private final GetConfiguration getConfiguration;
    private final CompositeDisposable compositeDisposable;
    @Inject
    public DetailsPresenter(GetMovieDetails getMovieDetails, GetConfiguration getConfiguration) {
        this.getMovieDetails = getMovieDetails;
        this.getConfiguration = getConfiguration;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void viewShowed() {
        super.viewShowed();
        if(!view.isRestored()){
            initialize();
        }
    }

    @Override
    public void viewHidden() {
        super.viewHidden();
        compositeDisposable.dispose();
    }

    private void initialize() {
        long movieId = view.getMovieId();
        if(movieId == -1){
            // threat error
            return;
        }else{
            compositeDisposable.add(execute(movieId));
        }
    }

    private Disposable execute(long movieId) {
        return getConfiguration.toObservable(null)
                .flatMap(configuration -> getMovieDetails.toObservable(new GetMovieDetails.Params(movieId))
                        .map(m -> MovieModelMapper.transform(m,configuration))
                ).subscribeWith(new MovieObservable());
    }

    public void onMovieClicked(MovieModel movie) {
        view.showDetails(movie);
    }


    private class MovieObservable extends SimpleDisposableObserver<MovieModel> {
        @Override
        public void onNext(MovieModel movie) {
            super.onNext(movie);
            view.showDetails(movie);
        }
    }
}
