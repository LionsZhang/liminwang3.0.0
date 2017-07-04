package com.example.administrator.lmw.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.utils.ALLog;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * webview展示页面（用户协议，版本等）
 *
 * @author lion
 * @Description:TODO
 * @Date 2016-8-26
 */
public class WebViewActivity extends BaseActivity {
    public static final String URL_TYPE = "url_type";
    public static final String URL = "url";
    public static final int URL_USER_DEAL = 0;// 用户协议
    public static final int URL_RISK_HINT = 1;// 风险提示
    public static final int URL_FILL_LIMIT_INSTURCTION = 2;// 限额说明
    public static final int URL_FILL_MANAGE_INSTURCTION = 3;// 限额说明
    public static final int URL_VIP_CENTER = 4;// 会员中心
    public static final int URL_LMW_DATA = 5;// 利民数据
    public static final int URL_FINANCE_CALCULATE = 6;// 理财计算器
    public static final int URL_ESIGNATURE_AUTH = 7;// 查看电子授权页
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.right_title)
    TextView rightTitle;
    @InjectView(R.id.right_icon)
    ImageView rightIcon;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout titleBarRightLayout;
    @InjectView(R.id.wv_ranking_intro)
    WebView wvRankingIntro;
    private int type = -1;
    private String url;// 加载的地址
    private Intent intent;
    private WebView mWebView;
    private String title;

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.wv_ranking_intro);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }
        });
        ALLog.e("==============="+url);
     mWebView.loadUrl(url);
    }

    @Override
    protected void initializeData() {
        initPara();

    }

    private void setTitle() {
        switch (type) {
            case URL_USER_DEAL://用户协议
                setBarTitle("用户协议");
                break;
            case URL_RISK_HINT://风险提示
                setBarTitle("风险提示");
                break;
            case URL_FILL_LIMIT_INSTURCTION://充值限额说明
                setBarTitle("银行充值额度明细表");
                break;
            case URL_FILL_MANAGE_INSTURCTION://充值限额说明
                setBarTitle("资金管理服务协议");
                break;
            case URL_VIP_CENTER://会员中心
                setBarTitle("会员中心");
                break;
            case URL_LMW_DATA://利民数据
                setBarTitle("利民数据");
                break;
            case URL_FINANCE_CALCULATE://理财计算器
                setBarTitle("理财计算器");
                break;
            case URL_ESIGNATURE_AUTH://查看授权合同
                setBarTitle("查看授权合同");
                break;

            default:
                break;
        }
    }

    private void initPara() {
        intent = getIntent();
        if (intent.hasExtra(WebViewActivity.URL_TYPE))
            type = intent.getIntExtra(WebViewActivity.URL_TYPE, -1);
        if (intent.hasExtra(WebViewActivity.URL))
           url = intent.getStringExtra(WebViewActivity.URL);

    }

    @OnClick({R.id.back_layout,R.id.title_bar_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(WebViewActivity.this);
                break;
            case R.id.title_bar_right_layout:

                break;
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_webview_layout;
    }

    @Override
    protected void initializeView() {
        initWebView();
        setTitle();
    }

    public void setBarTitle(String title) {
        barTitle.setText(title);
     //   back.setText("返回");
       barIconBack.setImageResource(R.drawable.nav_back);
    }
}
