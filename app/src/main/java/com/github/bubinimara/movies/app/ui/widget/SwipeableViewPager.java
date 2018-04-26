package com.github.bubinimara.movies.app.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by davide.
 */
public class SwipeableViewPager extends ViewPager {
    private boolean isSwipeEnabled = false;

    public SwipeableViewPager(@NonNull Context context) {
        super(context);
    }

    public SwipeableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSwipeEnabled && super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSwipeEnabled && super.onTouchEvent(ev);
    }

    /**
     * Return swiping state
     * @return true is swipe is enabled, false otherwise
     */
    public boolean isSwipeEnabled() {
        return isSwipeEnabled;
    }

    /**
     * Enable or disable swipe
     * @param swipeEnabled true to enable swipe, false to disable
     */
    public void setSwipeEnabled(boolean swipeEnabled) {
        isSwipeEnabled = swipeEnabled;
    }
}
