package com.reptile.show.project.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.di.module.MoveDirModule;
import com.reptile.show.project.mvp.contract.MineContract;
import com.reptile.show.project.mvp.contract.MoveDirContract;
import com.reptile.show.project.mvp.ui.activity.MoveDirActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = MoveDirModule.class,dependencies = AppComponent.class)
public interface MoveDirComponent {
    void inject(MoveDirActivity activity);

    @Component.Builder
    interface Builder{
        @BindsInstance
        MoveDirComponent.Builder view(MoveDirContract.View view);

        MoveDirComponent.Builder appComponent(AppComponent appComponent);

        MoveDirComponent build();
    }
}
