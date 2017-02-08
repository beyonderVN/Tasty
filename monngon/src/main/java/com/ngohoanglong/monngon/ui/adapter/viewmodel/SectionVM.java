package com.ngohoanglong.monngon.ui.adapter.viewmodel;

import com.ngohoanglong.monngon.ui.adapter.vmfactory.ViewTypeFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 8/9/2016.
 */

public class SectionVM extends BaseVM {

    private String headerTitle;
    private List<BaseVM> items = new ArrayList<>();

    public SectionVM(String headerTitle) {
        this.headerTitle = headerTitle;
    }
    public SectionVM(String headerTitle, List<BaseVM> items) {
        this(headerTitle);
        this.items = items;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public List<BaseVM> getItems() {
        return items;
    }

    public void setItems(List<BaseVM> items) {
        this.items = items;
    }

    @Override
    public int getVMType(ViewTypeFactory vmTypeFactory) {
        return vmTypeFactory.getType(this);
    }
}
