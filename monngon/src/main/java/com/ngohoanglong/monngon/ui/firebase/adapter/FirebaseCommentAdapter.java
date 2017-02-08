package com.ngohoanglong.monngon.ui.firebase.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.ngohoanglong.advancedui.appcore.common.util.ParseRelativeDate;
import com.ngohoanglong.monngon.R;
import com.ngohoanglong.monngon.model.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nguyen.D.Hoang on 11/25/2016.
 */

public class FirebaseCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Comment> comments;

    public FirebaseCommentAdapter(List<Comment> ingredients) {
        this.comments = ingredients;
    }
    RecyclerView recyclerView;

    ChildEventListener listener;
    public FirebaseCommentAdapter(Query query) {
        comments = new ArrayList<>();
        setChildEventListener(query);
    }
    private void setChildEventListener(Query query) {
        comments.clear();
        listener = query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    Comment model = dataSnapshot.getValue(Comment.class);
                    comments.add(model);
                    notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                    return;
                }

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
