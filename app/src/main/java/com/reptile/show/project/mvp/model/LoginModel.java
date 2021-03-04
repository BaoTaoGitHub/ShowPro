package com.reptile.show.project.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.model.api.service.CommonService;
import com.reptile.show.project.mvp.model.api.service.LoginService;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.LoginEntity;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseResponse<Object>> getVerCode(String phone) {
        return mRepositoryManager.obtainRetrofitService(CommonService.class).getVerCode(phone);
    }

    @Override
    public Observable<BaseResponse<Object>> register(String phone, String pwd, String code) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).register(phone,pwd,code);
    }

    @Override
    public Observable<BaseResponse<LoginEntity>> login(String phone, String pwd) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).login(phone,pwd);
    }
}
