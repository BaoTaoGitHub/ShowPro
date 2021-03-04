package com.reptile.show.project.mvp.contract;

import android.app.Activity;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

public interface UrlContract {

    interface View extends IView{
        Activity getActivity();

    }

    interface Model extends IModel{

    }
}
