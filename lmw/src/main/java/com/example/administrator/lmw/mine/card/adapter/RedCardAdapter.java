package com.example.administrator.lmw.mine.card.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.mine.card.entity.RedBean;

/**
 * 红包页面适配器
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/16 11:43
 **/
public class RedCardAdapter extends CommonBaseAdapter<RedBean, CommonViewHolder> {


    public RedCardAdapter(Context context) {
        super(context);
    }


    @Override
    public void convert(CommonViewHolder holder, RedBean item, int position) {
        holder.setText(R.id.red_card_source, item.getAcquireSource()); // 红包来源
        if (TextUtils.isEmpty(item.getCreateTime())) {
            holder.setText(R.id.red_card_times, item.getCreateTime()); // 红包时间
        } else {
            holder.setText(R.id.red_card_times, item.getCreateTime().substring(0, 10)); // 红包时间
        }
        switch (item.getUseStatus()) {
            case 1:// 未拆
                holder.setText(R.id.red_card_money, item.getTitle());// 红包标题
                holder.setImageResource(R.id.red_card_bg, R.drawable.bg_coupon_red);
                break;
            case 3://
                holder.setText(R.id.red_card_money, item.getAmount() + "元");// 红包金额
                holder.setImageResource(R.id.red_card_bg, R.drawable.bg_coupon_red_disable);
                break;
        }

    }


    @Override
    public CommonViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        return CommonViewHolder.get(mContext, convertView, parent, R.layout.red_card_item, position);
    }

}
