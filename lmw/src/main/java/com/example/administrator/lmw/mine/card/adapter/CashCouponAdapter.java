package com.example.administrator.lmw.mine.card.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.entity.ChoseCouponEvent;
import com.example.administrator.lmw.finance.utils.TextViewUtils;
import com.example.administrator.lmw.mine.card.entity.CashCouponBeanInfo;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.NumberParserUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 选择现金券类表
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/15 19:38
 **/
public class CashCouponAdapter extends CommonBaseAdapter<CashCouponBeanInfo, CashCouponAdapter.ViewHolder> {
    private int mCanUseCouponNum;

    public CashCouponAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(mContext, parent, R.layout.item_cash_coupon_card, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    @Override
    public void convert(ViewHolder holder, CashCouponBeanInfo item, int position) {
        if (item == null) return;

        //是否显示标题栏
        initItemTitle(holder, item);

        initItemView(holder, item);

        holder.bg_cash.setTag(item);
        onItemClick(holder);


    }

    /**
     * 设置item的状态
     *
     * @param holder
     * @param item
     */
    private void setItemStatus(ViewHolder holder, CashCouponBeanInfo item) {
        final int usableStatus = NumberParserUtil.parserInt(item.getUsableStatus(), 1);
        switch (usableStatus) {
            case 1://可以使用
                holder.tv_item_title.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                holder.bg_cash.setBackgroundResource(R.drawable.bg_card);
                holder.tv_item_title.setText(R.string.can_use_coupon_card_num);
                break;
            case 2://没达到最低投资额度
                holder.tv_item_title.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                holder.bg_cash.setBackgroundResource(R.drawable.bg_yellow);
                holder.tv_item_title.setText(R.string.use_coupon_not_suitable_rule);
                break;
            case 3:
        }
    }

    /**
     * 显示标题栏
     *
     * @param holder
     * @param item
     */
    private void initItemTitle(ViewHolder holder, CashCouponBeanInfo item) {
        if (item.isSelect()) {//已选
            holder.tv_item_title.setVisibility(View.VISIBLE);
            holder.tv_item_title.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
            holder.bg_cash.setBackgroundResource(R.drawable.bg_card_sel);
            holder.tv_item_title.setText(R.string.coupon_selected);
        } else {//未选中
            holder.tv_item_title.setVisibility(item.getGroupItemIndex() == 0 ? View.VISIBLE : View.GONE);
            setItemStatus(holder, item);
        }
    }

    /**
     * 显示item内容
     *
     * @param holder
     * @param item
     */
    private void initItemView(ViewHolder holder, CashCouponBeanInfo item) {
        //卡券名
        holder.tv_card_type.setText(item.getCouponTypeName());

        //使用规则
        holder.tv_useRule.setText(item.getUseRule());
        //适用产品
        holder.tv_supportedProduct.setText(item.getSupportedProduct());
        //适用期限
        holder.tv_supportedDeadline.setText(item.getSupportedDeadline());
        //有效日期
        holder.tv_validity_time.setText(String.format(mContext.getResources().getString(R.string.txt_validity_time), item.getEffectiveDate()));

        //券类型(0现金券;2抵扣券;3加息券;4理财金)
        if (TextUtils.equals("0", item.getCouponType()) || TextUtils.equals("2", item.getCouponType())) {
            holder.tv_days.setVisibility(View.GONE);
            //金额或者利率
            showAmountFmt(holder, item);
        } else if (TextUtils.equals("3", item.getCouponType())) {
            holder.tv_days.setVisibility(View.VISIBLE);
            holder.tv_days.setText(String.format(mContext.getResources().getString(R.string.txt_raise_days), item.getMaxActiveDay()));
            showRateFmt(holder, item);
        } else if (TextUtils.equals("4", item.getCouponType())) {
            holder.tv_days.setVisibility(View.VISIBLE);
            holder.tv_days.setText(String.format(mContext.getResources().getString(R.string.txt_financial_days), item.getMaxActiveDay()));
            showAmountFmt(holder, item);
        }

    }

    /**
     * 显示金额格式
     *
     * @param holder
     * @param item
     */
    private void showAmountFmt(ViewHolder holder, CashCouponBeanInfo item) {
        if (!TextUtils.isEmpty(item.getCouponAmount())) {
            String money = BigDecemalCalculateUtil.formatValue(item.getCouponAmount(), 0, 0);
            String allStr = String.format(mContext.getResources().getString(R.string.txt_money), money);
            SpannableStringBuilder txtMoney = TextViewUtils.getSizeSpannBuilder(mContext, ContextCompat.getColor(mContext, R.color.white), 28, allStr, money);
            holder.tv_card_money.setText(txtMoney);
        }
    }

    /**
     * 显示利率格式
     *
     * @param holder
     * @param item
     */
    private void showRateFmt(ViewHolder holder, CashCouponBeanInfo item) {
        if (!TextUtils.isEmpty(item.getCouponAmount())) {
            String m = BigDecemalCalculateUtil.formatValue(item.getCouponAmount(), 0, 0);
            String allStr = item.getCouponAmountFmt();
            SpannableStringBuilder txtMoney = TextViewUtils.getSizeSpannBuilder(mContext, ContextCompat.getColor(mContext, R.color.white), 28, allStr, m);
            holder.tv_card_money.setText(txtMoney);
        }
    }

    /**
     * 卡券点击事件
     *
     * @param holder
     */
    private void onItemClick(ViewHolder holder) {
        holder.bg_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashCouponBeanInfo cashCouponBeanInfo = (CashCouponBeanInfo) v.getTag();
                int usableStatu = NumberParserUtil.parserInt(cashCouponBeanInfo.getUsableStatus());
                switch (usableStatu) {
                    case 1://可以使用
                        cashCouponBeanInfo.setCanUseCouponNum(mCanUseCouponNum);
                        ChoseCouponEvent choseCouponEvent = new ChoseCouponEvent();
                        choseCouponEvent.setCashCouponBeanInfo(cashCouponBeanInfo);
                        EventBus.getDefault().post(choseCouponEvent);
                        ActivityManage.create().finishActivity();
                        break;
                    case 2://没达到最低投资额度

                        break;
                    case 3:
                }
            }
        });
    }


    public void setCashCouponNum(int cashCouponNum) {
        this.mCanUseCouponNum = cashCouponNum;
    }


    final public static class ViewHolder extends CommonViewHolder {
        TextView tv_item_title;//标题提示
        RelativeLayout bg_cash;//卡券布局
        TextView tv_supportedDeadline;//适用期限
        TextView tv_useRule;//使用规则
        TextView tv_supportedProduct;//使用产品
        TextView tv_card_type;//卡的类型
        TextView tv_card_money;//卡券金额
        TextView tv_validity_time;//使用日期
        TextView tv_days;//加息或理财天数


        public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            super(context, parent, layoutId, position);
            bg_cash = getView(R.id.rl_card);
            tv_supportedDeadline = getView(R.id.tv_supportedDeadline);
            tv_item_title = getView(R.id.tv_item_title);
            tv_validity_time = getView(R.id.tv_validity_time);
            tv_useRule = getView(R.id.tv_useRule);
            tv_card_money = getView(R.id.tv_card_money);
            tv_supportedProduct = getView(R.id.tv_supportedProduct);
            tv_card_type = getView(R.id.tv_card_type);
            tv_days = getView(R.id.tv_days);

        }
    }


}
