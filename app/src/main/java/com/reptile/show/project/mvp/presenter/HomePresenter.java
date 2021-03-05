package com.reptile.show.project.mvp.presenter;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.FolderEntity;
import com.reptile.show.project.mvp.model.entity.LoginEntity;
import com.reptile.show.project.mvp.model.entity.UrlEntity;
import com.reptile.show.project.mvp.ui.adapter.HomeAdapter;

import java.security.PublicKey;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    List<DirectoryEntity.DirUrlBean> mDirUrlList;
    @Inject
    HomeAdapter mAdapter;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取目录内容
     * @param d_id 传0为最外层目录
     */
    public void getDirList(int d_id){
        LoginEntity entity =  getSP(mRootView.getActivity(),AppConstants.LOGIN_SP);
        mModel.getDirContent(entity.getToken(),d_id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DirectoryEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseResponse<DirectoryEntity> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            mDirUrlList.clear();
                            mDirUrlList.addAll(stringBaseResponse.getInfo().getDirUrl());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    public void getUrlList(int url_id){
        LoginEntity entity =  getSP(mRootView.getActivity(),AppConstants.LOGIN_SP);
        mModel.getUrlContent(entity.getToken(),url_id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UrlEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseResponse<UrlEntity> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            mRootView.showWebUrl(stringBaseResponse.getInfo().getUrl());
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    public void createDir(String name,int parentId){
        LoginEntity entity =  getSP(mRootView.getActivity(),AppConstants.LOGIN_SP);
        mModel.createDir(entity.getToken(),name,parentId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseResponse<Object> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            getDirList(parentId);
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    public void moveDir(int dId,int parentId){
        LoginEntity entity =  getSP(mRootView.getActivity(),AppConstants.LOGIN_SP);
        mModel.moveDir(entity.getToken(),dId,parentId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseResponse<Object> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            //TODO
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    public void renameDir(int dId,String name){
        LoginEntity entity =  getSP(mRootView.getActivity(),AppConstants.LOGIN_SP);
        mModel.editDir(entity.getToken(),dId,name)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseResponse<Object> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    public void removeDir(int dId){
        LoginEntity entity =  getSP(mRootView.getActivity(),AppConstants.LOGIN_SP);
        mModel.deleteDir(entity.getToken(),dId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Object>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseResponse<Object> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    //此处一般为退出HomeFragment时调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mApplication = null;
    }

}
