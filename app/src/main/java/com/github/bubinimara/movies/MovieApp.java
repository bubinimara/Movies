package com.github.bubinimara.movies;

import android.app.Application;

import com.github.bubinimara.movies.di.components.ActivityComponent;
import com.github.bubinimara.movies.di.components.ApplicationComponent;
import com.github.bubinimara.movies.di.components.DaggerActivityComponent;
import com.github.bubinimara.movies.di.components.DaggerApplicationComponent;
import com.github.bubinimara.movies.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by davide.
 */

public class MovieApp extends Application {

    private ApplicationComponent applicationComponent;
    private ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDI();

        installLeakCanary();
    }

    private void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if(BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    private void initDI(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build();
    }
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
