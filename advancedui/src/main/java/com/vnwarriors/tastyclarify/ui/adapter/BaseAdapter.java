package com.vnwarriors.tastyclarify.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnwarriors.tastyclarify.ui.adapter.viewholder.BaseViewHolder;
import com.vnwarriors.tastyclarify.ui.adapter.viewmodel.BaseVM;
import com.vnwarriors.tastyclarify.ui.adapter.vmfactory.HolderFactory;
import com.vnwarriors.tastyclarify.ui.adapter.vmfactory.HolderFactoryImpl;

import java.util.List;

/**
 * Created by Long on 10/5/2016.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder<BaseVM>> {

    private Context context;
    public HolderFactory holderFactory = new HolderFactoryImpl();
    private List<BaseVM> list;
    public BaseAdapter(Context context, List<BaseVM> list) {
        this(context);
        this.list = list;

    }
    public BaseAdapter(List<BaseVM> list) {
        this.list = list;
    }
    public BaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder<BaseVM> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent != null) {
            View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
            return holderFactory.createHolder(viewType, view);
        }
        throw new RuntimeException("Parent is null");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<BaseVM> holder, int position) {
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
