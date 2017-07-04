package com.example.administrator.lmw.finance.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.finance.entity.DetailFragmentEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.finance.utils.BigDecimalUtils;
import com.example.administrator.lmw.finance.utils.CustBottomView;
import com.example.administrator.lmw.finance.utils.CustScrollView;
import com.example.administrator.lmw.finance.utils.DateUtils;
import com.example.administrator.lmw.finance.utils.ProgressViewTest;
import com.example.administrator.lmw.finance.utils.TimeCount;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.JSInteraction;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 理财详情
 * <p>
 * Created by Administrator on 2016/9/1 0001.
 */
public class DetailProductFragment extends BaseFragment implements TimeCount.TimeDownListener {

    @InjectView(R.id.product_earnings_tv)
    TextView productEarningsTv;
    @InjectView(R.id.product_earnings_percent_tv)
    TextView productEarningsPercentTv;
    @InjectView(R.id.product_callout1)
    TextView productCallout1;
    @InjectView(R.id.product_callout2)
    TextView productCallout2;
    @InjectView(R.id.product_days_tv)
    TextView productDaysTv;
    @InjectView(R.id.product_days_type_tv)
    TextView productDaysTypeTv;
    @InjectView(R.id.product_moneys_tv)
    TextView productMoneysTv;
    @InjectView(R.id.product_persons_tv)
    TextView productPersonsTv;
    @InjectView(R.id.product_percent_tv)
    TextView productPercentTv;
    @InjectView(R.id.product_surplus_money_tv)
    TextView productSurplusMoneyTv;
    @InjectView(R.id.product_value_date_tv)
    TextView productValueDateTv;
    @InjectView(R.id.product_income_way_tv)
    TextView productIncomeWayTv;
    @InjectView(R.id.product_raise_tv)
    TextView productRaiseTv;
    @InjectView(R.id.product_time_days_tv)
    TextView productTimeDaysTv;
    @InjectView(R.id.product_time_hour_tv)
    TextView productTimeHourTv;
    @InjectView(R.id.product_time_minute_tv)
    TextView productTimeMinuteTv;
    @InjectView(R.id.product_time_second_tv)
    TextView productTimeSecondTv;
    @InjectView(R.id.product_lin1)
    LinearLayout productLin1;
    @InjectView(R.id.product_lin2)
    LinearLayout productLin2;
    @InjectView(R.id.product_value_parent_lin_one)
    LinearLayout productValueParentLinOne;
    @InjectView(R.id.product_transfer_amount_tv)
    TextView productTransferAmountTv;
    @InjectView(R.id.product_value_parent_lin_two)
    TextView productValueParentLinTwo;
    @InjectView(R.id.product_progress_blue)
    ProgressViewTest productProgressBlue;
    @InjectView(R.id.product_value_time_tv)
    TextView productValueTimeTv;
    @InjectView(R.id.product_conditions_tv)
    TextView productConditionsTv;
    @InjectView(R.id.product_conditions_prompt)
    ImageView productConditionsPrompt;
    @InjectView(R.id.finance_webview)
    CustBottomView financeWebview;
    @InjectView(R.id.finance_scrollview)
    CustScrollView financeScrollview;
    @InjectView(R.id.product_continuous_im)
    ImageView productContinuousIm;
    private String subjectId, mobileAddInfoUrl;
    private BigDecimalUtils bdu = new BigDecimalUtils();
    private TimeCount timeCount;
    private Intent intent;
    private DetailProductFragmentLister lister;
    private CommonInfo commonInfo;
    private String repay_desc_url;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lister = (DetailProductFragmentLister) mContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeData() {
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);
        intent = getActivity().getIntent();
        intents();
    }

    @Override
    protected void initializeView() {
        productProgressBlue.setMaxCount(100);
        http();
    }

    private void http() {
        if (!TextUtils.isEmpty(mobileAddInfoUrl)) {
            financeWebview.setVisibility(View.VISIBLE);
            financeScrollview.setVisibility(View.GONE);
            webView(mobileAddInfoUrl);
        } else {
            financeWebview.setVisibility(View.GONE);
            financeScrollview.setVisibility(View.VISIBLE);
            producthttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_finance_detail_product;
    }


    private void intents() {
        if (intent.hasExtra("subjectId"))
            subjectId = intent.getStringExtra("subjectId");
        if (intent.hasExtra("mobileAddInfo"))
            mobileAddInfoUrl = intent.getStringExtra("mobileAddInfo");
    }

    /**
     * 访问网络
     *
     * @param borrowId
     * @param token
     */
    private void producthttp(String borrowId, String token) {
        Singlton.getInstance(FinanceHttp.class).getDetailProductFragment(mContext, borrowId, token, new OnResponseListener<DetailFragmentEntity>() {
            @Override
            public void onSuccess(DetailFragmentEntity detailFragmentEntity) {
                if (detailFragmentEntity == null) return;
                if (detailFragmentEntity.getData() == null) return;
                if (detailFragmentEntity.getCode().equals("0")) {
                    assignment(detailFragmentEntity);
                } else {
                    ToastUtil.showToast(mContext, detailFragmentEntity.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 赋值初始化
     *
     * @param entity
     */
    private void assignment(DetailFragmentEntity entity) {
        // 转让条件点击说明
        productConditionsPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singlton.getInstance(PopWindowManager.class).
                        showLeftOnebtnDialog(mContext, "转让条件", StringUtils.getTransferInstruction().toString(), "我知道了",
                                new OnDialogClickListener() {
                                    @Override
                                    public void onClick(int id, View v) {
                                        if (id == 2) {

                                        }
                                    }
                                });
            }
        });
        // 还款方式说明
        productContinuousIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(commonInfo.getRepay_desc_url()) && !TextUtils.isEmpty(repay_desc_url)) {
                    Intent intent = new Intent(mContext, WebViewMore.class);
                    intent.putExtra("url", commonInfo.getRepay_desc_url() + repay_desc_url);
                    intent.putExtra("title", "还款方式说明");
                    startActivity(intent);
                }
            }
        });
        // 年化利率
        if (!TextUtils.isEmpty(entity.getData().getAnnualRate())) {
            int sky = entity.getData().getAnnualRate().indexOf(".");
            productEarningsTv.setText(entity.getData().getAnnualRate().substring(0, sky));
            if (!TextUtils.isEmpty(entity.getData().getIncreaseRate()))
                if (NumberParserUtil.parserDouble(entity.getData().getIncreaseRate(), 0) > 0) {
                    productEarningsPercentTv.setText(entity.getData().getAnnualRate().substring(sky, entity.getData().getAnnualRate().length()) + "%" + entity.getData().getIncreaseRate());
                } else {
                    productEarningsPercentTv.setText(entity.getData().getAnnualRate().substring(sky, entity.getData().getAnnualRate().length()));
                }
        }
        // 标签
        if (entity.getData().getBaseInfoDesc() != null)
            switch (entity.getData().getBaseInfoDesc().size()) {
                case 0:
                    productCallout1.setText("");
                    productCallout2.setText("");
                    productLin1.setVisibility(View.GONE);
                    productLin2.setVisibility(View.GONE);
                    break;
                case 1:
                    productCallout1.setText(entity.getData().getBaseInfoDesc().get(0));
                    productCallout2.setText("");
                    productLin1.setVisibility(View.VISIBLE);
                    productLin2.setVisibility(View.GONE);
                    break;
                case 2:
                    productCallout1.setText(entity.getData().getBaseInfoDesc().get(0));
                    productCallout2.setText(entity.getData().getBaseInfoDesc().get(1));
                    productLin1.setVisibility(View.VISIBLE);
                    productLin2.setVisibility(View.VISIBLE);
                    break;
            }
        // 投资期限
        if (!TextUtils.isEmpty(entity.getData().getDeadLineValue()))
            productDaysTv.setText(entity.getData().getDeadLineValue());// 天数
        if (!TextUtils.isEmpty(entity.getData().getDeadLineType()))
            productDaysTypeTv.setText(entity.getData().getDeadLineType());// 单位
        // 转让本金
        if (!TextUtils.isEmpty(entity.getData().getBuyTotalAmount()))
            productTransferAmountTv.setText(entity.getData().getBuyTotalAmount());
        // 购买人数
        if (!TextUtils.isEmpty(entity.getData().getInvestPeople()))
            productPersonsTv.setText(entity.getData().getInvestPeople());
        // 已募集
        if (TextUtils.isEmpty(entity.getData().getHasInvestMoney())) {
            productPercentTv.setText("0.0");
            productProgressBlue.setCurrentCount(0);
        } else {
            if (!TextUtils.isEmpty(entity.getData().getBuyTotalAmount()))
                if (Double.parseDouble(entity.getData().getHasInvestMoney()) > 0) {// 进度条
                    float pro = BigDecemalCalculateUtil.divide(entity.getData().getBuyTotalAmount(), entity.getData().getHasInvestMoney(), 1);
                    productPercentTv.setText(pro + "");
                    productProgressBlue.setCurrentCount(pro);
                } else {
                    productPercentTv.setText("0.0");
                    productProgressBlue.setCurrentCount(0);
                }
        }
        // 剩余金额
        if (!TextUtils.isEmpty(entity.getData().getRemaMoney()))
            productSurplusMoneyTv.setText(entity.getData().getRemaMoney());
        //2-预售3-(在售)正在招标中4-（售罄）已满标5-募集中6-募集失败7-募集成功9-已经结束
        if (!TextUtils.isEmpty(entity.getData().getStatus()))
            switch (Integer.parseInt(entity.getData().getStatus())) {
                case 2:
                    productValueTimeTv.setText("离开始购买还剩");
                    break;
                case 3:
                    productValueTimeTv.setText("投资剩余时间");
                    break;
                case 4:
                    productValueTimeTv.setText("投资结束时间");
                    break;
                case 5:
                    productValueTimeTv.setText("投资剩余时间");
                    break;
                case 7:
                    productValueTimeTv.setText("投资结束时间");
                    break;
                case 10:
                    productValueTimeTv.setText("离开始购买还剩");
                    break;
                default:
                    productValueTimeTv.setText("投资结束时间");
                    break;
            }

        /**
         * 倒计时 TODO
         */
        if (!TextUtils.isEmpty(entity.getData().getStatus()))
            if (entity.getData().getStatus().equals("2") || entity.getData().getStatus().equals("3") || entity.getData().getStatus().equals("5")) {
                if (TextUtils.isEmpty(entity.getData().getCollectEndTime())) {
                    productTimeDaysTv.setText("--");// 天
                    productTimeHourTv.setText("--");// 时
                    productTimeMinuteTv.setText("--");// 分
                    productTimeSecondTv.setText("--");// 秒
                } else {
                    if (!TextUtils.isEmpty(entity.getData().getCollectEndTime()) && !TextUtils.isEmpty(entity.getData().getNowTime())) {
                        long diff = DateUtils.getStringToDate(entity.getData().getCollectEndTime()) - DateUtils.getStringToDate(entity.getData().getNowTime());
                        timeCount = new TimeCount(getActivity(), diff, 1000, this);
                        timeCount.start();
                    }
                }
            } else if (entity.getData().getStatus().equals("10")) {
                if (TextUtils.isEmpty(entity.getData().getSaleTimeStart())) {
                    productTimeDaysTv.setText("--");// 天
                    productTimeHourTv.setText("--");// 时
                    productTimeMinuteTv.setText("--");// 分
                    productTimeSecondTv.setText("--");// 秒
                } else {
                    if (!TextUtils.isEmpty(entity.getData().getSaleTimeStart()) && !TextUtils.isEmpty(entity.getData().getNowTime())) {
                        long diff = DateUtils.getStringToDate(entity.getData().getSaleTimeStart()) - DateUtils.getStringToDate(entity.getData().getNowTime());
                        timeCount = new TimeCount(getActivity(), diff, 1000, this);
                        timeCount.start();
                    }
                }
            } else {
                productTimeDaysTv.setText("--");
                productTimeHourTv.setText("--");// 时
                productTimeMinuteTv.setText("--");// 分
                productTimeSecondTv.setText("--");// 秒
            }
        for (int i = 0; i < entity.getData().getProductInfoList().size(); i++) {
            // 起投金额
            if (entity.getData().getProductInfoList().get(i).getType().equals("investAmt"))
                productMoneysTv.setText(entity.getData().getProductInfoList().get(i).getValue());
            // 开始募集
            if (entity.getData().getProductInfoList().get(i).getType().equals("saleTimeStart"))
                productValueDateTv.setText(entity.getData().getProductInfoList().get(i).getValue());
            // 还款方式
            if (entity.getData().getProductInfoList().get(i).getType().equals("repayMode")) {
                productIncomeWayTv.setText(entity.getData().getProductInfoList().get(i).getValue());
                if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "一次性还本付息")) {
                    repay_desc_url = "?tar=1#1490086433277";
                } else if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "按月付息到期还本")) {
                    repay_desc_url = "?tar=2#1490086433277";

                } else if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "等额本息")) {
                    repay_desc_url = "?tar=3#1490086433277";
                } else if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "等额本金")) {
                    repay_desc_url = "?tar=4#1490086433277";
                }
            }
            // 募集期间年化收益
            if (entity.getData().getProductInfoList().get(i).getType().equals("collectRate"))
                productRaiseTv.setText(entity.getData().getProductInfoList().get(i).getValue());
            // 转让条件
            if (entity.getData().getProductInfoList().get(i).getType().equals("isCanTransfer"))
                productConditionsTv.setText(entity.getData().getProductInfoList().get(i).getValue());
        }
        // 满标时间
        if (!TextUtils.isEmpty(entity.getData().getCollectFinshTime()))
            productValueParentLinTwo.setText(entity.getData().getCollectFinshTime());

        switch (Integer.parseInt(entity.getData().getStatus())) {
            case -1:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
            case 0:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 1:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 2:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 3:
                productValueParentLinOne.setVisibility(View.VISIBLE);
                productValueParentLinTwo.setVisibility(View.GONE);
                break;
            case 4:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 5:
                productValueParentLinOne.setVisibility(View.VISIBLE);
                productValueParentLinTwo.setVisibility(View.GONE);
                break;
            case 6:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 7:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 8:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 9:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
            case 10:
                productValueParentLinOne.setVisibility(View.VISIBLE);
                productValueParentLinTwo.setVisibility(View.GONE);
                break;
            default:
                productValueParentLinOne.setVisibility(View.GONE);
                productValueParentLinTwo.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timeCount != null)
            timeCount.cancel();
        ButterKnife.reset(this);
    }

    /**
     * 倒计时显示
     *
     * @param h 02
     * @param m 02
     * @param s 02
     */
    @Override
    public void onTimer(String msgID, String d, String h, String m, String s, boolean isFinish) {
        if (productTimeDaysTv != null)
            productTimeDaysTv.setText(d);// 天
        if (productTimeHourTv != null)
            productTimeHourTv.setText(h);// 时
        if (productTimeMinuteTv != null)
            productTimeMinuteTv.setText(m);// 分
        if (productTimeSecondTv != null)
            productTimeSecondTv.setText(s);// 秒
        if (isFinish) {
            http();
            lister.lister("0");
        }

    }

    /**
     * 月增标webview显示
     *
     * @param url
     */
    private void webView(String url) {
        financeWebview.getSettings().setJavaScriptEnabled(true);
        financeWebview.getSettings().setDomStorageEnabled(true);
        financeWebview.getSettings().setUseWideViewPort(true);
        financeWebview.getSettings().setLoadWithOverviewMode(true);
        financeWebview.getSettings().setBuiltInZoomControls(true);
        financeWebview.getSettings().setSupportZoom(true);
        financeWebview.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            financeWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
      /*  financeWebview.getSettings().setCacheMode(
                WebSettings.LOAD_CACHE_ELSE_NETWORK);*/
        String ua = financeWebview.getSettings().getUserAgentString();
        financeWebview.getSettings().setUserAgentString(ua + " LIMIN/2.0.0");
        financeWebview.addJavascriptInterface(new JSInteraction(mContext), "liminApp");
        financeWebview.loadUrl(url);

        financeWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                financeWebview.loadUrl(url);
                return true;
                // return super.shouldOverrideUrlLoading(view, url);
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
        financeWebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((WebView) view).requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    public interface DetailProductFragmentLister {
        void lister(String str);
    }

}
