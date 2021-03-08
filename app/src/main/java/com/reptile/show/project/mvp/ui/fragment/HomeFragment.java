package com.reptile.show.project.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.MyAlertDialog;
import com.jess.arms.utils.MyDialog;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.ProgressDialogUtils;
import com.jess.arms.widget.smartpopupwindow.HorizontalPosition;
import com.jess.arms.widget.smartpopupwindow.SmartPopupWindow;
import com.jess.arms.widget.smartpopupwindow.VerticalPosition;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.app.EventBusTags;
import com.reptile.show.project.di.component.DaggerHomeComponent;
import com.reptile.show.project.mvp.contract.HomeContract;
import com.reptile.show.project.mvp.model.entity.BaseEventEntity;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.presenter.HomePresenter;
import com.reptile.show.project.mvp.ui.activity.MainActivity;
import com.reptile.show.project.mvp.ui.activity.MoveDirActivity;
import com.reptile.show.project.mvp.ui.activity.SearchActivity;
import com.reptile.show.project.mvp.ui.activity.UrlActivity;
import com.reptile.show.project.mvp.ui.adapter.HomeAdapter;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.HashSet;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View
        , DefaultAdapter.OnRecyclerViewItemClickListener
        , DefaultAdapter.onRecycleViewItemLongClickLisrtener, SwipeRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrl_refresh;

    @BindString(R.string.toolbar_menu_new_folder)
    String mNewFolder;
    @BindString(R.string.rename)
    String mRename;

    static TextView mTv_popup_title;

    @Inject
    HomeAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    //记录跳转的数量
    private int jumpNum;
    //是否开启多选模式
    private boolean isMultipleEnable = false;
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
        iniToolbar();
        iniRefresh();
        iniRecyclerView();
        mTv_title.setText(ArmsUtils.getString(getActivity(), R.string.bottom_main));
        mPresenter.getDirList(0);
    }

    private void iniRefresh() {
        mSrl_refresh.setOnRefreshListener(this);
    }

    private void iniToolbar() {
        mToolbar_title.setNavigationOnClickListener(v -> {
            mPresenter.getDirList(mPresenter.mParent_Id);
        });
        mToolbar_title.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showMessage("点击了" + item.getTitle());
                switch (item.getItemId()) {
                    case R.id.menu_batch_editor:
                        return true;
                    case R.id.menu_new_folder:
                        iniEditDialog(1,true, 0);
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
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(getActivity());
            progressDialogUtils.setMessage("请稍后...");
        }
        progressDialogUtils.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialogUtils != null)
            progressDialogUtils.dismiss();
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
        ((MainActivity) getActivity()).killMyself();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isCheckModel = null;
        mChoice = null;
        mTv_popup_title = null;
    }

    /**
     * 点击搜索布局监听
     */
    @OnClick(R.id.ll_search)
    public void onLinearSearchClick() {
        showMessage("暂未开放");
        //TODO 暂时隐藏，没有接口
//        launchActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @Subscriber(tag = EventBusTags.Main2Home,mode = ThreadMode.MAIN)
    public void onMain2HomeEvent(BaseEventEntity<String> eventEntity){
        //TODO 暂时创建URL到第一层目录
        iniEditDialog(3,true,mPresenter.mParent_Id);
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
        if (isMultipleEnable && isCheckModel) {
            RelativeLayout mRl_folder = (RelativeLayout) view.findViewById(R.id.rl_folder);
            mRl_folder.setActivated(!mRl_folder.isActivated());
            if (data instanceof DirectoryEntity.DirUrlBean) {
                DirectoryEntity.DirUrlBean entity = ((DirectoryEntity.DirUrlBean) data);
                entity.setCheck(!mRl_folder.isActivated());
                if (mRl_folder.isActivated()) {
                    mChoice.add(entity);
                } else {
                    mChoice.remove(entity);
                }
                mTv_popup_title.setText("已选择" + mChoice.size() + "个文件");
            }

        } else {
            if (data instanceof DirectoryEntity.DirUrlBean) {
                DirectoryEntity.DirUrlBean entity = ((DirectoryEntity.DirUrlBean) data);
                if (viewType == AppConstants.HomeAdapterViewType.TYPE_DIR) {
                    mPresenter.getDirList(entity.getId());
                } else {
                    mPresenter.getUrlContent(entity.getId());
                }
            }
        }
    }


    @Override
    public void onItemLongClick(@NonNull View view, int viewType, @NonNull Object data, int position) {
        if (!isMultipleEnable) {
            if (data instanceof DirectoryEntity.DirUrlBean) {
                boolean isDir = ((DirectoryEntity.DirUrlBean) data).getViewType() == AppConstants.HomeAdapterViewType.TYPE_DIR;
                iniOnLongMenuDialog(isDir, ((DirectoryEntity.DirUrlBean) data).getId(),position);
            }

        } else {
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
                iniChoiceLayout(position);
                notifyChangeChoiceBottom();
            }
        }
    }

    @Override
    public void showWebUrl(String url) {
        Intent intent = new Intent(getActivity(), UrlActivity.class);
        intent.putExtra("u", url);
        launchActivity(intent);
    }

    private void iniOnLongMenuDialog(boolean isDir, int id,int position) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_single_menu, null, false);
        MyAlertDialog myAlertDialog = new MyAlertDialog(getActivity(), layout);
        View.OnClickListener listener = view -> {
            myAlertDialog.dismiss();
            switch (view.getId()) {
                case R.id.rl_new_folder:
                    iniEditDialog(1,isDir, id);
                    break;
                case R.id.rl_rename:
                    iniEditDialog(2,isDir, id);
                    break;
                case R.id.rl_delete:
                    if(isDir){
                        mPresenter.removeDir(id,position);
                    }else {
                        showMessage("暂未开放");
                    }
                    break;
                case R.id.rl_move:
                    Intent intent = new Intent(getActivity(), MoveDirActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.INTENT_KEY_HOME2MOVE_ISDIR,isDir+"");
                    bundle.putInt(AppConstants.INTENT_KEY_HOME2MOVE_ID,id);
                    intent.putExtras(bundle);
                    launchActivity(intent);
                    break;
            }
        };
        RelativeLayout mRename = layout.findViewById(R.id.rl_rename);
        RelativeLayout mDelete = layout.findViewById(R.id.rl_delete);
        RelativeLayout mMove = layout.findViewById(R.id.rl_move);
        RelativeLayout mNew = layout.findViewById(R.id.rl_new_folder);
        mRename.setOnClickListener(listener);
        mNew.setOnClickListener(listener);
        mDelete.setOnClickListener(listener);
        mMove.setOnClickListener(listener);
        if (!isDir) {
            mNew.setVisibility(View.GONE);
            mRename.setVisibility(View.GONE);
            mDelete.setVisibility(View.GONE);
        }
        myAlertDialog.setBackgroundDrawable(R.drawable.shape_all_round_white);
        myAlertDialog.show();
    }

    private void iniEditDialog(int type,boolean isDir, int parentId) {
        String titleName;
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edittext_cancel_ok, null, false);
        TextView mTv_dialog_title = layout.findViewById(R.id.tv_dialog_title);
        EditText mContent = layout.findViewById(R.id.et_dialog_content);
        switch (type){
            case 1:
                titleName = mNewFolder;
                break;
            case 2:
                titleName = mRename;
                break;
            case 3:
                titleName = "链接收藏";
                break;
            default:
                titleName = "";
        }
        mTv_dialog_title.setText(titleName);
        MyDialog myDialog = new MyDialog(getActivity(), layout, MyDialog.LocationView.CENTER);
        Button mOk = layout.findViewById(R.id.bt_dialog_ok);
        Button mCancel = layout.findViewById(R.id.bt_dialog_cancel);
        mOk.setOnClickListener(v -> {
            String input = mContent.getText().toString();
            if (Preconditions.checkString(input)) {
                switch (type){
                    case 1:
                        mPresenter.createDir(input, parentId);
                        break;
                    case 2:
                        if(isDir){
                            mPresenter.renameDir(parentId, input);
                        }else {
                            //TODO 重命名URL
                        }
                        break;
                    case 3:
                        if(Preconditions.checkUrl(input)){
                            mPresenter.createUrl(parentId,input);
                        }
                        break;
                }
                myDialog.dismiss();
            }
        });
        mCancel.setOnClickListener(v -> {
            myDialog.dismiss();
        });
        myDialog.show();
    }

    private RadioButton rb_popup_remove;
    private RadioButton rb_popup_rename;
    private RadioButton rb_popup_move;
    private RadioButton rb_popup_new_folder;
    private void iniChoiceLayout(int position) {
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
        rb_popup_remove = (RadioButton) bottom_view.findViewById(R.id.rb_popup_remove);
        rb_popup_rename = (RadioButton) bottom_view.findViewById(R.id.rb_popup_rename);
        rb_popup_move = (RadioButton) bottom_view.findViewById(R.id.rb_popup_move);
        rb_popup_new_folder = (RadioButton) bottom_view.findViewById(R.id.rb_popup_new_folder);
        View.OnClickListener listener = view -> {
            switch (view.getId()) {
                case R.id.rb_popup_new_folder:
                    if (mChoice.iterator().hasNext()) {
                        int dirId = mChoice.iterator().next().getId();
                        iniEditDialog(1,true, dirId);
                    }
                    break;
                case R.id.rb_popup_move:
                    showMessage("点击移动");
                    break;
                case R.id.rb_popup_rename:
                    if (mChoice.iterator().hasNext()) {
                        int dirId = mChoice.iterator().next().getId();
                        iniEditDialog(2,true, dirId);
                    }
                    break;
                case R.id.rb_popup_remove:
                    int dirId = mChoice.iterator().next().getId();
                    mPresenter.removeDir(dirId,position);
                    break;
            }
        };
        rb_popup_remove.setOnClickListener(listener);
        rb_popup_rename.setOnClickListener(listener);
        rb_popup_move.setOnClickListener(listener);
        rb_popup_new_folder.setOnClickListener(listener);

        //Top的按钮事件
        top_view.findViewById(R.id.tv_popup_cancel).setOnClickListener(view1 -> {
            mAdapter.itemSelectAll(false);
            mAdapter.notifyDataSetChanged();
            isCheckModel = false;
            topPopupWindow.dismiss();
            bottomPopupWindow.dismiss();
        });
        TextView mSelAll = top_view.findViewById(R.id.tv_popup_select_all);
        mSelAll.setOnClickListener(v -> {
            mAdapter.itemSelectAll(true);
            mAdapter.notifyDataSetChanged();
        });
    }

    //根据批量选择的文件夹与URL更新状态
    private void notifyChangeChoiceBottom() {
        if (rb_popup_remove != null) {
            int dirNum = 0;
            int urlNum = 0;
            for (DirectoryEntity.DirUrlBean bean : mChoice) {
                if (bean.getViewType() == AppConstants.HomeAdapterViewType.TYPE_DIR) {
                    dirNum++;

                } else {
                    urlNum++;
                }
            }
            //TODO 暂时不做
            if (dirNum == 1 && urlNum == 0) {
                rb_popup_new_folder.setEnabled(true);
            } else if (dirNum > 1 || urlNum > 0) {
                rb_popup_rename.setEnabled(false);
                rb_popup_new_folder.setEnabled(false);
            }
            if (urlNum > 0) {
                rb_popup_new_folder.setEnabled(false);
            }

        }
    }

    @Override
    public void showTitleBack(boolean canBack) {
        if (canBack) {
            mToolbar_title.setNavigationIcon(R.mipmap.ico_title_back);
        } else {
            mToolbar_title.setNavigationIcon(null);
        }
    }

    @Override
    public SwipeRefreshLayout getRefresh() {
        return mSrl_refresh;
    }

    @Override
    public void onRefresh() {
        mPresenter.getDirList(mPresenter.mParent_Id);
    }
}
