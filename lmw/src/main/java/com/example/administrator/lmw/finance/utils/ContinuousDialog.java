package com.example.administrator.lmw.finance.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.utils.JSInteraction;

/**
 * 续投购买提示框
 * <p>
 * Created by Administrator on 2017/4/12.
 */

public class ContinuousDialog extends Dialog implements View.OnClickListener {

    private View continuousView;
    private TextView continuous_title, continuous_head, continuous_center, continuous_one_bt;
    private WebView continuous_web;
    private OnDialogClickListener listener;
    private String titleStr, headStr, centerStr, btn_one;

    /**
     * @param context
     * @param titleStr      标题
     * @param headStr       头部内容
     * @param centerStr     内容文字
     * @param btn_one       单个按键文字
     * @param continuousUrl 显示的url
     * @param listener
     */
    public ContinuousDialog(Context context, String titleStr, String headStr, String centerStr, String btn_one, String continuousUrl, OnDialogClickListener listener) {
        this(context);
        this.titleStr = titleStr;
        this.headStr = headStr;
        this.centerStr = centerStr;
        this.btn_one = btn_one;
        this.listener = listener;


        continuousView = LayoutInflater.from(context).inflate(R.layout.continuous_dialog, null);
        initView();

        this.setContentView(continuousView);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        showWebview(continuousUrl, context);
        this.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        continuous_title = (TextView) continuousView.findViewById(R.id.continuous_title);
        continuous_head = (TextView) continuousView.findViewById(R.id.continuous_head);
        continuous_center = (TextView) continuousView.findViewById(R.id.continuous_center);
        continuous_one_bt = (TextView) continuousView.findViewById(R.id.continuous_one_bt);
        continuous_web = (WebView) continuousView.findViewById(R.id.continuous_web);

        continuous_title.setText(titleStr);
        continuous_head.setText(headStr);
        continuous_center.setText(centerStr);
        continuous_one_bt.setText(btn_one);
        continuous_one_bt.setOnClickListener(this);
    }

    public ContinuousDialog(Context context) {
        super(context, R.style.Normal_dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continuous_one_bt:
                dismiss();
                listener.onClick(2, continuous_one_bt);
                break;
        }
    }

    /**
     * @param continuousUrl
     * @param context
     */
    private void showWebview(String continuousUrl, Context context) {
        continuous_web.getSettings().setJavaScriptEnabled(true);
        continuous_web.getSettings().setDomStorageEnabled(true);
        continuous_web.getSettings().setUseWideViewPort(true);
        continuous_web.getSettings().setLoadWithOverviewMode(true);
        continuous_web.getSettings().setBuiltInZoomControls(true);
        continuous_web.getSettings().setSupportZoom(true);
        continuous_web.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            continuous_web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String ua = continuous_web.getSettings().getUserAgentString();
        continuous_web.getSettings().setUserAgentString(ua + " LIMIN/2.0.0");
        continuous_web.addJavascriptInterface(new JSInteraction(context), "liminApp");
        continuous_web.loadUrl(continuousUrl);

        continuous_web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                continuous_web.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onReceivedError(android.webkit.WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }
        });
    }
}
