package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.widget.ClearableEditText;
import com.reptile.show.project.R;
import com.reptile.show.project.di.component.DaggerSearchComponent;
import com.reptile.show.project.mvp.contract.SearchContract;
import com.reptile.show.project.mvp.presenter.SearchPresenter;
import com.reptile.show.project.mvp.ui.adapter.SearchAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View
        , DefaultAdapter.OnRecyclerViewItemClickListener {
    @Inject
    RxPermissions rxPermissions;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    SearchAdapter mAdapter;

    @BindView(R.id.ed_search)
    ClearableEditText mEd_search;
    //取消
    @BindView(R.id.tv_cancel)
    TextView mTv_cancel;

    //搜索标签布局
    @BindView(R.id.ll_search_tag)
    LinearLayout mLl_search_tag;
    @BindView(R.id.tv_collect_files)
    TextView mTv_collect_files;
    @BindView(R.id.tv_document_notes)
    TextView mTv_document_notes;

    //搜索结果布局
    @BindView(R.id.ll_search_result)
    LinearLayout mLl_search_result;
    @BindView(R.id.rv_search)
    RecyclerView mRv_search;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        iniRecycler();
        mEd_search.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_SEARCH){
                showMessage("您搜索了"+textView.getText().toString());
                visibleResultLayout(true);
                mPresenter.getSearchResult();
                DeviceUtils.hideSoftKeyboard(this,mEd_search);
            }
            return false;
        });
    }

    private void iniRecycler() {
        ArmsUtils.configRecyclerView(mRv_search,mLayoutManager);
        mRv_search.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage(@NonNull String message) {
        Preconditions.checkNotNull(message);
        ArmsUtils.TopSnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.tv_cancel,R.id.tv_collect_files,R.id.tv_document_notes})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_cancel:
                killMyself();
                break;
            case R.id.tv_collect_files://收藏文件

                break;
            case R.id.tv_document_notes://文档笔记

                break;
        }
    }

    /**
     * 搜索之后的结果列表点击事件
     * @param view     被点击的 {@link View}
     * @param viewType 布局类型
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    @Override
    public void onItemClick(@NonNull View view, int viewType, @NonNull Object data, int position) {
        showMessage("点击了第"+(position+1)+"个");
    }

    /**
     * 显示或隐藏搜索返回布局
     * @param isVisible
     */
    private void visibleResultLayout(boolean isVisible){
        if(isVisible){
            if(mLl_search_tag.getVisibility() != View.GONE){
                mLl_search_tag.setVisibility(View.GONE);
            }
            if(mLl_search_result.getVisibility() != View.VISIBLE){
                mLl_search_result.setVisibility(View.VISIBLE);
            }
        }else {
            if(mLl_search_tag.getVisibility() != View.VISIBLE){
                mLl_search_tag.setVisibility(View.VISIBLE);
            }
            if(mLl_search_result.getVisibility() != View.GONE){
                mLl_search_result.setVisibility(View.GONE);
            }
        }

    }
}
