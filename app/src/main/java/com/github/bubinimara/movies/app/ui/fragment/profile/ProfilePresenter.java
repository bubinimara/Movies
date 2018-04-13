package com.github.bubinimara.movies.app.ui.fragment.profile;

import com.github.bubinimara.movies.app.ui.BasePresenter;

import javax.inject.Inject;

/**
 * Created by davide.
 */
public class ProfilePresenter extends BasePresenter<ProfileView> {
    @Inject
    public ProfilePresenter() {
    }

    public void onLogin(){
        view.showError(ProfileView.Errors.UNKNOW);
    }
}
