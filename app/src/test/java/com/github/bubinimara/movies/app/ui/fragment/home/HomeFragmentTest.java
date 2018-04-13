package com.github.bubinimara.movies.app.ui.fragment.home;

import android.app.Activity;
import android.content.Intent;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.FakeMovieApp;
import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.ui.activity.details.DetailsActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;


/**
 * Created by davide.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,application = FakeMovieApp.class)
public class HomeFragmentTest {

    @Test
    public void showMovies() {
        HomeFragment fragment = new HomeFragment();
        SupportFragmentTestUtil.startVisibleFragment(fragment);

        Collection<MovieModel> movies = getMovies();
        fragment.showMovies(movies);

        assertThat(fragment.recyclerView.getAdapter().getItemCount(),is(movies.size()));
        assertThat(fragment.recyclerView.getChildCount(), greaterThan(0));
        assertThat(fragment.recyclerView.getChildCount(), lessThan(movies.size()));
    }

    @Test
    public void showError() {
        assertFalse("Not implemented",true);
    }

    @Test
    public void showDetailsView() {
        HomeFragment fragment = new HomeFragment();
        SupportFragmentTestUtil.startVisibleFragment(fragment);

        MovieModel movie = Mockito.mock(MovieModel.class);
        fragment.showDetailsView(movie);
        assertActivityStarted(fragment, DetailsActivity.class);

    }

    private void assertActivityStarted(HomeFragment fragment, Class<? extends Activity> clazz) {
        ShadowActivity shadowActivity = shadowOf(fragment.getActivity());
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertThat(shadowIntent.getIntentClass().getName(),
                equalTo(clazz.getName()));
    }


    private Collection<MovieModel> getMovies() {
        List<MovieModel> movies = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            MovieModel m = new MovieModel();
            m.setId(i);
            m.setTitle(String.valueOf(i));
            m.setImageUrl(String.valueOf(i));
            m.setOverview(String.valueOf(i));
            m.setYear(String.valueOf(i));
            movies.add(m);
        }
        return movies;
    }
}