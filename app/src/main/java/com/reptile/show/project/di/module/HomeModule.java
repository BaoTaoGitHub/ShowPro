package com.reptile.show.project.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.model.HomeModel;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.FolderEntity;
import com.reptile.show.project.mvp.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class HomeModule {

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HomeContract.View view){
        return new LinearLayoutManager(view.getActivity());
    }

    @FragmentScope
    @Provides
    static List<DirectoryEntity.DirUrlBean> provideFolderList(){
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static HomeAdapter provideHomeAdapter(List<DirectoryEntity.DirUrlBean> list){
        return new HomeAdapter(list);
    }

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);
}
