package com.ngohoanglong.monngon.model;

/**
 * Created by Nguyen.D.Hoang on 11/25/2016.
 */

public class ItemDetailViewModel {
    private String content;
    private int viewType;

    public ItemDetailViewModel(String content, int viewType) {
        this.content = content;
        this.viewType = viewType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
