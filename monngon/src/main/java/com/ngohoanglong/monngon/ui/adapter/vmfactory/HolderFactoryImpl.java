package com.ngohoanglong.monngon.ui.adapter.vmfactory;

import android.view.View;

import com.ngohoanglong.monngon.R;
import com.ngohoanglong.monngon.ui.adapter.viewholder.BaseViewHolder;
import com.ngohoanglong.monngon.ui.adapter.viewholder.SectionHolder;
import com.ngohoanglong.monngon.ui.adapter.viewholder.SimpleHorizontalHolder;
import com.ngohoanglong.monngon.ui.adapter.viewholder.SimpleVerticalHolder;
import com.ngohoanglong.monngon.ui.adapter.viewmodel.SectionVM;
import com.ngohoanglong.monngon.ui.adapter.viewmodel.SimpleHorizontalVM;
import com.ngohoanglong.monngon.ui.adapter.viewmodel.SimpleVerticalVM;


/**
 * Created by Long on 10/5/2016.
 */

public class HolderFactoryImpl implements HolderFactory {

    private static final int SECTION_VM = R.layout.layout_section;
    private static final int SIMPLE_HORIZONTAL_VM = R.layout.layout_item_horizontal;
    private static final int SIMPLE_VERTICAL_VM = R.layout.layout_item_vertical;

    @Override
    public BaseViewHolder createHolder(int type, View view) {
        switch(type) {
            case SECTION_VM: return new SectionHolder(view);
            case SIMPLE_VERTICAL_VM: return new SimpleVerticalHolder(view);
            case SIMPLE_HORIZONTAL_VM: return new SimpleHorizontalHolder(view);

        }
        return null;
    }

    @Override
    public int getType(SimpleVerticalVM simpleVerticalVM) {
        return SIMPLE_VERTICAL_VM;
    }

    @Override
    public int getType(SectionVM sectionVM) {
        return SECTION_VM;
    }

    @Override
    public int getType(SimpleHorizontalVM simpleHorizontalVM) {
        return SIMPLE_HORIZONTAL_VM;
    }


}
