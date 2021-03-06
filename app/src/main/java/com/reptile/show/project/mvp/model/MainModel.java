package com.reptile.show.project.mvp.model;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.reptile.show.project.mvp.contract.MainContract;

import javax.inject.Inject;

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(){

    }
}
