package com.reptile.show.project.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.di.module.SearchModule;
import com.reptile.show.project.mvp.contract.SearchContract;
import com.reptile.show.project.mvp.ui.activity.SearchActivity;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Provides;

@ActivityScope
@Component(modules = SearchModule.class,dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);

    @Component.Builder
    interface Builder{
        @BindsInstance
        SearchComponent.Builder view(SearchContract.View view);

        SearchComponent.Builder appComponent(AppComponent appComponent);

        SearchComponent build();
    }
}
