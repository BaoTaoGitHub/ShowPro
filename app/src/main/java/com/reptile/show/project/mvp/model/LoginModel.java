package com.reptile.show.project.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.model.api.service.CommonService;
import com.reptile.show.project.mvp.model.entity.StatusEntity;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<StatusEntity> getVerCode(String phone) {
//        return Observable.just(mRepositoryManager
//        .obtainRetrofitService(CommonService.class).getVerCode(phone)
//                .flatMap(Function));
        return null;
    }

    @Override
    public Observable<StatusEntity> register(String phone, String pwd, String code) {
        return null;
    }
}
