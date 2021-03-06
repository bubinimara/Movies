package com.github.bubinimara.movies.app.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.bubinimara.movies.app.MovieApp;
import com.github.bubinimara.movies.app.di.components.ActivityComponent;
import com.github.bubinimara.movies.app.ui.IView;

/**
 * Created by davide.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {

    private boolean isRestored;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRestored = savedInstanceState!=null;
    }

    protected ActivityComponent getActivityComponent(){
        // TODO: Find differences between dagger declaration of component
      return ((MovieApp) getApplication()).getActivityComponent();
    }

    @Override
    public boolean isRestored() {
        return isRestored;
    }

    @Override
    public void clearRestored() {
        isRestored = false;
    }


}
