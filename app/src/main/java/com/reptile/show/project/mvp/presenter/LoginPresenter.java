package com.reptile.show.project.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.reptile.show.project.mvp.contract.LoginContract;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model,LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mErrorHandler = null;
        mAppManager = null;
        mApplication = null;

    }
}
