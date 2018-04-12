package com.github.bubinimara.movies.app.ui.fragment.profile;

import com.github.bubinimara.movies.app.ui.IView;

/**
 * Created by davide.
 */
public interface ProfileView extends IView {
    @interface Errors{
        int UNKNOW = -1;
    }
    void showError(@Errors int errorType);
}
