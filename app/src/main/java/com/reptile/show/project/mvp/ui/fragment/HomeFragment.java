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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.jess.arms.utils.MyDialog;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.ProgressDialogUtils;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
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

    @BindString(R.string.toolbar_menu_new_folder)
    String mNewFolder;
    @BindString(R.string.rename)
    String mRename;
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
                        iniEditDialog(true,0);
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
        mPresenter.getDirList(0);
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
            mAdapter.itemSelectAll(false);
            mAdapter.notifyDataSetChanged();
            mChoice.clear();
            RelativeLayout mRl_folder = (RelativeLayout) view.findViewById(R.id.rl_folder);
            mRl_folder.setActivated(true);
            if (data instanceof DirectoryEntity.DirUrlBean) {
                DirectoryEntity.DirUrlBean entity = ((DirectoryEntity.DirUrlBean) data);
                entity.setCheck(true);
                mChoice.add(entity);
                boolean isDir;
                if(isDir = entity.getViewType()==AppConstants.HomeAdapterViewType.TYPE_DIR){
                    mTv_popup_title.setText("已选择" + mChoice.size() + "个文件夹");
                }else{
                    mTv_popup_title.setText("已选择" + mChoice.size() + "个文件");
                }
                notifyChangeSingleBottom(isDir);
            }
            //TODO 复选
//            RelativeLayout mRl_folder = (RelativeLayout) view.findViewById(R.id.rl_folder);
//            mRl_folder.setActivated(!mRl_folder.isActivated());
//            if (data instanceof DirectoryEntity.DirUrlBean) {
//                DirectoryEntity.DirUrlBean entity = ((DirectoryEntity.DirUrlBean) data);
//                entity.setCheck(!mRl_folder.isActivated());
//                if(mRl_folder.isActivated()){
//                    mChoice.add(entity);
//                }else {
//                    mChoice.remove(entity);
//                }
//                mTv_popup_title.setText("已选择" + mChoice.size() + "个文件");
//            }

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
            boolean isDir = ((DirectoryEntity.DirUrlBean) data).getViewType()==AppConstants.HomeAdapterViewType.TYPE_DIR;
            String choiceType = isDir?
            "文件夹":"文件";
            iniChoiceLayout(choiceType);
            notifyChangeSingleBottom(isDir);
        }

    }

    @Override
    public void showWebUrl(String url) {
        Intent intent = new Intent(getActivity(), UrlActivity.class);
        intent.putExtra("u",url);
        launchActivity(intent);
    }

    private void iniEditDialog(boolean isNewFolder,int parentId){
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edittext_cancel_ok,null,false);
        TextView mTv_dialog_title = layout.findViewById(R.id.tv_dialog_title);
        EditText mContent = layout.findViewById(R.id.et_dialog_content);
        mTv_dialog_title.setText(isNewFolder?mNewFolder:mRename);
        MyDialog myDialog = new MyDialog(getActivity(),layout,MyDialog.LocationView.CENTER);
        Button mOk = layout.findViewById(R.id.bt_dialog_ok);
        Button mCancel = layout.findViewById(R.id.bt_dialog_cancel);
        mOk.setOnClickListener(v -> {
            String input = mContent.getText().toString();
            if(Preconditions.checkString(input)){
                if(isNewFolder){
                    mPresenter.createDir(input,parentId);
                }else {
                    mPresenter.renameDir(parentId,input);
                }
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
    private void iniChoiceLayout(String choiceType) {
        //Top
        View top_view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_top_title, null);
        SmartPopupWindow topPopupWindow = SmartPopupWindow.Builder.build(getActivity(), top_view)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.getActionBarHeight(getActivity()))
                .setOutsideTouchDismiss(false)
                .createPopupWindow();
        topPopupWindow.setFocusable(false);
        mTv_popup_title = (TextView) top_view.findViewById(R.id.tv_popup_title);
        mTv_popup_title.setText("已选择" + mChoice.size() + "个"+choiceType);
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
        View.OnClickListener listener = view->{
            switch (view.getId()) {
                case R.id.rb_popup_new_folder:
                    if(mChoice.iterator().hasNext()){
                        int dirId = mChoice.iterator().next().getId();
                        iniEditDialog(true,dirId);
                    }
                    break;
                case R.id.rb_popup_move:
                    showMessage("点击移动");
                    break;
                case R.id.rb_popup_rename:
                    if(mChoice.iterator().hasNext()){
                        int dirId = mChoice.iterator().next().getId();
                        iniEditDialog(false,dirId);
                    }
                    break;
                case R.id.rb_popup_remove:
                    showMessage("点击删除");
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
        //TODO 多选 暂时隐藏
        TextView mSelAll = top_view.findViewById(R.id.tv_popup_select_all);
        mSelAll.setVisibility(View.GONE);
        mSelAll.setOnClickListener(v -> {
            mAdapter.itemSelectAll(true);
            mAdapter.notifyDataSetChanged();
        });
    }

    //根据批量选择的文件夹与URL更新状态
    private void notifyChangeChoiceBottom(){
        if(rb_popup_remove!=null){
            int dirNum = 0;
            int urlNum = 0;
            for (DirectoryEntity.DirUrlBean bean :mChoice) {
                if(bean.getViewType()==AppConstants.HomeAdapterViewType.TYPE_DIR){
                    dirNum++;

                }else{
                    urlNum++;
                }
            }
            //TODO 暂时不做
            if(dirNum==1 && urlNum==0){
                rb_popup_new_folder.setEnabled(true);
            }else if(dirNum > 1 || urlNum>0){
                rb_popup_rename.setEnabled(false);
                rb_popup_new_folder.setEnabled(false);
            }
            if(urlNum>0){
                rb_popup_new_folder.setEnabled(false);
            }

        }
    }
    //根据单选的文件夹或URL更新底部导航状态
    private void notifyChangeSingleBottom(boolean isDir){
        if(!isDir){
            if(rb_popup_new_folder.isEnabled()){
                rb_popup_new_folder.setEnabled(false);
            }
        }else{
            rb_popup_remove.setEnabled(true);
            rb_popup_rename.setEnabled(true);
            rb_popup_move.setEnabled(true);
            rb_popup_new_folder.setEnabled(true);
        }
    }
}
