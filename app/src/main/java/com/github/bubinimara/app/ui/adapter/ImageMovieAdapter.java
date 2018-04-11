package com.github.bubinimara.app.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.bubinimara.app.R;
import com.github.bubinimara.app.model.MovieModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davide.
 */
public class ImageMovieAdapter extends BaseAdapter<MovieModel> {

    @Override
    Holder<MovieModel> createHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflateViewFromResource(parent,R.layout.row_item_image_movie));
    }

    static class MyHolder extends Holder<MovieModel>{
        @BindView(R.id.movie_image)
        ImageView image;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void render(MovieModel movieModel) {
            if(movieModel.haveImageUrl()) {
                Glide.with(image)
                        .load(movieModel.getImageUrl())
                        .into(image);
            }
        }
    }
}
