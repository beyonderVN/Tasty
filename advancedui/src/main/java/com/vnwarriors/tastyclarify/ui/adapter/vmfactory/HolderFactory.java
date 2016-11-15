package com.vnwarriors.tastyclarify.ui.adapter.vmfactory;

import android.view.View;

import com.vnwarriors.tastyclarify.ui.adapter.viewholder.BaseViewHolder;


/**
 * Created by Long on 10/5/2016.
 */

public interface HolderFactory extends com.vnwarriors.tastyclarify.ui.adapter.vmfactory.ViewTypeFactory {
    BaseViewHolder createHolder(int type, View view);
}
