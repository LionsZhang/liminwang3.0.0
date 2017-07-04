package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.inteface.OnDialogClickListener;


public class ShareDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private Context mContext;
    private TextView inv_friends, inv_wechat, inv_sms, inv_sina, inv_qq;
    private final int wechat_friends = 0;
    private final int wechat = 1;
    private final int sms = 2;
    private final int sina = 3;
    private final int qq = 4;
    private OnDialogClickListener onDialogClickListener;

    public ShareDialog(Context context, OnDialogClickListener onDialogClickListener) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        this.onDialogClickListener = onDialogClickListener;
        dialog = LayoutInflater.from(context).inflate(R.layout.invite_firiend_dialog_layout, null);
        inv_friends = (TextView) dialog.findViewById(R.id.inv_friends);
        inv_wechat = (TextView) dialog.findViewById(R.id.inv_wechat);
        inv_sms = (TextView) dialog.findViewById(R.id.inv_sms);
        inv_sina = (TextView) dialog.findViewById(R.id.inv_sina);
        inv_qq = (TextView) dialog.findViewById(R.id.inv_qq);
        inv_wechat.setOnClickListener(this);
        inv_sms.setOnClickListener(this);
        inv_friends.setOnClickListener(this);
        inv_sina.setOnClickListener(this);
        inv_qq.setOnClickListener(this);
        this.show();
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(dialog);
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        //   l.dimAmount = 0.0f;
        l.gravity = Gravity.BOTTOM;
        l.width = WindowManager.LayoutParams.MATCH_PARENT;
        //  wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inv_wechat:
                onDialogClickListener.onClick(wechat, v);
                dismiss();
                break;
            case R.id.inv_friends:
                onDialogClickListener.onClick(wechat_friends, v);
                dismiss();
                break;
            case R.id.inv_sms:
                onDialogClickListener.onClick(sms, v);
                dismiss();
                break;
            case R.id.inv_sina:
                onDialogClickListener.onClick(sina, v);
                dismiss();
                break;
            case R.id.inv_qq:
                onDialogClickListener.onClick(qq, v);
                dismiss();
                break;
        }
    }
}
