package com.github.bubinimara.movies.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.fragment.home.HomeFragment;
import com.github.bubinimara.movies.fragment.search.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView {

    MainPresenter presenter;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        presenter.setRestoreState(savedInstanceState != null);
        ButterKnife.bind(this);

        initialize();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    private void initialize() {
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void showHomeScreen() {
        //TODO: check if already displayed
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,getHomeFragment())
                .commit();
    }

    @Override
    public void showSearchScreen(){
        //TODO: check if already displayed
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,getSearchFragment())
                .commit();
    }

    private Fragment getSearchFragment() {
        //TODO: don't recreate
        return SearchFragment.newInstance();
    }

    private Fragment getHomeFragment() {
        //TODO: don't recreate
        return HomeFragment.newInstance();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                presenter.onNavigationHome();
                return true;
            case R.id.navigation_search:
                presenter.onNavigationSearch();
                return true;
            case R.id.navigation_notifications:
                // TODO: to be replaced or removed
                return false;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
