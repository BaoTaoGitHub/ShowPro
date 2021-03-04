package com.reptile.show.project.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.reptile.show.project.mvp.contract.UrlContract;

import javax.inject.Inject;

@ActivityScope
public class UrlModel extends BaseModel implements UrlContract.Model {

    @Inject
    public UrlModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
