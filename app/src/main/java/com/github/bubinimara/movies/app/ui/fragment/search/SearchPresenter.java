package com.github.bubinimara.movies.app.ui.fragment.search;

import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.cases.GetConfiguration;
import com.github.bubinimara.movies.domain.cases.SearchForMovies;
import com.github.bubinimara.movies.app.model.mapper.MovieModelMapper;
import com.github.bubinimara.movies.app.ui.BasePresenter;
import com.github.bubinimara.movies.app.util.StringUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;



/**
 * Created by davide.
 */
//TODO: image details maybe should handled by others ( not this class responsibility )
public class SearchPresenter extends BasePresenter<SearchView> {

    private PublishSubject<State> statePublishSubject;
    private CompositeDisposable compositeDisposable;
    private CompositeDisposable useCaseDisposable;

    private final SearchForMovies searchForMovies;
    private final GetConfiguration getConfiguration;

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
    }

    @Override
    public void viewHidden() {
        super.viewHidden();
        clear();
    }

    private void initialize(){

        compositeDisposable.add(
                statePublishSubject
                .debounce(300, TimeUnit.MILLISECONDS) // reduce frequency
                .distinctUntilChanged() // ignore same request
                .subscribe(state -> {
                    useCaseDisposable.clear();
                    useCaseDisposable.add(onStateChange(state));
                })
        );

    }

    private Disposable onStateChange(State state) {
        return Observable.zip(
                getConfiguration.toObservable(new GetConfiguration.Params()),
                searchForMovies.toObservable(new SearchForMovies.Params(state.search,state.page)),
                (configuration,pageMovies)-> new Object[]{configuration,pageMovies})
                .subscribe((n)->{
                    Configuration configuration = (Configuration) n[0];
                    PageMovie pageMovies = (PageMovie) n[1];

                    view.hideProgress();
                    view.hideError();
                    if(pageMovies.isFirstPage()){
                        view.showEmptyMovies();
                    }
                    view.showMovies(MovieModelMapper.transform(pageMovies,configuration));
                    },(e)->{
                    view.hideProgress();
                    view.showError(SearchView.Errors.UNKNOWN);
                    e.printStackTrace();
                });
    }

    private void clear() {
        compositeDisposable.dispose();
        useCaseDisposable.dispose();
    }


    public void onViewLoadPage(int page){
        view.showProgress();
        statePublishSubject.onNext(new State(view.getSearchTerm(), page));
    }
    public void onViewSearchTermChange(String term){
        view.showProgress();
        statePublishSubject.onNext(new State(term, 1));
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
