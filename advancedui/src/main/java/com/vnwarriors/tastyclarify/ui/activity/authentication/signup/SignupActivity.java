package com.vnwarriors.tastyclarify.ui.activity.authentication.signup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.ViewAnimator;

import com.google.firebase.auth.AuthResult;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.databinding.ActivitySignupBinding;
import com.vnwarriors.tastyclarify.ui.activity.BaseActivity;
import com.vnwarriors.tastyclarify.ui.activity.authentication.resetpassword.ResetPasswordActivity;
import com.vnwarriors.tastyclarify.ui.activity.browser.BrowserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SignupActivity extends BaseActivity {
    private static final String TAG = "SignupActivity";
    private ActivitySignupBinding binding;
    SignupViewModel viewModel = new SignupViewModel();
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    @BindView(R.id.vaLoginState)
    ViewAnimator viewAnimator;
    @OnClick(R.id.btnSignup)
    void signupClick(){
        viewModel.signup()
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
                    }

                    @Override
                    public void onNext(AuthResult authResult) {
                        Log.d(TAG, "onNext: ");
                        Intent intent = new Intent(SignupActivity.this, BrowserActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
    @OnClick(R.id.btnResetPassword)
    void resetPasswordClick(){
        startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
    }
    @OnClick(R.id.btnSignin)
    void signinClick(){
        finishAfterTransition();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        binding.setViewModel(viewModel);
        ButterKnife.bind(this);
        setupUI();
    }

    private void setupUI() {
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
