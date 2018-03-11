package com.github.bubinimara.movies.fragment.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.adapter.MovieAdapter;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchView{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SearchPresenter presenter;
    private Unbinder unbinder;
    private MovieAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        adapter = new MovieAdapter();
        adapter.setOnLoadMore(this::onLoadMore);
        adapter.setOnItemClicked(this::onItemClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void onItemClicked(MovieModel movieModel) {

    }

    private void onLoadMore(int page) {
        presenter.onLoadMore(page);
    }

    @Override
    public void showEmptyMovies() {
        adapter.removeAllMovies();
    }

    @OnTextChanged(R.id.search_text)
    public void onSeachTextChange(CharSequence search){
        presenter.onSearch(search.toString());
    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public void showMovies(List<MovieModel> movieModels, int currentPageNumber) {
        adapter.addMovies(movieModels,currentPageNumber);
    }
}
