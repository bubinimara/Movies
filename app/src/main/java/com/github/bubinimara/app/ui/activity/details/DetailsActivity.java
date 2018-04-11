package com.github.bubinimara.app.ui.activity.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import com.github.bubinimara.app.R;
import com.github.bubinimara.app.model.MovieModel;
import com.github.bubinimara.app.ui.AutoLifecycleBinding;
import com.github.bubinimara.app.ui.activity.BaseActivity;
import com.github.bubinimara.app.ui.adapter.MovieAdapter;
import com.github.bubinimara.app.ui.fragment.details.MovieDetailsFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity implements DetailsView, MovieAdapter.OnItemClicked {

    private static final String EXTRA_MOVIE_ID = "extra_movie_id";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    DetailsPresenter presenter;

    private long movieId;

    public static void launchActivity(Context context, long movieId){
        Intent intent = new Intent(context,DetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID,movieId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, -1);

        getLifecycle().addObserver(new AutoLifecycleBinding<>(this,presenter));



    }

    @Override
    public void onItemClicked(MovieModel movie) {
        presenter.onMovieClicked(movie);
    }

    @Override
    public long getMovieId() {
        return movieId;
    }

    @Override
    public void showDetails(MovieModel movieModel) {
        getSupportActionBar().setTitle(movieModel.getTitle());
        toolbar.setTitle(movieModel.getTitle());
        collapsingToolbarLayout.setTitle(movieModel.getTitle());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, MovieDetailsFragment.newInstance(movieModel.getId()))
                .addToBackStack(null)
                .commit();

    }
}
