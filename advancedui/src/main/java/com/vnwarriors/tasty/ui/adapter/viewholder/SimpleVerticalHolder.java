package com.vnwarriors.tasty.ui.adapter.viewholder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.vnwarriors.tasty.R;
import com.vnwarriors.tasty.ui.adapter.viewmodel.SimpleVerticalVM;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 11/10/2016.
 */
public class SimpleVerticalHolder extends BaseViewHolder<SimpleVerticalVM> {
    @BindView(R.id.cvWrap)
    CardView cvWrap;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    public SimpleVerticalHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(SimpleVerticalVM item) {
        cvWrap.setBackgroundColor(itemView.getResources().getColor(item.getColor()));
        tvTitle.setText(item.getTittle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
