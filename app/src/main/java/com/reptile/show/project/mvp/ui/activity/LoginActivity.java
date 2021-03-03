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
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.ProgressDialogUtils;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.di.component.DaggerLoginComponent;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.model.entity.LoginEntity;
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
    EditText mEt_login_phone;
    @BindView(R.id.et_login_pwd)
    EditText mEt_login_pwd;
    @BindView(R.id.bt_login)
    Button mBt_login;
    @BindView(R.id.tv_login_register)
    TextView mTv_login_register;

    private String mPhone;
    private String mPwd;

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
                mPhone  = mEt_login_phone.getText().toString();
                mPwd = mEt_login_pwd.getText().toString();
                if(Preconditions.checkPhone(mPhone)
                        &&Preconditions.checkPwd(mPwd)){
                    mPresenter.login(mPhone,mPwd);
                }
                break;
            case R.id.tv_login_register:
                launchActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }


    @Override
    public void showLoading() {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(this);
            progressDialogUtils.setMessage("请稍后...");
        }
        progressDialogUtils.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialogUtils != null)
            progressDialogUtils.dismiss();
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

    //TODO 不需要在此实现
    @Override
    public void CodeCountdown() {

    }

    @Override
    public void registerAndLogin(LoginEntity entity) {
        Preconditions.checkNotNull(entity);
        LoginEntity.InfoBean infoBean = entity.getInfo();
        infoBean.setPhone(mPhone);
        infoBean.setPwd(mPwd);
        entity.setInfo(infoBean);
        if(DataHelper.saveDeviceData(getActivity(), AppConstants.LOGIN_SP,entity)){
            launchActivity(new Intent(this,MainActivity.class));
            killMyself();
        }else {
            showMessage("登录失败,数据存储失败！");
        }
    }

    @Override
    public void killMyself() {
        mRxPermissions = null;
        finish();
    }

}
