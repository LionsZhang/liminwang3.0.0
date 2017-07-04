package com.example.administrator.lmw.finance.adapter;

import android.content.Context;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.finance.entity.CashCouponsDatasBean;

import java.util.List;

/**
 * 现金券适配器
 * <p/>
 * Created by Administrator on 2016/9/6 0006.
 */
public class DialogAdapter extends CommonAdapter {

    private List<CashCouponsDatasBean> cashCouponsDatasBeen;
    private int items;

    public DialogAdapter(Context context, List mDatas, int items, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.cashCouponsDatasBeen = mDatas;
        this.items = items;
    }

    @Override
    public void convert(CommonViewHolder holder, Object item, int position) {
        holder.setText(R.id.dialog_rules, cashCouponsDatasBeen.get(position).getUseRule());// 使用规则
        holder.setText(R.id.dialog_scope, cashCouponsDatasBeen.get(position).getUseScope());// 使用范围
        holder.setText(R.id.dialog_source, cashCouponsDatasBeen.get(position).getMinInvestDeadline());// 投资期限
        holder.setText(R.id.dialog_times, cashCouponsDatasBeen.get(position).getEndTime().substring(0,10));// 有效日期
        holder.setText(R.id.dialog_moneys, cashCouponsDatasBeen.get(position).getCouponAmount());// 金额
        // TODO 做判断
        if (position == items) {
            holder.setImageResource(R.id.dialog_select, R.drawable.ic_pop_quan_sel);
        } else {
            holder.setImageResource(R.id.dialog_select, R.drawable.ic_pop_quan_normal);
        }

    }
}
