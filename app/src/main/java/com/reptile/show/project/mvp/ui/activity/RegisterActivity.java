package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.RxLifecycleUtils;
import com.reptile.show.project.R;
import com.reptile.show.project.di.component.DaggerLoginComponent;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.presenter.LoginPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.RxLifecycle;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.OnLifecycleEvent;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableSubscribeOn;
import kotlin.contracts.Returns;
import kotlin.jvm.functions.Function1;

@ActivityScope
public class RegisterActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @Inject
    RxPermissions mRxPermissions;
    @Inject
    AppManager mAppManager;

    @BindView(R.id.tv_title_left)
    TextView mTv_title_left;
    @BindView(R.id.tv_register_code)
    TextView mTv_register_code;

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
        return R.layout.activity_register;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_title_left, R.id.tv_register_code, R.id.bt_register})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_left:
                killMyself();
                break;
            case R.id.tv_register_code://验证码
                CodeCountdown();
                break;
            case R.id.bt_register://注册并登录
                if (mAppManager.activityClassIsLive(LoginActivity.class)) {
                    mAppManager.killActivity(LoginActivity.class);
                }
                launchActivity(new Intent(this, MainActivity.class));
                killMyself();
                break;
        }
    }

    private void CodeCountdown() {
        final int count = 60;
        Observable
                .interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔1秒发送执行一次
                .compose(RxLifecycleUtils.bindToLifecycle((IView) RegisterActivity.this))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .take(count + 1)//设置循环+1次
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count-aLong;
                    }
                })
                .doOnSubscribe(disposable -> {
                    mTv_register_code.setEnabled(false);

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        mTv_register_code.setEnabled(false);
                        mTv_register_code.setText("剩余时间"+aLong+"秒");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        mTv_register_code.setText(ArmsUtils.getString(RegisterActivity.this,R.string.register_get_code));
                        mTv_register_code.setEnabled(true);
                    }
                });

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
