package com.vnwarriors.tastyclarify.model;

import java.io.Serializable;

/**
 * Created by Long on 11/29/2016.
 */

public class Comment implements Serializable{
    public UserModel userModel;
    public String message;
    public String createAt;
    public String updateAt;
    public Comment() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
