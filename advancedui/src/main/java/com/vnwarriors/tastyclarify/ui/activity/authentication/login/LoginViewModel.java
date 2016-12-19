package com.vnwarriors.tastyclarify.ui.activity.authentication.login;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.vnwarriors.tastyclarify.Dependencies;
import com.vnwarriors.tastyclarify.data.service.LoginService;
import com.vnwarriors.tastyclarify.utils.view.TextChange;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Long on 12/1/2016.
 */

public class LoginViewModel {
    private static final String TAG = "LoginViewModel";
    private LoginValidator validator = new LoginValidator();
    private LoginService loginService ;
    private String email = "";
    private String password = "";
    public ObservableField<String> emailError = new ObservableField<>();
    public ObservableField<String> passwordError = new ObservableField<>();
    public ObservableBoolean loginBtnState = new ObservableBoolean(false);
    private PublishSubject<String> message = PublishSubject.create();
    private PublishSubject<Integer> loadingState = PublishSubject.create();

    public void init() {
        loginService = Dependencies.INSTANCE.getLoginService();
        loginService.getAuthentication().subscribe(authentication -> {
            if (authentication.isSuccess()) {
                message.onNext("Login Successfully!");
            } else {
                loadingState.onNext(1);
                message.onNext(authentication.getFailure().getMessage());
            }
        });
    }

    public Observable<String> message() {
        return message.asObservable();
    }

    public Observable<Integer> loadingState() {
        return loadingState.asObservable();
    }

    public TextChange emailChange = value -> {
        email = value;
        validateEmail();
    };

    public TextChange passwordChange = value -> {
        password = value;
        validatePassword();
    };

    private void validatePassword() {
        passwordError.set(null);

        if (!validator.validatePassword(password)) {
            passwordError.set("Password is too short");
        }
        updateBtnState();
    }

    private void validateEmail() {
        emailError.set(null);
        if (!validator.validateEmail(email)) {
            emailError.set("Invalid email");
        }
        updateBtnState();
    }

    private void updateBtnState() {
        loginBtnState.set(!hasEmptyData() && !hasError());
    }

    private boolean hasEmptyData() {
        return email.equals("")
                || password.equals("");
    }

    private boolean hasError() {
        return emailError.get() != null
                || passwordError.get() != null;
    }

    public void loginDemo() {
        loadingState.onNext(0);
        loginService.loginWithEmailAndPassword(email,password)
        ;
    }

}
