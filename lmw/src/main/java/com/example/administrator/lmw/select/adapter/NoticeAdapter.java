package com.example.administrator.lmw.select.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.select.entity.NoticeDatasBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class NoticeAdapter extends CommonAdapter {

    private List<NoticeDatasBean> noticeDatasBeen;
    private int i = -1;


    public NoticeAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.noticeDatasBeen = mDatas;
    }

    public void addNotice(List mDatas) {
        noticeDatasBeen.addAll(mDatas);
    }

    public void Noticeclick(int i) {
        this.i = i;

    }

    @Override
    public void convert(CommonViewHolder holder, Object item, int position) {
        // 标题
        holder.setText(R.id.message_item, noticeDatasBeen.get(position).getMessageTitle());
        // 时间
        holder.setText(R.id.message_times, noticeDatasBeen.get(position).getCreateTime());
        // 图片
        if (noticeDatasBeen.get(position).getMessageStatus() == 0) {// 0:未读;1:已读
            holder.setImageResource(R.id.message_iv, R.drawable.ic_notice_mes_normal);
        } else {
            holder.setImageResource(R.id.message_iv, R.drawable.ic_notice_mes_press);
        }
        // 详情
        holder.setText(R.id.message_content, noticeDatasBeen.get(position).getMessageContent());
        if (i == position) {
            holder.setImageResource(R.id.message_dwon, R.drawable.arrow_notice02);
            holder.getView(R.id.message_content).setVisibility(View.VISIBLE);
            holder.setImageResource(R.id.message_iv, R.drawable.ic_notice_mes_press);
            noticeDatasBeen.get(i).setMessageStatus(1);
        } else {
            holder.setImageResource(R.id.message_dwon, R.drawable.arrow_notice01);
            holder.getView(R.id.message_content).setVisibility(View.GONE);
        }
        if (position == 0) {
            holder.getView(R.id.message_kong).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.message_kong).setVisibility(View.GONE);
        }

    }

}
