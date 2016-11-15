package com.vnwarriors.tasty.ui.adapter.vmfactory;

import android.view.View;

import com.vnwarriors.tasty.ui.adapter.viewholder.BaseViewHolder;


/**
 * Created by Long on 10/5/2016.
 */

public interface HolderFactory extends com.vnwarriors.tasty.ui.adapter.vmfactory.ViewTypeFactory {
    BaseViewHolder createHolder(int type, View view);
}
