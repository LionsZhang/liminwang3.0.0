package com.example.administrator.lmw.mine.invest.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.lmw.R;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class IdCardDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private Activity mContext;
    private TextView pass_tv, pass_center, left_text, right_text, pass_title;
    private EditText psw_container;
    private OnIdCardDialogListener onIdCardDialogListener;
    private int inputPswType = -1;
    private final int left = 0;
    private final int right = 1;
    private final int inputCompulte = 2;
    private String title, lefttitle, righttitle, psw;

    public IdCardDialog(Activity context, String title, String lefttitle, String righttitle, OnIdCardDialogListener onIdCardDialogListener) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        this.onIdCardDialogListener = onIdCardDialogListener;
        this.title = title;
        this.lefttitle = lefttitle;
        this.righttitle = righttitle;
        dialog = LayoutInflater.from(context).inflate(R.layout.pay_id_card_dialog_layout, null);
        pass_title = (TextView) dialog.findViewById(R.id.pass_title);
        psw_container = (EditText) dialog.findViewById(R.id.pass_psw_container);
        pass_center = (TextView) dialog.findViewById(R.id.pass_tv_center);// pass_tv_center
        pass_tv = (TextView) dialog.findViewById(R.id.pass_tv_content);// pass_tv_content
        left_text = (TextView) dialog.findViewById(R.id.pass_left_text);
        right_text = (TextView) dialog.findViewById(R.id.pass_right_text);

        pass_center.setVisibility(View.GONE);
        pass_tv.setVisibility(View.GONE);
        left_text.setOnClickListener(this);
        right_text.setOnClickListener(this);
        left_text.setText(lefttitle);
        right_text.setText(righttitle);
        pass_title.setText(title);
        this.show();
    }


    public IdCardDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(dialog);
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        l.gravity = Gravity.CENTER;
        wind.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pass_left_text:
                dismiss();
                onIdCardDialogListener.onClick(left, null);
                break;
            case R.id.pass_right_text:
                dismiss();
                onIdCardDialogListener.onClick(right, psw_container.getText().toString());
                break;

        }
    }

}