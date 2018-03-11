package com.github.bubinimara.movies.fragment.search;

import com.github.bubinimara.movies.IPresenter;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by davide.
 */

public class SearchPresenter implements IPresenter<SearchView> {

    private SearchView searchView;
    private MockRepo mockRepo;

    private int currentPageNumber;
    private String currentSearchTerm;

    private Disposable disposable;

    private PublishSubject<State> statePublishSubject;

    public SearchPresenter() {
        mockRepo = new MockRepo();
        currentPageNumber = 0;
        currentSearchTerm = "";
        statePublishSubject = PublishSubject.create();
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
        disposable = statePublishSubject.flatMap(state -> mockRepo.searchMovie(state.search,state.page))
                .subscribe(this::onSuccess);
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
        statePublishSubject.onNext(new State(currentSearchTerm,currentPageNumber));
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
                if(!emitter.isDisposed())
                    emitter.onNext(getMore(search,page));
            });
        }
    }

}
