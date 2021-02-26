package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.contract.SearchContract;
import com.reptile.show.project.mvp.presenter.SearchPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

@ActivityScope
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    @Inject
    RxPermissions rxPermissions;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public RxPermissions getRxPermissions() {
        return rxPermissions;
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
        finish();
    }
}
