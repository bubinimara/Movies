package com.github.bubinimara.app.ui.activity.main;

import com.github.bubinimara.app.ui.BasePresenter;

import javax.inject.Inject;

/**
 * Created by davide.
 */

public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    public MainPresenter() {

    }

    @Override
    public void viewShowed() {
        super.viewShowed();
        if(!view.isRestored()){
            view.showHomeScreen();
        }
    }

    public void onNavigationHome() {
        view.showHomeScreen();
    }

    public void onNavigationSearch() {
        view.showSearchScreen();
    }

    public void onNavigationProfile() {
        view.showProfileScreen();
    }
}
