package com.github.bubinimara.movies.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.di.components.ActivityComponent;
import com.github.bubinimara.movies.app.di.components.ApplicationComponent;
import com.github.bubinimara.movies.app.di.components.DaggerActivityComponent;
import com.github.bubinimara.movies.app.di.components.DaggerApplicationComponent;
import com.github.bubinimara.movies.app.di.modules.ApplicationDataModule;
import com.github.bubinimara.movies.app.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by davide.
 */
// TODO: check model movie mapper use page and remove it - should use only movieModel
public class MovieApp extends Application {

    protected ApplicationComponent applicationComponent;
    protected ActivityComponent activityComponent;
    protected boolean isDiInitialized = false;
    @Override
    public void onCreate() {
        super.onCreate();
        //initDI(); // lazy loading for test
        isDiInitialized = false;
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
                .applicationModule(buildApplicationModule())
                .applicationDataModule(buildApplicationDataModule())
                .build();
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build();
    }


    @NonNull
    protected ApplicationDataModule buildApplicationDataModule() {
        return new ApplicationDataModule();
    }

    @NonNull
    protected ApplicationModule buildApplicationModule() {
        return new ApplicationModule(this);
    }

    public ApplicationComponent getApplicationComponent() {
        if(!isDiInitialized){
            initDI();
        }
        return applicationComponent;
    }

    public ActivityComponent getActivityComponent() {
        if(!isDiInitialized){
            initDI();
        }
        return activityComponent;
    }

}
