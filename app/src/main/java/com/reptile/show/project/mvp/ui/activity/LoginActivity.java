package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.reptile.show.project.R;
import com.reptile.show.project.di.component.DaggerLoginComponent;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.presenter.LoginPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

@ActivityScope
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @Inject
    RxPermissions mRxPermissions;

    @BindView(R.id.et_login_phone)
    private EditText mEt_login_phone;
    @BindView(R.id.et_login_pwd)
    private EditText mEt_login_pwd;
    @BindView(R.id.bt_login)
    private Button mBt_login;
    @BindView(R.id.tv_login_register)
    private TextView mTv_login_register;


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
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.bt_login,R.id.tv_login_register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_login:
                launchActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.tv_login_register:
                launchActivity(new Intent(this,RegisterActivity.class));
                break;
        }
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void killMyself() {
        mRxPermissions = null;
        finish();
    }

}
