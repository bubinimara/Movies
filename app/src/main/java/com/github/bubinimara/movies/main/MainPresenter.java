package com.github.bubinimara.movies.main;

import com.github.bubinimara.movies.IPresenter;

/**
 * Created by davide.
 */

public class MainPresenter implements IPresenter<MainView> {
    MainView mainView;
    private boolean restoreState;

    public MainPresenter() {
        restoreState=false;
    }

    @Override
    public void bindView(MainView view) {
        this.mainView = view;
        initialize();
    }

    private void initialize(){
        if(!restoreState) {
            mainView.showHomeScreen();
        }
    }

    @Override
    public void unbind() {
        this.mainView = null;
    }

    public void onNavigationHome() {
        mainView.showHomeScreen();
    }

    public void onNavigationSearch() {
        mainView.showSearchScreen();
    }

    public void setRestoreState(boolean restoreState) {
        this.restoreState = restoreState;
    }
}
