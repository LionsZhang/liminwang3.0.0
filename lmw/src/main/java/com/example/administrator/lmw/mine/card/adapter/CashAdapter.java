package com.example.administrator.lmw.mine.card.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.finance.utils.TextViewUtils;
import com.example.administrator.lmw.mine.card.entity.CashDatasBean;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;

/**
 * 卡券适配器
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/16 15:39
 **/
public class CashAdapter extends CommonBaseAdapter<CashDatasBean, CashAdapter.ViewHolder> {


    public CashAdapter(Context context) {
        super(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(mContext, parent, R.layout.item_cash_card, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    @Override
    public void convert(final ViewHolder holder, final CashDatasBean item, final int position) {

        if (item == null) {
            return;
        }

        //现金券状态
        setCardStatus(holder, item.getUseStatus());
        //设置卡券类型
        setCouponType(holder, item);

        //设置日期  (有效日期-未使用，使用日期-已使用，过期日期-已过期)
        showDate(holder, item);
        //查看更多
        showMoreInfo(holder, item);

        //展开或收起
        final boolean isOpen = item.isOpen();
        setOpenStatus(holder, isOpen);
        holder.tv_down_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = !isOpen;
                item.setOpen(flag);
                notifyDataSetChanged();
                setOpenStatus(holder, flag);
            }
        });


    }


    /**
     * 显示日期格式 和 更多信息
     * 使用状态(1可使用;2:使用中;3:已使用;5:已过期)
     *
     * @param holder
     * @param item
     */
    private void showDate(ViewHolder holder, CashDatasBean item) {
        switch (item.getUseStatus()) {
            case 1:
                //有效日期、
                holder.tv_validity_time.setText(String.format(getString(R.string.txt_validity_time), item.getEffectiveDate()));
                break;
            case 2:
            case 3:
                //使用日期
                holder.tv_validity_time.setText(String.format(getString(R.string.txt_useDate), item.getUseDate()));
                break;
            case 5:
                //过期日期
                holder.tv_validity_time.setText(String.format(getString(R.string.txt_overDate), item.getEndDate()));
                break;

            default:
                break;
        }
    }

    /**
     * 显示日期格式 和 更多信息
     * 使用状态(1可使用;2:使用中;3:已使用;5:已过期)
     *
     * @param holder
     * @param item
     */
    private void showMoreInfo(ViewHolder holder, CashDatasBean item) {
        //来源
        holder.tv_acquireSource.setText(String.format(getString(R.string.txt_coupon_acquireSource), item.getAcquireSource()));
        switch (item.getUseStatus()) {
            case 1:
                //投资产品 或者 有效日期
                holder.tv_used_product.setVisibility(View.GONE);
                //使用终端
                holder.tv_supportedPlatform.setText(String.format(getString(R.string.txt_coupon_supportedPlatform), item.getSupportedPlatform()));
                //使用时间
                holder.tv_used_time.setVisibility(View.GONE);
                //加息天数 或者 理财天数
                holder.tv_validity_days.setVisibility(View.GONE);
                break;
            case 2:
            case 3:
                //投资产品
                holder.tv_used_product.setVisibility(View.VISIBLE);
                holder.tv_used_product.setText(String.format(getString(R.string.txt_investProduct), item.getInvestProduct()));
                //投资终端
                holder.tv_supportedPlatform.setText(String.format(getString(R.string.txt_investPlatform), item.getInvestPlatform()));
                //使用时间
                holder.tv_used_time.setVisibility(View.VISIBLE);
                holder.tv_used_time.setText(String.format(getString(R.string.txt_useTime), item.getUseTime()));
                //加息天数 或者 理财天数
                setCarDays(holder.tv_validity_days, item);
                break;
            case 5:
                //有效日期
                holder.tv_used_product.setVisibility(View.VISIBLE);
                holder.tv_used_product.setText(String.format(getString(R.string.txt_validity_time), item.getEffectiveDate()));
                //使用终端
                holder.tv_supportedPlatform.setText(String.format(getString(R.string.txt_coupon_supportedPlatform), item.getSupportedPlatform()));
                //使用时间
                holder.tv_used_time.setVisibility(View.GONE);
                //加息天数 或者 理财天数
                holder.tv_validity_days.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }

    /**
     * 设置加息天数或者理财天数
     * 卡券类型(0现金券;1红包;2抵扣券;3加息券;4理财金)
     *
     * @param tv_validity_days
     * @param item
     */
    private void setCarDays(TextView tv_validity_days, CashDatasBean item) {
        switch (item.getCouponType()) {
            case 0:
            case 1:
            case 2:
                tv_validity_days.setVisibility(View.GONE);
                break;
            case 3:
                tv_validity_days.setVisibility(View.VISIBLE);
                tv_validity_days.setText(String.format(getString(R.string.txt_raise_days), item.getMaxActiveDay()));
                break;
            case 4:
                tv_validity_days.setVisibility(View.VISIBLE);
                tv_validity_days.setText(String.format(getString(R.string.txt_financial_days), item.getMaxActiveDay()));
                break;

            default:
                break;
        }
    }

    /**
     * 设置卡券类型
     * <p>
     * 卡券类型(0现金券;1红包;2抵扣券;3加息券;4理财金)
     *
     * @param holder
     * @param item
     */
    private void setCouponType(ViewHolder holder, CashDatasBean item) {
        //卡券名称
        holder.tv_card_type.setText(item.getCouponTypeName());
        //使用规则
        holder.tv_useRule.setText(item.getUseRule());
        //适用期限
        holder.tv_supportedDeadline.setText(item.getSupportedDeadline());
        //适用产品
        holder.tv_supportedProduct.setText(item.getSupportedProduct());

        switch (item.getCouponType()) {
            case 0:
            case 1:
            case 2:
                holder.rl_days.setVisibility(View.GONE);
                showAmountFmt(holder, item);
                break;
            case 3:
                holder.rl_days.setVisibility(View.VISIBLE);
                holder.txt_days.setText(getString(R.string.txt_days_raise));
                holder.tv_days.setText(String.format(getString(R.string.txt_format_day), item.getMaxActiveDay()));
                showRateFmt(holder, item);
                break;
            case 4:
                holder.rl_days.setVisibility(View.VISIBLE);
                holder.txt_days.setText(getString(R.string.txt_days_gold));
                holder.tv_days.setText(String.format(getString(R.string.txt_format_day), item.getMaxActiveDay()));
                showAmountFmt(holder, item);
                break;

            default:
                break;
        }
    }


    /**
     * 显示金额格式
     * 使用状态(1可使用;2:使用中;3:已使用;5:已过期)
     *
     * @param holder
     * @param item
     */
    private void showAmountFmt(ViewHolder holder, CashDatasBean item) {
        if (!TextUtils.isEmpty(item.getCouponAmount())) {
            String money = BigDecemalCalculateUtil.formatValue(item.getCouponAmount(), 0, 0);
            String allStr = String.format(mContext.getResources().getString(R.string.txt_money), money);
            int color = item.getUseStatus() == 1 ? ContextCompat.getColor(mContext, R.color.white) : ContextCompat.getColor(mContext, R.color.select_list_earnings);
            SpannableStringBuilder txtMoney = TextViewUtils.getSizeSpannBuilder(mContext, color, 28, allStr, money);
            holder.tv_card_money.setText(txtMoney);
        }
    }

    /**
     * 显示利率格式
     *
     * @param holder
     * @param item
     */
    private void showRateFmt(ViewHolder holder, CashDatasBean item) {
        if (!TextUtils.isEmpty(item.getCouponAmount())) {
            String m = BigDecemalCalculateUtil.formatValue(item.getCouponAmount(), 0, 0);
            String allStr = item.getCouponAmountFmt();
            int color = item.getUseStatus() == 1 ? ContextCompat.getColor(mContext, R.color.white) : ContextCompat.getColor(mContext, R.color.select_list_earnings);
            SpannableStringBuilder txtMoney = TextViewUtils.getSizeSpannBuilder(mContext, color, 28, allStr, m);
            holder.tv_card_money.setText(txtMoney);
        }
    }


    /**
     * 使用状态(1可使用;2:使用中;3:已使用;5:已过期)
     *
     * @param holder
     * @param status
     */
    private void setCardStatus(ViewHolder holder, int status) {
        switch (status) {
            case 1:
                holder.bg_cash.setBackgroundResource(R.drawable.bg_card);
                holder.tv_down_up.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
                holder.tv_card_money.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.tv_useRule.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.tv_supportedDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.tv_validity_time.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_supportedProduct.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.tv_days.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.txt_days.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.txt_useRule.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.txt_supportedProduct.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.txt_supportedDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.tv_card_type.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.tv_card_type.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.line_left, 0,
                        R.drawable.line_right, 0);
                holder.iv_tag.setVisibility(View.GONE);
                break;
            case 2:
                break;
            case 3:
            case 5:
                holder.bg_cash.setBackgroundResource(R.drawable.bg_card_gray);
                holder.tv_down_up.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                holder.tv_card_money.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_useRule.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_supportedDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_validity_time.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_days.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_supportedProduct.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_card_type.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.txt_days.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.txt_supportedDeadline.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.txt_supportedProduct.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.txt_useRule.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_earnings));
                holder.tv_card_type.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.line_left_gray, 0,
                        R.drawable.line_right_gray, 0);
                holder.iv_tag.setVisibility(View.VISIBLE);
                holder.iv_tag.setImageResource(status == 3 ? R.drawable.ic_yz_sy : R.drawable.ic_yz_gq);
                break;

            default:
                break;
        }
    }

    /**
     * 设置展开或收起状态
     *
     * @param holder
     * @param isOpen
     */
    private void setOpenStatus(ViewHolder holder, boolean isOpen) {
        holder.ll_card_use_more.setVisibility(isOpen ? View.VISIBLE : View.GONE);
        holder.tv_down_up.setText(isOpen ? R.string.txt_up : R.string.txt_down);
        holder.tv_down_up.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                isOpen ? R.drawable.jt_up_gray : R.drawable.jt_down_gray, 0);
    }


    final public static class ViewHolder extends CommonViewHolder {
        RelativeLayout bg_cash;//卡券布局
        RelativeLayout rl_useRule;//使用规则布局
        RelativeLayout rl_supportedDeadline;//适用期限布局
        RelativeLayout rl_supportedProduct;//适用产品布局
        RelativeLayout rl_days;//加息天数或者理财天数布局
        TextView txt_supportedDeadline;//适用期限
        TextView txt_supportedProduct;//适用产品
        TextView txt_useRule;//使用规则
        TextView txt_days;//加息或理财天数


        TextView tv_supportedDeadline;//适用期限
        TextView tv_useRule;//使用规则
        TextView tv_supportedProduct;//适用产品
        TextView tv_card_type;//卡的类型
        TextView tv_supportedPlatform;//使用终端
        TextView tv_acquireSource;//来源
        TextView tv_card_money;//卡券金额
        TextView tv_validity_time;//使用日期
        TextView tv_down_up;//查看更多
        TextView tv_days;//加息或理财天数
        TextView tv_used_product;//投资产品或有效日期
        TextView tv_used_time;//使用时间
        TextView tv_validity_days;//加息或理财天数(已使用)
        LinearLayout ll_card_use_more;//适用项目
        ImageView iv_tag;//标记

        public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            super(context, parent, layoutId, position);
            bg_cash = getView(R.id.rl_card);
            rl_days = getView(R.id.rl_days);
            rl_supportedDeadline = getView(R.id.rl_supportedDeadline);
            rl_supportedProduct = getView(R.id.rl_supportedProduct);
            rl_useRule = getView(R.id.rl_useRule);

            txt_supportedDeadline = getView(R.id.txt_supportedDeadline);
            txt_supportedProduct = getView(R.id.txt_supportedProduct);
            txt_useRule = getView(R.id.txt_useRule);
            txt_days = getView(R.id.txt_days);


            tv_supportedDeadline = getView(R.id.tv_supportedDeadline);
            tv_supportedPlatform = getView(R.id.tv_supportedPlatform);
            tv_acquireSource = getView(R.id.tv_acquireSource);
            tv_validity_time = getView(R.id.tv_validity_time);
            tv_useRule = getView(R.id.tv_useRule);
            tv_card_money = getView(R.id.tv_card_money);
            tv_supportedProduct = getView(R.id.tv_supportedProduct);
            tv_card_type = getView(R.id.tv_card_type);
            tv_down_up = getView(R.id.tv_down_up);
            tv_days = getView(R.id.tv_days);
            tv_used_product = getView(R.id.tv_used_product);
            tv_used_time = getView(R.id.tv_used_time);
            tv_validity_days = getView(R.id.tv_validity_days);
            ll_card_use_more = getView(R.id.ll_card_use_more);
            iv_tag = getView(R.id.iv_tag);

        }
    }

}
