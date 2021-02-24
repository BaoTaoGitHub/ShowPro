package com.reptile.show.project.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.model.entity.FolderEntity;

import butterknife.BindView;

public class HomeItemHolder extends BaseHolder<FolderEntity> {
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.tv_subtitle)
    TextView mTv_subtitle;
    //可以初始化一些用到的工具类
    public HomeItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull FolderEntity data, int position) {
        mTv_title.setText(data.getTitle());
        mTv_subtitle.setText(data.getSubtitle()+"个内容");
    }
}
