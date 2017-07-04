package com.example.administrator.lmw.mine.invest.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.finance.utils.TextViewUtils;
import com.example.administrator.lmw.mine.invest.adapter.ListDialogAdapter;

import java.util.List;

/**
 * 自定义多选框
 * <p>
 * Created by Administrator on 2017/3/15.
 */

public class ListDialog extends Dialog implements View.OnClickListener {

    private View listDialog;
    private ListView listDialogList;
    private TextView listDialogTitle, listDialogYes;
    private String title;
    private Context context;
    private ListDialogListener viewOnClick;
    private ListDialogAdapter adapter;
    private List<String> value;
    private List<String> key;
    private List<Boolean> listboolean;
    private StringBuffer sbvalue = new StringBuffer();
    private StringBuffer sbkey = new StringBuffer();
    private final int ok = 1;
    private int falgNo = 0;

    /**
     * @param context
     * @param key         列表key
     * @param value       显示列表
     * @param listboolean 是否选中列表
     * @param title       标题
     * @param viewOnClick
     */
    public ListDialog(Context context, List<String> value, List<String> key, List<Boolean> listboolean, String title, ListDialogListener viewOnClick) {
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

    public ListDialog(Context context) {
        super(context);
    }

    private void setView(final List<String> list, final List<Boolean> listboolean) {


        adapter = new ListDialogAdapter(context, list, listboolean, R.layout.list_dialog_item);
        listDialogList.setAdapter(adapter);
        listDialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                falgNo = 0;
                if (position == 0) {
                    if (listboolean.get(position))
                        listboolean.set(position, false);
                    else {
                        listboolean.set(position, true);
                        for (int i = 1; i < listboolean.size(); i++) {
                            listboolean.set(i, false);
                        }
                    }
                } else {
                    if (listboolean.get(position)) {
                        listboolean.set(0, false);
                        listboolean.set(position, false);
                    } else {
                        listboolean.set(position, true);
                        listboolean.set(0, false);
                    }
                }
                for (int i = 1; i < listboolean.size(); i++) {
                    if (listboolean.get(i).equals(false)) {
                        falgNo++;
                    }
                }
                // 全部都不选的时候默认选择不限
                if (falgNo == (listboolean.size() - 1)) {
                    listboolean.set(0, true);
                }
                // 全部都选的时候默认选择不限
//                if (listboolean.get(0).equals(false) && falgNo == 0) {
//                    listboolean.set(0, true);
//                    for (int i = 1; i < listboolean.size(); i++) {
//                        listboolean.set(i, false);
//                    }
//                }
                adapter.ListDialogAdapter(listboolean);
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
                        sbvalue.append(value.get(i) + ",");
                        sbkey.append(key.get(i) + ",");
                    }

                }
                if (!TextViewUtils.isEmpty(sbkey) && !TextViewUtils.isEmpty(sbvalue)) {
                    viewOnClick.onClick(ok, sbvalue.toString().substring(0, sbvalue.toString().length() - 1),
                            sbkey.toString().substring(0, sbkey.toString().length() - 1), listboolean);
                } else {
                    viewOnClick.onClick(ok, sbvalue.toString(), sbkey.toString(), listboolean);
                }

                dismiss();
                break;
        }

    }
}
