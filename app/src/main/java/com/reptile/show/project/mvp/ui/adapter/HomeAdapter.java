package com.reptile.show.project.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.ui.holder.HomeItemHolder;

import java.util.List;

public class HomeAdapter extends DefaultAdapter<DirectoryEntity.DirUrlBean> {

    public HomeAdapter(List<DirectoryEntity.DirUrlBean> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<DirectoryEntity.DirUrlBean> getHolder(@NonNull View v, int viewType) {
//        if(viewType== AppConstants.HomeAdapterViewType.TYPE_DIR){
            return new HomeItemHolder(v);
//        }else {
//            return new HomeItemHolder(v);
//        }
    }

    @Override
    public int getLayoutId(int viewType) {
        if(viewType== AppConstants.HomeAdapterViewType.TYPE_DIR){
            return R.layout.list_home;
        }else{
            return R.layout.list_search;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    /**
     * 全选/反选
     * @param isSelectAll
     */
    public void itemSelectAll(boolean isSelectAll){
        for (int i = 0; i < getItemCount(); i++) {
            if(getItem(i).isCheck() != isSelectAll){
                getItem(i).setCheck(isSelectAll);
            }
        }
    }
}
