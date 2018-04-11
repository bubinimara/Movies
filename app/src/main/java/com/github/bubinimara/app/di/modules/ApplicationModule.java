package com.github.bubinimara.app.di.modules;

import android.content.Context;

import com.github.bubinimara.app.BuildConfig;
import com.github.bubinimara.app.MovieApp;
import com.github.bubinimara.app.data.ConfigurationRepositoryImpl;
import com.github.bubinimara.app.data.LanguageRepositoryImpl;
import com.github.bubinimara.app.data.MovieRepositoryImpl;
import com.github.bubinimara.app.data.net.ApiClient;
import com.github.bubinimara.app.data.net.ApiTmb;
import com.github.bubinimara.app.domain.repository.ConfigurationRepository;
import com.github.bubinimara.app.domain.repository.LanguageRepository;
import com.github.bubinimara.app.domain.repository.MovieRepository;
import com.github.bubinimara.app.domain.scheduler.BgScheduler;
import com.github.bubinimara.app.domain.scheduler.UiScheduler;
import com.github.bubinimara.app.ui.adapter.BigMovieAdapter;
import com.github.bubinimara.app.ui.adapter.ImageMovieAdapter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davide.
 */
@Singleton
@Module
public class ApplicationModule {
    private final MovieApp application;

    public ApplicationModule(MovieApp application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext(){
        return application;
    }

    @Singleton
    @Provides
    ApiClient provideApiClient(){
        return new ApiClient(BuildConfig.TMDB_API_KEY);
    }

    @Singleton
    @Provides
    ApiTmb provideApiTmb(ApiClient apiClient){
        return apiClient.getApiTmb();
    }

    @Singleton
    @Provides
    MovieRepository provideMovieRepository(MovieRepositoryImpl repository){
        return repository;
    }


    @Singleton
    @Provides
    LanguageRepository provideLanguageRepository(LanguageRepositoryImpl repository){
        return repository;
    }

    @Singleton
    @Provides
    ConfigurationRepository configurationRepository(ConfigurationRepositoryImpl repository){
        return repository;
    }

    @Provides
    UiScheduler provideUiScheduler(){
        return AndroidSchedulers::mainThread;
    }

    @Singleton
    @Provides
    BgScheduler provideBgScheduler(){
        return Schedulers::io;
    }

    @Singleton
    @Provides
    @Named("Home")
    BigMovieAdapter provideMovieAdapterForHome(){
        return new BigMovieAdapter();
    }

    @Singleton
    @Provides
    @Named("Search")
    BigMovieAdapter provideMovieAdapterForSearch(){
        return new BigMovieAdapter();
    }

    @Provides
    ImageMovieAdapter provideImageMovieAdapter(){
        return new ImageMovieAdapter();
    }
}

