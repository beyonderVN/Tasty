package com.vnwarriors.tastyclarify;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.vnwarriors.tastyclarify.ui.activity.authentication.login.database.FirebaseAuthDatabase;
import com.vnwarriors.tastyclarify.ui.activity.authentication.login.database.FirebaseUserDatabase;
import com.vnwarriors.tastyclarify.ui.activity.authentication.login.database.rx.FirebaseObservableListeners;
import com.vnwarriors.tastyclarify.ui.activity.authentication.login.service.FirebaseLoginService;
import com.vnwarriors.tastyclarify.ui.activity.authentication.login.service.LoginService;

/**
 * Created by Long on 12/6/2016.
 */

public enum Dependencies {
    INSTANCE;

    private LoginService loginService;

//    private UserService userService;

    public void init(Context context) {
        if (needsInitialisation()) {
            Context appContext = context.getApplicationContext();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            FirebaseObservableListeners firebaseObservableListeners = new FirebaseObservableListeners();
            FirebaseUserDatabase userDatabase = new FirebaseUserDatabase(firebaseDatabase, firebaseObservableListeners);
            loginService = new FirebaseLoginService(new FirebaseAuthDatabase(firebaseAuth), userDatabase);
//            userService = new PersistedUserService(userDatabase);
        }
    }

    private boolean needsInitialisation() {
        return loginService == null
//                || userService == null
                ;
    }
    public LoginService getLoginService() {
        return loginService;
    }

}
