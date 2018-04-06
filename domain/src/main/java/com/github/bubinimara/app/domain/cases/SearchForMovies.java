package com.github.bubinimara.app.domain.cases;

import com.github.bubinimara.app.domain.PageMovie;
import com.github.bubinimara.app.domain.repository.Repository;
import com.github.bubinimara.app.domain.scheduler.BgScheduler;
import com.github.bubinimara.app.domain.scheduler.UiScheduler;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class SearchForMovies  extends UseCase<PageMovie,SearchForMovies.Params>{

    private final Repository repository;

    @Inject
    public SearchForMovies(UiScheduler uiScheduler, BgScheduler bgScheduler, Repository repository) {
        super(uiScheduler, bgScheduler);
        this.repository = repository;
    }


    @Override
    Observable<PageMovie> buildObservable(Params params) {
        return repository.searchMovie(params.search,params.page);
    }

    public static class  Params{
        String search;
        int page;

        public Params(String search, int page) {
            this.search = search;
            this.page = page;
        }
    }
}
