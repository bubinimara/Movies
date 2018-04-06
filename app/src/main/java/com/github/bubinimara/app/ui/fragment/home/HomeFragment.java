package com.github.bubinimara.app.ui.fragment.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.bubinimara.app.R;
import com.github.bubinimara.app.ui.AutoLifecycleBinding;
import com.github.bubinimara.app.ui.adapter.MovieAdapter;
import com.github.bubinimara.app.ui.fragment.BaseFragment;
import com.github.bubinimara.app.model.MovieModel;
import com.github.bubinimara.app.util.ButterKnifeUtil;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    @Named("Home")
    MovieAdapter adapter;

    @Inject
    HomePresenter presenter;

    private Unbinder unbinder;

    @NonNull
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        getLifecycle().addObserver(new AutoLifecycleBinding<>(this,presenter));

        adapter.setOnLoadMore(this::onLoadMoreItem);
        adapter.setOnItemClicked(this::onRowItemClicked);

    }

    @Override
    public void onDestroy() {
        adapter.clean();
        super.onDestroy();
    }

    private void onLoadMoreItem(int currentPage) {
        presenter.onLoadMore(currentPage);
    }

    private void onRowItemClicked(MovieModel movieModel) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
        ButterKnifeUtil.safeUnbind(unbinder);
    }

    @Override
    public void showMovies(Collection<MovieModel> movies){
        adapter.addMovies(movies);
    }

    @Override
    public void showError(int type) {
        Toast.makeText(getContext(),R.string.unknown_error_msg,Toast.LENGTH_SHORT).show();
    }
}
