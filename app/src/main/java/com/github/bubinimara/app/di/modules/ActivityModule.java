package com.github.bubinimara.app.di.modules;

import com.github.bubinimara.app.di.ActivityScope;
import com.github.bubinimara.app.domain.scheduler.BgScheduler;
import com.github.bubinimara.app.domain.scheduler.UiScheduler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davide.
 */
@ActivityScope
@Module
public class ActivityModule {
    @Provides
    @Named("bgScheduler")
    Scheduler provideBgScheduler(){
        return Schedulers.io();
    }

    @Provides
    @Named("uiScheduler")
    Scheduler provideUiScheduler(){
        return AndroidSchedulers.mainThread();
    }

}
