package com.github.bubinimara.movies.app.rules.module;

import com.github.bubinimara.movies.domain.Configuration;
import com.github.bubinimara.movies.domain.Language;
import com.github.bubinimara.movies.domain.Movie;
import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Data Module that return empty data
 */
public class EmptyApplicationDataModule extends TestApplicationDataModule {
    @Override
    protected MovieRepository createMovieRepository() {
        return new EmptyMovieRepository();
    }

    @Override
    protected LanguageRepository createLanguageRepository() {
        return new EmptyLanguageRepository();
    }

    @Override
    protected ConfigurationRepository createConfigurationRepository() {
        return new EmptyConfigurationRepository();
    }

    static class EmptyConfigurationRepository implements ConfigurationRepository {
        @Override
        public Observable<Configuration> getConfiguration() {
            return Observable.just(new Configuration());
        }
    }

    static class EmptyLanguageRepository implements LanguageRepository {
        @Override
        public Observable<Language> getLanguageInUse() {
            return Observable.just(new Language());
        }

        @Override
        public void setLanguageInUse(Language languageInUse) {

        }

        @Override
        public Observable<List<Language>> getLanguages() {
            return Observable.just(new ArrayList<>());
        }
    }

    static class EmptyMovieRepository implements MovieRepository {
        @Override
        public Observable<PageMovie> getMostPopularMovies(String language, int pageNumber) {
            return Observable.just(new PageMovie());
        }

        @Override
        public Observable<PageMovie> searchMovie(String language, String searchTerm, int pageNumber) {
            return Observable.just(new PageMovie());
        }

        @Override
        public Observable<PageMovie> getSimilarMovies(String language, long movieId, int page) {
            return Observable.just(new PageMovie());
        }

        @Override
        public Observable<Movie> getMovieById(String language, long movieId) {
            return Observable.just(new Movie());
        }
    }
}
