package com.reptile.show.project.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.reptile.show.project.di.module.HomeModule;
import com.reptile.show.project.di.module.MainModule;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.HomeModel;
import com.reptile.show.project.mvp.ui.activity.MainActivity;
import com.reptile.show.project.mvp.ui.fragment.HomeFragment;

import dagger.BindsInstance;
import dagger.Component;

@FragmentScope
@Component(modules = HomeModule.class,dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);

        HomeComponent.Builder appComponent(AppComponent appComponent);

        HomeComponent build();
    }
}
