package com.github.bubinimara.movies.ui.fragment.search;

import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.cases.GetConfiguration;
import com.github.bubinimara.movies.domain.cases.SearchForMovies;
import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.model.mapper.MovieModelMapper;
import com.github.bubinimara.movies.rx.SimpleDisposableObserver;
import com.github.bubinimara.movies.ui.BasePresenter;
import com.github.bubinimara.movies.util.StringUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;



/**
 * Created by davide.
 */

public class SearchPresenter extends BasePresenter<SearchView> {

    private PublishSubject<State> statePublishSubject;
    private CompositeDisposable compositeDisposable;
    private CompositeDisposable useCaseDisposable;

    private final SearchForMovies searchForMovies;
    private final GetConfiguration getConfiguration;

    private String lastSearchTerm;

    @Inject
    public SearchPresenter(SearchForMovies searchForMovies, GetConfiguration getConfiguration) {
        this.searchForMovies = searchForMovies;
        this.getConfiguration = getConfiguration;
        this.statePublishSubject = PublishSubject.create();
        this.compositeDisposable = new CompositeDisposable();
        this.useCaseDisposable = new CompositeDisposable();
    }


    @Override
    public void viewShowed() {
        super.viewShowed();
        initialize();
        if(view.isRestored()){
            lastSearchTerm = view.getSearchTerm();
        }
    }

    @Override
    public void viewHidden() {
        super.viewHidden();
        clear();
    }

    private void initialize(){

        compositeDisposable.add(statePublishSubject
                .debounce(300, TimeUnit.MILLISECONDS) // reduce frequency
                .distinctUntilChanged() // ignore same request
                .filter(state -> !state.search.isEmpty()) // no empty search
                //.buffer(2,1) -> to compare previous elements
                .subscribe(state -> {
                    useCaseDisposable.clear();
                    useCaseDisposable.add(toObservable(state, new PageMovieDisposableObserver()));
                })
        );
    }

    private Disposable toObservable(@NonNull State state,@NonNull DisposableObserver<List<MovieModel>> observer){
        if(state == null){
            throw new RuntimeException("State cannot be null");
        }

        return Observable.zip(
                getConfiguration.toObservable(new GetConfiguration.Params()),
                searchForMovies.toObservable(new SearchForMovies.Params(state.search,state.page))
                        .map(PageMovie::getMovies),
                (configuration,movies)->MovieModelMapper.transformConf(movies,configuration))

                .subscribeWith(observer);
    }

    private void clear() {
        compositeDisposable.dispose();
        useCaseDisposable.dispose();
    }


    public void onViewStateChange(){
        if(isSearchTermChanged()){
            view.showEmptyMovies();
        }
        statePublishSubject.onNext(new State(view.getSearchTerm(), view.getPageNumber()));
    }


    private boolean isSearchTermChanged() {
        boolean isChanged = StringUtil.areNotEquals(lastSearchTerm,view.getSearchTerm());
        lastSearchTerm = view.getSearchTerm();
        return isChanged;
    }


    private class PageMovieDisposableObserver extends SimpleDisposableObserver<List<MovieModel>> {

        @Override
        public void onNext(List<MovieModel> movies) {
            view.showMovies(movies);
        }

        @Override
        public void onError(Throwable e) {
            view.showError(SearchView.Errors.UNKNOWN);
            e.printStackTrace();
        }
    }


    private static class State{
        String search;
        int page;

        State(@NonNull String search, int page) {
            this.search = search;
            this.page = page;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof State)){
                return super.equals(obj);
            }
            State s = (State) obj;
            return StringUtil.areEquals(s.search,search) &&
                    s.page==page;

        }
    }
}
