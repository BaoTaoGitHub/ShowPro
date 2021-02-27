package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.reptile.show.project.R;
import com.reptile.show.project.di.component.DaggerSearchComponent;
import com.reptile.show.project.mvp.contract.SearchContract;
import com.reptile.show.project.mvp.presenter.SearchPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

@ActivityScope
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {
    @Inject
    RxPermissions rxPermissions;

    @BindView(R.id.ed_search)
    EditText mEd_search;

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
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mEd_search.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_SEARCH){
                showMessage("您搜索了"+textView.getText().toString());
                
            }
            return false;
        });
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
}
