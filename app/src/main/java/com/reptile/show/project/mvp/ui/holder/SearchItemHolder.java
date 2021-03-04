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
    @BindView(R.id.tv_search_content)
    TextView mTv_search_content;
    @BindView(R.id.tv_search_time)
    TextView mTv_search_time;

    public SearchItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(@NonNull String data, int position) {
        mTv_search_title.setText(data);
        mTv_search_content.setText("推荐非常贴心的一款文献翻译工具，这个绝对是论文科研党的有力助手，翻译外国文献用它非常的方便。首先得这样那样，再这样那样。就好了");
        mTv_search_time.setText("2020-12-31");

    }
}
