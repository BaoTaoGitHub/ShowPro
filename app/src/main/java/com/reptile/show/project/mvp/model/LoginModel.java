package com.reptile.show.project.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.reptile.show.project.mvp.contract.LoginContract;

import javax.inject.Inject;

@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
