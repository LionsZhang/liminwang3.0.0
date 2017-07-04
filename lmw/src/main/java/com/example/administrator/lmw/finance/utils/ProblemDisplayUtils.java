package com.example.administrator.lmw.finance.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.finance.entity.DetailFragmentEntity;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;

import java.text.DecimalFormat;

/**
 * 标的投资 赋值显示
 * <p>
 * Created by Administrator on 2017/4/19.
 */

public class ProblemDisplayUtils {

    private DecimalFormat dfZero = new DecimalFormat("##0");

    /**
     * 标的购买 利率显示 投资期限显示
     *
     * @param entity                数据类
     * @param problemBuyLin         加息显示总布局view
     * @param problemBuyEarningsTv  最低利率整数view
     * @param problemBuyEarningsTvX 最低利率小数view
     * @param problemBuyYieldsTv    最高利率整数view
     * @param problemBuyYieldsTvPer 最高利率整数view
     * @param problemBuyHeadTv      加息显示view
     * @param problemBuyDaysTv      投资期限日期view
     * @param problemBuyDaysUnitTv  投资期限单位view
     */
    public void showAnnualRate(DetailFragmentEntity entity, LinearLayout problemBuyLin, TextView problemBuyEarningsTv,
                               TextView problemBuyEarningsTvX, TextView problemBuyYieldsTv, TextView problemBuyYieldsTvPer,
                               TextView problemBuyHeadTv, TextView problemBuyDaysTv, TextView problemBuyDaysUnitTv) {
        if (TextUtils.equals(entity.getData().getIsFlow(), "2")) {// 是否浮动利率(1=固定利率|2=浮动利率)
            problemBuyLin.setVisibility(View.VISIBLE);
            int flowMinAnnualRate = entity.getData().getFlowMinAnnualRate().indexOf(".");
            problemBuyEarningsTv.setText(entity.getData().getFlowMinAnnualRate().substring(0, flowMinAnnualRate));// 浮动最小利率
            problemBuyEarningsTvX.setText(entity.getData().getFlowMinAnnualRate().substring(flowMinAnnualRate, entity.getData().getFlowMinAnnualRate().length()));
            int flowMaxAnnualRate = entity.getData().getFlowMaxAnnualRate().indexOf(".");
            problemBuyYieldsTv.setText("~" + entity.getData().getFlowMaxAnnualRate().substring(0, flowMaxAnnualRate));// 浮动最大利率
            problemBuyYieldsTvPer.setText(entity.getData().getFlowMaxAnnualRate().substring(flowMaxAnnualRate, entity.getData().getFlowMaxAnnualRate().length()) + "%");
            if (BigDecemalCalculateUtil.compareTo(entity.getData().getIncreaseRate(), "0") == 0) {
                problemBuyHeadTv.setVisibility(View.GONE);
            } else {
                problemBuyHeadTv.setVisibility(View.VISIBLE);
                problemBuyHeadTv.setText(entity.getData().getIncreaseRate() + "%");
            }
        } else {
            problemBuyLin.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(entity.getData().getAnnualRate())) {
                int rale = entity.getData().getAnnualRate().indexOf(".");
                problemBuyEarningsTv.setText(entity.getData().getAnnualRate().substring(0, rale));// 利率  increaseRate
                if (!TextUtils.isEmpty(entity.getData().getIncreaseRate()))
                    if (Double.parseDouble(entity.getData().getIncreaseRate()) == 0) {
                        problemBuyEarningsTvX.setText(entity.getData().getAnnualRate().substring(rale, entity.getData().getAnnualRate().length()));
                    } else {
                        problemBuyEarningsTvX.setText(entity.getData().getAnnualRate().substring(rale, entity.getData().getAnnualRate().length()) + "%" + entity.getData().getIncreaseRate());
                    }
            }
        }
        if (TextUtils.equals(entity.getData().getIsFlowDead(), "2")) {// 是否浮动期限(1=固定期限|2=浮动期限)
            if (!TextUtils.isEmpty(entity.getData().getMinTenderedMoney()) && !TextUtils.isEmpty(entity.getData().getMaxTenderedMoney()))
                problemBuyDaysTv.setText(entity.getData().getCollectLineMinValue() + "~" + entity.getData().getCollectLineMaxValue());// 投资期限 时间
        } else {
            if (!TextUtils.isEmpty(entity.getData().getDeadLineValue()))
                problemBuyDaysTv.setText(entity.getData().getDeadLineValue());// 投资期限 时间
        }
        if (!TextUtils.isEmpty(entity.getData().getDeadLineType()))
            problemBuyDaysUnitTv.setText(entity.getData().getDeadLineType());// 投资期限 单位
    }

    /**
     * 标的投资
     *
     * @param entity                数据类
     * @param problemBuyContinueRel 续投显示布局view
     * @param problemBuyTitleTv     标题
     * @param problemBuyMoneysTv    剩余金额
     * @param problemBuyMoneyEt     edittext提示显示
     */
    public void showProductFindView(DetailFragmentEntity entity, RelativeLayout problemBuyContinueRel, TextView problemBuyTitleTv,
                                TextView problemBuyMoneysTv, TextView problemBuyMoneyEt) {
        // 是否显示续投方式  新手标、债转标不支持续投，购买时不显示续投选项
        if (entity.getData().getBorrowType().equals("7")) {
            problemBuyContinueRel.setVisibility(View.GONE);
        } else {
            problemBuyContinueRel.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(entity.getData().getBorrowTitle()))
            problemBuyTitleTv.setText(entity.getData().getBorrowTitle());// 标题
        if (!TextUtils.isEmpty(entity.getData().getRemaMoney()))
            problemBuyMoneysTv.setText(entity.getData().getRemaMoney());// 剩余金额
        if (!TextUtils.isEmpty(entity.getData().getRemaMoney()) && !TextUtils.isEmpty(entity.getData().getMinTenderedMoney()))
            if (Double.parseDouble(entity.getData().getRemaMoney()) < (Double.parseDouble(entity.getData().getMinTenderedMoney()) * 2)
                    && Double.parseDouble(entity.getData().getRemaMoney()) > 0) {
                problemBuyMoneyEt.setHint(entity.getData().getRemaMoney() + "元需一同购买");
            } else {
                if (!TextUtils.isEmpty(entity.getData().getSingleMaxTenderedMoney()) && !TextUtils.isEmpty(entity.getData().getIncreaseTenderedMoney()))
                    if (Double.parseDouble(entity.getData().getSingleMaxTenderedMoney()) <= 0) {
                        problemBuyMoneyEt.setHint("此标个人限额已投完,不可再投");
                    } else if (entity.getData().getMaxTenderedMoney().equals(entity.getData().getBuyTotalAmount())) {
                        problemBuyMoneyEt.setHint(dfZero.format(Double.parseDouble(entity.getData().getMinTenderedMoney())) + "起投," + dfZero.format(Double.parseDouble(entity.getData().getIncreaseTenderedMoney())) + "元递增,认购无上限");
                    } else {
                        problemBuyMoneyEt.setHint(dfZero.format(Double.parseDouble(entity.getData().getMinTenderedMoney())) + "起投," + dfZero.format(Double.parseDouble(entity.getData().getIncreaseTenderedMoney())) + "元递增,认购上限"
                                + dfZero.format(Double.parseDouble(entity.getData().getSingleMaxTenderedMoney())) + "元");
                    }
            }
    }
}
