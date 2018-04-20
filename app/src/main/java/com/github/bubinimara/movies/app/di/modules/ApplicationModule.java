package com.github.bubinimara.movies.app.di.modules;

import android.content.Context;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.MovieApp;
import com.github.bubinimara.movies.data.ConfigurationRepositoryImpl;
import com.github.bubinimara.movies.data.LanguageRepositoryImpl;
import com.github.bubinimara.movies.data.MovieRepositoryImpl;
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
    public Context provideContext(){
        return application;
    }

    @Singleton
    @Provides
    public  ApiClient provideApiClient(){
        return new ApiClient(BuildConfig.TMDB_API_KEY);
    }

    @Singleton
    @Provides
    public ApiTmb provideApiTmb(ApiClient apiClient){
        return apiClient.getApiTmb();
    }


    @Provides
    public UiScheduler provideUiScheduler(){
        return AndroidSchedulers::mainThread;
    }

    @Singleton
    @Provides
    public BgScheduler provideBgScheduler(){
        return Schedulers::io;
    }

    @Singleton
    @Provides
    @Named("Home")
    public BigMovieAdapter provideMovieAdapterForHome(){
        return new BigMovieAdapter();
    }

    @Singleton
    @Provides
    @Named("Search")
    public BigMovieAdapter provideMovieAdapterForSearch(){
        return new BigMovieAdapter();
    }

    @Provides
    public ImageMovieAdapter provideImageMovieAdapter(){
        return new ImageMovieAdapter();
    }
}

