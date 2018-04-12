package com.github.bubinimara.movies.app.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.app.ui.AutoLifecycleBinding;
import com.github.bubinimara.movies.app.ui.activity.BaseActivity;
import com.github.bubinimara.movies.app.ui.fragment.home.HomeFragment;
import com.github.bubinimara.movies.app.ui.fragment.profile.ProfileFragment;
import com.github.bubinimara.movies.app.ui.fragment.search.SearchFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView {

    @interface Screens{
        int HOME = 0;
        int SEARCH = 1;
        int PROFILE = 2;
    }

    @Inject
    MainPresenter presenter;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        super.onCreate(savedInstanceState);

        AutoLifecycleBinding<MainView,MainPresenter> autoLifecycleBinding = new AutoLifecycleBinding<>(this,presenter);
        getLifecycle().addObserver(autoLifecycleBinding);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initialize();

    }

    private void initialize() {
        viewPager.setAdapter(new ScreenAdapter(getSupportFragmentManager()));
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void showHomeScreen() {
        viewPager.setCurrentItem(Screens.HOME);
    }

    @Override
    public void showSearchScreen(){
        viewPager.setCurrentItem(Screens.SEARCH);
    }

    @Override
    public void showProfileScreen(){
        viewPager.setCurrentItem(Screens.PROFILE);
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
            case R.id.navigation_profile:
                presenter.onNavigationProfile();
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private static class ScreenAdapter extends FragmentStatePagerAdapter{
        private static final int TOTAL_SCREEN = 3;

        public ScreenAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(@Screens int position) {
            switch (position){
                case Screens.HOME:
                    return HomeFragment.newInstance();
                case Screens.SEARCH:
                    return SearchFragment.newInstance();
                case Screens.PROFILE:
                    return ProfileFragment.newInstance();
            }
            throw new RuntimeException("Invalid screen position");
        }

        @Override
        public int getCount() {
            return TOTAL_SCREEN;
        }
    }
}