package com.reptile.show.project.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MoveDirContract;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.LoginEntity;
import com.reptile.show.project.mvp.ui.activity.MoveDirActivity;
import com.reptile.show.project.mvp.ui.adapter.MoveDirAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class MoveDirPresenter extends BasePresenter<MoveDirContract.Model,MoveDirContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;

    @Inject
    List<DirectoryEntity.DirectoryBean> mDirList;
    @Inject
    MoveDirAdapter mAdapter;

    public boolean canBack;
    //当前所处页面父ID
    public int parentId;

    @Inject
    public MoveDirPresenter(MoveDirContract.Model model, MoveDirContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取目录内容
     *
     * @param d_id 传0为最外层目录
     */
    public void getDirList(int d_id) {

        LoginEntity entity = getSP(mRootView.getActivity(), AppConstants.LOGIN_SP);
        mModel.getDirContent(entity.getToken(), d_id,true)
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
                            parentId = d_id;
                            mDirList.clear();
                            mDirList.addAll(stringBaseResponse.getInfo().getDirectory());
                            mAdapter.notifyDataSetChanged();
                            if(d_id==0){
                                canBack = false;
                            }else {
                                canBack = true;
                            }
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    /**
     * 新建文件夹
     * @param name
     * @param parentId
     */
    public void createDir(String name, int parentId) {
        LoginEntity entity = getSP(mRootView.getActivity(), AppConstants.LOGIN_SP);
        mModel.createDir(entity.getToken(), name, parentId,true)
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

    /**
     * 移动文件夹
     * @param dId
     * @param parentId
     */
    public void moveDir(int dId, int parentId) {
        LoginEntity entity = getSP(mRootView.getActivity(), AppConstants.LOGIN_SP);
        mModel.moveDir(entity.getToken(), dId, parentId,true)
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
                            mRootView.showMessage("移动文件夹成功");
                            mRootView.killMyself();
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

    /**
     * 移动URL
     * @param dId
     * @param url_id
     */
    public void moveUrl(int dId,int url_id) {
        LoginEntity entity = getSP(mRootView.getActivity(), AppConstants.LOGIN_SP);
        mModel.moveUrl(entity.getToken(), dId,url_id,true)
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
                            mRootView.showMessage("移动文件成功");
                            mRootView.killMyself();
                        } else {
                            mRootView.showMessage(stringBaseResponse.getDesc());
                        }
                    }
                });
    }

}
