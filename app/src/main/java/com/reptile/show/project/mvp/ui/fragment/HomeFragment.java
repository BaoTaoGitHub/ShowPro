package com.reptile.show.project.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.reptile.show.project.R;
import com.reptile.show.project.di.component.DaggerHomeComponent;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.model.entity.FolderEntity;
import com.reptile.show.project.mvp.presenter.HomePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, DefaultAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.toolbar_title)
    Toolbar mToolbar_title;
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.recycle_list)
    RecyclerView mRecycle_list;

    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        iniRecycleView();
        mTv_title.setText(ArmsUtils.getString(getActivity(),R.string.bottom_main));
        mToolbar_title.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ArmsUtils.TopSnackbarText("点击了"+item.getTitle());
                switch (item.getItemId()) {
                    case R.id.menu_batch_editor:
                        return true;
                    case R.id.menu_new_folder:
                        return true;
                    case R.id.menu_sort_modify_time:
                        item.setChecked(!item.isChecked());
                        return true;
                    case R.id.menu_sort_create_time:
                        item.setChecked(!item.isChecked());
                        return true;
                    case R.id.menu_sort_name:
                        item.setChecked(!item.isChecked());
                        return true;
                }
                return false;
            }
        });
        mPresenter.getRecycleData();
    }

    private void iniRecycleView() {
        ArmsUtils.configRecyclerView(mRecycle_list,mLayoutManager);
        mRecycle_list.setAdapter(mAdapter);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {
    }

    /**
     * item 点击事件
     * @param view     被点击的 {@link View}
     * @param viewType 布局类型
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    @Override
    public void onItemClick(@NonNull View view, int viewType, @NonNull Object data, int position) {
        showMessage("点击了"+(position+1));
    }

    @Override
    public DefaultAdapter.OnRecyclerViewItemClickListener getItemListener() {
        return this;
    }
}
