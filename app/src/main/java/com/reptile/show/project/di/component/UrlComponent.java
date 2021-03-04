package com.reptile.show.project.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.di.module.UrlModule;
import com.reptile.show.project.mvp.contract.UrlContract;
import com.reptile.show.project.mvp.ui.activity.UrlActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = UrlModule.class,dependencies = AppComponent.class)
public interface UrlComponent {
    void inject(UrlActivity activity);

    @Component.Builder
    interface Builder{
        @BindsInstance
        UrlComponent.Builder view(UrlContract.View view);

        UrlComponent.Builder appComponent(AppComponent appComponent);

        UrlComponent build();
    }
}
