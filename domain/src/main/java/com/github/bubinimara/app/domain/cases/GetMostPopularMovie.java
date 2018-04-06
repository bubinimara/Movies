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

public class GetMostPopularMovie extends UseCase<PageMovie, GetMostPopularMovie.Params> {
    private final Repository repository;

    @Inject
    public GetMostPopularMovie(UiScheduler uiScheduler, BgScheduler bgScheduler, Repository repository) {
        super(uiScheduler, bgScheduler);
        this.repository = repository;
    }
    @Override
    Observable<PageMovie> buildObservable(Params params) {
        return repository.getMostPopularMovies(params.page);
    }

    public static class Params{
        int page;

        public Params(int page) {
            this.page = page;
        }
    }
}
