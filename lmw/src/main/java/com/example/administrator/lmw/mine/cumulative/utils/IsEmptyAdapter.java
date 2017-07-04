package com.example.administrator.lmw.mine.cumulative.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.lmw.R;

/**
 * 为空界面适配器
 * <p>
 * Created by Administrator on 2016/12/5.
 */

public class IsEmptyAdapter extends BaseAdapter {
    private Context context;
    private String content;

    public IsEmptyAdapter(Context context, String content) {
        this.context = context;
        this.content = content;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.is_empty_item, null);
            holder.is_empty_content = (TextView) convertView.findViewById(R.id.is_empty_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 提示语
        if (!TextUtils.isEmpty(content))
            holder.is_empty_content.setText(content);

        return convertView;
    }

    private static class ViewHolder {

        private TextView is_empty_content;
    }
}
