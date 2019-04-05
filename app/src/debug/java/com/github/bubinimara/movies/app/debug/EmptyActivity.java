package com.github.bubinimara.movies.app.debug;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Empty Mock activity used for test
 * Cannot use resources
 * otherwise unit test with mockito fails
 */
public class EmptyActivity extends AppCompatActivity {

    private static final String VIEW_ROOT_ID = "ARGS_VIEW_ROOT_ID";
    private ViewGroup content;

    private int viewRootId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        content = new FrameLayout(this);
        content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        content.setId(getContentId(savedInstanceState));
        relativeLayout.addView(content);
        setContentView(relativeLayout);
    }

    private int getContentId(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            viewRootId = ViewCompat.generateViewId();
        }else{
            viewRootId = savedInstanceState.getInt(VIEW_ROOT_ID);
        }
        return viewRootId;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(VIEW_ROOT_ID,viewRootId);
        super.onSaveInstanceState(outState);
    }

    public void addContent(View view){
        content.addView(view);
    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(content.getId(),fragment)
                .commit();
    }
}
