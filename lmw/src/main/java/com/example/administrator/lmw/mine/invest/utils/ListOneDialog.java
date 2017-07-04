package com.example.administrator.lmw.mine.invest.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.mine.invest.adapter.ListOneDialogAdapter;

import java.util.List;

/**
 * 自定义单选框
 * <p>
 * Created by Administrator on 2017/3/15.
 */

public class ListOneDialog extends Dialog implements View.OnClickListener {

    private View listDialog;
    private ListView listDialogList;
    private TextView listDialogTitle, listDialogYes;
    private String title;
    private Context context;
    private ListDialogListener viewOnClick;
    private ListOneDialogAdapter adapter;
    private List<String> value;
    private List<String> key;
    private List<Boolean> listboolean;
    private StringBuffer sbvalue = new StringBuffer();
    private StringBuffer sbkey = new StringBuffer();
    private final int ok = 1;

    /**
     * @param context
     * @param key         列表key
     * @param value       显示列表
     * @param listboolean 是否选中列表
     * @param title       标题
     * @param viewOnClick
     */
    public ListOneDialog(Context context, List<String> value, List<String> key, List<Boolean> listboolean, String title, ListDialogListener viewOnClick) {
        this(context);
        this.title = title;
        this.context = context;
        this.viewOnClick = viewOnClick;
        this.value = value;
        this.key = key;
        this.listboolean = listboolean;
        listDialog = LayoutInflater.from(context).inflate(R.layout.list_dialog, null);
        listDialogList = (ListView) listDialog.findViewById(R.id.list_dialog_list);
        listDialogTitle = (TextView) listDialog.findViewById(R.id.list_dialog_title);
        listDialogYes = (TextView) listDialog.findViewById(R.id.list_dialog_yes);

        setView(value, listboolean);
        listDialogTitle.setText(title);
        listDialogYes.setOnClickListener(this);
        this.show();
    }

    public ListOneDialog(Context context) {
        super(context);
    }

    private void setView(final List<String> list, final List<Boolean> listboolean) {


        adapter = new ListOneDialogAdapter(context, list, listboolean, R.layout.list_dialog_item);
        listDialogList.setAdapter(adapter);
        listDialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < listboolean.size(); i++) {
                    if (position == i) {
                        listboolean.set(i, true);
                    } else {
                        listboolean.set(i, false);
                    }
                }
                adapter.ListOneDialogAdapter(listboolean);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(listDialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_dialog_yes:
                for (int i = 0; i < listboolean.size(); i++) {
                    if (listboolean.get(i)) {
                        sbvalue.append(value.get(i));
                        sbkey.append(key.get(i));
                    }

                }
                viewOnClick.onClick(ok, sbvalue.toString(), sbkey.toString(), listboolean);
                dismiss();
                break;
        }

    }
}