package com.github.bubinimara.movies.app.ui.activity.main;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.app.rules.EmptyDataModuleRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by davide.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class,false,false);


    @Rule
    public EmptyDataModuleRule dataModuleRule = new EmptyDataModuleRule();

    @Before
    public void setUp(){
        mActivityRule.launchActivity(null);
    }

    @Test
    public void bottom_navigation(){

        onView(withId(R.id.viewPager)).check(matches(isDisplayed()));
        onView(withId(R.id.navigation)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.navigation_home)).perform(click());
        onView(allOf(withId(R.id.home_title),withText(R.string.home_most_popular))).check(matches(isDisplayed()));

        onView(withId(R.id.navigation_profile)).perform(click());
        onView(allOf(withId(R.id.btn_login),withText(R.string.btn_login))).check(matches(isDisplayed()));

        onView(withId(R.id.navigation_search)).perform(click());
        onView(allOf(withId(R.id.search_text),withHint(R.string.hint_search))).check(matches(isCompletelyDisplayed()));

    }
}