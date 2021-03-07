package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.MyDialog;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.ProgressDialogUtils;
import com.reptile.show.project.R;
import com.reptile.show.project.app.AppConstants;
import com.reptile.show.project.di.component.DaggerMoveDirComponent;
import com.reptile.show.project.mvp.contract.MoveDirContract;
import com.reptile.show.project.mvp.model.entity.DirectoryEntity;
import com.reptile.show.project.mvp.presenter.MoveDirPresenter;
import com.reptile.show.project.mvp.ui.adapter.MoveDirAdapter;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

@ActivityScope
public class MoveDirActivity extends BaseActivity<MoveDirPresenter> implements MoveDirContract.View
        , DefaultAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.bt_new_folder)
    Button mBt_new_folder;
    @BindView(R.id.bt_move)
    Button mBt_move;
    @BindView(R.id.toolbar_title)
    Toolbar mToolbar_title;
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.recycle_list)
    RecyclerView mRecycle_list;

    @BindString(R.string.toolbar_menu_new_folder)
    String mNewFolder;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MoveDirAdapter mAdapter;
    private boolean isDir;
    private int currentId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMoveDirComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_movedir;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getIntentData();
        iniToolbar();
        iniRecyclerView();
        mTv_title.setText(ArmsUtils.getString(getActivity(), R.string.move));
        mPresenter.getDirList(0);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        String i_dir = bundle.getString(AppConstants.INTENT_KEY_HOME2MOVE_ISDIR);
        int i_id = bundle.getInt(AppConstants.INTENT_KEY_HOME2MOVE_ID);
        if(Preconditions.checkString(i_dir) && i_id!=0){
            isDir = "true".equals(i_dir)?true:false;
            currentId = i_id;
        }else {
            showMessage("数据错误，传输为空");
            killMyself();
        }
    }

    private void iniToolbar() {
        mToolbar_title.setNavigationIcon(R.mipmap.ico_title_back);
        mToolbar_title.setNavigationOnClickListener(v -> {
            if(mPresenter.canBack){
//                mPresenter.getDirList();
                mPresenter.getDirList(0);
            }else{
                killMyself();
            }
        });
    }

    private void iniRecyclerView() {
        ArmsUtils.configRecyclerView(mRecycle_list, mLayoutManager);
        mRecycle_list.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.bt_new_folder,R.id.bt_move})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_new_folder:
                iniEditDialog(mPresenter.parentId);
                break;
            case R.id.bt_move:
                if(isDir){
                    mPresenter.moveDir(currentId,mPresenter.parentId);
                }else
                    mPresenter.moveUrl(mPresenter.parentId,currentId);
                break;
        }
    }

    @Override
    public Activity getActivity() {
        return this;
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
    public void killMyself() {
        finish();
    }

    /**
     * @param view     被点击的 {@link View}
     * @param viewType 布局类型
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    @Override
    public void onItemClick(@NonNull View view, int viewType, @NonNull Object data, int position) {
        if (data instanceof DirectoryEntity.DirectoryBean) {
            DirectoryEntity.DirectoryBean entity = ((DirectoryEntity.DirectoryBean) data);
            mPresenter.getDirList(entity.getD_id());
        }
    }

    //新建文件夹
    private void iniEditDialog(int parentId) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edittext_cancel_ok, null, false);
        TextView mTv_dialog_title = layout.findViewById(R.id.tv_dialog_title);
        EditText mContent = layout.findViewById(R.id.et_dialog_content);
        mTv_dialog_title.setText(mNewFolder);
        MyDialog myDialog = new MyDialog(getActivity(), layout, MyDialog.LocationView.CENTER);
        Button mOk = layout.findViewById(R.id.bt_dialog_ok);
        Button mCancel = layout.findViewById(R.id.bt_dialog_cancel);
        mOk.setOnClickListener(v -> {
            String input = mContent.getText().toString();
            if (Preconditions.checkString(input)) {
                mPresenter.createDir(input, parentId);
                myDialog.dismiss();
            }
        });
        mCancel.setOnClickListener(v -> {
            myDialog.dismiss();
        });
        myDialog.show();
    }

}
