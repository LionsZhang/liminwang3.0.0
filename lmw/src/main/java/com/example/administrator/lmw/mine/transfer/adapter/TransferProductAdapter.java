package com.example.administrator.lmw.mine.transfer.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.mine.transfer.entity.TransferDataBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class TransferProductAdapter extends CommonAdapter<TransferDataBean> {

    public TransferProductAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }


    @Override
    public void convert(CommonViewHolder holder, TransferDataBean item, int position) {
        // 标题
        if (!TextUtils.isEmpty(item.getTitle()))
            holder.setText(R.id.transfer_item_title, item.getTitle());
        // 期限
        if (!TextUtils.isEmpty(item.getPeriods()))
            holder.setText(R.id.transfer_item_term, item.getPeriods());
        // 结息时间
        if (!TextUtils.isEmpty(item.getRepayDate()))
            holder.setText(R.id.transfer_item_times, item.getRepayDate());
        if (item.getStillPrincipal() > 0) {
            holder.setText(R.id.transfer_item_times_title, "回款时间");
        } else {
            holder.setText(R.id.transfer_item_times_title, "结息时间");
        }
        // 应收本息
        holder.setText(R.id.transfer_item_moneys, item.getRepayAmt() + "元");
        /**
         * 状态 0-默认未偿还 1-已偿还
         */
        if (!TextUtils.isEmpty(item.getRepayStatus()))
            if (item.getRepayStatus().equals("0")) {
                holder.setText(R.id.transfer_item_status, "未偿还");
            } else {
                holder.setText(R.id.transfer_item_status, "已偿还");
            }

    }
}
