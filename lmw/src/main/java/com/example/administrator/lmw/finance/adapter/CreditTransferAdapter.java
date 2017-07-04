package com.example.administrator.lmw.finance.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.finance.entity.CreditDatasBean;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.view.NumberProgressBar;

/**
 * 债权转让 适配器
 * <p/>
 * Created by Administrator on 2016/8/24 0024.
 */
public class CreditTransferAdapter extends CommonBaseAdapter<CreditDatasBean, CommonViewHolder> {

    public CreditTransferAdapter(Context context) {
        super(context);
    }


    @Override
    public CommonViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        return CommonViewHolder.get(mContext, convertView, parent, R.layout.item_financial_product, position);
    }

    @Override
    public void convert(CommonViewHolder holder, CreditDatasBean item, int position) {
        holder.getView(R.id.increase_lin).setVisibility(View.GONE);
        holder.getView(R.id.yields_view).setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(item.getRepayMode()))
            holder.setText(R.id.investment_time_tv, item.getRepayMode());
        if (!TextUtils.isEmpty(item.getDebtTitle()))
            holder.setText(R.id.financial_title_tv, item.getDebtTitle());// 标题
        holder.getView(R.id.title_tv_bg_one).setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.getRepayMode()))
            holder.setText(R.id.title_tv_bg_one, item.getRepayMode());// 还款方式
        if (!TextUtils.isEmpty(item.getDeadLineValue()))
            holder.setText(R.id.investment_tv, item.getDeadLineValue());// 投资期限
        if (!TextUtils.isEmpty(item.getDeadLineType()))
            holder.setText(R.id.investment_tv_p, item.getDeadLineType());// 投资期限类型

        showProgress(holder, item);// 进度条
        showTransferRate(holder, item);// 年化利率

        // 剩余金额 或者 转让本金
        if (!TextUtils.isEmpty(item.getStatus()))
            if (item.getStatus().equals("0")) {
                if (!TextUtils.isEmpty(item.getHasDebtInvestAmt())) {
                    holder.setText(R.id.remaining_money_tv, BigDecemalCalculateUtil.subtractToString(item.getDebtPrincipalAmt(), item.getHasDebtInvestAmt(), 2));
                    holder.setText(R.id.remaining_text_tv, "剩余");
                }
            } else if (item.getStatus().equals("1")) {
                if (!TextUtils.isEmpty(item.getDebtPrincipalAmt()))
                    holder.setText(R.id.remaining_money_tv, item.getDebtPrincipalAmt());
                holder.setText(R.id.remaining_text_tv, "转让本金");
            }
        /**
         * 0.转让中，1.转让成功,2.取消转让,3.时间过期,4.转让失败
         */
        if (!TextUtils.isEmpty(item.getStatus()))
            if (item.getStatus().equals("0")) {
                findBlueView(holder);
            } else if (item.getStatus().equals("1")) {
                findGredView(holder);
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_zrcg);
            } else if (item.getStatus().equals("2")) {
                findGredView(holder);
                holder.setImageResource(R.id.buys_succee, R.drawable.finance_zq_qxzr);
            } else if (item.getStatus().equals("3")) {
                findGredView(holder);
                holder.setImageResource(R.id.buys_succee, R.drawable.finance_zq_sjgq);
            } else if (item.getStatus().equals("4")) {
                findGredView(holder);
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_zrsb);
            }
    }

    /**
     * 年华收益
     *
     * @param holder
     * @param item
     */
    private void showTransferRate(CommonViewHolder holder, CreditDatasBean item) {
        if (!TextUtils.isEmpty(item.getTransferRate())) {
            int j = item.getTransferRate().indexOf(".");
            holder.setText(R.id.yields_tv, item.getTransferRate().substring(0, j));
            holder.setText(R.id.yields_tv_per, item.getTransferRate().substring(j, item.getTransferRate().length()) + "%");
        }

    }

    /**
     * 进度条
     *
     * @param holder
     * @param item
     */
    private void showProgress(CommonViewHolder holder, CreditDatasBean item) {
        NumberProgressBar progresssRb = holder.getView(R.id.number_progresss_bar);
        // 进度条  等发版替换字段 debtPrincipalPriceAmt
        float progress = 0;
        if (!TextUtils.isEmpty(item.getDebtPrincipalAmt()) && !TextUtils.isEmpty(item.getHasDebtInvestAmt())) {
            progress = BigDecemalCalculateUtil.divide(item.getDebtPrincipalAmt(), item.getHasDebtInvestAmt(), 1);
        }
        progresssRb.setProgress(progress);
    }

    /**
     * 转让成功 取消转让 时间过期 转让失败 界面显示
     *
     * @param holder
     */
    private void findGredView(CommonViewHolder holder) {
        holder.getView(R.id.buys_succee).setVisibility(View.VISIBLE);
        holder.setTextColor(R.id.yields_tv, R.color.finance_pro);
        holder.setTextColor(R.id.yields_tv_per, R.color.finance_pro);
        holder.setTextColor(R.id.investment_tv, R.color.finance_pro);
        holder.setTextColor(R.id.investment_tv_p, R.color.select_list_earnings);
        holder.setTextColor(R.id.remaining_money_tv, R.color.finance_pro);
        holder.setTextColor(R.id.remaining_text_tv, R.color.select_list_earnings);
        NumberProgressBar progresssRb = holder.getView(R.id.number_progresss_bar);
        progresssRb.setReachedBarColor(ContextCompat.getColor(mContext, R.color.progress_bg_gray));
    }

    /**
     * 立即购买 界面显示
     *
     * @param holder
     */
    private void findBlueView(CommonViewHolder holder) {
        holder.getView(R.id.buys_succee).setVisibility(View.GONE);
        holder.setTextColor(R.id.yields_tv, R.color.select_list_detail);
        holder.setTextColor(R.id.yields_tv_per, R.color.select_list_detail);
        holder.setTextColor(R.id.investment_tv, R.color.announcement_tv);
        holder.setTextColor(R.id.investment_tv_p, R.color.select_list_earnings);
        holder.setTextColor(R.id.remaining_money_tv, R.color.announcement_tv);
        holder.setTextColor(R.id.remaining_text_tv, R.color.select_list_earnings);
        NumberProgressBar progresssRb = holder.getView(R.id.number_progresss_bar);
        progresssRb.setReachedBarColor(ContextCompat.getColor(mContext, R.color.text_blue));
    }
}
