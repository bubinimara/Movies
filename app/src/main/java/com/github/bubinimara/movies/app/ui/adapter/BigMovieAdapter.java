package com.github.bubinimara.movies.app.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
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

    @Override
    public int getItemCount() {
        int itemCount = super.getItemCount();
        return (itemCount>0)? itemCount+1 : itemCount;
    }

    @Override
    public MovieModel getItem(int position) {
        return isLastItem(position) ? null : super.getItem(position);
    }

    private boolean isLastItem(int position){
        return position == getItemCount()-1;
    }
    @Override
    public int getItemViewType(int position) {
        return isLastItem(position)?1:0;
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
            if(movie == null)
                return;
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            if(TextUtils.isEmpty(movie.getYear())){
                year.setVisibility(View.GONE);
            }else {
                year.setVisibility(View.VISIBLE);
                year.setText(movie.getYear());
            }

            if(movie.haveImageUrl()) {
                image.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext())
                        .load(movie.getImageUrl())
                        .listener(getRequestListener())
                        .apply(getRequestOptions())
                        .into(image)
                        ;
            }else{
                image.setVisibility(View.GONE);
            }
        }

        @NonNull
        private RequestListener<Drawable> getRequestListener() {
            return new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    image.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            };
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
