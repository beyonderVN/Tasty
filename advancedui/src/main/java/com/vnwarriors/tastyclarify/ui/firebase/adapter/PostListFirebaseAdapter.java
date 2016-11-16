package com.vnwarriors.tastyclarify.ui.firebase.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.vnwarriors.advancedui.appcore.common.DynamicHeightImageView;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.PostModel;


/**
 * Created by Alessandro Barreto on 23/06/2016.
 */
public class PostListFirebaseAdapter extends FirebaseRecyclerAdapter<PostModel,PostListFirebaseAdapter.PostViewHolder> {


    private static final int POST = 0;
    private ClickListenerChatFirebase mClickListenerChatFirebase;


    public PostListFirebaseAdapter(DatabaseReference ref, ClickListenerChatFirebase mClickListenerChatFirebase) {
        super(PostModel.class, R.layout.layout_post, PostViewHolder.class, ref);
        this.mClickListenerChatFirebase = mClickListenerChatFirebase;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
//        if (viewType == RIGHT_MSG){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right,parent,false);
//            return new PostViewHolder(view);
//        }else if (viewType == LEFT_MSG){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left,parent,false);
//            return new PostViewHolder(view);
//        }else if (viewType == POST){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right_img,parent,false);
//            return new PostViewHolder(view);
//        }else{
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left_img,parent,false);
//            return new PostViewHolder(view);
//        }
        if (viewType == POST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);
            return new PostViewHolder(view);
        }
        return null;
    }



    @Override
    public int getItemViewType(int position) {
        return POST;
    }

    @Override
    protected void populateViewHolder(PostViewHolder viewHolder, PostModel model, int position) {
        viewHolder.tvName.setText(model.getTipName());
        viewHolder.ivTipImage.setRatio(model.getTipImageRatio());
        Picasso.with(viewHolder.itemView.getContext()).load(model.getTipImage().getUrl()).into(viewHolder.ivTipImage);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        DynamicHeightImageView ivTipImage;
        public PostViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivTipImage = (DynamicHeightImageView) itemView.findViewById(R.id.ivTipImage);
        }

        @Override
        public void onClick(View view) {

        }

    }

    private CharSequence converteTimestamp(String mileSegundos){
        return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSegundos),System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

}
