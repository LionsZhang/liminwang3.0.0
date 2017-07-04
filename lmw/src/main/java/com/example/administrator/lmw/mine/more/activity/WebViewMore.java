package com.example.administrator.lmw.mine.more.activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.mine.credit.CreditActivity;
import com.example.administrator.lmw.mine.fill.FillActivity;
import com.example.administrator.lmw.mine.seting.BindBankSuccussActivity;
import com.example.administrator.lmw.mine.seting.DespositOperateSuccessActivity;
import com.example.administrator.lmw.mine.seting.SetingActivity;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.mine.more.http.Available;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.JSInteraction;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.InjectView;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewMore extends BaseTitleActivity {
    public static final String INTENT_KEY_TITLE = "title";
    public static final String INTENT_KEY_URL = "url";
    public static final String INTENT_KEY_POST_PARAMETER = "intent_key_post_parameter";
    public static final String INTENT_KEY_EXTRA_TYPE = "intent_key_extra_type";
    @InjectView(R.id.webview)
    WebView webview;
    private String title;
    private String postParameter;
    private String url;
    private String buyType;//购买类型

    @Override
    protected void initializeView() {
        intents();
        if (!TextUtils.isEmpty(url)) {
            initWebView(url);
            ALLog.e("============1111 postParameter" + postParameter);
        }
        setTitleName(title);
        setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
                    Log.d("===left goback===", "onClick: ");
                } else {
                    Log.d("===left===", "onClick: ");
                    ActivityManage.create().finishActivity();

                }
            }
        });
    }

    @Override
    protected void setTitleContentView() {
        initView("", R.layout.activity_webview);
    }

    private void intents() {
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_KEY_TITLE))
            title = intent.getStringExtra(INTENT_KEY_TITLE);
        if (intent.hasExtra(INTENT_KEY_URL))
            url = intent.getStringExtra(INTENT_KEY_URL);
        if (intent.hasExtra(INTENT_KEY_POST_PARAMETER))
            postParameter = intent.getStringExtra(INTENT_KEY_POST_PARAMETER);
        if (intent.hasExtra(INTENT_KEY_EXTRA_TYPE))
            buyType = intent.getStringExtra(INTENT_KEY_EXTRA_TYPE);
    }

    private void initWebView(String url) { // 加载的地址
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setTextZoom(100);
        webview.getSettings().setBlockNetworkImage(false);
        webview.getSettings().setTextZoom(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
      /*  webview.getSettings().setCacheMode(
                WebSettings.LOAD_CACHE_ELSE_NETWORK);*/
        String ua = webview.getSettings().getUserAgentString();
        webview.getSettings().setUserAgentString(ua + " LIMIN/2.0.0");
        webview.addJavascriptInterface(new JSInteraction(mContext, buyType), "liminApp");
        ALLog.e("url===============" + url);
        if (TextUtils.isEmpty(postParameter))
            webview.loadUrl(url);
        else {
            ALLog.e("============ postParameter" + postParameter);
            webview.postUrl(url, postParameter.getBytes());
//            webview.loadUrl(String.format("%s?%s", url, postParameter));
        }

        //  webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {

            @Override

            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                ALLog.i("snubee  webview url:  " + url);
                if (!TextUtils.isEmpty(url) && (url.contains("toWeiXin") || url.contains("toweixin"))) {//打开微信 http://toWeiXin/?key1=value1&key2=value2
                    String[] keys = url.substring(url.indexOf("?")).split("&");
                    ALLog.i("snubee  webview url value  " + keys[0].split("=")[1] + "  " + keys[1].split("=")[0] + "  --  " + keys[1].split("=")[1]);
                    openWeiXin(keys[0].split("=")[1], keys[1].split("=")[1]);
                    return true;
                } else {
                    webview.loadUrl(url);
                    return true;
                }
//                 return super.shouldOverrideUrlLoading(view, url);
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
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((WebView) view).requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webview.canGoBack()) {// goBack()表示返回WebView的上一页面
                webview.goBack();
            } else
                finishActivity(WebViewMore.this);
            return true;
        }
        return false;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    /*  if (webview.canGoBack()) {
            webview.goBack();
            webview.loadUrl(url);
        } else {
            webview.reload();
        }
      if (webview != null) {
            webview.clearCache(true);
            webview.clearHistory();
        }*/
    }

    /**
     * 打开微信
     *
     * @param wechat_str
     * @param wechat_no
     */
    private void openWeiXin(final String wechat_str, final String wechat_no) {
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(URLDecoder.decode(wechat_no, "UTF-8"));
                    Singlton.getInstance(PopWindowManager.class).
                            showTwoButtonDialog(mContext, "提示", Html.fromHtml(URLDecoder.decode(wechat_str, "UTF-8")).toString(), "取消", "去关注",
                                    new OnDialogClickListener() {
                                        @Override
                                        public void onClick(int id, View v) {
                                            if (id == 1) {
                                                if (Available.isWeixinAvilible(mContext)) {
                                                    Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                                    mContext.startActivity(intent);
                                                } else {
                                                    ToastUtil.showToast(mContext, "您没有安装微信");
                                                }

                                            }
                                        }
                                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
