package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;
import com.github.bubinimara.movies.domain.scheduler.BgScheduler;
import com.github.bubinimara.movies.domain.scheduler.UiScheduler;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */

public class SearchForMovies  extends UseCase<PageMovie,SearchForMovies.Params>{

    private final MovieRepository movieRepository;
    private final LanguageRepository languageRepository;


    @Inject
    public SearchForMovies(UiScheduler uiScheduler, BgScheduler bgScheduler, MovieRepository movieRepository, LanguageRepository languageRepository) {
        super(uiScheduler, bgScheduler);
        this.movieRepository = movieRepository;
        this.languageRepository = languageRepository;
    }


    @Override
    Observable<PageMovie> buildObservable(Params params) {
        if(params.search.isEmpty())return Observable.just(new PageMovie());
        return languageRepository.getLanguageInUse()
                .flatMap(language -> movieRepository.searchMovie(language.getIsoCode(),params.search,params.page));
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
