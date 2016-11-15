package com.vnwarriors.tastyclarify.ui.resume.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnwarriors.tastyclarify.ui.resume.adapter.viewholder.BaseViewHolder;
import com.vnwarriors.tastyclarify.ui.resume.adapter.viewmodel.ResumeVisitable;
import com.vnwarriors.tastyclarify.ui.resume.adapter.vmfactory.ResumeHolderFactory;
import com.vnwarriors.tastyclarify.ui.resume.adapter.vmfactory.ResumeHolderFactoryImpl;

import java.util.List;

/**
 * Created by Long on 10/5/2016.
 */

public class RepoAdapter extends RecyclerView.Adapter<BaseViewHolder<ResumeVisitable>> {

    private Context context;
    public ResumeHolderFactory holderFactory = new ResumeHolderFactoryImpl();
    private List<ResumeVisitable> list;


    Activity activity;
    public RepoAdapter(Context context, List<ResumeVisitable> list) {

        this.list = list;
        this.context = context;

    }

    public void setRepos(List<ResumeVisitable> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder<ResumeVisitable> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent != null) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return holderFactory.createHolder(viewType, view);
        }
        throw new RuntimeException("Parent is null");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ResumeVisitable> holder, int position) {
        if(holder!=null){
            holder.bind(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getVMType(holderFactory);
    }
}
