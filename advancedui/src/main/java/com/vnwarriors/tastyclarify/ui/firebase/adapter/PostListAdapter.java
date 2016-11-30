package com.vnwarriors.tastyclarify.ui.firebase.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.vnwarriors.advancedui.appcore.common.DynamicHeightImageView;
import com.vnwarriors.advancedui.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.PostModel;
import com.vnwarriors.tastyclarify.ui.activity.ItemActivity;
import com.vnwarriors.tastyclarify.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 11/25/2016.
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
    private static final String TAG = "PostListAdapter";
    List<PostModel> postModels;
    RecyclerView recyclerView;

    ChildEventListener listener;
    public PostListAdapter(Query query) {
        postModels = new ArrayList<>();
        setChildEventListener(query);
    }
    public PostListAdapter(List<PostModel> postModels) {
        this.postModels = postModels;
//        setChildEventListener(query);
    }

    private void setChildEventListener(Query query) {
        postModels.clear();
        listener = query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PostModel model = dataSnapshot.getValue(PostModel.class);
                postModels.add(0,model);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public PostListAdapter(RecyclerView recyclerView, Query query) {
        postModels = new ArrayList<>();
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this);
        setChildEventListener(query);
    }
    public void setQuery(Query query){
        setChildEventListener(query);
    }

    @Override
    public PostListAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);
        return new PostListAdapter.PostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PostListAdapter.PostViewHolder viewHolder, int position) {
        PostModel model = postModels.get(position);
        viewHolder.tvName.setText(model.getTipName());
        viewHolder.ivTipImage.setRatio(model.getTipImageRatio());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)viewHolder.itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        Picasso.with(viewHolder.itemView.getContext())
                .load(model.getTipImage().getUrl())
                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(position))
                .resize(400, (int) (400 * model.getTipImageRatio()))
                .into(viewHolder.ivTipImage);
        ImageView imageView;
        imageView = (ImageView) viewHolder.itemView.findViewById(R.id.imageView2);

        int color = ColorUtils.getColorByCatalogue(Integer.valueOf((model.getTipCategories().substring(0, 1)))+1);
        imageView.setColorFilter(color);
        ImageView imageView2;
        imageView2 = (ImageView) viewHolder.itemView.findViewById(R.id.icChef1);
        imageView2.setColorFilter(color);
        viewHolder.icChef1.setColorFilter(color);
        viewHolder.icChef2.setColorFilter(color);
        viewHolder.icChef3.setColorFilter(color);


        switch (model.getTipDifficulty()) {
            case 3:
                break;
            case 2:
                viewHolder.icChef3.setAlpha(0.5f);
                break;
            case 1:
                viewHolder.icChef2.setAlpha(0.5f);
                viewHolder.icChef3.setAlpha(0.5f);
                break;
        }

        String[] strings = model.getTipTime().split("(#tp)|(#tc)");
        int sum = 0;
        for (String str : strings) {
            if (str.length() > 0) {
                int i = Integer.parseInt(str.trim());
                sum = +i;
            }

        }
        viewHolder.tvTime.setText(String.valueOf(sum) + " min.");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), ItemActivity.class);
                intent.putExtra("POST", model);
                ActivityOptions ops = ActivityOptions.makeSceneTransitionAnimation((Activity) viewHolder.itemView.getContext(),
                        Pair.create(viewHolder.ivTipImage, viewHolder.itemView.getContext().getString(R.string.detail_image))
                );
                viewHolder.itemView.getContext().startActivity(intent, ops.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.ivTipImage)
        DynamicHeightImageView ivTipImage;
        @BindView(R.id.tvTime)
        TextView tvTime;

        @BindView(R.id.icChef1)
        ImageView icChef1;

        @BindView(R.id.icChef2)
        ImageView icChef2;

        @BindView(R.id.icChef3)
        ImageView icChef3;

        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    private CharSequence converteTimestamp(String mileSegundos) {
        return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSegundos), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }
}
