package com.example.administrator.lmw.mine.account.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.mine.account.entity.AccountDatasBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class CompleteAdapter extends CommonAdapter {

    public List<AccountDatasBean> accountDatasBeen;

    public CompleteAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.accountDatasBeen = mDatas;
    }

    public void addComplete(List mDatas) {
        accountDatasBeen.addAll(mDatas);
    }

    @Override
    public void convert(CommonViewHolder holder, Object item, int position) {
        if (accountDatasBeen.size() == 1) {
            holder.getView(R.id.bule_view_up).setVisibility(View.GONE);
            holder.getView(R.id.bule_view_dwon).setVisibility(View.GONE);
        } else {
            if (position == 0) {
                holder.getView(R.id.bule_view_up).setVisibility(View.GONE);
                holder.getView(R.id.bule_view_dwon).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.bule_view_up).setVisibility(View.VISIBLE);
                holder.getView(R.id.bule_view_dwon).setVisibility(View.VISIBLE);
            }
            if (position == (accountDatasBeen.size() - 1)) {
                holder.getView(R.id.bule_view_dwon).setVisibility(View.GONE);
            }
        }

        // 标题
        holder.setText(R.id.account_title_tv, accountDatasBeen.get(position).getFundTypeName());
        // 金额
        holder.setText(R.id.account_money_tv, accountDatasBeen.get(position).getHandleAmount());
        // 金额颜色
        if (Double.parseDouble(accountDatasBeen.get(position).getHandleAmount()) > 0) {
            holder.setTextColor(R.id.account_money_tv, R.color.select_list_detail);
        } else {
            holder.setTextColor(R.id.account_money_tv, R.color.acc_green);
        }
        // 时间
        holder.setText(R.id.account_times_tv, accountDatasBeen.get(position).getCreateTime());
        // 剩余金额
        holder.setText(R.id.account_surplus_moneys_tv, accountDatasBeen.get(position).getUsableAmount());
        // 备注
        holder.setText(R.id.account_remark_tv, accountDatasBeen.get(position).getRemark());

    }
}
