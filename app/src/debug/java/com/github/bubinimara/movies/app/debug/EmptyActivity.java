package com.github.bubinimara.movies.app.debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.github.bubinimara.movies.R;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    }

    public void addContent(View view){
        ViewGroup viewGroup = findViewById(R.id.container);
        viewGroup.addView(view);
    }
}
