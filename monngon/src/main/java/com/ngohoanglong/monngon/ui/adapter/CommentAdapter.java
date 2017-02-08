package com.ngohoanglong.monngon.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ngohoanglong.advancedui.appcore.common.util.ParseRelativeDate;
import com.ngohoanglong.monngon.R;
import com.ngohoanglong.monngon.model.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nguyen.D.Hoang on 11/25/2016.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Comment> comments;

    public CommentAdapter(List<Comment> ingredients) {
        this.comments = ingredients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_comment, parent, false);
                return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            bindPrepareHolder((CommentHolder) holder, position);
    }

    private void bindPrepareHolder(CommentHolder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvCreatAt)
        TextView tvCreatAt;
        @BindView(R.id.civUserImage)
        CircleImageView civUserImage;


        public CommentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void bind(Comment comment) {
            String s = "<font color=\"#e1431c\"><strong>" + comment.getUserModel().getName() + "</strong></font>"+" "+comment.getMessage();
            tvContent.setText(Html.fromHtml(s));
            Picasso.with(itemView.getContext()).load(comment.getUserModel().getPhoto_profile()).into(civUserImage);
            tvCreatAt.setText("- "+ParseRelativeDate.getTimeFromDateMillis(Long.parseLong(comment.createAt)) );
        }

    }

}
