package com.reptile.show.project.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.model.entity.PopupAddEntity;

import java.util.List;

public class PopupAddAdapter extends DefaultAdapter<PopupAddEntity> {

    public PopupAddAdapter(List<PopupAddEntity> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<PopupAddEntity> getHolder(@NonNull View v, int viewType) {
        return null;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.;
    }
}
