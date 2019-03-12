package com.github.bubinimara.movies.app.ui.fragment.search;

import android.view.View;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.FakeMovieApp;
import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.robo.ActivityAssertions;
import com.github.bubinimara.movies.app.ui.activity.details.DetailsActivity;
import com.github.bubinimara.movies.app.ui.fragment.home.HomeView;
import com.github.bubinimara.movies.app.utils.MovieModelTestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by davide.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,application = FakeMovieApp.class)
public class SearchFragmentTest {

    private SearchFragment fragment;

    @Before
    public void setUp() {
        fragment = new SearchFragment();
        SupportFragmentTestUtil.startVisibleFragment(fragment);
    }

    @Test
    public void should_show_search_input_at_startup() {
        assertEquals(fragment.editTextSearch.getVisibility(), View.VISIBLE);
        assertTrue(fragment.editTextSearch.getText().toString().isEmpty());
    }

    @Test
    public void showMovies() {
        List<MovieModel> movies = MovieModelTestUtil.createMovies(33);
        fragment.showMovies(movies);

        assertThat(fragment.recyclerView.getAdapter().getItemCount(),is(movies.size()+1));
        assertThat(fragment.recyclerView.getChildCount(), greaterThan(0));
        assertThat(fragment.recyclerView.getChildCount(), lessThan(movies.size()));
    }

    @Test
    public void getText() {
        String myText = "my custom text";
        fragment.editTextSearch.setText(myText);
        assertEquals(myText,fragment.editTextSearch.getText().toString());
    }


    @Test
    public void should_trigger_new_search_on_text_change() {
        String searchTerm = "searchTerm";
        fragment.presenter = Mockito.mock(SearchPresenter.class);
        fragment.editTextSearch.setText(searchTerm);

        Mockito.verify(fragment.presenter).onViewSearchTermChange(searchTerm);
    }

    @Test
    public void should_trigger_new_search_on_page_load() {
        fragment.presenter = Mockito.mock(SearchPresenter.class);
        fragment.editTextSearch.setText("my custom text");
        fragment.onLoadMore(1);

        Mockito.verify(fragment.presenter).onViewLoadPage(1);
    }

    @Test
    public void showError() {
        fragment.showError(HomeView.Errors.UNKNOWN);
        assertThat(fragment.errorView.getVisibility(),is(View.VISIBLE));
        assertThat(fragment.recyclerView.getVisibility(),is(View.GONE));
    }

    @Test
    public void hideError() {
        fragment.hideError();
        assertThat(fragment.errorView.getVisibility(),is(View.GONE));
        assertThat(fragment.recyclerView.getVisibility(),is(View.VISIBLE));
    }

    @Test
    public void showDetailsView() {
        MovieModel movie = Mockito.mock(MovieModel.class);
        fragment.showDetailsView(movie);
        ActivityAssertions.assertActivityStarted(fragment, DetailsActivity.class);
    }


}