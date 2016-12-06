package com.vnwarriors.tastyclarify.ui.activity.authentication.login.model;

import com.vnwarriors.tastyclarify.model.UserModel;

/**
 * Created by Long on 12/6/2016.
 */

public class Authentication {

    private final UserModel user;
    private final Throwable failure;

    public Authentication(UserModel user) {
        this.user = user;
        this.failure = null;
    }

    public Authentication(Throwable failure) {
        this.user = null;
        this.failure = failure;
    }

    public boolean isSuccess() {
        return user != null;
    }

    public UserModel getUser() {
        if (user == null) {
            throw new IllegalStateException("Authentication is failed please check with isSuccess first");
        }
        return user;
    }

    public Throwable getFailure() {
        if (failure == null) {
            throw new IllegalStateException("Authentication is successful please check with isSuccess first");
        }
        return failure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Authentication that = (Authentication) o;

        if (user != null ? !user.equals(that.user) : that.user != null) {
            return false;
        }
        return failure != null ? failure.equals(that.failure) : that.failure == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (failure != null ? failure.hashCode() : 0);
        return result;
    }
}