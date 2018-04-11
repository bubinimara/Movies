package com.github.bubinimara.app.ui.fragment.details;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bubinimara.app.R;
import com.github.bubinimara.app.model.MovieModel;
import com.github.bubinimara.app.ui.AutoLifecycleBinding;
import com.github.bubinimara.app.ui.adapter.ImageMovieAdapter;
import com.github.bubinimara.app.ui.adapter.MovieAdapter;
import com.github.bubinimara.app.ui.fragment.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends BaseFragment implements MovieDetailsView {
    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";

    private long movieId;

    @Inject
    MovieDetailsPresenter presenter;

    @Inject
    ImageMovieAdapter adapter;

    @BindView(R.id.movie_overview)
    TextView overview;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    public MovieDetailsFragment() {
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param movieId the movie id
     * @return A new instance of fragment MovieDetailsFragment.
     */
    public static MovieDetailsFragment newInstance(long movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLifecycle().addObserver(new AutoLifecycleBinding<>(this,presenter));
        if (getArguments() != null) {
            movieId = getArguments().getLong(ARG_MOVIE_ID);
        }
    }

    @Override
    public void onAttach(Context context) {
        getActivityComponent().inject(this);
        super.onAttach(context);
        if((context instanceof MovieAdapter.OnItemClicked)){
            MovieAdapter.OnItemClicked listener = (MovieAdapter.OnItemClicked) context;
            adapter.setOnItemClicked(listener);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setOnItemClicked(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public long getMovieId() {
        return movieId;
    }

    @Override
    public void setOverview(String o){
        overview.setText(o);
    }

    @Override
    public void setTitle(String title) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        toolbar.setTitle(title);
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void showSimilarMovie(List<MovieModel> movieModels) {
        adapter.setMovies(movieModels);
    }
}
