package com.example.administrator.lmw.mine.invest.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.mine.invest.activity.BackToThePlanActivity;
import com.example.administrator.lmw.mine.invest.activity.ContinueSetActivity;
import com.example.administrator.lmw.mine.invest.entity.InvestmentTransferDatasBean;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.Singlton;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class InvestmentTransferAdapter extends CommonAdapter {


    private List<InvestmentTransferDatasBean> datasBeen;

    private InvestmentListener listener;


    private boolean falg = false;

    private int subType;

    private int i = -1;

    public InvestmentTransferAdapter(Context context, List mDatas, int itemLayoutId, InvestmentListener listener, int subType) {
        super(context, mDatas, itemLayoutId);
        this.datasBeen = mDatas;
        this.listener = listener;
        this.subType = subType;
    }

    public void addInvestmentTransfer(List mDatas) {
        datasBeen.addAll(mDatas);
    }

    public void InvestmentSelect(boolean falg) {
        this.falg = falg;
    }

    public void InvestmentNo(int i) {
        this.i = i;
    }

    @Override
    public void convert(CommonViewHolder holder, Object item, final int position) {
        // 标题
        holder.setText(R.id.item_titles, datasBeen.get(position).getBorrowTitle());
        // 年华
        holder.setText(R.id.item_percent, showRate(datasBeen, position));
        // 投资时间
        holder.setText(R.id.item_date, datasBeen.get(position).getInvestTime());
        // 期数
        if (datasBeen.get(position).getDeadLineType().equals("1")) {
            holder.setText(R.id.item_day, showDays(datasBeen, position) + "天");
        } else if (datasBeen.get(position).getDeadLineType().equals("2")) {
            holder.setText(R.id.item_day, showDays(datasBeen, position) + "个月");
        } else if (datasBeen.get(position).getDeadLineType().equals("3")) {
            holder.setText(R.id.item_day, showDays(datasBeen, position) + "年");
        }
        // 待收本金
        holder.setText(R.id.item_principal, datasBeen.get(position).getStillPrincipal());
        // 预估总收益
        holder.setText(R.id.item_income, datasBeen.get(position).getExpectProfit());
        if (!TextUtils.isEmpty(datasBeen.get(position).getStillInterest())) {
            holder.getView(R.id.item_income_unit).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.item_income_unit).setVisibility(View.GONE);
        }
        // 回款方式
        holder.setText(R.id.item_back_way_tv, datasBeen.get(position).getRepayMode());
        // 起息日
        holder.setText(R.id.item_value, datasBeen.get(position).getCulInterestStartTime());
        // 到息日
        holder.setText(R.id.item_sabbath, datasBeen.get(position).getCulInterestEndTime());
        // 回款续投方式
        holder.setText(R.id.continue_set, datasBeen.get(position).getDisabled());
        // 协议点击
        holder.getView(R.id.item_protocol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onclick(position, datasBeen.get(position).getUrl());
            }
        });
        // 回款计划点击
        holder.getView(R.id.back_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subType == 1 && datasBeen.get(position).getIsInterest().equals("1")) {
                    // 募集中 无法点击
                } else {
                    Intent intent = new Intent(mContext, BackToThePlanActivity.class);
                    intent.putExtra("investId", datasBeen.get(position).getInvestId());
                    mContext.startActivity(intent);
                }

            }
        });
        // 续投设置点击
        holder.getView(R.id.continue_set_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(datasBeen.get(position).getIsContinue(), "1")) {// 是否显示续投按钮 0不显示1显示
                    // 判断是否开通存管
                    if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext)) {
                        Intent intent = new Intent(mContext, ContinueSetActivity.class);
                        intent.putExtra("investId", datasBeen.get(position).getInvestId());
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        // 续投设置按钮颜色设置
        if (datasBeen.get(position).getIsContinue().equals("1")) {// 是否显示续投按钮 0不显示1显示
            holder.setTextColor(R.id.continue_set_tv, R.color.bule_press);
        } else {
            holder.setTextColor(R.id.continue_set_tv, R.color.gray_text);
        }
        // 回款计划按钮颜色设置
        if (subType == 1 && datasBeen.get(position).getIsInterest().equals("1")) {
            holder.setTextColor(R.id.back_plan, R.color.gray_text);
        } else {
            holder.setTextColor(R.id.back_plan, R.color.bule_press);
        }
        /**
         * 图片
         */
        switch (subType) {
            case 1:
                if (datasBeen.get(position).getIsInterest().equals("1")) {
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_tzz);
                    holder.setText(R.id.item_item_principal_tv, "投资本金");
                } else if (datasBeen.get(position).getIsInterest().equals("2")) {
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_jxz);
                    holder.setText(R.id.item_item_principal_tv, "待收本金");
                }
                holder.getView(R.id.item_periods).setVisibility(View.GONE);
                holder.setText(R.id.item_income_tv, "预期收益");
                break;
            case 2:
                if (datasBeen.get(position).getIsInterest().equals("1")) {
                    holder.setText(R.id.item_income_tv, "预期收益");
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_kzr);
                } else if (datasBeen.get(position).getIsInterest().equals("2")) {
                    holder.setText(R.id.item_income_tv, "预期收益");
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_zrz);
                } else if (datasBeen.get(position).getIsInterest().equals("3")) {
                    holder.setText(R.id.item_income_tv, "预期收益");
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_bfzr);
                } else if (datasBeen.get(position).getIsInterest().equals("4")) {
                    holder.setText(R.id.item_income_tv, "预期收益");
                    holder.setImageResource(R.id.item_status, R.drawable.finance_succee);
                } else if (datasBeen.get(position).getIsInterest().equals("5")) {
                    holder.setText(R.id.item_income_tv, "预期收益");
                    holder.setImageResource(R.id.item_status, R.drawable.finance_zq_zrsb);
                }
                holder.getView(R.id.item_periods).setVisibility(View.GONE);
                holder.setText(R.id.item_item_principal_tv, "待收本金");
                break;
            case 3:
                if (datasBeen.get(position).getIsInterest().equals("1")) {
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_tzhk);
                } else if (datasBeen.get(position).getIsInterest().equals("2")) {
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_zrhk);
                } else if (datasBeen.get(position).getIsInterest().equals("3")) {
                    holder.setImageResource(R.id.item_status, R.drawable.img_tz_lbhk);
                }
                holder.getView(R.id.item_periods).setVisibility(View.VISIBLE);
                holder.setText(R.id.item_item_principal_tv, "已收本金");
                holder.setText(R.id.item_income_tv, "已收总收益");
                break;
        }
        if (falg) {
            holder.getView(R.id.item_select).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.item_select).setVisibility(View.GONE);
        }
        if (i == position) {
            holder.setImageResource(R.id.item_select, R.drawable.con_lc_sel);
        } else {
            holder.setImageResource(R.id.item_select, R.drawable.con_lc_normal);
        }
    }

    /**
     * 年华显示
     *
     * @param datasBeen
     * @param position
     * @return
     */
    private String showRate(List<InvestmentTransferDatasBean> datasBeen, int position) {
        if (TextUtils.equals(datasBeen.get(position).getIsFlow(), "2")) {
            if (datasBeen.get(position).getIncreaseRate().equals("")) {
                return datasBeen.get(position).getFlowMinAnnualRate() + "%~" + datasBeen.get(position).getFlowMaxAnnualRate() + "%";
            } else {
                if (NumberParserUtil.parserDouble(datasBeen.get(position).getIncreaseRate(), 0) <= 0) {
                    return datasBeen.get(position).getFlowMinAnnualRate() + "%~" + datasBeen.get(position).getFlowMaxAnnualRate() + "%";
                } else {
                    return "（" + datasBeen.get(position).getFlowMinAnnualRate() + "%~" + datasBeen.get(position).getFlowMaxAnnualRate() +
                            "%）+" + datasBeen.get(position).getIncreaseRate() + "%";
                }
            }
        } else {
            if (datasBeen.get(position).getIncreaseRate().equals("")) {
                return datasBeen.get(position).getAnnualRate() + "%";
            } else {
                if (NumberParserUtil.parserDouble(datasBeen.get(position).getIncreaseRate(), 0) <= 0) {
                    return datasBeen.get(position).getAnnualRate() + "%";
                } else {
                    return datasBeen.get(position).getAnnualRate() + "%+" + datasBeen.get(position).getIncreaseRate() + "%";
                }
            }
        }
    }

    /**
     * 日期显示
     *
     * @param datasBeen
     * @param position
     * @return
     */
    private String showDays(List<InvestmentTransferDatasBean> datasBeen, int position) {
        if (TextUtils.equals(datasBeen.get(position).getIsFlowDead(), "2")) {
            if (TextUtils.equals(datasBeen.get(position).getIsOutTime(), "1")) {// 是否设置退出日期：0 未设置 1：已设置
                return datasBeen.get(position).getActualDays();
            } else {
                return datasBeen.get(position).getCollectLineMinValue() + "~" + datasBeen.get(position).getCollectLineMaxValue();
            }

        } else {
            return datasBeen.get(position).getDeadLineValue();
        }
    }


    public interface InvestmentListener {
        void onclick(int position, String url);
    }

}
