package com.reptile.show.project.di.module;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.contract.SearchContract;
import com.reptile.show.project.mvp.model.SearchModel;
import com.reptile.show.project.mvp.ui.activity.SearchActivity;
import com.reptile.show.project.mvp.ui.adapter.SearchAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

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

    @ActivityScope
    @Provides
    static List<String> provideList(){
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SearchContract.View view){
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static SearchAdapter provideSearchAdapter(List<String> infos){
        return new SearchAdapter(infos);
    }

    @Binds
    abstract SearchContract.Model bindSearchModel(SearchModel model);
}
