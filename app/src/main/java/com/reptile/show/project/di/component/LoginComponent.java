package com.reptile.show.project.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.reptile.show.project.di.module.LoginModule;
import com.reptile.show.project.mvp.contract.LoginContract;
import com.reptile.show.project.mvp.ui.activity.LoginActivity;
import com.reptile.show.project.mvp.ui.activity.RegisterActivity;

import dagger.BindsInstance;
import dagger.Component;

@ActivityScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    @Component.Builder
    interface Builder{
        @BindsInstance
        LoginComponent.Builder view(LoginContract.View view);

        LoginComponent.Builder appComponent(AppComponent appComponent);

        LoginComponent build();
    }
}
