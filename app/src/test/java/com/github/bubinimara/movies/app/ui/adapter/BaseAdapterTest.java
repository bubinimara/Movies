package com.github.bubinimara.movies.app.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.bubinimara.movies.BuildConfig;
import com.github.bubinimara.movies.app.FakeMovieApp;
import com.github.bubinimara.movies.app.debug.EmptyActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by davide.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,application = FakeMovieApp.class)
public class BaseAdapterTest {

    private TestAdapter adapter;
    private RecyclerView recyclerView;

    @Before
    public void setUp() {
        adapter = new TestAdapter();
        EmptyActivity emptyActivity = Robolectric.buildActivity(EmptyActivity.class)
                .create().start().resume().visible().get();

        recyclerView = new RecyclerView(RuntimeEnvironment.application);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RuntimeEnvironment.application));

        emptyActivity.addContent(recyclerView);
    }

    @Test
    public void should_add_items() {
        adapter.addData(getMockedData(33));
        assertEquals(adapter.getItemCount(),33);

        adapter.addData(getMockedData(33));
        assertEquals(adapter.getItemCount(),66);
    }

    @Test
    public void test_load_more() {
        BaseAdapter.OnLoadMore onLoadMoreListener = Mockito.mock(BaseAdapter.OnLoadMore.class);
        adapter.setOnLoadMore(onLoadMoreListener);
        adapter.addData(getMockedData(33));
        recyclerView.scrollToPosition(32);

        Mockito.verify(onLoadMoreListener,Mockito.times(1)).onLoadMore(2);
        recyclerView.scrollToPosition(1);
        recyclerView.scrollToPosition(32);
        Mockito.verify(onLoadMoreListener,Mockito.times(1)).onLoadMore(2);

        //add another page
        adapter.addData(getMockedData(33));
        recyclerView.scrollToPosition(65);
        Mockito.verify(onLoadMoreListener,Mockito.times(1)).onLoadMore(3);

    }

    @Test
    public void click_on_item_should_call_listener() {
        BaseAdapter.OnItemClicked<String> clickListener = Mockito.mock(BaseAdapter.OnItemClicked.class);
        List<String> mockedData = getMockedData(1);
        adapter.setOnItemClicked(clickListener);
        adapter.addData(mockedData);

        recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();

        Mockito.verify(clickListener,Mockito.times(1)).onItemClicked(mockedData.get(0));

    }

    @Test
    public void test_holder_have_correct_data() {
        List<String> data = getMockedData(33);
        adapter.addData(data);
        for (int i = 0; i < data.size(); i++) {
            BaseAdapter.Holder<String> holder = getHolder(i);
            assertEquals(holder.get(),data.get(i));
        }
    }


    private @NonNull BaseAdapter.Holder<String> getHolder(int i) {
        return (BaseAdapter.Holder<String>) recyclerView.findViewHolderForAdapterPosition(i);
    }

    private @NonNull List<String> getMockedData(int size) {
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(String.valueOf(size));
        }
        return list;
    }

    static class TestAdapter extends BaseAdapter<String>{
        @Override
        Holder<String> createHolder(ViewGroup parent, int viewType) {
            return new Holder<String>(new View(parent.getContext())) {
                @Override
                public void render(String data) {

                }
            };
        }
    }
}