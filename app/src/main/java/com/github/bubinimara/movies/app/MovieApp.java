package com.github.bubinimara.movies.app;

import android.app.Application;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.di.components.ActivityComponent;
import com.github.bubinimara.movies.app.di.components.ApplicationComponent;
import com.github.bubinimara.movies.app.di.components.DaggerActivityComponent;
import com.github.bubinimara.movies.app.di.components.DaggerApplicationComponent;
import com.github.bubinimara.movies.app.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by davide.
 */
// TODO: check model movie mapper use page and remove it - should use only movieModel
public class MovieApp extends Application {

    private ApplicationComponent applicationComponent;
    private ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDI();

        installLeakCanary();
    }

    protected void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if(BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    protected void initDI(){
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
