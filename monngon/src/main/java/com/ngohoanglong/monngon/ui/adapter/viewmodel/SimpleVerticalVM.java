package com.ngohoanglong.monngon.ui.adapter.viewmodel;


import com.ngohoanglong.monngon.R;

/**
 * Created by Long on 11/10/2016.
 */
public class SimpleVerticalVM extends com.ngohoanglong.monngon.ui.adapter.viewmodel.BaseVM {
    int color = R.color.white;
    public String getTittle() {
        return tittle;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    String tittle;
    public SimpleVerticalVM(String tittle) {
        this.tittle = tittle;
    }
    public SimpleVerticalVM(String tittle,int color) {
        this(tittle);
        this.color = color;
    }
    @Override
    public int getVMType(com.ngohoanglong.monngon.ui.adapter.vmfactory.ViewTypeFactory vmTypeFactory) {
        return vmTypeFactory.getType(this);
    }
}
