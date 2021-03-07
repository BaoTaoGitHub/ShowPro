package com.reptile.show.project.mvp.ui.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseHolder;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;

import butterknife.BindView;

public class MoveDirItemHolder extends BaseHolder<DirectoryEntity.DirectoryBean> {
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.tv_subtitle)
    TextView mTv_subtitle;
    @BindView(R.id.rl_folder)
    RelativeLayout mRl_folder;
    @BindView(R.id.cb_check)
    CheckBox mCb_check;

    //可以初始化一些用到的工具类
    public MoveDirItemHolder(View itemView) {
        super(itemView);
        mCb_check.setVisibility(View.GONE);
        mTv_subtitle.setVisibility(View.GONE);
    }

    @Override
    public void setData(@NonNull DirectoryEntity.DirectoryBean data, int position) {
        mTv_title.setText(data.getName());
    }
}
