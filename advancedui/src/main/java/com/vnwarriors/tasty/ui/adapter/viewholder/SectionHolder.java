package com.vnwarriors.tasty.ui.adapter.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vnwarriors.tasty.R;
import com.vnwarriors.tasty.ui.adapter.BaseAdapter;
import com.vnwarriors.tasty.ui.adapter.viewmodel.SectionVM;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vnwarriors.tasty.MainApplication.mContext;

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
