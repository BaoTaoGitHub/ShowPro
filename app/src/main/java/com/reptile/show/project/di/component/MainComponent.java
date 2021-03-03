package com.reptile.show.project.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.di.module.MainModule;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.ui.activity.MainActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = MainModule.class,dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        MainComponent.Builder appComponent(AppComponent appComponent);

        MainComponent build();
    }
}
