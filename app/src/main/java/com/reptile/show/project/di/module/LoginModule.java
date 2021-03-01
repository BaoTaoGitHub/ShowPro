package com.reptile.show.project.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.LoginModel;
import com.reptile.show.project.mvp.ui.activity.LoginActivity;
import com.reptile.show.project.mvp.ui.activity.RegisterActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class LoginModule {

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(LoginContract.View view) {

        if (view instanceof LoginActivity) {
            return new RxPermissions((LoginActivity) view.getActivity());
        }
        return new RxPermissions((RegisterActivity) view.getActivity());

    }

    @Binds
    abstract LoginContract.Model bindLoginModel(LoginModel model);
}
