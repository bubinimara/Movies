package com.github.bubinimara.movies.app.rules;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.github.bubinimara.movies.app.TestMovieApp;
import com.github.bubinimara.movies.app.rules.module.TestApplicationDataModule;

/**
 * Created by davide.
 */
public class BaseActivityTestRule<T extends Activity> extends ActivityTestRule<T> {
    private @Nullable TestApplicationDataModule applicationDataModule;
    private Instrumentation mInstrumentation;

    public BaseActivityTestRule(Class<T> activityClass) {
        this(activityClass,false,false);
    }

    public BaseActivityTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActyivity) {
        super(activityClass, initialTouchMode,launchActyivity);
        this.mInstrumentation = InstrumentationRegistry.getInstrumentation();
    }

    public T launchActivity(@Nullable Intent startIntent, TestApplicationDataModule applicationDataModule) {
        this.applicationDataModule = applicationDataModule;
        return launchActivity(startIntent);
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        if(applicationDataModule!=null) {
            getApp().setApplicationDataModule(applicationDataModule);
        }
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        applicationDataModule = null;
        getApp().clearApplicationDataModule();
    }

    protected TestMovieApp getApp() {
        return (TestMovieApp) mInstrumentation.getTargetContext().getApplicationContext();
    }

}
