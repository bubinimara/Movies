package com.github.bubinimara.movies.app;

import android.support.annotation.NonNull;

import com.github.bubinimara.movies.app.di.modules.ApplicationDataModule;
import com.github.bubinimara.movies.app.di.modules.ApplicationModule;

import dagger.Module;

/**
 * Created by davide.
 */
public class FakeMovieApp extends MovieApp {

    @NonNull
    @Override
    protected ApplicationDataModule buildApplicationDataModule() {
        return new MockApplicationDataModule();
    }

    @Override
    protected void installLeakCanary() {

    }

    private static class MockApplicationDataModule extends ApplicationDataModule {

    }
}
