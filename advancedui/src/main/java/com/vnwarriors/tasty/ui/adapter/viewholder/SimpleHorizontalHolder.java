package com.vnwarriors.tasty.ui.adapter.viewholder;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.vnwarriors.tasty.R;
import com.vnwarriors.tasty.ui.activity.ItemActivity;
import com.vnwarriors.tasty.ui.adapter.viewmodel.SimpleHorizontalVM;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 11/10/2016.
 */
public class SimpleHorizontalHolder extends BaseViewHolder<SimpleHorizontalVM> {
    @BindView(R.id.cvWrap)
    CardView cvWrap;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    public SimpleHorizontalHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(SimpleHorizontalVM item) {
        cvWrap.setBackgroundColor(itemView.getResources().getColor(item.getColor()));
        tvTitle.setText(item.getTittle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View sharedView = tvTitle;
                View sharedView1 = v.findViewById(R.id.vWhite);

                ActivityOptions ops = ActivityOptions.makeSceneTransitionAnimation((Activity) itemView.getContext(),
                        Pair.create(sharedView, itemView.getContext().getString(R.string.detail_image)),
                        Pair.create(sharedView1, itemView.getContext().getString(R.string.detail_image1))
                        );
                Intent intent = new Intent(itemView.getContext(), ItemActivity.class);
                intent.putExtra("SimpleHorizontalVM", item);
                itemView.getContext().startActivity(intent,ops.toBundle());
            }
        });
    }
}
