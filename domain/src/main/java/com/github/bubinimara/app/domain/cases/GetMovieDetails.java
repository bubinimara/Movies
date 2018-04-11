package com.github.bubinimara.app.domain.cases;

import com.github.bubinimara.app.domain.Movie;
import com.github.bubinimara.app.domain.repository.ConfigurationRepository;
import com.github.bubinimara.app.domain.repository.LanguageRepository;
import com.github.bubinimara.app.domain.repository.MovieRepository;
import com.github.bubinimara.app.domain.scheduler.BgScheduler;
import com.github.bubinimara.app.domain.scheduler.UiScheduler;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide.
 */
public class GetMovieDetails extends UseCase<Movie,GetMovieDetails.Params> {
    private final MovieRepository movieRepository;
    private final LanguageRepository languageRepository;


    @Inject
    public GetMovieDetails(UiScheduler uiScheduler, BgScheduler bgScheduler, MovieRepository movieRepository, LanguageRepository languageRepository) {
        super(uiScheduler, bgScheduler);
        this.movieRepository = movieRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    Observable<Movie> buildObservable(Params params) {
        return languageRepository.getLanguageInUse().flatMap(
                language -> movieRepository.getMovieById(language.getIsoCode(),params.movieId)
        );
    }

    public static class Params{
        long movieId;

        public Params(long movieId) {
            this.movieId = movieId;
        }
    }
}
