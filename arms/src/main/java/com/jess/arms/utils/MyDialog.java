package com.jess.arms.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jess.arms.R;

public class MyDialog extends Dialog {
    //Dialog View
    private View view;

    //Dialog弹出位置
    private LocationView locationView = LocationView.CENTER;

    private boolean isCancelable;
    private boolean isCanceledOnTouchOutside;
    /**
     * @param context 上下文
     * @param view    Dialog View
     */
    public MyDialog(Context context, View view) {
        super(context, R.style.DialogNormalStyle);
        this.view = view;
    }

    /**
     * @param context      上下文
     * @param view         Dialog View
     * @param locationView Dialog弹出位置
     */
    public MyDialog(Context context, View view, LocationView locationView) {
        super(context, R.style.DialogNormalStyle);
        this.view = view;
        this.locationView = locationView;
    }


    @SuppressLint("RtlHardcoded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != view) {
            setContentView(view);
            setCancelable(isCancelable);//点击外部是否可以关闭Dialog
            setCanceledOnTouchOutside(isCanceledOnTouchOutside);//返回键是否可以关闭Dialog
            Window window = this.getWindow();
            assert window != null;
            switch (locationView) {
                case TOP:
                    window.setGravity(Gravity.TOP);
                    break;
                case BOTTOM:
                    window.setGravity(Gravity.BOTTOM);
                    break;
                case CENTER:
                    window.setGravity(Gravity.CENTER);
                    break;
            }
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    public enum LocationView {
        CENTER, TOP, BOTTOM
    }

}
