package com.reptile.show.project.di.module;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.MainModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainModule {
    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(MainContract.View view){
        return  new RxPermissions((FragmentActivity)view.getActivity());
    }

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);
}
