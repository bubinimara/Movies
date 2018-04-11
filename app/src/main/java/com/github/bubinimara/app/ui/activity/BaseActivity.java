package com.github.bubinimara.app.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.bubinimara.app.MovieApp;
import com.github.bubinimara.app.di.components.ActivityComponent;
import com.github.bubinimara.app.di.components.ApplicationComponent;
import com.github.bubinimara.app.ui.IView;

/**
 * Created by davide.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {
    private boolean isRestored;
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRestored = savedInstanceState!=null;
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MovieApp) getApplication()).getApplicationComponent();
    }

    protected ActivityComponent getActivityComponent(){
        // TODO: Find differences between dagger declaration of component
      /*  if(activityComponent == null){
            activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(new ActivityModule())
                    .build();
        }
        return activityComponent;
      */
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
