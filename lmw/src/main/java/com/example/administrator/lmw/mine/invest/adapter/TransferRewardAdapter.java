package com.example.administrator.lmw.mine.invest.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.finance.utils.TextViewUtils;
import com.example.administrator.lmw.mine.invest.entity.TranferRewardBean;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;

/**
 * 待结卡券适配器
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/16 13:59
 **/
public class TransferRewardAdapter extends CommonBaseAdapter<TranferRewardBean, TransferRewardAdapter.ViewHolder> {

    private String moneyUnit;

    public TransferRewardAdapter(Context context) {
        super(context);
        moneyUnit = mContext.getResources().getString(R.string.txt_yuan);
    }


    @Override
    public ViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(mContext, parent, R.layout.item_transfer_reward, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    @Override
    public void convert(final ViewHolder holder, final TranferRewardBean item, final int position) {

        if (item == null) {
            return;
        }

        //标的
        holder.tv_name.setText(item.getBorrowTitle());
        //回款日
        holder.tv_return_date.setText(item.getRepayDate());
        //使用时间
        holder.tv_use_date.setText(item.getInvestTime());
        //天数
        holder.tv_day.setText(String.format("%s%s", item.getMaxActiveDay(), getString(R.string.txt_day)));
        //卡券类型，加息券或理财金
        setCouponType(holder, item);
        //收益金额
        showAmountFmt(holder.tv_earnings, item.getStillInterest());

    }

    /**
     * 类型3加息券;4理财金
     *
     * @param holder
     * @param item
     */
    private void setCouponType(ViewHolder holder, TranferRewardBean item) {
        if (item.getCouponType() == 3) {
            holder.tv_number.setText(String.format("%s%s%s", getString(R.string.txt_raise_coupon), BigDecemalCalculateUtil.formatValue(item.getCouponAmount(), 0, 2), "%"));
            showAmountFmt(holder.tv_principal, item.getRealAmount());
            holder.tv_principal_text.setText(getString(R.string.txt_jxbj));
            holder.tv_earnings_txt.setText(getString(R.string.txt_jxsy));
        } else if (item.getCouponType() == 4) {
            holder.tv_number.setText(String.format("%s%s%s", getString(R.string.txt_financial_gold), BigDecemalCalculateUtil.formatValue(item.getCouponAmount(), 0, 0), moneyUnit));
            String text = TextUtils.isEmpty(item.getIncreaseRate()) || TextUtils.equals("0.00", BigDecemalCalculateUtil.formatValue(item.getIncreaseRate(), 0, 2)) ?
                    String.format("%s%s", BigDecemalCalculateUtil.formatValue(item.getAnnualRate(), 0, 2), "%") :
                    String.format("%s%s+%s%s", BigDecemalCalculateUtil.formatValue(item.getAnnualRate(), 0, 2), "%", item.getIncreaseRate(), "%");
            holder.tv_principal.setText(text);
            holder.tv_principal_text.setText(getString(R.string.txt_lcll));
            holder.tv_earnings_txt.setText(getString(R.string.txt_lcsy));
        }
    }

    /**
     * 显示元格式
     *
     * @param textView
     * @param amount
     */
    private void showAmountFmt(TextView textView, String amount) {
        if (!TextUtils.isEmpty(amount)) {
            String allStr = String.format(mContext.getResources().getString(R.string.txt_transfer_money), amount);
            int color = ContextCompat.getColor(mContext, R.color.select_list_earnings);
            SpannableStringBuilder txtMoney = TextViewUtils.getSizeSpannBuilder(mContext, color, 11, allStr, moneyUnit);
            textView.setText(txtMoney);
        }
    }


    final public static class ViewHolder extends CommonViewHolder {
        TextView tv_name;//标的名称
        TextView tv_number;//加息券利率或者理财金额度
        TextView tv_day;//天数
        TextView tv_use_date;//使用日期
        TextView tv_principal;//本金或者理财利率
        TextView tv_principal_text;//计息本金或理财利率
        TextView tv_earnings;//加息或理财收益
        TextView tv_return_date;//回款日期
        TextView tv_earnings_txt;//加息收益或理财收益

        public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            super(context, parent, layoutId, position);
            tv_name = getView(R.id.tv_name);
            tv_principal = getView(R.id.tv_principal);
            tv_principal_text = getView(R.id.tv_principal_text);
            tv_earnings = getView(R.id.tv_earnings);
            tv_number = getView(R.id.tv_number);
            tv_return_date = getView(R.id.tv_return_date);
            tv_day = getView(R.id.tv_day);
            tv_use_date = getView(R.id.tv_use_date);
            tv_earnings_txt = getView(R.id.tv_earnings_txt);

        }
    }

}
