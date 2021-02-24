package com.reptile.show.project.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.integration.AppManager;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.model.entity.FolderEntity;
import com.reptile.show.project.mvp.ui.holder.HomeItemHolder;

import java.util.List;

import javax.inject.Inject;

public class HomeAdapter extends DefaultAdapter<FolderEntity> {

    public HomeAdapter(List<FolderEntity> infos,@NonNull OnRecyclerViewItemClickListener listener) {
        super(infos);
        if(listener!=null){
            setOnItemClickListener(listener);
        }
    }

    @NonNull
    @Override
    public BaseHolder<FolderEntity> getHolder(@NonNull View v, int viewType) {
        return new HomeItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_home;
    }
}
