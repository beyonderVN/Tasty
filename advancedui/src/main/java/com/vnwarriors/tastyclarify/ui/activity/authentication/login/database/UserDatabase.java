package com.vnwarriors.tastyclarify.ui.activity.authentication.login.database;


import com.vnwarriors.tastyclarify.model.UserModel;

import rx.Observable;

public interface UserDatabase {

    Observable<UserModel> readUserFrom(String userId);

    void writeCurrentUser(UserModel user);

    Observable<UserModel> observeUser(String userId);

}
