package com.reptile.show.project.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import androidx.fragment.app.FragmentActivity;

public interface MineContract {
    interface View extends IView{
        Activity getActivity();
    }

    interface Model extends IModel{

    }
}
