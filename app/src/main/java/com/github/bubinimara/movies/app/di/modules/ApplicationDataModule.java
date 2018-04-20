package com.github.bubinimara.movies.app.di.modules;

import com.github.bubinimara.movies.data.ConfigurationRepositoryImpl;
import com.github.bubinimara.movies.data.LanguageRepositoryImpl;
import com.github.bubinimara.movies.data.MovieRepositoryImpl;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by davide.
 */
@Singleton
@Module
public class ApplicationDataModule {

    @Singleton
    @Provides
    public MovieRepository provideMovieRepository(MovieRepositoryImpl repository){
        return repository;
    }


    @Singleton
    @Provides
    public LanguageRepository provideLanguageRepository(LanguageRepositoryImpl repository){
        return repository;
    }

    @Singleton
    @Provides
    public ConfigurationRepository provideConfigurationRepository(ConfigurationRepositoryImpl repository){
        return repository;
    }
}
