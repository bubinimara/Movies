package com.github.bubinimara.movies.app.di.components;

import android.content.Context;

import com.github.bubinimara.movies.app.MovieApp;
import com.github.bubinimara.movies.app.di.modules.ApplicationDataModule;
import com.github.bubinimara.movies.app.di.modules.ApplicationModule;
import com.github.bubinimara.movies.data.net.ApiClient;
import com.github.bubinimara.movies.data.net.ApiTmb;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;
import com.github.bubinimara.movies.domain.scheduler.BgScheduler;
import com.github.bubinimara.movies.domain.scheduler.UiScheduler;
import com.github.bubinimara.movies.app.ui.adapter.BigMovieAdapter;
import com.github.bubinimara.movies.app.ui.adapter.ImageMovieAdapter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by davide.
 */
@Singleton
@Component(modules = {ApplicationModule.class,ApplicationDataModule.class})
public interface ApplicationComponent {
    // expose to sub-graph
    Context provideContext();

    MovieRepository provideMovieRepository();
    LanguageRepository provideLanguageRepository();
    ConfigurationRepository provideConfigurationRepository();

    UiScheduler provideuiScheduler();
    BgScheduler providebgScheduler();

    @Named("Home")
    BigMovieAdapter provideMovieAdapterForHome();
    @Named("Search") BigMovieAdapter provideMovieAdapterForSearch();
    ImageMovieAdapter provideImageMovieAdapter();
}
