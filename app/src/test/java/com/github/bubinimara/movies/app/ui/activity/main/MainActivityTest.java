package com.github.bubinimara.movies.app.ui.activity.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.app.FakeMovieApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by davide.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,application = FakeMovieApp.class)
public class MainActivityTest {


    private FakeMovieApp getApp() {
        return (FakeMovieApp) RuntimeEnvironment.application;
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_show_home_screen_at_startup() {

        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(mainActivity);
        assertFalse(mainActivity.isDestroyed());

        assertThat(mainActivity.viewPager.getCurrentItem(),is(MainActivity.Screens.HOME));
    }

    @Test
    public void should_change_screen_on_bottom_tab_clicked() {
        ActivityController controller =
                Robolectric.buildActivity(MainActivity.class).create().start();
        MainActivity mainActivity = (MainActivity) controller.get();

                assertNotNull(mainActivity);
        assertFalse(mainActivity.isDestroyed());

        mainActivity.findViewById(R.id.navigation_search).performClick();
        assertThat(mainActivity.viewPager.getCurrentItem(),is(MainActivity.Screens.SEARCH));

        mainActivity.findViewById(R.id.navigation_profile).performClick();
        assertThat(mainActivity.viewPager.getCurrentItem(),is(MainActivity.Screens.PROFILE));

        mainActivity.findViewById(R.id.navigation_home).performClick();
        assertThat(mainActivity.viewPager.getCurrentItem(),is(MainActivity.Screens.HOME));

    }

    @Test
    public void should_not_change_screen_on_configuration_change() {
        ActivityController controller =
                Robolectric.buildActivity(MainActivity.class).create().start().resume();
        MainActivity mainActivity = (MainActivity) controller.get();
        assertNotNull(mainActivity);


        mainActivity.findViewById(R.id.navigation_profile).performClick();
        assertThat(mainActivity.viewPager.getCurrentItem(),is(MainActivity.Screens.PROFILE));

        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();

        mainActivity = Robolectric.buildActivity(MainActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        assertThat(mainActivity.viewPager.getCurrentItem(),is(MainActivity.Screens.PROFILE));

    }

    @Test
    public void should_set_correct_screen_title() {
        ActivityController controller =
                Robolectric.buildActivity(MainActivity.class).create().start().resume();
        MainActivity mainActivity = (MainActivity) controller.get();
        assertNotNull(mainActivity);

        mainActivity.findViewById(R.id.navigation_search).performClick();
        assertThat(mainActivity.getTitle().toString(),is(mainActivity.getResources().getString(R.string.toolbar_title_search)));

        mainActivity.findViewById(R.id.navigation_profile).performClick();
        assertThat(mainActivity.getTitle().toString(),is(mainActivity.getResources().getString(R.string.toolbar_title_profile)));

        mainActivity.findViewById(R.id.navigation_home).performClick();
        assertThat(mainActivity.getTitle().toString(),is(mainActivity.getResources().getString(R.string.toolbar_title_home)));

    }
}