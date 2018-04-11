package com.github.bubinimara.app.di.modules;

import com.github.bubinimara.app.di.ActivityScope;
import com.github.bubinimara.app.domain.scheduler.BgScheduler;
import com.github.bubinimara.app.domain.scheduler.UiScheduler;
import com.github.bubinimara.app.ui.adapter.ImageMovieAdapter;

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


}
