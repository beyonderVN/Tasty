package com.vnwarriors.tastyclarify.ui.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.ItemDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nguyen.D.Hoang on 11/25/2016.
 */

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemDetailViewModel> mIngredients;

    public IngredientAdapter(List<ItemDetailViewModel> ingredients) {
        this.mIngredients = ingredients;
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
                return new SectionIngredientHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SectionIngredientHolder) {
            bindIngredientHolder((SectionIngredientHolder) holder, position);
        } else if (holder instanceof SectionTitleSpannerHolder) {
            bindTitleSpannerHolder((SectionTitleSpannerHolder) holder, position);
        }

    }

    private void bindTitleSpannerHolder(SectionTitleSpannerHolder holder, int position) {
        holder.bind(mIngredients.get(position).getContent());
    }

    private void bindIngredientHolder(SectionIngredientHolder holder, int position) {
        holder.bind(mIngredients.get(position).getContent());
        if ((mIngredients.size()-1) == position){
            holder.hideDivider();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mIngredients.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class SectionIngredientHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvIngredient)
        TextView tvIngredient;
        @BindView(R.id.cbStatus)
        AppCompatCheckBox cbStatus;
        @BindView(R.id.vDevider)
        View vDevider;


        public SectionIngredientHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(String ingredient) {
            tvIngredient.setText(Html.fromHtml(ingredient));

        }

        public void hideDivider(){
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
