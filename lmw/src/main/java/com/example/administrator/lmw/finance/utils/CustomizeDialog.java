package com.example.administrator.lmw.finance.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.inteface.OnDialogClickListener;

/**
 * 自定义弹出框
 * <p>
 * Created by Administrator on 2017/3/16.
 */

public class CustomizeDialog extends Dialog implements View.OnClickListener {

    private View customizeView;
    private String title, center, btn_one;
    private TextView custpmize_title, custpmize_center, custpmize_one_bt;
    private OnDialogClickListener listener;

    /**
     * @param context
     * @param title    标题
     * @param center   内容文字
     * @param btn_one  单个按键文字
     * @param listener
     */
    public CustomizeDialog(Context context, String title, String center, String btn_one, OnDialogClickListener listener) {
        this(context);
        this.title = title;
        this.center = center;
        this.btn_one = btn_one;
        this.listener = listener;
        customizeView = LayoutInflater.from(context).inflate(R.layout.customize_dialog, null);
        initView();
        this.setContentView(customizeView);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        this.show();

    }

    private void initView() {
        custpmize_title = (TextView) customizeView.findViewById(R.id.custpmize_title);
        custpmize_center = (TextView) customizeView.findViewById(R.id.custpmize_center);
        custpmize_one_bt = (TextView) customizeView.findViewById(R.id.custpmize_one_bt);
        custpmize_title.setText(title);
        custpmize_center.setText(center);
        custpmize_one_bt.setText(btn_one);
        custpmize_one_bt.setOnClickListener(this);

    }

    public CustomizeDialog(Context context) {
        super(context, R.style.Normal_dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custpmize_one_bt:
                dismiss();
                listener.onClick(2, custpmize_one_bt);
                break;
        }
    }
}
