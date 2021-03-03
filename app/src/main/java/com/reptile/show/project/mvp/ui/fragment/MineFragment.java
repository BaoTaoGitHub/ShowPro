package com.reptile.show.project.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.reptile.show.project.R;
import com.reptile.show.project.di.component.DaggerMainComponent;
import com.reptile.show.project.di.component.DaggerMineComponent;
import com.reptile.show.project.mvp.contract.MineContract;
import com.reptile.show.project.mvp.presenter.MinePresenter;
import com.reptile.show.project.mvp.ui.activity.LoginActivity;
import com.reptile.show.project.mvp.ui.activity.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

@FragmentScope
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {
    @Inject
    RxPermissions rxPermissions;

    @BindView(R.id.ib_notice)
    ImageButton mIb_notice;
    @BindView(R.id.ib_settings)
    ImageButton mIb_settings;
    @BindView(R.id.iv_avatar)
    ImageView mIv_avatar;
    @BindView(R.id.tv_nickname)
    TextView mTv_nickname;
    @BindView(R.id.tv_signature)
    TextView mTv_signature;
    @BindView(R.id.cl_avatar)
    ConstraintLayout mCl_avatar;
    @BindView(R.id.cl_nickname)
    ConstraintLayout mCl_nickname;
    @BindView(R.id.cl_basic_info)
    ConstraintLayout mCl_basic_info;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.from(getActivity()).inflate(R.layout.fragment_mine,container,false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @OnClick({R.id.bt_logout})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_logout:
                ArmsUtils.exitApp();
                launchActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
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
        ((MainActivity) getActivity()).killMyself();
    }


}
