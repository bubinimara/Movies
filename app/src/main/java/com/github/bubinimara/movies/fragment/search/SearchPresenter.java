package com.github.bubinimara.movies.fragment.search;

import com.github.bubinimara.movies.IPresenter;
import com.github.bubinimara.movies.data.Repository;
import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.model.mapper.MovieModelMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;



/**
 * Created by davide.
 */

public class SearchPresenter implements IPresenter<SearchView> {

    private final Scheduler uiScheduler;
    private final Scheduler bgScheduler;
    private final Repository repository;
    private SearchView searchView;

    private int currentPageNumber;
    private String currentSearchTerm;

    private Disposable disposable;
    private Disposable currentSearchDisposable;
    private PublishSubject<State> statePublishSubject;

    public SearchPresenter(Repository repository, Scheduler bgScheduler, Scheduler uiScheduler) {
        this.repository = repository;
        currentPageNumber = 1;
        currentSearchTerm = "";
        statePublishSubject = PublishSubject.create();
        this.bgScheduler = bgScheduler;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void bindView(SearchView view) {
        this.searchView = view;
        initialize();
    }

    @Override
    public void unbind() {
        this.searchView = null;
        clear();
    }

    private void initialize() {
        observForPageChange();
    }



    private void observForPageChange(){
        disposable = statePublishSubject
                .debounce(300, TimeUnit.MILLISECONDS) // reduce frequency
                //.distinctUntilChanged() // ignore same request
                .switchMap(this::searchMovieFromRepo)
                .observeOn(uiScheduler)
                .subscribeOn(bgScheduler)
                .subscribeWith(new DisposableObserver<List<MovieModel>>(){
                    @Override
                    public void onNext(List<MovieModel> movies) {
                        onSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private ObservableSource<List<MovieModel>> searchMovieFromRepo(State state) {
        return repository
                .searchMovie(state.search,state.page)
                .doOnSubscribe(this::cancelCurrentSearchMovieTask)
                .map(MovieModelMapper::transform);
    }

    private void onError(Throwable throwable) {
        searchView.showError(SearchView.Errors.UNKNOWN);
        throwable.printStackTrace();
    }

    private void cancelCurrentSearchMovieTask(Disposable disposable) {
        if(currentSearchDisposable!=null){
            currentSearchDisposable.dispose();
        }
        currentSearchDisposable = disposable;
    }

    private void clear() {
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private void onSuccess(List<MovieModel> movieModels) {
        this.searchView.showMovies(movieModels,currentPageNumber);
    }


    public void onLoadMore(int currentPage){
        currentPageNumber++;
        statePublishSubject.onNext(new State(currentSearchTerm,currentPageNumber));
    }

    public void onSearch(String s) {
        currentPageNumber=1;
        currentSearchTerm = s;
        searchView.showEmptyMovies();
        if(currentSearchTerm.isEmpty()){
            return;
        }
        statePublishSubject
                .onNext(new SearchPresenter.State(currentSearchTerm,currentPageNumber));

    }

    static class State{
        String search;
        int page;

        public State(String search, int page) {
            this.search = search;
            this.page = page;
        }
    }
}
