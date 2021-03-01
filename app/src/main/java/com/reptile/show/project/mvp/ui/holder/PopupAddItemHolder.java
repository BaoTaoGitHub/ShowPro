package com.reptile.show.project.mvp.ui.holder;

import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.reptile.show.project.R;
import com.reptile.show.project.mvp.model.entity.PopupAddEntity;

import butterknife.BindView;

public class PopupAddItemHolder extends BaseHolder<PopupAddEntity> {

    @BindView(R.id.rb_add)
    RadioButton mRb_add;

    private AppComponent mAppComponent;

    public PopupAddItemHolder(View itemView) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());

    }

    @Override
    public void setData(@NonNull PopupAddEntity data, int position) {
        mRb_add.setText(data.getTitle());
        mRb_add.setCompoundDrawablesWithIntrinsicBounds(null,data.getIcon(),null,null);
    }
}
