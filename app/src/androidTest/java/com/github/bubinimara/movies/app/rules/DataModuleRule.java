package com.github.bubinimara.movies.app.rules;

import android.app.Instrumentation;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.github.bubinimara.movies.app.MockMovieApp;
import com.github.bubinimara.movies.app.rules.module.TestApplicationDataModule;
import com.github.bubinimara.movies.domain.repository.ConfigurationRepository;
import com.github.bubinimara.movies.domain.repository.LanguageRepository;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by davide.
 * Used to isolate test from data module
 */
public class DataModuleRule implements TestRule {

    private final @NonNull
    Instrumentation mInstrumentation;

    private final @NonNull
    TestApplicationDataModule applicationDataModule;

    public DataModuleRule(@NonNull TestApplicationDataModule applicationDataModule) {
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        this.applicationDataModule = applicationDataModule;
    }

    public MovieRepository getMovieRepository() {
        return applicationDataModule.provideMovieRepository(null);
    }

    public LanguageRepository getLanguageRepository() {
        return applicationDataModule.provideLanguageRepository(null);
    }

    public ConfigurationRepository getConfigurationRepository() {
        return applicationDataModule.provideConfigurationRepository(null);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                getApp().setApplicationDataModule(applicationDataModule);
                base.evaluate();
                getApp().setApplicationDataModule(null);
            }
        };
    }

    private MockMovieApp getApp() {
        return (MockMovieApp) mInstrumentation.getTargetContext().getApplicationContext();
    }

}
