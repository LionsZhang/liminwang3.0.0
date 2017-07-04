package com.example.administrator.lmw.select.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.select.entity.AnnouncementDatasBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class AnnouncementAdapter extends CommonAdapter {

    private List<AnnouncementDatasBean> bean;

    public AnnouncementAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.bean = mDatas;
    }

    public void addAnnouncement(List mDatas) {
        bean.addAll(mDatas);
    }

    @Override
    public void convert(CommonViewHolder holder, Object item, int position) {
        // 标题
        holder.setText(R.id.message_item, bean.get(position).getPostTitle());
        // 时间
        holder.setText(R.id.message_times, bean.get(position).getCreateTime());
        // 图片
        holder.setImageResource(R.id.message_iv, R.drawable.ic_notice_mes_press);
        if (position == 0) {
            holder.getView(R.id.message_kong).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.message_kong).setVisibility(View.GONE);
        }

    }
}
