package com.vnwarriors.tastyclarify.ui.activity.authentication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ViewAnimator;

import com.google.firebase.auth.AuthResult;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.databinding.ActivityLoginBinding;
import com.vnwarriors.tastyclarify.ui.activity.BaseActivity;
import com.vnwarriors.tastyclarify.ui.activity.browser.BrowserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    LoginViewModel viewModel = new LoginViewModel();
    @BindView(R.id.vaLoginState)
    ViewAnimator viewAnimator;
    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        Log.d(TAG, "onLoginClick: ");
        viewAnimator.setDisplayedChild(1);
        viewModel.login()
                .takeUntil(stopEvent())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResult>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                        showMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(AuthResult authResult) {
                        Log.d(TAG, "onNext: ");
                        Intent intent = new Intent(LoginActivity.this, BrowserActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
    @OnClick(R.id.btn_signup)
    public void onSighupClick(){
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }
    @OnClick(R.id.btn_reset_password)
    public void onResetPasswordClick(){
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void bindViewModel() {
        viewModel.message()
                .takeUntil(stopEvent())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMessage);
    }


    private void showMessage(String value) {
        new AlertDialog.Builder(this)
                .setMessage(value)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
