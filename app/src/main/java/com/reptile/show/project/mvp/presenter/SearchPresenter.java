package com.reptile.show.project.mvp.presenter;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.contract.SearchContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class SearchPresenter extends BasePresenter<SearchContract.Model,SearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;

    @Inject
    public SearchPresenter(SearchContract.Model model, SearchContract.View rootView) {
        super(model, rootView);
    }
    //打开App时调用
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(){

    }

    //此Activity初始化时调用
    @Override
    public void onStart() {
        super.onStart();

    }

    //此处一般为退出此Activity时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }
}
