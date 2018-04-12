package com.github.bubinimara.movies.app.ui.fragment.profile;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.app.ui.AutoLifecycleBinding;
import com.github.bubinimara.movies.app.ui.fragment.BaseFragment;
import com.github.bubinimara.movies.app.util.ButterKnifeUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements ProfileView {

    @Inject
    ProfilePresenter presenter;

    private Unbinder unbinder;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    public ProfileFragment() {
    }

    @Override
    public void onAttach(Context context) {
        getActivityComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new AutoLifecycleBinding<>(this,presenter));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnifeUtil.safeUnbind(unbinder);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClicked(){
        presenter.onLogin();
    }

    @Override
    public void showError(int errorType) {
        Toast.makeText(getContext(),"Not implemented yet",Toast.LENGTH_LONG).show();
    }
}
