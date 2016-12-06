package com.vnwarriors.tastyclarify.ui.activity.authentication.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ViewAnimator;

import com.vnwarriors.tastyclarify.MainApplication;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.databinding.ActivityLoginBinding;
import com.vnwarriors.tastyclarify.ui.activity.BaseActivity;
import com.vnwarriors.tastyclarify.ui.activity.authentication.resetpassword.ResetPasswordActivity;
import com.vnwarriors.tastyclarify.ui.activity.authentication.signup.SignupActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    LoginViewModel viewModel = new LoginViewModel();
    @BindView(R.id.vaLoginState)
    ViewAnimator viewAnimator;

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        Log.d(TAG, "onLoginClick: ");
        viewModel.loginDemo();
    }

    @OnClick(R.id.btn_signup)
    public void onSighupClick() {
        startActivity(new Intent(MainApplication.mContext, SignupActivity.class));
    }

    @OnClick(R.id.btnResetPassword)
    public void onResetPasswordClick() {
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);
        ButterKnife.bind(this);
        setupUI();

    }

    private void setupUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewAnimator.setDisplayedChild(1);
    }


    @Override
    protected void bindViewModel() {
        viewModel.message()
                .takeUntil(stopEvent())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showMessage);
        viewModel.loadingState()
                .takeUntil(stopEvent())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setLoadingState);
        viewModel.init();
    }


    private void showMessage(String value) {
        new AlertDialog.Builder(this)
                .setMessage(value)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setLoadingState(Integer value) {
        viewAnimator.setDisplayedChild(value);
    }
}
