package com.example.administrator.lmw.finance.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.finance.utils.CustWebView;
import com.example.administrator.lmw.http.LmwHttp;
import com.example.administrator.lmw.utils.JSInteraction;

import butterknife.InjectView;

/**
 * 产品详情
 * <p>
 * Created by Administrator on 2016/8/26 0026.
 */
public class ProductDetail extends BaseFragment {

    public static final String URL_TYPE = "url_type";
    public static final int URL_USER_DEAL = 0;// 用户协议
    @InjectView(R.id.webview)
    CustWebView webview;
    private Intent intent;
    private String subjectId, type;
    private String URL = LmwHttp.baseUrl + "pages/finance/mobile_info.html?id=";// 预发布环境
    //  private String URL = "http://m.limin.cn/pages/finance/mobile_info.html?id=";// 正式环境
    private CommonInfo commonInfo;

    private void initWebView(String url) { // 加载的地址

        webview.loadUrl(url);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        String ua = webview.getSettings().getUserAgentString();
        webview.getSettings().setUserAgentString(ua + " LIMIN/2.0.0");
        webview.addJavascriptInterface(new JSInteraction(mContext), "liminApp");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                hideLoadingDialog();
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                showLoadingDialog();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }

        });
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((WebView) view).requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    @Override
    protected void initializeData() {
        intent = getActivity().getIntent();
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        intents();
    }

    @Override
    protected void initializeView() {
        Log.d("url ", URL + subjectId);
        if (type.equals("0")) {//标的
            initWebView(URL + subjectId + "&type=1");// 1为标的,2为债权
//            initWebView("http://192.168.2.135/demo/useragent.html");// 1为标的,2为债权
//            producthttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
        } else if (type.equals("1")) {//债权
            initWebView(URL + subjectId + "&type=2");// 1为标的,2为债权
//            detailhttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
        }
    }


    @Override
    protected int getContentLayout() {
        return R.layout.product_webview;
    }


    private void intents() {
        if (intent.hasExtra("subjectId"))
            subjectId = intent.getStringExtra("subjectId");
        if (intent.hasExtra("type"))
            type = intent.getStringExtra("type");
    }

}
