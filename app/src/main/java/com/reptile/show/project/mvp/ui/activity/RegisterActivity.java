package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.reptile.show.project.di.component.DaggerLoginComponent;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.presenter.LoginPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@ActivityScope
public class RegisterActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @Inject
    RxPermissions mRxPermissions;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Preconditions.checkNotNull(message);
        ArmsUtils.TopSnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent);
        ArmsUtils.startActivity(intent);

    }

    @Override
    public void killMyself() {
        mRxPermissions = null;
        finish();
    }
}
