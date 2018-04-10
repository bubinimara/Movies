package com.github.bubinimara.app.ui.fragment.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.bubinimara.app.R;
import com.github.bubinimara.app.model.MovieModel;
import com.github.bubinimara.app.ui.AutoLifecycleBinding;
import com.github.bubinimara.app.ui.adapter.MovieAdapter;
import com.github.bubinimara.app.ui.fragment.BaseFragment;
import com.github.bubinimara.app.ui.widget.SimpleTextWatcher;
import com.github.bubinimara.app.util.ButterKnifeUtil;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements SearchView{

    private static final String TAG = SearchFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.search_text)
    EditText editTextSearch;

    @Inject
    SearchPresenter presenter;

    private Unbinder unbinder;


    @Inject
    @Named("Search")
    MovieAdapter adapter;

    private TextWatcher textWatcher;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    public SearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        textWatcher = new MyTextWatcher();
        getLifecycle().addObserver(new AutoLifecycleBinding<>(this,presenter));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        adapter.setOnLoadMore(this::onLoadMore);
        adapter.setOnItemClicked(this::onItemClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        editTextSearch.addTextChangedListener(textWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editTextSearch.removeTextChangedListener(textWatcher);
        recyclerView.setAdapter(null);
        adapter.clean();
        ButterKnifeUtil.safeUnbind(unbinder);
    }

    @Override
    public String getSearchTerm() {
        return editTextSearch.getText().toString();
    }

    @Override
    public int getPageNumber() {
        return adapter.getCurrentPage();
    }

    private void onItemClicked(MovieModel movieModel) {

    }

    public void onLoadMore(int page) {
        presenter.onViewStateChange();
    }

    @Override
    public void showEmptyMovies() {
        adapter.removeAllMovies();
    }

    @Override
    public void showMovies(List<MovieModel> movieModels) {
        adapter.addMovies(movieModels);
    }


    @Override
    public void showError(int error) {
        // TODO: get string from error type
        Toast.makeText(getContext(),R.string.unknown_error_msg,Toast.LENGTH_SHORT).show();
    }

    class MyTextWatcher extends SimpleTextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            presenter.onViewStateChange();
        }
    }

}