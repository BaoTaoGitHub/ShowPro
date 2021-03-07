package com.jess.arms.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class MyAlertDialog extends AlertDialog {
    private int BackgroundDrawable;
    private Context mContext;
    private View mView;

    public MyAlertDialog(@NonNull Context context,View view) {
        super(context);
        this.mContext = context;
        this.mView = view;
    }

    public MyAlertDialog(@NonNull Context context,View view,int themeResId) {
        super(context, themeResId);
        this.mView = view;
        this.mContext = context;

    }

    public MyAlertDialog(@NonNull Context context,View view,boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mView = view;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        //保证Edittext 弹出键盘
//        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    public void setBackgroundDrawable(int drawable){
        this.BackgroundDrawable = drawable;
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = getWindow();
        WindowManager m = dialogWindow.getWindowManager();
        dialogWindow.setBackgroundDrawable(ArmsUtils.getDrawablebyResource(mContext,BackgroundDrawable));
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
//        p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
    }
}
