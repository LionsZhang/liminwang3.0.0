package com.example.administrator.lmw.finance.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.finance.entity.InvestmentDatasBean;

import java.util.List;

/**
 * 投资记录adapter
 * <p/>
 * Created by Administrator on 2016/8/26 0026.
 */
public class RecordAdapter extends CommonAdapter<InvestmentDatasBean> {

    public RecordAdapter(Context context, List<InvestmentDatasBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, InvestmentDatasBean item, int position) {
        if (!TextUtils.isEmpty(item.getMobile()))
            holder.setText(R.id.record_person, item.getMobile()); // 投资人
        if (!TextUtils.isEmpty(item.getRealAmount()))
            holder.setText(R.id.record_money, item.getRealAmount()); // 投资金额
        if (!TextUtils.isEmpty(item.getInvestTime()))
            holder.setText(R.id.record_times, item.getInvestTime()); // 投资时间

    }
}
