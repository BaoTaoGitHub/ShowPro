package com.reptile.show.project.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.ui.holder.SearchItemHolder;

import java.util.List;

public class SearchAdapter extends DefaultAdapter<String> {

    public SearchAdapter(List<String> infos) {
        super(infos);
    }

    @NonNull
    @Override
    public BaseHolder<String> getHolder(@NonNull View v, int viewType) {
        return new SearchItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.list_search;
    }
}
