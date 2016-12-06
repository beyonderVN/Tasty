package com.vnwarriors.tastyclarify.ui.activity.authentication.login.service;

import com.vnwarriors.tastyclarify.ui.activity.authentication.login.model.Authentication;

import rx.Observable;

/**
 * Created by Long on 12/6/2016.
 */

public interface LoginService {
    Observable<Authentication> getAuthentication();

    void loginWithGoogle(String idToken);
    void loginWithEmailAndPassword(String email,String password);
}
