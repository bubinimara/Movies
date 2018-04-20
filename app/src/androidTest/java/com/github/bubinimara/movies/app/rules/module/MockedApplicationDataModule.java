package com.github.bubinimara.movies.app.rules.module;

import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import org.mockito.Mockito;

/**
 * Data Module that return mocked repository
 */
public class MockedApplicationDataModule extends TestApplicationDataModule {
    @Override
    protected MovieRepository createMovieRepository() {
        return Mockito.mock(MovieRepository.class);
    }

    @Override
    protected LanguageRepository createLanguageRepository() {
        return Mockito.mock(LanguageRepository.class);
    }

    @Override
    protected ConfigurationRepository createConfigurationRepository() {
        return Mockito.mock(ConfigurationRepository.class);
    }
}
