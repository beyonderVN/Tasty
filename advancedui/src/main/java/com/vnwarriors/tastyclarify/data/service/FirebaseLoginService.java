package com.vnwarriors.tastyclarify.data.service;

import com.vnwarriors.tastyclarify.data.database.AuthDatabase;
import com.vnwarriors.tastyclarify.data.database.UserDatabase;
import com.vnwarriors.tastyclarify.model.Authentication;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Long on 12/6/2016.
 */

public class FirebaseLoginService implements LoginService {


    private final AuthDatabase authDatabase;
    private final UserDatabase userDatabase;
    BehaviorSubject<Authentication> authSubject;

    public FirebaseLoginService(AuthDatabase authDatabase, UserDatabase userDatabase) {
        this.authDatabase = authDatabase;
        this.userDatabase = userDatabase;
        authSubject = BehaviorSubject.create();
    }

    @Override
    public Observable<Authentication> getAuthentication() {
        return authSubject.asObservable();
    }



    @Override
    public void loginWithGoogle(String idToken) {
        authDatabase.loginWithGoogle(idToken)
                .subscribe(authentication -> {
                    if (authentication.isSuccess()) {
                        userDatabase.writeCurrentUser(authentication.getUser());
                    }
                    authSubject.onNext(authentication);
                });
    }

    @Override
    public void loginWithEmailAndPassword(String email, String password) {
        authDatabase.loginWithEmailAndPassword(email, password)
                .subscribe(authentication -> {
                    if (authentication.isSuccess()) {
                        userDatabase.writeCurrentUser(authentication.getUser());
                    }
                    authSubject.onNext(authentication);
                });


    }
}
