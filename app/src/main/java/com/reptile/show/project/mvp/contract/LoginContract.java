package com.reptile.show.project.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.reptile.show.project.mvp.model.entity.StatusEntity;

import io.reactivex.Observable;

public interface LoginContract {
    interface View extends IView{
        Activity getActivity();
    }

    interface Model extends IModel{
        Observable<StatusEntity> getVerCode(String phone);

        Observable<StatusEntity> register(String phone,String pwd,String code);
    }
}
