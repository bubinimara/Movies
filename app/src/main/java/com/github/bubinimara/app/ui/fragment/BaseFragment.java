package com.github.bubinimara.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.bubinimara.app.ui.IView;
import com.github.bubinimara.app.MovieApp;
import com.github.bubinimara.app.di.components.ActivityComponent;

/**
 * Created by davide.
 */

public class BaseFragment extends Fragment implements IView {
    private ActivityComponent activityComponent;
    private boolean isRestored;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApp app = (MovieApp) getActivity().getApplication();
        activityComponent = app.getActivityComponent();
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
        return activityComponent;
    }
}
