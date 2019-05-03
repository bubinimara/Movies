package com.github.bubinimara.movies.app.ui.fragment.home;

import android.view.View;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.FakeMovieApp;
import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.ui.activity.details.DetailsActivity;
import com.github.bubinimara.movies.app.utils.MovieModelTestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.Collection;

import static com.github.bubinimara.movies.app.robo.ActivityAssertions.assertActivityStarted;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Created by davide.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,application = FakeMovieApp.class)
public class HomeFragmentTest {

    private HomeFragment fragment;

    @Before
    public void setUp() {
        fragment = new HomeFragment();
        SupportFragmentTestUtil.startVisibleFragment(fragment);
    }

    //@Test
    public void showMovies() {
        fragment.showEmptyMovies();
        assertThat(fragment.recyclerView.getAdapter().getItemCount(),is(0));

        Collection<MovieModel> movies = MovieModelTestUtil.createMovies(33);
        fragment.showMovies(movies);

        assertThat(fragment.recyclerView.getAdapter().getItemCount(),is(movies.size()+1));
        assertThat(fragment.recyclerView.getChildCount(), greaterThan(0));
        assertThat(fragment.recyclerView.getChildCount(), lessThan(movies.size()));
    }

    @Test
    public void showError() {
        fragment.showError(HomeView.Errors.UNKNOWN);
        assertThat(fragment.errorView.getVisibility(),is(View.VISIBLE));
    }

    @Test
    public void showDetailsView() {
        MovieModel movie = Mockito.mock(MovieModel.class);
        fragment.showDetailsView(movie);
        assertActivityStarted(fragment, DetailsActivity.class);
    }
}