package com.github.bubinimara.movies.fragment.home;

import com.github.bubinimara.movies.IPresenter;

/**
 * Created by davide.
 */

public class HomePresenter implements IPresenter<HomeView> {

    private HomeView homeView;

    @Override
    public void bindView(HomeView view) {
        this.homeView = view;
        initialize();
    }

    @Override
    public void unbind() {
        this.homeView = null;
        clear();
    }

    private void initialize() {

    }

    private void clear() {

    }


}
