package com.github.bubinimara.movies.app.ui.fragment.home;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.app.rules.FragmentTestRule;
import com.github.bubinimara.movies.app.rules.module.EmptyApplicationDataModule;
import com.github.bubinimara.movies.app.rules.module.TestApplicationDataModule;
import com.github.bubinimara.movies.app.utils.ActivityTestUtil;
import com.github.bubinimara.movies.app.utils.CustomMatchers;
import com.github.bubinimara.movies.app.utils.DataTestUtils;
import com.github.bubinimara.movies.domain.Movie;
import com.github.bubinimara.movies.domain.PageMovie;
import com.github.bubinimara.movies.domain.repository.MovieRepository;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by davide.
 * ui test
 */
public class HomeFragmentTest {

    @Rule
    public FragmentTestRule<HomeFragment> fragmentTestRule = new FragmentTestRule<>(HomeFragment.class);


    @Test
    public void should_be_show_current_item_on_configuration_change() {
        TestApplicationDataModule applicationDataModule = new EmptyApplicationDataModule(){
            @Override
            protected MovieRepository createMovieRepository() {
                MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
                Mockito.when(movieRepository.getMostPopularMovies(Mockito.any(),Mockito.anyInt()))
                        .thenAnswer(i-> Observable.just(DataTestUtils.createPageMovie(i.getArgument(1))));
                return movieRepository;
            }
        };
        fragmentTestRule.launchActivity(null,applicationDataModule);

        //onView(withId(R.id.home_title)).check(matches(isDisplayed()));
        onView(withText(DataTestUtils.getTitleOfElementAtPosition(1))).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(DataTestUtils.getPageSize()-1));
        onView(withText(DataTestUtils.getTitleOfElementAtPosition(1))).check(doesNotExist());
        onView(withText(DataTestUtils.getTitleOfElementAtPosition(DataTestUtils.getPageSize()-1))).check(matches(isCompletelyDisplayed()));

        // scroll to and get first visible item
        ScrollToPositionViewAction scrollToPositionViewAction = new ScrollToPositionViewAction(DataTestUtils.getPageSize()-1);
        onView(withId(R.id.recyclerView)).perform(scrollToPositionViewAction);
        String titleOfElementAtPosition = DataTestUtils.getTitleOfElementAtPosition(scrollToPositionViewAction.getFirstVisibleItem());

        onView(withText(titleOfElementAtPosition)).check(matches(isCompletelyDisplayed()));

        ActivityTestUtil.rotateScreen(fragmentTestRule.getActivity());

        onView(withText(titleOfElementAtPosition)).check(matches(isCompletelyDisplayed()));

        ActivityTestUtil.rotateScreen(fragmentTestRule.getActivity());
        onView(withText(titleOfElementAtPosition)).check(matches(isCompletelyDisplayed()));

    }


    @Test
    public void should_be_first_item_with_large_text_full_displayed() {
        TestApplicationDataModule applicationDataModule = new EmptyApplicationDataModule(){
            @Override
            protected MovieRepository createMovieRepository() {
                MovieRepository movieRepository = Mockito.mock(MovieRepository.class);

                Movie mocked = Mockito.mock(Movie.class);
                Mockito.when(mocked.getTitle()).thenReturn(getLargeText());
                Mockito.when(mocked.getOverview()).thenReturn(getLargeText());
                Mockito.when(mocked.getReleaseDate()).thenReturn(getLargeText());
                PageMovie pageMovie = new PageMovie();
                pageMovie.setMovies(Collections.singletonList(mocked));

                Mockito.when(movieRepository.getMostPopularMovies(Mockito.any(),Mockito.anyInt()))
                        .thenReturn(Observable.just((pageMovie)));

                return movieRepository;
            }
        };

        fragmentTestRule.launchActivity(null,applicationDataModule);

    //    onView(withId(R.id.home_title)).check(matches(isDisplayed()));
        onView(hasDescendant(CustomMatchers.withIndex(withText(getLargeText()),0)))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void should_show_error_message_on_error() {
        TestApplicationDataModule applicationDataModule = new EmptyApplicationDataModule(){
            @Override
            protected MovieRepository createMovieRepository() {
                MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
                Mockito.when(movieRepository.getMostPopularMovies(Mockito.any(),Mockito.anyInt()))
                        .thenReturn(Observable.error(new Throwable("error")));
                return movieRepository;
            }
        };

        fragmentTestRule.launchActivity(null,applicationDataModule);

        onView(withText(R.string.error_retry_movies)).check(matches(isDisplayed()));
    }

    private String getLargeText() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    }


    private static final class ScrollToPositionViewAction implements ViewAction {
        private final int position;
        private int firstVisibleItem;

        private ScrollToPositionViewAction(int position) {
            this.position = position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll RecyclerView to position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(position);
            uiController.loopMainThreadUntilIdle();
            firstVisibleItem = finFirstVisibleItem(recyclerView);
        }

        public int getFirstVisibleItem() {
            return firstVisibleItem;
        }

        private int finFirstVisibleItem(RecyclerView recyclerView) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            RecyclerView.Adapter adapter = recyclerView.getAdapter();

            for (int i = 0; i < adapter.getItemCount(); i++) {
                View v = layoutManager.findViewByPosition(i);
                if (ViewMatchers.isCompletelyDisplayed().matches(v)) {
                    return i;
                }
            }
            throw new RuntimeException("ScrollToPositionViewAction: Can't find first visible item in recyclerView");
        }
    }

}