package com.reptile.show.project.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.ui.holder.HomeItemHolder;
import com.reptile.show.project.mvp.ui.holder.MoveDirItemHolder;

import java.util.List;

public class MoveDirAdapter extends DefaultAdapter<DirectoryEntity.DirectoryBean> {

    public MoveDirAdapter(List<DirectoryEntity.DirectoryBean> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<DirectoryEntity.DirectoryBean> getHolder(@NonNull View v, int viewType) {
            return new MoveDirItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
            return R.layout.list_home;
    }

}
