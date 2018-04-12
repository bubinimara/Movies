package com.github.bubinimara.movies.di.components;

import com.github.bubinimara.movies.di.ActivityScope;
import com.github.bubinimara.movies.di.modules.ActivityModule;
import com.github.bubinimara.movies.ui.activity.details.DetailsActivity;
import com.github.bubinimara.movies.ui.activity.main.MainActivity;
import com.github.bubinimara.movies.ui.fragment.details.MovieDetailsFragment;
import com.github.bubinimara.movies.ui.fragment.home.HomeFragment;
import com.github.bubinimara.movies.ui.fragment.search.SearchFragment;

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
    void inject(DetailsActivity detailsActivity);

    void inject(MovieDetailsFragment movieDetailsFragment);
}
