package com.example.administrator.lmw.finance.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.finance.adapter.RecordAdapter;
import com.example.administrator.lmw.finance.entity.InvestmentEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.finance.utils.CustListView;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.JSInteraction;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 投资记录
 * <p>
 * Created by Administrator on 2016/8/26 0026.
 */
public class InvestmentRecord extends BaseFragment implements CustListView.CustListViewListener {

    private RecordAdapter recordAdapter;
    private Handler handler;
    private int pageCount;
    private int pageIndex = 1;
    @InjectView(R.id.xlistview_lv)
    CustListView listview;
    private String types, subjectId, cateId;
    private int type;
    private Intent intent;
    private WebView webview;
    private View viewhead;
    private LinearLayout web_lin;
    private CommonInfo commonInfo;

    @Override
    protected void initializeData() {
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        intent = getActivity().getIntent();
        intents();

    }

    /**
     * 头部图片展示
     */
    private void banner() {
        if (commonInfo != null && !TextUtils.isEmpty(commonInfo.getLmw_has_prize_king_url()) &&
                !TextUtils.equals(cateId, "1003") && !TextUtils.equals(cateId, "2000") &&
                !TextUtils.isEmpty(cateId)) {
            web_lin.setVisibility(View.VISIBLE);
            initWebView(commonInfo.getLmw_has_prize_king_url() + subjectId);
        } else {
            web_lin.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initializeView() {
        xlist();
        banner();
        credithttp(subjectId, type, 1, 15, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
    }

    private void xlist() {
        if (listview != null) {
            handler = new Handler();
            listview.setPullRefreshEnable(true);
            listview.setPullLoadEnable(true);
            listview.setCustListViewListener(this);
            listview.setRefreshTime(DateUtil.getRefreshTime());
            viewhead = LayoutInflater.from(mContext).inflate(R.layout.investment_record_head_item, null);
            webview = (WebView) viewhead.findViewById(R.id.invertment_head_web);
            web_lin = (LinearLayout) viewhead.findViewById(R.id.investment_web_lin);
            listview.addHeaderView(viewhead);
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_record_layout;
    }

    private void intents() {
        if (intent.hasExtra("subjectId"))
            subjectId = intent.getStringExtra("subjectId");
        if (intent.hasExtra("type")) {
            types = intent.getStringExtra("type");
            type = Integer.valueOf(types).intValue();
        }
        if (intent.hasExtra("cateId"))
            cateId = intent.getStringExtra("cateId");
        Log.d("cateId ", " " + cateId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listview != null) {
                    listview.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    credithttp(subjectId, type, 1, 15, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
                    listview.stopRefresh();
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listview != null) {
                    pageIndex++;
//                    if (pageIndex > pageCount) {
//                        ToastUtil.showToast(mContext, "暂无更多数据");
//                    } else {
                    credithttp(subjectId, type, pageIndex, 15, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
//                    }
                    listview.stopLoadMore();
                }
            }
        }, 1000);

    }

    /**
     * 访问网络
     */
    private void credithttp(String borrowId, int isDebt, final int pageIndex, int pageSize, String token) {
        Singlton.getInstance(FinanceHttp.class).getInvestmentRecord(mContext, borrowId, isDebt, pageIndex, pageSize, token, new OnResponseListener<InvestmentEntity>() {
            @Override
            public void onSuccess(InvestmentEntity investmentEntity) {
                if (listview == null) return;
                if (investmentEntity == null) return;
                if (investmentEntity.getData() == null) return;
                if (investmentEntity.getData().getDatas() == null) return;
                if (investmentEntity.getCode().equals("0")) {
                    if (investmentEntity.getData().getDatas().size() > 0) {
                        if (pageIndex == 1) {
                            if (investmentEntity.getData().getDatas().size() < 15) {
                                listview.setPullLoadEnable(false);
                            } else {
                                listview.setPullLoadEnable(true);
                            }
                            recordAdapter = new RecordAdapter(mContext, investmentEntity.getData().getDatas(), R.layout.fragment_record_item);
                            listview.setAdapter(recordAdapter);
                        } else {
                            if (investmentEntity.getData().getDatas().size() < 15) {
                                listview.setPullLoadEnable(false);
                            } else {
                                listview.setPullLoadEnable(true);
                            }
                            recordAdapter.addData(investmentEntity.getData().getDatas());
                            recordAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (pageIndex == 1) {
                            //TODO 没有数据 需要显示不同的界面

                        } else {
                            ToastUtil.showToast(mContext, "没有更多数据");
                        }
                    }
                    pageCount = investmentEntity.getPageCount();// 添加最多页数标识
                } else {
                    ToastUtil.showToast(mContext, investmentEntity.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if (isInMultiWindowMode) {
            listview.autoRefresh();
        }
    }


    /**
     * webview加载
     *
     * @param url
     */
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
}
