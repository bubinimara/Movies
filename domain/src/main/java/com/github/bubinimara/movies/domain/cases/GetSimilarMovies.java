package com.github.bubinimara.movies.domain.cases;

import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;
import com.github.bubinimara.movies.domain.scheduler.BgScheduler;
import com.github.bubinimara.movies.domain.scheduler.UiScheduler;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public class GetSimilarMovies extends UseCase<PageMovie,GetSimilarMovies.Params> {
    private final MovieRepository movieRepository;
    private final LanguageRepository languageRepository;

    @Inject
    public GetSimilarMovies(UiScheduler uiScheduler, BgScheduler bgScheduler, MovieRepository movieRepository, LanguageRepository languageRepository) {
        super(uiScheduler, bgScheduler);
        this.movieRepository = movieRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    Observable<PageMovie> buildObservable(Params params) {
        return languageRepository.getLanguageInUse().flatMap(
                language -> movieRepository.getSimilarMovies(language.getIsoCode(),params.movieId,params.page)
        );
    }


    public static class Params{
        long movieId;
        int page;

        public Params(long movieId, int page) {
            this.movieId = movieId;
            this.page = page;
        }
    }
}
