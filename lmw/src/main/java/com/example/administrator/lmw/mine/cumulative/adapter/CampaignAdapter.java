package com.example.administrator.lmw.mine.cumulative.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.mine.cumulative.entity.CumulativeDatasBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 活动奖励收益 适配器
 * <p/>
 * Created by Administrator on 2016/8/24 0024.
 */
public class CampaignAdapter extends CommonAdapter<CumulativeDatasBean> {

    private DecimalFormat df = new DecimalFormat("##0.00");

    public CampaignAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, CumulativeDatasBean item, int position) {
        // 收益名称
        if (!TextUtils.isEmpty(item.getAwardName()))
            holder.setText(R.id.cumulative_title_tv, item.getAwardName());
        // 收益金额
        if (!TextUtils.isEmpty(item.getAwardAmount()))
            holder.setText(R.id.cumulative_money_tv, "+ " + df.format(Double.parseDouble(item.getAwardAmount())));
        // 收益获得时间
        if (!TextUtils.isEmpty(item.getGetTime()))
            holder.setText(R.id.cumulative_times_tv, item.getGetTime());
        // 收益来源
        if (!TextUtils.isEmpty(item.getAwardRemark()))
            holder.setText(R.id.cumulative_note_tv, "备注：" + item.getAwardRemark());
    }
}
