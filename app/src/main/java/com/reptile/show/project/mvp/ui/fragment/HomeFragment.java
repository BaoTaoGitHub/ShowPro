package com.reptile.show.project.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
import com.jess.arms.widget.smartpopupwindow.HorizontalPosition;
import com.jess.arms.widget.smartpopupwindow.SmartPopupWindow;
import com.jess.arms.widget.smartpopupwindow.VerticalPosition;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.di.component.DaggerHomeComponent;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.model.entity.FolderEntity;
import com.reptile.show.project.mvp.presenter.HomePresenter;
import com.reptile.show.project.mvp.ui.activity.MainActivity;
import com.reptile.show.project.mvp.ui.activity.SearchActivity;
import com.reptile.show.project.mvp.ui.activity.UrlActivity;
import com.reptile.show.project.mvp.ui.adapter.HomeAdapter;

import java.util.HashSet;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View
        , DefaultAdapter.OnRecyclerViewItemClickListener
        , DefaultAdapter.onRecycleViewItemLongClickLisrtener {
    @BindView(R.id.linear_home)
    LinearLayout mLinear_home;
    @BindView(R.id.toolbar_title)
    Toolbar mToolbar_title;
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.recycle_list)
    RecyclerView mRecycle_list;
    @BindView(R.id.ll_search)
    LinearLayout mLl_search;

    static TextView mTv_popup_title;

    @Inject
    HomeAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    //是否是长按 选择模式
    private static Boolean isCheckModel = false;
    private static HashSet<DirectoryEntity.DirUrlBean> mChoice;

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
        iniRecyclerView();
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
//        mPresenter.getDirList();
    }

    private void iniRecyclerView() {
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
        mTv_popup_title = null;
        ((MainActivity) getActivity()).killMyself();
    }

    /**
     * 点击搜索布局监听
     */
    @OnClick(R.id.ll_search)
    public void onLinearSearchClick(){
        launchActivity(new Intent(getActivity(), SearchActivity.class));
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
            if (data instanceof DirectoryEntity.DirUrlBean) {
                DirectoryEntity.DirUrlBean entity = ((DirectoryEntity.DirUrlBean) data);
                entity.setCheck(!mRl_folder.isActivated());
                if(mRl_folder.isActivated()){
                    mChoice.add(entity);
                }else {
                    mChoice.remove(entity);
                }
                mTv_popup_title.setText("已选择" + mChoice.size() + "个文件");
            }

        }else{
            if (data instanceof DirectoryEntity.DirUrlBean) {
                DirectoryEntity.DirUrlBean entity = ((DirectoryEntity.DirUrlBean) data);
                if(viewType == AppConstants.HomeAdapterViewType.TYPE_DIR){
                    mPresenter.getDirList(entity.getId());
                }else {
                    mPresenter.getUrlList(entity.getId());
                }
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
        if (data instanceof DirectoryEntity.DirUrlBean) {
            DirectoryEntity.DirUrlBean entity = (DirectoryEntity.DirUrlBean) data;
            entity.setCheck(true);
            mChoice.add(entity);
            RelativeLayout mRl_folder = (RelativeLayout) view.findViewById(R.id.rl_folder);
            mRl_folder.setActivated(!mRl_folder.isActivated());
            iniChoiceLayout();
        }

    }

    @Override
    public void showWebUrl(String url) {
        Intent intent = new Intent(getActivity(), UrlActivity.class);
        intent.putExtra("u",url);
        launchActivity(intent);
    }

    private void iniEditDialog(){
        Dialog dialog = new Dialog(getActivity(),R.style.DialogNormalStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edittext_cancel_ok);
        dialog.setCancelable(false);
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        TextView mTv_dialog_title = dialog.findViewById(R.id.tv_dialog_title);
        dialog.show();
    }

    private void iniChoiceLayout() {
        //Top
        View top_view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_top_title, null);
        SmartPopupWindow topPopupWindow = SmartPopupWindow.Builder.build(getActivity(), top_view)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getActionBarHeight(getActivity()))
                .setOutsideTouchDismiss(false)
                .createPopupWindow();
        topPopupWindow.setFocusable(false);
        mTv_popup_title = (TextView) top_view.findViewById(R.id.tv_popup_title);
        mTv_popup_title.setText("已选择" + mChoice.size() + "个文件");
        //Bottom
        View bottom_view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_bottom_title, null);
        LogUtils.debugInfo("==测试Dimen==", ArmsUtils.getDimens(getActivity(), R.dimen.dimen_49_dp) + "");
        SmartPopupWindow bottomPopupWindow = SmartPopupWindow.Builder.build(getActivity(), bottom_view)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ArmsUtils.dip2px(getActivity()
                        , 49f))
                .setOutsideTouchDismiss(false)
                .createPopupWindow();
        bottomPopupWindow.setFocusable(false);

        topPopupWindow.showAtAnchorView(mToolbar_title, VerticalPosition.ALIGN_TOP, HorizontalPosition.CENTER);
        bottomPopupWindow.showAtAnchorView(mLinear_home, VerticalPosition.BELOW, HorizontalPosition.CENTER);
        RadioGroup mRg_popup_bottom = (RadioGroup) bottom_view.findViewById(R.id.rg_popup_bottom);
        mRg_popup_bottom.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_popup_new_folder:
                    showMessage("点击新建文件夹");
                    break;
                case R.id.rb_popup_move:
                    showMessage("点击移动");
                    break;
                case R.id.rb_popup_rename:
                    showMessage("点击重命名");
                    break;
                case R.id.rb_popup_remove:
                    showMessage("点击删除");
                    break;
            }
        });
        //Top的按钮事件
        top_view.findViewById(R.id.tv_popup_cancel).setOnClickListener(view1 -> {
            mAdapter.itemSelectAll(false);
            mAdapter.notifyDataSetChanged();
            isCheckModel = false;
            topPopupWindow.dismiss();
            bottomPopupWindow.dismiss();
        });
        top_view.findViewById(R.id.tv_popup_select_all).setOnClickListener(v -> {
            mAdapter.itemSelectAll(true);
            mAdapter.notifyDataSetChanged();
        });
    }

}
