package com.github.bubinimara.app.di.components;

import android.content.Context;

import com.github.bubinimara.app.domain.repository.MovieRepository;
import com.github.bubinimara.app.ui.adapter.MovieAdapter;
import com.github.bubinimara.app.domain.repository.ConfigurationRepository;
import com.github.bubinimara.app.di.modules.ApplicationModule;
import com.github.bubinimara.app.domain.scheduler.BgScheduler;
import com.github.bubinimara.app.domain.scheduler.UiScheduler;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by davide.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    // expose to sub-graph
    Context provideContext();
    MovieRepository provideRepository();
    ConfigurationRepository configurationRepository();
    UiScheduler provideuiScheduler();
    BgScheduler providebgScheduler();
    @Named("Home") MovieAdapter provideMovieAdapterForHome();
    @Named("Search") MovieAdapter provideMovieAdapterForSearch();
}
