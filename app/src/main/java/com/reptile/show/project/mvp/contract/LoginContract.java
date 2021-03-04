package com.reptile.show.project.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.reptile.show.project.mvp.model.entity.BaseResponse;
import com.reptile.show.project.mvp.model.entity.LoginEntity;

import io.reactivex.Observable;

public interface LoginContract {
    interface View extends IView{
        Activity getActivity();

        //验证码倒计时
        void CodeCountdown();
        //注册并登录
        void registerAndLogin(LoginEntity entity);
    }

    interface Model extends IModel{
        Observable<BaseResponse<Object>> getVerCode(String phone);

        Observable<BaseResponse<Object>> register(String phone,String pwd,String code);

        Observable<BaseResponse<LoginEntity>> login(String phone,String pwd);
    }
}
