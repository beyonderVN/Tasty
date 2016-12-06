package com.vnwarriors.tastyclarify.ui.activity.authentication.login.database;


import com.vnwarriors.tastyclarify.ui.activity.authentication.login.model.Authentication;

import rx.Observable;

public interface AuthDatabase {

    Observable<Authentication> readAuthentication();

    Observable<Authentication> loginWithGoogle(String idToken);
    Observable<Authentication> loginWithEmailAndPassword(String email,String password);

}
