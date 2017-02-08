package com.ngohoanglong.monngon.ui.adapter.vmfactory;

import android.view.View;

import com.ngohoanglong.monngon.ui.adapter.viewholder.BaseViewHolder;


/**
 * Created by Long on 10/5/2016.
 */

public interface HolderFactory extends com.ngohoanglong.monngon.ui.adapter.vmfactory.ViewTypeFactory {
    BaseViewHolder createHolder(int type, View view);
}
