package com.ngohoanglong.monngon.ui.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngohoanglong.monngon.R;
import com.ngohoanglong.monngon.model.ItemDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nguyen.D.Hoang on 11/25/2016.
 */

public class PrepareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemDetailViewModel> mPrepares;

    public PrepareAdapter(List<ItemDetailViewModel> ingredients) {
        this.mPrepares = ingredients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.title_spanner, parent, false);
                return new SectionTitleSpannerHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_detail_ingredient, parent, false);
                return new SectionPrepareAdapter(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SectionPrepareAdapter) {
            bindPrepareHolder((SectionPrepareAdapter) holder, position);
        } else if (holder instanceof SectionTitleSpannerHolder) {
            bindTitleSpannerHolder((SectionTitleSpannerHolder) holder, position);
        }
    }

    private void bindTitleSpannerHolder(SectionTitleSpannerHolder holder, int position) {
        holder.bind(mPrepares.get(position).getContent());

    }

    private void bindPrepareHolder(SectionPrepareAdapter holder, int position) {
        holder.bind(mPrepares.get(position).getContent());
        if ((mPrepares.size()-1) == position){
            holder.hideDivider();
        }
    }

    @Override
    public int getItemCount() {
        return mPrepares.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mPrepares.get(position).getViewType();
    }

    class SectionPrepareAdapter extends RecyclerView.ViewHolder {

        @BindView(R.id.tvIngredient)
        TextView tvIngredient;
        @BindView(R.id.cbStatus)
        AppCompatCheckBox cbStatus;
        @BindView(R.id.vDevider)
        View vDevider;

        public SectionPrepareAdapter(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(String prepare) {
            tvIngredient.setText(Html.fromHtml(prepare));
        }

        public void hideDivider() {
            vDevider.setVisibility(View.GONE);
        }
    }

    public class SectionTitleSpannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitleSpanner)
        TextView tvTitleSpanner;

        public SectionTitleSpannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String ingredient) {
            tvTitleSpanner.setText(Html.fromHtml(ingredient));

        }
    }
}
