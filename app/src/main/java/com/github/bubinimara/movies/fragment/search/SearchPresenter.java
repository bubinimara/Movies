package com.github.bubinimara.movies.fragment.search;

import com.github.bubinimara.movies.IPresenter;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;



/**
 * Created by davide.
 */

public class SearchPresenter implements IPresenter<SearchView> {

    private final Scheduler uiScheduler;
    private final Scheduler bgScehduler;
    private SearchView searchView;
    private MockRepo mockRepo;

    private int currentPageNumber;
    private String currentSearchTerm;

    private Disposable disposable;
    private Disposable currentSearchDisposable;
    private PublishSubject<State> statePublishSubject;

    public SearchPresenter(Scheduler bgScehduler, Scheduler uiScheduler) {
        mockRepo = new MockRepo();
        currentPageNumber = 0;
        currentSearchTerm = "";
        statePublishSubject = PublishSubject.create();
        this.bgScehduler =  bgScehduler;
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
        statePublishSubject.onNext(new State(currentSearchTerm,currentPageNumber));
    }



    private void observForPageChange(){
        //TODO: check cancel
        disposable = statePublishSubject
                .flatMap(state -> mockRepo.searchMovie(state.search,state.page)
                        .doOnSubscribe(this::cancelCurrentSearchMovieTask)
                        .subscribeOn(bgScehduler)
                        .observeOn(uiScheduler))
                .subscribe(this::onSuccess);
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
        this.searchView.showEmptyMovies();

        currentSearchTerm = s;
        statePublishSubject.onNext(new SearchPresenter.State(currentSearchTerm,currentPageNumber));
    }

    static class State{
        String search;
        int page;

        public State(String search, int page) {
            this.search = search;
            this.page = page;
        }
    }
    private static class MockRepo{


        private ArrayList<MovieModel> getMore(String search, int pageNumber){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<MovieModel> lm = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                MovieModel m = new MovieModel();
                m.setTitle("Search "+pageNumber+"_"+i + ": "+search);
                lm.add(m);
            }

            return lm;
        }

        public Observable<List<MovieModel>> searchMovie(String search, int page) {
            return Observable.create(emitter -> {
                if(!emitter.isDisposed()) {
                    emitter.onNext(getMore(search, page));
                }
            });
        }
    }

}