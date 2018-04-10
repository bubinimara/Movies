package com.github.bubinimara.app.ui.activity.details;

import com.github.bubinimara.app.domain.cases.GetConfiguration;
import com.github.bubinimara.app.domain.cases.GetMovieDetails;
import com.github.bubinimara.app.model.MovieModel;
import com.github.bubinimara.app.model.mapper.MovieModelMapper;
import com.github.bubinimara.app.rx.SimpleDisposableObserver;
import com.github.bubinimara.app.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
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
            //view.showDetails(movieId);
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
