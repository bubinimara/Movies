package com.github.bubinimara.movies.app;

import android.support.annotation.NonNull;

import com.github.bubinimara.movies.app.di.modules.ApplicationDataModule;

/**
 * Created by davide.
 */
public class MockMovieApp extends MovieApp {

    private ApplicationDataModule applicationModule;

    @NonNull
    @Override
    protected ApplicationDataModule buildApplicationDataModule() {
        if(applicationModule!=null)
            return applicationModule;
        return super.buildApplicationDataModule();
    }


    public void setApplicationDataModule(ApplicationDataModule applicationModule) {
        this.applicationModule = applicationModule;
    }

    public void clearApplicationDataModule(){
        this.applicationModule = null;
        this.isDiInitialized = false;
    }

    @Override
    protected void installLeakCanary() { }


}
