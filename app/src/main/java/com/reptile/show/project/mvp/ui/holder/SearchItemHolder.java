package com.reptile.show.project.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.reptile.show.project.R;

import butterknife.BindView;

public class SearchItemHolder extends BaseHolder<String> {
    @BindView(R.id.tv_search_title)
    TextView mTv_search_title;

    public SearchItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull String data, int position) {
        mTv_search_title.setText(data);
    }
}
