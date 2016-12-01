package com.vnwarriors.tastyclarify.ui.activity.authentication;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vnwarriors.tastyclarify.utils.view.TextChange;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

/**
 * Created by Long on 12/1/2016.
 */

public class LoginViewModel {
    private static final String TAG = "LoginViewModel";
    private LoginValidator validator = new LoginValidator();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    ;
    private String email = "";
    private String password = "";
    public ObservableField<String> emailError = new ObservableField<>();
    public ObservableField<String> passwordError = new ObservableField<>();
    public ObservableBoolean registerBtnState = new ObservableBoolean(false);
    private PublishSubject<String> message = PublishSubject.create();

    public Observable<String> message() {
        return message.asObservable();
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
        registerBtnState.set(!hasEmptyData() && !hasError());
    }

    private boolean hasEmptyData() {
        return email.equals("")
                || password.equals("");
    }

    private boolean hasError() {
        return emailError.get() != null
                || passwordError.get() != null;
    }

    public Observable<AuthResult> login() {
        Log.d(TAG, "Observable<AuthResult> login(): ");
        return Observable.just(registerBtnState.get())
                .filter(btnState -> btnState)
                .flatMap((value) -> signInWithEmailAndPassword(auth,email, password))
                .doOnNext(value -> message.onNext("Successful!"))
                .doOnError(throwable -> message.onNext(throwable.getMessage()));
    }

    @NonNull
    public static Observable<AuthResult> signInWithEmailAndPassword(@NonNull final FirebaseAuth firebaseAuth,
                                                                    @NonNull final String email,
                                                                    @NonNull final String password) {
        return Observable.create(new Observable.OnSubscribe<AuthResult>() {
            @Override
            public void call(final Subscriber<? super AuthResult> subscriber) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> t) {
                                Log.d(TAG, "onComplete: ");
                                subscriber.onCompleted();
                            }
                        })
                        .addOnFailureListener(subscriber::onError)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult t1) {
                                Log.d(TAG, "onSuccess: ");
                                subscriber.onNext(t1);
                            }
                        });
            }
        });
    }

}
