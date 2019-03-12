package com.github.bubinimara.movies.app.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.FakeMovieApp;
import com.github.bubinimara.movies.app.debug.EmptyActivity;
import com.github.bubinimara.movies.app.model.MovieModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by davide.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,application = FakeMovieApp.class)
public class BigMovieAdapterTest {

    private BigMovieAdapter adapter;
    private RecyclerView recyclerView;

    @Before
    public void setUp() {
        adapter = new BigMovieAdapter();
        EmptyActivity activity = Robolectric.buildActivity(EmptyActivity.class).create().start().resume().visible().get();
        recyclerView = new RecyclerView(RuntimeEnvironment.application);
        recyclerView.setLayoutManager(new LinearLayoutManager(RuntimeEnvironment.application));
        recyclerView.setAdapter(adapter);
        activity.addContent(recyclerView);
    }

    @Test
    public void should_not_display_data_if_empty() {
        assertEquals(adapter.getItemCount(),0);
    }

    @Test
    public void should_add_progress_view_at_the_end() {
        MovieModel model = Mockito.mock(MovieModel.class);
        adapter.addData(Collections.singletonList(model));
        assertEquals(adapter.getItemCount(),2);
        assertNotNull(getViewHolderForAdapterPosition(0));
        assertNotNull(getViewHolderForAdapterPosition(1));

    }

    @Test
    public void test_display_correct_data() {
        MovieModel model = Mockito.mock(MovieModel.class);
        Mockito.when(model.getTitle()).thenReturn("title");
        Mockito.when(model.getImageUrl()).thenReturn("invalid url");
        Mockito.when(model.getOverview()).thenReturn("overview");
        Mockito.when(model.getYear()).thenReturn("10");

        adapter.addData(Collections.singletonList(model));
        assertEquals(adapter.getItemCount(),2);

        BigMovieAdapter.MyHolder holder = getViewHolderForAdapterPosition(0);
        assertEquals(holder.title.getText(),"title");
        assertEquals(holder.overview.getText(),"overview");
        assertEquals(holder.year.getText(),"10");
        assertNull(holder.image.getDrawable());

        assertEquals(holder.title.getVisibility(), View.VISIBLE);
        assertEquals(holder.overview.getVisibility(), View.VISIBLE);
        assertEquals(holder.year.getVisibility(), View.VISIBLE);
        assertEquals(holder.image.getVisibility(), View.GONE);

    }

    @Test
    public void should_hide_year_if_not_present() {
        MovieModel model = Mockito.mock(MovieModel.class);
        Mockito.when(model.getYear()).thenReturn("");

        adapter.addData(Arrays.asList(model));
        BigMovieAdapter.MyHolder holder = getViewHolderForAdapterPosition(0);

        assertNotEquals(holder.year.getVisibility(), View.VISIBLE);
    }


    @Test
    public void should_not_display_placeholder() {
        MovieModel model = Mockito.mock(MovieModel.class);
        Mockito.when(model.getImageUrl()).thenReturn("invalid url");

        adapter.addData(Arrays.asList(model));
        BigMovieAdapter.MyHolder holder = getViewHolderForAdapterPosition(0);

        assertEquals(holder.image.getVisibility(), View.GONE);
        assertNull(holder.image.getDrawable());

/*
        int drawableResId = shadowOf(holder.image.getDrawable()).getCreatedFromResId();
        assertEquals(R.drawable.ic_image_placeholder,drawableResId);
*/

    }


    private BigMovieAdapter.MyHolder getViewHolderForAdapterPosition(int position) {
        return (BigMovieAdapter.MyHolder) recyclerView.findViewHolderForAdapterPosition(position);
    }
}