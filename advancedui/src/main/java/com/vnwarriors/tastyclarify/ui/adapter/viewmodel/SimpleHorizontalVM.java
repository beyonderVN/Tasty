package com.vnwarriors.tastyclarify.ui.adapter.viewmodel;


import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.ui.adapter.vmfactory.ViewTypeFactory;

/**
 * Created by Long on 11/10/2016.
 */
public class SimpleHorizontalVM extends BaseVM {
    int color = R.color.white;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    String tittle;
    public SimpleHorizontalVM(String tittle) {
        this.tittle = tittle;
    }
    public SimpleHorizontalVM(String tittle,int color) {
        this(tittle);
        this.color = color;
    }
    @Override
    public int getVMType(ViewTypeFactory vmTypeFactory) {
        return vmTypeFactory.getType(this);
    }
}
