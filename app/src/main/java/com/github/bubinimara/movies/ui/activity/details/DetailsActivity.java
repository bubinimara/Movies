package com.github.bubinimara.movies.ui.activity.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.model.MovieModel;
import com.github.bubinimara.movies.ui.AutoLifecycleBinding;
import com.github.bubinimara.movies.ui.activity.BaseActivity;
import com.github.bubinimara.movies.ui.adapter.BaseAdapter;
import com.github.bubinimara.movies.ui.fragment.details.MovieDetailsFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity implements DetailsView, BaseAdapter.OnItemClicked<MovieModel> {

    private static final String EXTRA_MOVIE_ID = "extra_movie_id";
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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MovieDetailsFragment.newInstance(movieModel.getId()))
                .addToBackStack(null)
                .commit();
    }
}
