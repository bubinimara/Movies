package com.github.bubinimara.movies.app.rules;

import android.support.v4.app.Fragment;

import com.github.bubinimara.movies.app.debug.EmptyActivity;


/**
 * Allow fragment to be tested in isolation
 * @param <F> the fragment class
 */
public class FragmentTestRule<F extends Fragment> extends BaseActivityTestRule<EmptyActivity> {
    private final Class<F> fragmentClass;
    private F fragment;

    public FragmentTestRule(Class<F> fragmentClass){
        this(fragmentClass,false,false);
    }

    public FragmentTestRule(Class<F> fragmentClass,boolean initialTouchMode, boolean launchActivity) {
        super(EmptyActivity.class, initialTouchMode, launchActivity);
        this.fragmentClass = fragmentClass;
    }

    public F getFragment() {
        return fragment;
    }


    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        try {
            fragment = fragmentClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot instantiate fragment",e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate fragment",e);
        }
        getActivity().replaceFragment(fragment);
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        System.out.println("FragmentTestRule.afterActivityFinished");
        fragment = null;
    }
}
