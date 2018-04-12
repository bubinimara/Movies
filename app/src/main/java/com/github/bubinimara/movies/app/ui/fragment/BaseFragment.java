package com.github.bubinimara.movies.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.bubinimara.movies.app.MovieApp;
import com.github.bubinimara.movies.app.di.components.ActivityComponent;
import com.github.bubinimara.movies.app.ui.IView;

/**
 * Created by davide.
 */

public class BaseFragment extends Fragment implements IView {
    private boolean isRestored;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRestored = savedInstanceState!=null;
    }

    @Override
    public boolean isRestored() {
        return isRestored;
    }

    @Override
    public void clearRestored() {
        isRestored = false;
    }

    public ActivityComponent getActivityComponent() {
/*
        BaseActivity activity = (BaseActivity) getActivity();
        return activity.getActivityComponent();
*/
        MovieApp app = (MovieApp) getActivity().getApplication();
        return app.getActivityComponent();
    }
}
