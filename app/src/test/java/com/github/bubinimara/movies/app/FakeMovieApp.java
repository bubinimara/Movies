package com.github.bubinimara.movies.app;

import com.github.bubinimara.movies.app.di.components.ActivityComponent;
import com.github.bubinimara.movies.app.di.components.ApplicationComponent;
import com.github.bubinimara.movies.app.di.components.DaggerActivityComponent;
import com.github.bubinimara.movies.app.di.components.DaggerApplicationComponent;
import com.github.bubinimara.movies.app.di.modules.ApplicationModule;

import dagger.Module;

/**
 * Created by davide.
 */
public class FakeMovieApp extends MovieApp {

    private ApplicationComponent applicationComponent ;
    private ActivityComponent activityComponent;


    @Override
    protected void initDI() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new MockApplicationModule(this))
                .build();
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build();
    }

    @Override
    protected void installLeakCanary() {

    }

    @Override
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }


    @Module
    public static class MockApplicationModule extends ApplicationModule{
        public MockApplicationModule(MovieApp application) {
            super(application);
        }
    }

}
