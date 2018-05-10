package com.github.bubinimara.movies.app.ui.fragment.home;


import android.content.Context;
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

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.app.model.MovieModel;
import com.github.bubinimara.movies.app.ui.AutoLifecycleBinding;
import com.github.bubinimara.movies.app.ui.activity.details.DetailsActivity;
import com.github.bubinimara.movies.app.ui.adapter.BigMovieAdapter;
import com.github.bubinimara.movies.app.ui.fragment.BaseFragment;
import com.github.bubinimara.movies.app.util.ButterKnifeUtil;

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

    private static final String STATE_PROGRESSBAR_VISIBILITY = "STATE_PROGRESSBAR_VISIBILITY";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    View progressBar;

    @Inject
    @Named("Home")
    BigMovieAdapter adapter;

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
    public void onAttach(Context context) {
        getActivityComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        presenter.onMovieClicked(movieModel);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        restoreView(savedInstanceState);
    }

    private void restoreView(Bundle state) {
        if(state == null){
            return;
        }
        progressBar.setVisibility(state.getInt(STATE_PROGRESSBAR_VISIBILITY));
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_PROGRESSBAR_VISIBILITY,progressBar.getVisibility());
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
        adapter.addData(movies);
    }

    public void showEmptyMovies(){
        adapter.removeData();
    }

    @Override
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int type) {
        Toast.makeText(getContext(),R.string.unknown_error_msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDetailsView(MovieModel movieModel) {
        DetailsActivity.launchActivity(getActivity(),movieModel.getId());
    }
}
