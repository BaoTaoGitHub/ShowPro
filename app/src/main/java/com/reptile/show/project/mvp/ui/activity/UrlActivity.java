package com.reptile.show.project.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.jess.arms.utils.ProgressDialogUtils;
import com.reptile.show.project.R;
import com.reptile.show.project.di.component.DaggerUrlComponent;
import com.reptile.show.project.mvp.contract.UrlContract;
import com.reptile.show.project.mvp.presenter.UrlPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

@ActivityScope
public class UrlActivity extends BaseActivity<UrlPresenter> implements UrlContract.View {
    @Inject
    RxPermissions rxPermissions;

    @BindView(R.id.tv_title_left)
    TextView mTv_title_left;
    @BindView(R.id.tv_title_center)
    TextView mTv_title_center;

    @BindView(R.id.wv_web)
    WebView mWv_web;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUrlComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_url;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        iniWebView();
        getIntentBundle();
    }

    private void getIntentBundle() {
        String url = getIntent().getStringExtra("u");
        if (Preconditions.checkString(url)) {
            mWv_web.loadUrl(url);
        } else
            showMessage("文档为空");
    }

    private void iniWebView() {
        WebSettings settings = mWv_web.getSettings();
        //设置视图滚动模式
        mWv_web.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        //取消滚动条
        mWv_web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
//        设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 开启 DOM storage API 功能,供给WEB端缓存调用
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getActivity().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // 设置标题
                mTv_title_center.setText(title);
            }
        };
        mWv_web.setWebChromeClient(webChromeClient);
        mWv_web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                if (!(url.startsWith("http") || url.startsWith("https"))) {
//                    return true;
//                }
                return true;
            }

//            @TargetApi(Build.VERSION_CODES.N)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return false;
//            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
                hideLoading();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
                hideLoading();

            }

        });
    }

    @OnClick(R.id.tv_title_left)
    public void onClick() {
        killMyself();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mWv_web.canGoBack()) {
//                String URLW = mWv_web.getUrl();
//                    onBackPressed();
                mWv_web.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showLoading() {
        if (progressDialogUtils == null) {
            progressDialogUtils = ProgressDialogUtils.getInstance(this);
            progressDialogUtils.setMessage("加载中...");
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
        finish();
    }
}
