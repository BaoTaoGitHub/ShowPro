package com.reptile.show.project.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.model.HomeModel;
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
    static List<FolderEntity> provideFolderList(){
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static DefaultAdapter.OnRecyclerViewItemClickListener provideItemListener(HomeContract.View view){
        return view.getItemListener();
    }

    @FragmentScope
    @Provides
    static RecyclerView.Adapter provideHomeAdapter(List<FolderEntity> list,DefaultAdapter.OnRecyclerViewItemClickListener listener){
        return new HomeAdapter(list,listener);
    }

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);
}
