package com.example.administrator.lmw.finance.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.finance.entity.DetailEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.finance.utils.BigDecimalUtils;
import com.example.administrator.lmw.finance.utils.DateUtils;
import com.example.administrator.lmw.finance.utils.ProgressViewTest;
import com.example.administrator.lmw.finance.utils.TimeCount;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 债权详情
 * <p>
 * Created by Administrator on 2016/8/26 0026.
 */
public class DetailFragment extends BaseFragment implements TimeCount.TimeDownListener {

    @InjectView(R.id.detail_earnings_tv)
    TextView detailEarningsTv;
    @InjectView(R.id.detail_earnings_percent_tv)
    TextView detailEarningsPercentTv;
    @InjectView(R.id.detail_callout1)
    TextView detailCallout1;
    @InjectView(R.id.detail_callout2)
    TextView detailCallout2;
    @InjectView(R.id.detail_days_tv)
    TextView detailDaysTv;
    @InjectView(R.id.detail_days_type_tv)
    TextView detailDaysTypeTv;
    @InjectView(R.id.detail_moneys_tv)
    TextView detailMoneysTv;
    @InjectView(R.id.detail_persons_tv)
    TextView detailPersonsTv;
    @InjectView(R.id.detail_percent_tv)
    TextView detailPercentTv;
    @InjectView(R.id.detail_surplus_money_tv)
    TextView detailSurplusMoneyTv;
    @InjectView(R.id.detail_value_date_tv)
    TextView detailValueDateTv;
    @InjectView(R.id.detail_the_sabbath_tv)
    TextView detailTheSabbathTv;
    @InjectView(R.id.details_income_way_tv)
    TextView detailsIncomeWayTv;
    @InjectView(R.id.details_original_yield_tv)
    TextView detailsOriginalYieldTv;
    @InjectView(R.id.details_discount_tv)
    TextView detailsDiscountTv;
    @InjectView(R.id.detail_time_days_tv)
    TextView detailTimeDaysTv;
    @InjectView(R.id.detail_time_hour_tv)
    TextView detailTimeHourTv;
    @InjectView(R.id.detail_time_minute_tv)
    TextView detailTimeMinuteTv;
    @InjectView(R.id.detail_time_second_tv)
    TextView detailTimeSecondTv;
    @InjectView(R.id.detail_lin1)
    LinearLayout detailLin1;
    @InjectView(R.id.detail_lin2)
    LinearLayout detailLin2;
    @InjectView(R.id.detail_value_parent_lin_one)
    LinearLayout detailValueParentLinOne;
    @InjectView(R.id.detail_value_parent_tv)
    TextView detailValueParentTv;
    @InjectView(R.id.detail_value_time_tv)
    TextView detailValueTimeTv;
    @InjectView(R.id.detail_the_none_other_tv)
    TextView detailTheNoneOtherTv;
    @InjectView(R.id.progress_blue)
    ProgressViewTest progressBlue;
    @InjectView(R.id.details_conditions_im)
    ImageView detailsConditionsIm;
    @InjectView(R.id.detail_value_parent_lin_two)
    LinearLayout detailValueParentLinTwo;
    private String type, subjectId;
    private BigDecimalUtils bdu = new BigDecimalUtils();
    private TimeCount timeCount;
    private Intent intent;
    private DetailFragmentLister lister;
    private CommonInfo commonInfo;
    private String repay_desc_url;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lister = (DetailFragmentLister) mContext;
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
        progressBlue.setMaxCount(100);
        http();

    }

    private void http() {
        detailhttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_finance_detail;
    }


    private void intents() {
        if (intent.hasExtra("subjectId"))
            subjectId = intent.getStringExtra("subjectId");

    }

    /**
     * 访问网络
     */
    private void detailhttp(String debtId, String token) {
        Singlton.getInstance(FinanceHttp.class).getDetailFragment(mContext, debtId, token, new OnResponseListener<DetailEntity>() {
            @Override
            public void onSuccess(DetailEntity detailEntity) {
                if (detailEntity == null) return;
                if (detailEntity.getData() == null) return;
                if (detailEntity.getCode().equals("0")) {
                    assignment(detailEntity);
                } else {
                    ToastUtil.showToast(mContext, detailEntity.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 初始化界面
     *
     * @param entity
     */
    private void assignment(DetailEntity entity) {
        // 年化利率
        if (!TextUtils.isEmpty(entity.getData().getTransferRate())) {
            int sky = entity.getData().getTransferRate().indexOf(".");
            detailEarningsTv.setText(entity.getData().getTransferRate().substring(0, sky));
            detailEarningsPercentTv.setText(entity.getData().getTransferRate().substring(sky, entity.getData().getTransferRate().length()));
        }
        // 还款方式说明
        detailsConditionsIm.setOnClickListener(new View.OnClickListener() {
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
        /**
         * 标签
         */
        if (entity.getData().getBaseInfoDesc() != null)
            switch (entity.getData().getBaseInfoDesc().size()) {
                case 0:
                    detailCallout1.setText("");
                    detailCallout2.setText("");
                    detailLin1.setVisibility(View.GONE);
                    detailLin2.setVisibility(View.GONE);
                    break;
                case 1:
                    detailCallout1.setText(entity.getData().getBaseInfoDesc().get(0));
                    detailCallout2.setText("");
                    detailLin1.setVisibility(View.VISIBLE);
                    detailLin2.setVisibility(View.GONE);
                    break;
                case 2:
                    detailCallout1.setText(entity.getData().getBaseInfoDesc().get(0));
                    detailCallout2.setText(entity.getData().getBaseInfoDesc().get(1));
                    detailLin1.setVisibility(View.VISIBLE);
                    detailLin2.setVisibility(View.VISIBLE);
                    break;
            }
        // 投资期限
        if (!TextUtils.isEmpty(entity.getData().getDeadLineValue()))
            detailDaysTv.setText(entity.getData().getDeadLineValue());
        if (!TextUtils.isEmpty(entity.getData().getDeadLineType()))
            if (entity.getData().getDeadLineType().equals("1")) {
                detailDaysTypeTv.setText("天");
            } else if (entity.getData().getDeadLineType().equals("2")) {
                detailDaysTypeTv.setText("月");
            } else if (entity.getData().getDeadLineType().equals("3")) {
                detailDaysTypeTv.setText("年");
            }
        // 转让本金
        if (!TextUtils.isEmpty(entity.getData().getDebtPrincipalAmt()))
            detailMoneysTv.setText(entity.getData().getDebtPrincipalAmt());
        // 购买人数
        if (!TextUtils.isEmpty(entity.getData().getInvestPeople())) {
            String textNumMan = String.format(getActivity().getResources().getString(R.string.txt_num_man), entity.getData().getInvestPeople());
            String text = String.format("%s%s", getActivity().getResources().getString(R.string.txt_buy_man), textNumMan);
            SpannableStringBuilder str = StringUtils.getColorSpannBuilder(ContextCompat.getColor(getActivity(), R.color.select_list_title), text, textNumMan);
            detailPersonsTv.setText(str);
        }

        // 进度条
        if (TextUtils.isEmpty(entity.getData().getHasDebtInvestAmt())) {
            progressBlue.setCurrentCount(0);
        } else {
            if (!TextUtils.isEmpty(entity.getData().getHasDebtInvestAmt()) && !TextUtils.isEmpty(entity.getData().getDebtPrincipalAmt()))
                if (Double.parseDouble(entity.getData().getHasDebtInvestAmt()) > 0) {// 进度条
                    float pro = BigDecemalCalculateUtil.divide(entity.getData().getDebtPrincipalAmt(), entity.getData().getHasDebtInvestAmt(), 1);
                    detailPercentTv.setText(pro + "");
                    progressBlue.setCurrentCount(pro);
                } else {
                    detailPercentTv.setText("0.0");
                    progressBlue.setCurrentCount(0);
                }
        }
        // 剩余金额
        if (!TextUtils.isEmpty(entity.getData().getRemaMoney()))
            detailSurplusMoneyTv.setText(entity.getData().getRemaMoney());
        /**
         * 倒计时
         */
        if (entity.getData().getStatus().equals("0")) {
            if (TextUtils.isEmpty(entity.getData().getTransferEndDate())) {
                detailTimeDaysTv.setText("--");// 天
                detailTimeHourTv.setText("--");// 时
                detailTimeMinuteTv.setText("--");// 分
                detailTimeSecondTv.setText("--");// 秒
            } else {
                if (!TextUtils.isEmpty(entity.getData().getTransferEndDate()) && !TextUtils.isEmpty(entity.getData().getNowTime())) {
                    long diff = DateUtils.getStringToDate(entity.getData().getTransferEndDate()) - DateUtils.getStringToDate(entity.getData().getNowTime());
                    timeCount = new TimeCount(getActivity(), diff, 1000, this);
                    timeCount.start();
                }
            }
        } else {
            detailTimeDaysTv.setText("--");// 天
            detailTimeHourTv.setText("--");// 时
            detailTimeMinuteTv.setText("--");// 分
            detailTimeSecondTv.setText("--");// 秒
        }


        for (int i = 0; i < entity.getData().getProductInfoList().size(); i++) {
            // 转让开始
            if (entity.getData().getProductInfoList().get(i).getType().equals("transferTime")) {
                detailValueDateTv.setText(entity.getData().getProductInfoList().get(i).getValue());
            }
            // 债权价值
            if (entity.getData().getProductInfoList().get(i).getType().equals("debtPrice")) {
                detailTheSabbathTv.setText(entity.getData().getProductInfoList().get(i).getValue());
            }
            // 收益方式
            if (entity.getData().getProductInfoList().get(i).getType().equals("repayMode")) {
                String text = String.format(getActivity().getResources().getString(R.string.txt_repayment_way), entity.getData().getProductInfoList().get(i).getValue());
                SpannableStringBuilder str = StringUtils.getColorSpannBuilder(ContextCompat.getColor(getActivity(), R.color.select_list_title),
                        text, entity.getData().getProductInfoList().get(i).getValue());
                if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "一次性还本付息")) {
                    repay_desc_url = "?tar=1#1490086433277";
                } else if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "按月付息到期还本")) {
                    repay_desc_url = "?tar=2#1490086433277";

                } else if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "等额本息")) {
                    repay_desc_url = "?tar=3#1490086433277";
                } else if (TextUtils.equals(entity.getData().getProductInfoList().get(i).getValue(), "等额本金")) {
                    repay_desc_url = "?tar=4#1490086433277";
                }






                detailsIncomeWayTv.setText(str);
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
            // 转让价格
            if (entity.getData().getProductInfoList().get(i).getType().equals("zrPrice")) {
                detailsOriginalYieldTv.setText(entity.getData().getProductInfoList().get(i).getValue());
            }
            // 让利
            if (entity.getData().getProductInfoList().get(i).getType().equals("rlPrice")) {
                detailTheNoneOtherTv.setText(entity.getData().getProductInfoList().get(i).getValue());
            }
        }

        /**
         * 0.转让中，1.转让成功,2.取消转让,3.时间过期,4.转让失败
         */
        if (!TextUtils.isEmpty(entity.getData().getStatus()))
            if (entity.getData().getStatus().equals("0")) {
                detailValueParentLinOne.setVisibility(View.VISIBLE);
                detailValueParentTv.setVisibility(View.GONE);
                detailValueTimeTv.setText("转让剩余时间");
            } else if (entity.getData().getStatus().equals("1")) {
                detailValueParentLinOne.setVisibility(View.GONE);
                detailValueParentTv.setVisibility(View.VISIBLE);
                detailValueTimeTv.setText("转让成功时间");
            } else if (entity.getData().getStatus().equals("2")) {
                detailValueParentLinOne.setVisibility(View.GONE);
                detailValueParentTv.setVisibility(View.VISIBLE);
                detailValueTimeTv.setText("转让结束时间");
            } else if (entity.getData().getStatus().equals("3")) {
                detailValueParentLinOne.setVisibility(View.GONE);
                detailValueParentTv.setVisibility(View.VISIBLE);
                detailValueTimeTv.setText("转让结束时间");
            } else if (entity.getData().getStatus().equals("4")) {
                detailValueParentLinOne.setVisibility(View.GONE);
                detailValueParentTv.setVisibility(View.VISIBLE);
                detailValueTimeTv.setText("转让结束时间");
            }

        /**
         * 满标时间
         */
        if (!TextUtils.isEmpty(entity.getData().getStatus()))
            if (entity.getData().getStatus().equals("1")) {
                if (!TextUtils.isEmpty(entity.getData().getLastInvestTime()))
                    detailValueParentTv.setText(entity.getData().getLastInvestTime());
            } else {
                if (!TextUtils.isEmpty(entity.getData().getCollectFinshTime()))
                    detailValueParentTv.setText(entity.getData().getCollectFinshTime());
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
        if (detailTimeDaysTv != null)
            detailTimeDaysTv.setText(d);// 天
        if (detailTimeHourTv != null)
            detailTimeHourTv.setText(h);// 时
        if (detailTimeMinuteTv != null)
            detailTimeMinuteTv.setText(m);// 分
        if (detailTimeSecondTv != null)
            detailTimeSecondTv.setText(s);// 秒
        if (isFinish) {
            http();
            lister.lister("0");
        }

    }

    public interface DetailFragmentLister {

        void lister(String str);
    }
}
