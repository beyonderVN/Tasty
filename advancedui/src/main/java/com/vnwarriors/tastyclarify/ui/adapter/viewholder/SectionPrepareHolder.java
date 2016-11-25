package com.vnwarriors.tastyclarify.ui.adapter.viewholder;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.TextView;

import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.SectionVM;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nguyen.D.Hoang on 11/25/2016.
 */

public class SectionPrepareHolder extends BaseViewHolder<SectionVM> {

    @BindView(R.id.tvIngredient)
    TextView tvIngredient;
    @BindView(R.id.cbStatus)
    AppCompatCheckBox cbStatus;


    public SectionPrepareHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(SectionVM item) {

    }
}
