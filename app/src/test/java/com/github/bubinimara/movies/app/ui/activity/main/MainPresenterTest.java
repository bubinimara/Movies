package com.github.bubinimara.movies.app.ui.activity.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * Created by davide.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    MainPresenter mainPresenter;

    @Mock
    MainView mainView;

    @Before
    public void setUp() throws Exception {
        mainPresenter = new MainPresenter();
        mainPresenter.bindView(mainView);
    }

    @Test
    public void test_viewShowed() {
        Mockito.when(mainView.isRestored()).thenReturn(false);
        mainPresenter.viewShowed();
        Mockito.verify(mainView,Mockito.atLeastOnce()).showHomeScreen();
    }

    @Test
    public void test_viewShowed_when_state_is_restored() {
        Mockito.when(mainView.isRestored()).thenReturn(true);
        mainPresenter.viewShowed();
        Mockito.verify(mainView,Mockito.never()).showHomeScreen();
    }

    @Test
    public void onNavigationHome() {
        mainPresenter.onNavigationHome();
        Mockito.verify(mainView,Mockito.atLeastOnce()).showHomeScreen();
    }

    @Test
    public void onNavigationSearch() {
        mainPresenter.onNavigationSearch();
        Mockito.verify(mainView,Mockito.atLeastOnce()).showSearchScreen();
    }

    @Test
    public void onNavigationProfile() {
        mainPresenter.onNavigationProfile();
        Mockito.verify(mainView,Mockito.atLeastOnce()).showProfileScreen();
    }
}