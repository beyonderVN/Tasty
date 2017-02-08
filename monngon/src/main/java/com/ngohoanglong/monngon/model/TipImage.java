package com.ngohoanglong.monngon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Long on 11/16/2016.
 */

public class TipImage implements Serializable{

    @SerializedName("__type")
    @Expose
    private String __type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    public TipImage() {
    }

    public TipImage(String __type, String name, String url) {
        this.__type = __type;
        this.name = name;
        this.url = url;
    }

    /**
     * @return The __type
     */
    public String get__type() {
        return __type;
    }

    /**
     * @param __type The __type
     */
    public void set__type(String __type) {
        this.__type = __type;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}