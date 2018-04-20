package com.github.bubinimara.movies.app.rules.module;

import com.github.bubinimara.movies.app.di.modules.ApplicationDataModule;
import com.github.bubinimara.movies.data.ConfigurationRepositoryImpl;
import com.github.bubinimara.movies.data.LanguageRepositoryImpl;
import com.github.bubinimara.movies.data.MovieRepositoryImpl;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

/**
 * Created by davide.
 */
public abstract class TestApplicationDataModule extends ApplicationDataModule {
    private ConfigurationRepository configurationRepository;
    private LanguageRepository languageRepository;
    private MovieRepository movieRepository;

    public TestApplicationDataModule() {
        this.configurationRepository = createConfigurationRepository();
        this.languageRepository = createLanguageRepository();
        this.movieRepository = createMovieRepository();
    }

    protected abstract MovieRepository createMovieRepository();

    protected abstract LanguageRepository createLanguageRepository();

    protected abstract ConfigurationRepository createConfigurationRepository();


    @Override
    public MovieRepository provideMovieRepository(MovieRepositoryImpl repository) {
        return movieRepository;
    }

    @Override
    public LanguageRepository provideLanguageRepository(LanguageRepositoryImpl repository) {
        return languageRepository;
    }

    @Override
    public ConfigurationRepository provideConfigurationRepository(ConfigurationRepositoryImpl repository) {
        return configurationRepository;
    }
}
