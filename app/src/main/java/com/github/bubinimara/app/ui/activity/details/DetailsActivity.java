package com.github.bubinimara.app.ui.activity.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.bubinimara.app.R;
import com.github.bubinimara.app.ui.activity.BaseActivity;

public class DetailsActivity extends BaseActivity {

    private static final String EXTRA_MOVIE_ID = "extra_movie_id";

    public static void launchActivity(Context context, long movieId){
        Intent intent = new Intent(context,DetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID,movieId);
        context.startActivity(intent);
    }

    private long movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID,-1);
    }
}
