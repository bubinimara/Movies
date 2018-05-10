package com.github.bubinimara.movies.app.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.bubinimara.movies.R;
import com.github.bubinimara.movies.app.model.MovieModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davide.
 */
public class BigMovieAdapter extends BaseAdapter<MovieModel> {

    @Inject
    public BigMovieAdapter() {
    }

    @Override
    Holder<MovieModel> createHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflateViewFromResource(parent,R.layout.row_item_movie));
    }

    public static class MyHolder extends Holder<MovieModel>{
        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.movie_overview)
        TextView overview;
        @BindView(R.id.movie_year)
        TextView year;
        @BindView(R.id.movie_image)
        ImageView image;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void render(MovieModel movie) {
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            if(TextUtils.isEmpty(movie.getYear())){
                year.setVisibility(View.GONE);
            }else {
                year.setVisibility(View.VISIBLE);
                year.setText(movie.getYear());
            }

            if(movie.haveImageUrl()) {
                Glide.with(itemView.getContext())
                        .load(movie.getImageUrl())
                        .apply(getRequestOptions())
                        .into(image)
                        ;
            }else{
                image.setImageResource(R.drawable.ic_image_placeholder);
            }
        }

        @NonNull
        private RequestOptions getRequestOptions() {
            return new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_image_placeholder)
                                .error(R.drawable.ic_image_placeholder);
        }
    }

}
