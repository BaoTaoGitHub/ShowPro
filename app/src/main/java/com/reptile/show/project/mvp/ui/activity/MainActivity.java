package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.FragmentUtils;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.widget.smartpopupwindow.HorizontalPosition;
import com.jess.arms.widget.smartpopupwindow.SmartPopupWindow;
import com.jess.arms.widget.smartpopupwindow.VerticalPosition;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.app.EventBusTags;
import com.reptile.show.project.di.component.DaggerMainComponent;
import com.reptile.show.project.mvp.contract.MainContract;
import com.reptile.show.project.mvp.model.entity.BaseEventEntity;
import com.reptile.show.project.mvp.model.entity.PopupAddEntity;
import com.reptile.show.project.mvp.presenter.MainPresenter;
import com.reptile.show.project.mvp.ui.adapter.PopupAddAdapter;
import com.reptile.show.project.mvp.ui.fragment.HomeFragment;
import com.reptile.show.project.mvp.ui.fragment.MineFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rl_parent)
    RelativeLayout mRl_parent;
    @BindView(R.id.fl_content)
    FrameLayout mFl_content;
    @BindView(R.id.rg_bottom)
    RadioGroup mRg_bottom;
    @BindView(R.id.rb_add)
    RadioButton mRb_add;

    @Inject
    RxPermissions rxPermissions;

    private HomeFragment homeFragment;
    private MineFragment mineFragment;
    private int mReplace = 0;

    private List<Fragment> mFragments;
    private double firstTime = 0;

    private final int[] mDrawables = new int[]{R.mipmap.ico_popup_add_links, R.mipmap.ico_popup_add_new};
    private final String[] mDrawableNames = new String[]{"链接收藏", "新建笔记(暂未开放)"};
    static SmartPopupWindow bottomPopupWindow;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //TODO 有权限可以在这先判断权限  rxPermissions.request(输入权限名称); 权限：Manifest.permission.ACCEPT_HANDOVER
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            mineFragment = MineFragment.newInstance();
        } else {
            FragmentManager manager = getSupportFragmentManager();
            homeFragment = (HomeFragment) FragmentUtils.findFragment(manager, HomeFragment.class);
            mineFragment = (MineFragment) FragmentUtils.findFragment(manager, MineFragment.class);
        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(homeFragment);
            mFragments.add(mineFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.fl_content, 0);
        mRg_bottom.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        return false;
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
        checkNotNull(message);
        ArmsUtils.TopSnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    //底部Tab选择监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main:
                if (mReplace != 0) {
                    mReplace = 0;
                    FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
                }
                break;
            case R.id.rb_mine:
                if (mReplace != 1) {
                    mReplace = 1;
                    FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
                }
                break;
        }
    }

    @OnClick(R.id.rb_add)
    public void onClick() {
        showPopupAdd();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存当前Activity显示的Fragment索引
        outState.putInt(AppConstants.ACTIVITY_FRAGMENT_REPLACE, mReplace);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ArmsUtils.TopSnackbarText("再按一次退出程序");
            firstTime = secondTime;
        } else {
            ArmsUtils.exitApp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.debugInfo("==MainActivityOnDestroy==","进入");
        DataHelper.removeSF(getActivity(),AppConstants.LOGIN_SP);
        this.rxPermissions = null;
        this.mFragments = null;
        this.bottomPopupWindow = null;
    }

    private void showPopupAdd() {
        if (bottomPopupWindow == null) {
            View bottomView = LayoutInflater.from(this).inflate(R.layout.popup_bottom_add, null);
            RecyclerView mRv_popup_add = (RecyclerView) bottomView.findViewById(R.id.rv_popup_add);
            TextView mTv_popup_cancel = (TextView) bottomView.findViewById(R.id.tv_popup_cancel);
            ArmsUtils.configRecyclerView(mRv_popup_add, new GridLayoutManager(this, 4));
            List<PopupAddEntity> entities = new ArrayList<>();
            PopupAddAdapter pAdapter = new PopupAddAdapter(entities);
            mRv_popup_add.setAdapter(pAdapter);
            for (int i = 0; i < mDrawables.length; i++) {
                entities.add(new PopupAddEntity(mDrawableNames[i], ArmsUtils.getDrawablebyResource(this, mDrawables[i])));
            }
            pAdapter.notifyDataSetChanged();
            bottomPopupWindow = SmartPopupWindow.Builder.build(getActivity(), bottomView)
                    .setAlpha(0.4f)
                    .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setOutsideTouchDismiss(false)
                    .createPopupWindow();
            bottomPopupWindow.setFocusable(true);
            mTv_popup_cancel.setOnClickListener(view -> {
                if (bottomPopupWindow.isShowing()) {
                    bottomPopupWindow.dismiss();
                }
            });
            pAdapter.setOnItemClickListener((View view, int viewType, @NonNull Object data, int position) -> {
                switch (position) {
                    case 0:
                        //链接收藏
                        bottomPopupWindow.dismiss();
                        EventBusManager.getInstance().post(new BaseEventEntity<String>("1"), EventBusTags.Main2Home);
                        break;
                    case 1:
                        //新建笔记
                        showMessage("暂未开放");
                        break;
                }
            });
        }
        if (!bottomPopupWindow.isShowing()) {
            bottomPopupWindow.showAtAnchorView(mRg_bottom, VerticalPosition.ALIGN_BOTTOM, HorizontalPosition.CENTER);
        }
    }
}
