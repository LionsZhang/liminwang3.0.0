package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.utils.SharedPreference;

import java.util.HashMap;
import java.util.Map;

import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;

public class ShowAnnouncementDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private WebView webview;
    private Context mContext;
    private ImageView close, iv_customer_service;
    private String url;
    private OnDialogClickListener onDialogClickListener;
    private boolean isContactCustomer = false;//是否联系客服
    private Button one_bt;

    public ShowAnnouncementDialog(Context context, String url, OnDialogClickListener onDialogClickListener, boolean... args) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        this.url = url;
        this.onDialogClickListener = onDialogClickListener;
        isContactCustomer = args != null && args.length > 0 ? args[0] : false;
        dialog = LayoutInflater.from(context).inflate(R.layout.announcement_dialog_layout, null);
        this.show();
    }

    public ShowAnnouncementDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(dialog);
        findView();
        initWebView();
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        l.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
        wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        l.width = display.getWidth() - 25; //设置宽度
        l.height = display.getHeight() - 110;//屏幕高度
        wind.setAttributes(l);
        url = isContactCustomer ? url + "/app.html" : url;//停服公告拼接url
    }

    private void findView() {
        close = (ImageView) dialog.findViewById(R.id.close);
        one_bt = (Button) dialog.findViewById(R.id.one_bt);
        iv_customer_service = (ImageView) dialog.findViewById(R.id.iv_customer_service);
        webview = (WebView) dialog.findViewById(R.id.webview);
        iv_customer_service.setVisibility(isContactCustomer ? View.VISIBLE : View.INVISIBLE);
        close.setOnClickListener(this);
        one_bt.setOnClickListener(this);
        iv_customer_service.setOnClickListener(this);
        one_bt.setText(isContactCustomer ? "我知道了" : "查看详情");
        close.setVisibility(isContactCustomer ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.one_bt:
                if (onDialogClickListener != null) {
                    onDialogClickListener.onClick(1, v);
                }
                dismiss();
                break;
            case R.id.iv_customer_service://联系客服
                startUdeskGuest();
                //  dismiss();
                break;
        }
    }

    /**
     * 连接客服系统
     */
    private void startUdeskGuest() {
        String appUdeskTokenGroop = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_UDESK_TOKEN_GROOP, "");
        Map<String, String> info = new HashMap<String, String>();
        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, appUdeskTokenGroop);
        UdeskSDKManager.getInstance().setUserInfo(mContext, appUdeskTokenGroop, info);
        String groupId = PreferenceCongfig.APP_UDESK_GROOP_ID;
        UdeskSDKManager.getInstance().lanuchChatByGroupId(mContext, groupId);
    }

    private void initWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBlockNetworkImage(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
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
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((android.webkit.WebView) view).requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }


}
