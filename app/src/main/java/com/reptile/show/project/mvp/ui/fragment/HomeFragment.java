package com.reptile.show.project.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.widget.CustomPopupWindow;
import com.jess.arms.widget.smartpopupwindow.HorizontalPosition;
import com.jess.arms.widget.smartpopupwindow.SmartPopupWindow;
import com.jess.arms.widget.smartpopupwindow.VerticalPosition;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.di.component.DaggerHomeComponent;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.model.entity.FolderEntity;
import com.reptile.show.project.mvp.presenter.HomePresenter;
import com.reptile.show.project.mvp.ui.activity.MainActivity;
import com.reptile.show.project.mvp.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, DefaultAdapter.OnRecyclerViewItemClickListener, DefaultAdapter.onRecycleViewItemLongClickLisrtener {
    @BindView(R.id.toolbar_title)
    Toolbar mToolbar_title;
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.recycle_list)
    RecyclerView mRecycle_list;

    @Inject
    HomeAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    //是否是长按 选择模式
    private static Boolean isCheckModel = false;
    private static HashSet<FolderEntity> mChoice;

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
        mTv_title.setText(ArmsUtils.getString(getActivity(), R.string.bottom_main));
        mToolbar_title.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showMessage("点击了" + item.getTitle());
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
        ArmsUtils.configRecyclerView(mRecycle_list, mLayoutManager);
        mRecycle_list.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setmOnItemLongClickListener(this);
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
        isCheckModel = null;
        mChoice = null;
        ((MainActivity) getActivity()).killMyself();
    }

    /**
     * item 点击事件
     *
     * @param view     被点击的 {@link View}
     * @param viewType 布局类型
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    @Override
    public void onItemClick(@NonNull View view, int viewType, @NonNull Object data, int position) {
        if (isCheckModel) {
            RelativeLayout mRl_folder = (RelativeLayout) view.findViewById(R.id.rl_folder);
            mRl_folder.setActivated(!mRl_folder.isActivated());
            if (data instanceof FolderEntity) {
                mChoice.add((FolderEntity) data);
            }
        }
    }

    @Override
    public void onItemLongClick(@NonNull View view, int viewType, @NonNull Object data, int position) {
        if (isCheckModel)
            return;
        isCheckModel = true;
        if (mChoice == null) {
            mChoice = new HashSet<>();
        } else {
            mChoice.clear();
        }
        if (data instanceof FolderEntity) {
            mChoice.add((FolderEntity) data);
        }
        mTv_title.setText("已选择" + mChoice.size() + "个文件");
        RelativeLayout mRl_folder = (RelativeLayout) view.findViewById(R.id.rl_folder);
        mRl_folder.setActivated(!mRl_folder.isActivated());
        View pview = LayoutInflater.from(getActivity()).inflate(R.layout.popup_top_title,null);
        SmartPopupWindow smartPopupWindow = SmartPopupWindow.Builder.build(getActivity(),pview)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getActionBarHeight(getActivity()))
                .setOutsideTouchDismiss(false)
                .createPopupWindow();
        smartPopupWindow.setFocusable(false);
        smartPopupWindow.showAtAnchorView(mToolbar_title, VerticalPosition.ALIGN_TOP, HorizontalPosition.CENTER);
        pview.findViewById(R.id.tv_popup_cancle).setOnClickListener(view1 -> {
            mAdapter.notifyDataSetChanged();
            isCheckModel = false;
            smartPopupWindow.dismiss();
        });
//        CustomPopupWindow.builder().contentView(CustomPopupWindow
//                .inflateView(getActivity(),R.layout.popup_top_title))
//                .parentView(mToolbar_title)
//                .isWrap(true)
//                .backgroundDrawable(getResources().getDrawable(R.color.black))
//                .customListener(contentView -> {
//                    LogUtils.debugInfo(TAG,"CustomPopupWindow初始化中");
//                })
//                .build()
//                .show();
    }
}
