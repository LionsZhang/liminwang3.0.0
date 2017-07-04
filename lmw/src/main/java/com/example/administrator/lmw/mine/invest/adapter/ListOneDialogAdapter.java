package com.example.administrator.lmw.mine.invest.adapter;

import android.content.Context;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ListOneDialogAdapter extends CommonAdapter {

    private List<Boolean> index;
    private List<String> type;

    public ListOneDialogAdapter(Context context, List<String> type, List<Boolean> index, int itemLayoutId) {
        super(context, type, itemLayoutId);
        this.type = type;
        this.index = index;
    }

    public void ListOneDialogAdapter(List<Boolean> index) {
        this.index = index;
    }

    @Override
    public void convert(CommonViewHolder holder, Object item, int position) {
        holder.setText(R.id.list_dialog_item_types_tv, type.get(position));
        if (index.get(position))
            holder.setImageResource(R.id.list_dialog_item_iv, R.drawable.btn_sel_03);
        else
            holder.setImageResource(R.id.list_dialog_item_iv, R.drawable.btn_sel_04);


    }

}
