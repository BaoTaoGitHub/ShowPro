package com.reptile.show.project.mvp.ui.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.FolderEntity;

import butterknife.BindView;

public class HomeItemHolder extends BaseHolder<DirectoryEntity.DirUrlBean> {
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.tv_subtitle)
    TextView mTv_subtitle;
    @BindView(R.id.rl_folder)
    RelativeLayout mRl_folder;

    //URL布局
    @BindView(R.id.tv_search_title)
    TextView mTv_search_title;
    @BindView(R.id.tv_search_content)
    TextView mTv_search_content;
    @BindView(R.id.tv_search_time)
    TextView mTv_search_time;

    //可以初始化一些用到的工具类
    public HomeItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull DirectoryEntity.DirUrlBean data, int position) {
        if(mTv_title!=null){
            mTv_title.setText(data.getTitle());
            mTv_subtitle.setText(data.getChildCount() + "个内容");
            mRl_folder.setActivated(data.isCheck());
        }else{
            mTv_search_title.setText(data.getTitle());
            mTv_search_content.setText(data.getContent());
            mTv_search_time.setText(data.getInit_time());
        }
    }
}
