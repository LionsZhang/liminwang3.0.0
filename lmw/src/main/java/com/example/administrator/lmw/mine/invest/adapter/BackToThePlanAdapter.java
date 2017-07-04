package com.example.administrator.lmw.mine.invest.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.mine.invest.entity.BackPlanBean;

/**
 * 回款计划适配器
 * <p>
 * Created by Administrator on 2017/3/14.
 */

public class BackToThePlanAdapter extends CommonBaseAdapter<BackPlanBean, BackToThePlanAdapter.ViewHolder> {

    public BackToThePlanAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new BackToThePlanAdapter.ViewHolder(mContext, parent, R.layout.back_plan_item, position);
        }
        return (BackToThePlanAdapter.ViewHolder) convertView.getTag();
    }

    @Override
    public void convert(ViewHolder holder, BackPlanBean item, int position) {
        if (item == null)
            return;
        // 期数 日期
        holder.backPlanDatasTv.setText(item.getRepayPeriod() + " " + item.getRepayDate());
        //  还款状态（0-默认未还款 1-已还款)
        if (!TextUtils.isEmpty(item.getRepayStatus()) && item.getRepayStatus().equals("1")) {
            holder.backPlanStatusTv.setText("已还款");
            holder.backPlanStatusTv.setBackgroundResource(R.color.bg_bule);
        } else {
            holder.backPlanStatusTv.setText("未还款");
            holder.backPlanStatusTv.setBackgroundResource(R.color.yellow);
        }
        // 应收本金
        holder.backPlanReceivableTv.setText(item.getReceivedPrincipal());
        // 应收收益
        holder.backPlanIncomeTv.setText(item.getReceivedInterest());
        // 应收总额
        holder.backPlanTotalTv.setText(item.getStillSum());

    }

    final public static class ViewHolder extends CommonViewHolder {
        TextView backPlanDatasTv;       // 期数 日期
        TextView backPlanStatusTv;      // 是否已还款
        TextView backPlanReceivableTv;  // 应收本金
        TextView backPlanIncomeTv;      // 应收收益
        TextView backPlanTotalTv;       // 应收总额

        public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            super(context, parent, layoutId, position);
            backPlanDatasTv = getView(R.id.back_plan_datas_tv);
            backPlanStatusTv = getView(R.id.back_plan_status_tv);
            backPlanReceivableTv = getView(R.id.back_plan_receivable_tv);
            backPlanIncomeTv = getView(R.id.back_plan_income_tv);
            backPlanTotalTv = getView(R.id.back_plan_total_tv);
        }
    }
}
