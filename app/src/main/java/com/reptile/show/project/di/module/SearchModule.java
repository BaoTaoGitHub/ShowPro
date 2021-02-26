package com.reptile.show.project.di.module;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.contract.SearchContract;
import com.reptile.show.project.mvp.model.SearchModel;
import com.reptile.show.project.mvp.ui.activity.SearchActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SearchModule {

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(SearchContract.View view){
        return  new RxPermissions((SearchActivity)view.getActivity());
    }

    @Binds
    abstract SearchContract.Model bindSearchModel(SearchModel model);
}
