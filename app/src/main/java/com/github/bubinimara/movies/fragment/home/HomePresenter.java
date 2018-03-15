package com.github.bubinimara.movies.fragment.home;

import com.github.bubinimara.movies.IPresenter;
import com.github.bubinimara.movies.data.Repository;
import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.model.mapper.MovieModelMapper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by davide.
 */

public class HomePresenter implements IPresenter<HomeView> {
    private static final String TAG = HomePresenter.class.getSimpleName();
    private final Scheduler uiScheduler;
    private final Scheduler bgScheduler;
    private final Repository repository;

    private HomeView homeView;

    private int currentPageNumber;
    private Disposable disposable;

    private PublishSubject<Integer> statePublishSubject;

    public HomePresenter(Repository repository, Scheduler bgScheduler, Scheduler uiScheduler) {
        this.repository = repository;
        this.bgScheduler = bgScheduler;
        this.uiScheduler = uiScheduler;

        currentPageNumber = 1;
        statePublishSubject = PublishSubject.create();
    }

    @Override
    public void bindView(HomeView view) {
        this.homeView = view;
        initialize();
    }

    @Override
    public void unbind() {
        this.homeView = null;
        clear();
    }

    private void initialize() {
        /*observeForPageChange();*/
        homePresenterState.saveState("",1);
        disposable = Observable.concat(buildObserverForStateChanges(),buildObserverForViews())

                .subscribeOn(bgScheduler)
                .observeOn(uiScheduler)
                .subscribe(this::onSuccess,this::onError);
    }

    private void observeForPageChange(){
        disposable = statePublishSubject
                .startWith(currentPageNumber)
                .flatMap(this::getMostPopularMoviesFromRepo)
                .observeOn(uiScheduler)
                .subscribeOn(bgScheduler)
                .subscribe(this::onSuccess,this::onError);
    }

    private ObservableSource<List<MovieModel>> getMostPopularMoviesFromRepo(Integer page) {
        Log.d(TAG, "getMostPopularMoviesFromRepo1: "+page);
        return repository
                .getMostPopularMovies(page)
                .map(MovieModelMapper::transform);
    }
    private ObservableSource<List<MovieModel>> getMostPopularMoviesFromRepo2(Integer page) {
        Log.d(TAG, "getMostPopularMoviesFromRepo2: "+page);
        return repository
                .getMostPopularMovies(page)
                .map(MovieModelMapper::transform)
                .observeOn(uiScheduler)
                .subscribeOn(bgScheduler)
                ;
    }

    Observable<List<MovieModel>> buildObserverForStateChanges(){
        return homePresenterState.loadState("")
                .defaultIfEmpty(5)
                .onErrorReturnItem(1)
                .flatMap(i -> Observable.range(1, i))
                .flatMap(this::getMostPopularMoviesFromRepo)
                .flatMapIterable(i -> i)
                .toList().toObservable();

    }
    Observable<List<MovieModel>> buildObserverForViews(){
        return statePublishSubject.flatMap(this::getMostPopularMoviesFromRepo2)
                ;
    }

    private void clear() {
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private void onSuccess(List<MovieModel> movieModels) {
        this.homeView.showMovies(movieModels,currentPageNumber);
    }

    private void onError(Throwable throwable) {
        homeView.showError(HomeView.Errors.UNKNOWN);
        throwable.printStackTrace();
    }
    public void onLoadMore(int currentPage){
        currentPageNumber++;
        statePublishSubject.onNext(currentPageNumber);
    }
}
