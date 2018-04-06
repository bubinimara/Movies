package com.github.bubinimara.app.di.components;

import com.github.bubinimara.app.di.ActivityScope;
import com.github.bubinimara.app.di.modules.ActivityModule;
import com.github.bubinimara.app.ui.fragment.home.HomeFragment;
import com.github.bubinimara.app.ui.activity.main.MainActivity;
import com.github.bubinimara.app.ui.fragment.search.SearchFragment;

import dagger.Component;

/**
 * Created by davide.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity baseActivity);
    void inject(HomeFragment homeFragment);
    void inject(SearchFragment searchFragment);
}
