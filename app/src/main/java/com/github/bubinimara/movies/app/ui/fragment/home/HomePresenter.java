package com.github.bubinimara.movies.app.ui.fragment.home;

import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.cases.GetConfiguration;
import com.github.bubinimara.movies.domain.cases.GetMostPopularMovie;
import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.model.mapper.MovieModelMapper;
import com.github.bubinimara.movies.app.rx.SimpleDisposableObserver;
import com.github.bubinimara.movies.app.ui.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by davide.
 */
public class HomePresenter extends BasePresenter<HomeView> {

    @Inject
    GetMostPopularMovie getMostPopularMovies;

    @Inject
    GetConfiguration getConfiguration;

    private final PublishSubject<Integer> statePublishSubject;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public HomePresenter() {
        statePublishSubject = PublishSubject.create();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void viewShowed() {
        super.viewShowed();
        initialize();
    }

    private void initialize() {
        compositeDisposable.add(buildDisposableForPageChange());
        if(!view.isRestored()){
            onLoadMore(0);
        }
    }

    @Override
    public void viewHidden() {
        super.viewHidden();
        compositeDisposable.clear();
    }

    @Override
    public void unbindView() {
        super.unbindView();
        compositeDisposable.dispose();
    }

    private Disposable buildDisposableForPageChange() {
        return statePublishSubject
                .distinct()
                .concatMap(this::getNextPageOfMovies)
                .subscribeWith(new ListMovieDisposable())
                ;
    }

    private Observable<List<MovieModel>> getNextPageOfMovies(int page) {
        return Observable
                .zip(getConfiguration.toObservable(null),
                        getMostPopularMovies.toObservable(new GetMostPopularMovie.Params(page))
                        .map(PageMovie::getMovies),
                        (configuration, movie) ->
                                MovieModelMapper.transform(movie, configuration));
    }


    public void onLoadMore(int currentPage){
        view.showProgress();
        statePublishSubject.onNext(currentPage+1);
    }

    public void onMovieClicked(MovieModel movieModel) {
        view.showDetailsView(movieModel);
    }


    class ListMovieDisposable extends SimpleDisposableObserver<List<MovieModel>>{

        @Override
        public void onNext(List<MovieModel> movies) {
            view.hideProgress();
            view.showMovies(movies);
        }

        @Override
        public void onError(Throwable e) {
            view.hideProgress();
            view.showError(HomeView.Errors.UNKNOWN);
            e.printStackTrace();
        }

    }
}
