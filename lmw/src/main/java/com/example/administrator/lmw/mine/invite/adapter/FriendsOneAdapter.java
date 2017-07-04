package com.example.administrator.lmw.mine.invite.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.mine.invite.entity.FriendListDatasBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class FriendsOneAdapter extends CommonAdapter<FriendListDatasBean> {

    private List<FriendListDatasBean> datasBeen;

    public FriendsOneAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.datasBeen = mDatas;
    }

    @Override
    public void convert(CommonViewHolder holder, FriendListDatasBean item, int position) {
        // 名字
        if (!TextUtils.isEmpty(item.getName()))
            holder.setText(R.id.friends_item_names, item.getName());
        // 时间
        if (!TextUtils.isEmpty(item.getRegTime()))
            holder.setText(R.id.friends_item_times, item.getRegTime());
    }
}
