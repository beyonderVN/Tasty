package com.vnwarriors.tastyclarify.data.auth.database;


import com.vnwarriors.tastyclarify.model.Authentication;

import rx.Observable;

public interface AuthDatabase {

    Observable<Authentication> readAuthentication();

    Observable<Authentication> loginWithGoogle(String idToken);
    Observable<Authentication> loginWithEmailAndPassword(String email,String password);

}
