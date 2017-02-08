package com.longngo.moviebox.wear.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.longngo.moviebox.wear.R;
import com.ngohoanglong.advancedui.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.ngohoanglong.advancedui.appcore.presentation.moviebox.viewmodel.TrailerMovieVM;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 10/12/2016.
 *
 */
public class MovieTrailerItemHolder extends BaseViewHolder<TrailerMovieVM> {


    @BindView(R.id.image_shot)
    ImageView imageView;

    public MovieTrailerItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public  void bind(TrailerMovieVM item) {

        Picasso.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w342"+item.getMovie().getPosterPath())
                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(item.getMovie().getId()))
                .into(imageView);


    }
}
