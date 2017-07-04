package com.example.administrator.lmw.mine.invest.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.mine.invest.adapter.BackToThePlanAdapter;
import com.example.administrator.lmw.mine.invest.entity.BackPlanEntity;
import com.example.administrator.lmw.mine.invest.utils.InvestmentHttp;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.view.XListView;

import butterknife.InjectView;

/**
 * 我的投资 回款计划
 * <p>
 * Created by Administrator on 2017/3/13
 */
public class BackToThePlanActivity extends BaseTitleActivity implements XListView.IXListViewListener {

    @InjectView(R.id.back_plan_xlist)
    XListView xlist;
    @InjectView(R.id.activity_back_to_the_plan)
    LinearLayout activityBackToThePlan;
    private TextView backPlanTitleTv;       // 标题
    private TextView backPlanRateTv;        // 利率
    private TextView backPlanDaysTv;        // 日期
    private TextView backPlanInvestmentTv;  // 投资本金
    private TextView backPlanReceiveTv;     // 已收本金
    private TextView backPlanPayIncomeTv;   // 已收收益
    private TextView backPlanPayAmountTv;   // 支付金额(债权)
    private TextView backPlanToPayTv;       // 待收本金
    private TextView backPlanToRevenueTv;   // 待收收益
    private TextView backPlanBackDayTv;     // 下一回款日
    private TextView backPlanWayTv;         // 回款方式
    private TextView backPlanBackDayT;       // 退出日期文字
    private BackToThePlanAdapter backToThePlanAdapter;
    private IsEmptyAdapter mEmptyAdapter;
    private String investId;// 标的id
    private Intent intent;
    private View backPlanHead;// 头部布局
    private boolean isSetEmptyAdapter;

    @Override
    protected void initializeData() {
        intent = getIntent();
        intents();
        backToThePlanAdapter = new BackToThePlanAdapter(mContext);
        mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));

    }

    @Override
    protected void initializeView() {
        xlist();
        findView();
        getIdCard(investId);
    }

    @Override
    protected void setTitleContentView() {
        initView("回款计划", R.layout.activity_back_to_the_plan);
    }

    /**
     * 获取传参
     */
    private void intents() {
        if (intent.hasExtra("investId"))
            investId = intent.getStringExtra("investId");
    }

    /**
     * 实例化
     */
    private void findView() {
        backPlanHead = LayoutInflater.from(mContext).inflate(R.layout.back_plan_head, null);
        backPlanTitleTv = (TextView) backPlanHead.findViewById(R.id.back_plan_title_tv);
        backPlanRateTv = (TextView) backPlanHead.findViewById(R.id.back_plan_rate_tv);
        backPlanDaysTv = (TextView) backPlanHead.findViewById(R.id.back_plan_days_tv);
        backPlanInvestmentTv = (TextView) backPlanHead.findViewById(R.id.back_plan_investment_tv);
        backPlanReceiveTv = (TextView) backPlanHead.findViewById(R.id.back_plan_receive_tv);
        backPlanPayIncomeTv = (TextView) backPlanHead.findViewById(R.id.back_plan_pay_income_tv);
        backPlanPayAmountTv = (TextView) backPlanHead.findViewById(R.id.back_plan_pay_amount_tv);
        backPlanToPayTv = (TextView) backPlanHead.findViewById(R.id.back_plan_to_pay_tv);
        backPlanToRevenueTv = (TextView) backPlanHead.findViewById(R.id.back_plan_to_revenue_tv);
        backPlanBackDayTv = (TextView) backPlanHead.findViewById(R.id.back_plan_back_day_tv);
        backPlanBackDayT = (TextView) backPlanHead.findViewById(R.id.back_plan_back_day_t);
        backPlanWayTv = (TextView) backPlanHead.findViewById(R.id.back_plan_way_tv);
        xlist.addHeaderView(backPlanHead);
    }

    /**
     * list实例化
     */
    private void xlist() {
        if (xlist != null) {
            xlist.setPullLoadEnable(false);
            xlist.setPullRefreshEnable(true);
            xlist.setXListViewListener(this);
            xlist.setRefreshTime(DateUtil.getRefreshTime());
        }
        xlist.setAdapter(backToThePlanAdapter);
    }

    /**
     * @param investId 标的 id
     */
    private void getIdCard(String investId) {
        Singlton.getInstance(InvestmentHttp.class).getBackPlan(mContext, investId, new OnResponseListener<BaseResult<BackPlanEntity>>() {
            @Override
            public void onSuccess(BaseResult<BackPlanEntity> backPlanEntity) {
                if (xlist == null) return;
                if (backPlanEntity == null) return;
                if (backPlanEntity.getData() == null) return;
                if (backPlanEntity.getData().getDatas() == null) return;
                if (TextUtils.equals(backPlanEntity.getCode(), "0")) {
                    loadInterface(backPlanEntity.getData());
                    if (backPlanEntity.getData().getDatas() != null && backPlanEntity.getData().getDatas().size() > 0) {
                        if (isSetEmptyAdapter) {
                            isSetEmptyAdapter = false;
                            xlist.setAdapter(backToThePlanAdapter);
                        }
                        backToThePlanAdapter.resetDatas(backPlanEntity.getData().getDatas());// 添加数据
                    } else {
                        isSetEmptyAdapter = true;
                        xlist.setAdapter(mEmptyAdapter);
                    }
                } else if (backPlanEntity.getCode().equals("150006")) {
                } else if (backPlanEntity.getCode().equals("1000")) {
                } else {
                    if (xlist != null) {
                        mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));
                        xlist.setAdapter(mEmptyAdapter);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 加载界面信息
     *
     * @param backPlanEntity
     */
    private void loadInterface(BackPlanEntity backPlanEntity) {
        // 标题
        backPlanTitleTv.setText(backPlanEntity.getBorrowTitle());
        // 利率
        backPlanRateTv.setText(showRate(backPlanEntity));
        // 期限类型(1=天数|2=自然月|3=年)
        switch (NumberParserUtil.parserInt(backPlanEntity.getDeadLineType(), 1)) {
            case 1:
                backPlanDaysTv.setText(getDeadLineType(backPlanEntity) + "天");
                break;
            case 2:
                backPlanDaysTv.setText(getDeadLineType(backPlanEntity) + "个月");
                break;
            case 3:
                backPlanDaysTv.setText(getDeadLineType(backPlanEntity) + "年");
                break;
        }
        // 投资本金
        backPlanInvestmentTv.setText(backPlanEntity.getInvestAmount());
        // 已收本金
        backPlanReceiveTv.setText(backPlanEntity.getReceivedPrincipal());
        // 已收收益
        backPlanPayIncomeTv.setText(backPlanEntity.getReceivedInterest());
        // 待收本金
        backPlanToPayTv.setText(backPlanEntity.getCollectPrincipal());
        // 待收收益
        backPlanToRevenueTv.setText(backPlanEntity.getExpectProfit());
        // 退出日期
        backPlanBackDayTv.setText(backPlanEntity.getLastRepayDate());
        Drawable nav_up = ContextCompat.getDrawable(mContext, R.drawable.tips_notes);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        if (TextUtils.equals(backPlanEntity.getIsFlow(), "2")) {
            backPlanBackDayT.setCompoundDrawables(null, null, nav_up, null);
        } else {
            backPlanBackDayT.setCompoundDrawables(null, null, null, null);
        }
        // 退出日期弹出框 月增利专有
        if (TextUtils.equals(backPlanEntity.getIsFlow(), "2"))
            backPlanBackDayT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singlton.getInstance(PopWindowManager.class).
                            showLeftOnebtnDialog(mContext, "退出日期", getString(R.string.back_to_plan), "我知道了",
                                    new OnDialogClickListener() {
                                        @Override
                                        public void onClick(int id, View v) {
                                            if (id == 2) {

                                            }
                                        }
                                    });
                }
            });
        // 回款方式
        backPlanWayTv.setText("回款方式：" + backPlanEntity.getRepayMode());
        // 支付金额(债权)
        if (NumberParserUtil.parserDouble(backPlanEntity.getDebtInvestAmount(), 0) > 0)
            backPlanPayAmountTv.setVisibility(View.VISIBLE);
        else
            backPlanPayAmountTv.setVisibility(View.GONE);
        backPlanPayAmountTv.setText("（支付金额：" + backPlanEntity.getDebtInvestAmount() + "元）");

    }

/*    *//**

     * 利率显示
     *
     * @param backPlanEntity
     * @return
     *//*
    private String showRate(BackPlanEntity backPlanEntity) {
        if (TextUtils.equals(backPlanEntity.getData().getIsFlow(), "2")) {
            if (!TextUtils.isEmpty(backPlanEntity.getData().getIncreaseRate()) && NumberParserUtil.parserDouble(backPlanEntity.getData().getIncreaseRate(), 0) > 0) {
                return "（" + backPlanEntity.getData().getFlowMinAnnualRate() + "%~" + backPlanEntity.getData().getFlowMaxAnnualRate()
                        + "%）+" + backPlanEntity.getData().getIncreaseRate() + "%";
            } else {
                return backPlanEntity.getData().getFlowMinAnnualRate() + "%~" + backPlanEntity.getData().getFlowMaxAnnualRate() + "%";
            }
        } else {
            if (!TextUtils.isEmpty(backPlanEntity.getData().getIncreaseRate()) && NumberParserUtil.parserDouble(backPlanEntity.getData().getIncreaseRate(), 0) > 0) {
                return backPlanEntity.getData().getAnnualRate() + "%+" + backPlanEntity.getData().getIncreaseRate() + "%";
            } else {
                return backPlanEntity.getData().getAnnualRate() + "%";
            }
        }
    }

    */

    /**
     * 利率显示
     *
     * @param backPlanEntity
     * @return
     */
    private String showRate(BackPlanEntity backPlanEntity) {
        if (TextUtils.equals(backPlanEntity.getIsFlow(), "2")) {
            if (!TextUtils.isEmpty(backPlanEntity.getIncreaseRate()) && NumberParserUtil.parserDouble(backPlanEntity.getIncreaseRate(), 0) > 0) {
                return "（" + backPlanEntity.getFlowMinAnnualRate() + "%~" + backPlanEntity.getFlowMaxAnnualRate()
                        + "%）+" + backPlanEntity.getIncreaseRate() + "%";
            } else {
                return backPlanEntity.getFlowMinAnnualRate() + "%~" + backPlanEntity.getFlowMaxAnnualRate() + "%";
            }
        } else {
            if (!TextUtils.isEmpty(backPlanEntity.getIncreaseRate()) && NumberParserUtil.parserDouble(backPlanEntity.getIncreaseRate(), 0) > 0) {
                return backPlanEntity.getAnnualRate() + "%+" + backPlanEntity.getIncreaseRate() + "%";
            } else {
                return backPlanEntity.getAnnualRate() + "%";
            }
        }
    }

    /**
     * 日期是否是月增利
     *
     * @param backPlanEntity
     * @return
     */
    private String getDeadLineType(BackPlanEntity backPlanEntity) {

        if (TextUtils.equals(backPlanEntity.getIsFlowDead(), "2")) {
            if (TextUtils.equals(backPlanEntity.getIsOutTime(), "1")) {// 是否设置退出日期：0 未设置 1：已设置
                return backPlanEntity.getActualDays();
            } else {
                return backPlanEntity.getCollectLineMinValue() + "~" + backPlanEntity.getCollectLineMaxValue();
            }

        } else {
            return backPlanEntity.getDeadLineValue();
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (xlist != null) {
                    xlist.setRefreshTime(DateUtil.getRefreshTime());
                    getIdCard(investId);
                    xlist.stopRefresh();
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);

    }
}
