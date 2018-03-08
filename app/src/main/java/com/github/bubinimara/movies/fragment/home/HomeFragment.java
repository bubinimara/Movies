package com.github.bubinimara.movies.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.adapter.MovieAdapter;
import com.github.bubinimara.movies.model.MovieModel;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MovieAdapter adapter;
    private Unbinder unbinder;
    private HomePresenter presenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter();
        adapter = new MovieAdapter();
        adapter.setOnItemClicked(this::onRowItemCliced);
        adapter.setOnLoadMore(this::onLoadMoreItem);

    }

    private void onLoadMoreItem(int currentPage) {
        Toast.makeText(getContext(),"onLoadMore",Toast.LENGTH_SHORT).show();
    }

    private void onRowItemCliced(MovieModel movieModel) {
        Toast.makeText(getContext(),"onRowItemClicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if(unbinder!=null){
                unbinder.unbind();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMovies(Collection<MovieModel> movies, int page){
        adapter.addMovies(movies,page);
    }
}
