package com.ngohoanglong.monngon.ui.adapter.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ngohoanglong.monngon.R;
import com.ngohoanglong.monngon.ui.adapter.BaseAdapter;
import com.ngohoanglong.monngon.ui.adapter.viewmodel.SectionVM;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ngohoanglong.monngon.MainApplication.mContext;

/**
 * Created by Long on 11/10/2016.
 */
public class SectionHolder extends BaseViewHolder<SectionVM> {

    @BindView(R.id.tvHeaderSection)
    TextView tvHeaderSection;

    @BindView(R.id.rvItemList)
    RecyclerView rvItemList;

    @BindView(R.id.btnMore)
    Button btnMore;

    public SectionHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    @Override
    public void bind(SectionVM item) {

        tvHeaderSection.setText(item.getHeaderTitle());
        BaseAdapter itemListDataAdapter = new BaseAdapter(item.getItems());
        rvItemList.setNestedScrollingEnabled(false);
        rvItemList.setHasFixedSize(true);
        rvItemList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvItemList.setAdapter(itemListDataAdapter);
    }
}
