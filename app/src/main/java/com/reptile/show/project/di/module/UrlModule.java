package com.reptile.show.project.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.contract.UrlContract;
import com.reptile.show.project.mvp.model.UrlModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.fragment.app.FragmentActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class UrlModule {

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(UrlContract.View view){
        return  new RxPermissions((FragmentActivity)view.getActivity());
    }

    @Binds
    abstract UrlContract.Model bindUrlModel(UrlModel model);
}
