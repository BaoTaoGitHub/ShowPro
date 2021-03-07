package com.reptile.show.project.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MoveDirContract;
import com.reptile.show.project.mvp.model.HomeModel;
import com.reptile.show.project.mvp.model.MoveDirModel;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.ui.adapter.HomeAdapter;
import com.reptile.show.project.mvp.ui.adapter.MoveDirAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MoveDirModule {

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MoveDirContract.View view){
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static List<DirectoryEntity.DirectoryBean> provideFolderList(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static MoveDirAdapter provideMoveDirAdapter(List<DirectoryEntity.DirectoryBean> list){
        return new MoveDirAdapter(list);
    }

    @Binds
    abstract MoveDirContract.Model bindMoveDirModel(MoveDirModel model);
}
