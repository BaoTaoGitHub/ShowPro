package com.reptile.show.project.mvp.presenter;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.entity.FolderEntity;
import com.reptile.show.project.mvp.ui.adapter.HomeAdapter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    List<FolderEntity> mFolderList;
    @Inject
    HomeAdapter mAdapter;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    //打开App时调用
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    void onCreate() {
//    }

    public void getRecycleData() {
        mFolderList.clear();
        for (int i = 0; i < 10; i++) {
            FolderEntity entity = new FolderEntity("标题" + i, String.valueOf(i + 1));
            mFolderList.add(entity);
        }
        mAdapter.notifyItemInserted(0);
//        mAdapter.notifyDataSetChanged();
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
