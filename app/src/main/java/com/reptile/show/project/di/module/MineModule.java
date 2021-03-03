package com.reptile.show.project.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.contract.MineContract;
import com.reptile.show.project.mvp.model.HomeModel;
import com.reptile.show.project.mvp.model.MineModel;
import com.reptile.show.project.mvp.ui.fragment.MineFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MineModule {

    @FragmentScope
    @Provides
    static RxPermissions provideRxPermissions(MineContract.View view){
        return  new RxPermissions((FragmentActivity) view.getActivity());
    }

    @Binds
    abstract MineContract.Model bindMineModel(MineModel model);
}
