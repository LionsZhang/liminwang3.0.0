package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.inteface.OnDialogClickListener;

public class ShowUpgradeDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private final int oneButton = 2;
    private OnDialogClickListener onDialogClickListener;
    private String title, content, oneButtonText, version;
    private TextView tv_title, tv_center, update_title, one_bt;
    private Context mContext;
    private ImageView close, gesture_iv;
    private ScrollView tv_center_sl;
    private int isForceUpgrade;

    public ShowUpgradeDialog(Context context, String title, String contentTxt, String version,
                             int isForceUpgrade, OnDialogClickListener onDialogClickListener) {
        this(context, R.style.couponDialogStyle);
        this.onDialogClickListener = onDialogClickListener;
        this.mContext = context;
        this.title = title;
        this.content = contentTxt;
        this.version = version;
        this.isForceUpgrade = isForceUpgrade;
        dialog = LayoutInflater.from(context).inflate(R.layout.upgrade_dialog_layout, null);
        this.show();
    }

    public ShowUpgradeDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(dialog);
        findView();
        setView();
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        l.gravity = Gravity.CENTER;
        wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }


    private void findView() {
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        update_title = (TextView) dialog.findViewById(R.id.update_title);
        tv_center = (TextView) dialog.findViewById(R.id.tv_center);
        one_bt = (TextView) dialog.findViewById(R.id.one_bt);
        close = (ImageView) dialog.findViewById(R.id.close);
        gesture_iv = (ImageView) dialog.findViewById(R.id.gesture_iv);
        tv_center_sl = (ScrollView) dialog.findViewById(R.id.tv_center_sl);
        one_bt.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    private void setView() {
        tv_title.setText(String.format(mContext.getString(R.string.update_version), version));
        update_title.setText(String.format(mContext.getString(R.string.update_version_title), version));
  /*      if (!TextUtils.isEmpty(title))
            tv_title.setText(title);//标题*/
        if (!TextUtils.isEmpty(content))
            tv_center.setText(Html.fromHtml(content));//文本内容
        if (!TextUtils.isEmpty(oneButtonText))
            one_bt.setText(oneButtonText);//一个button
        if (isForceUpgrade == 1)
            close.setVisibility(View.INVISIBLE);
        else
            close.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_bt:
                if (isForceUpgrade != 1) {
                    dismiss();
                }
                onDialogClickListener.onClick(oneButton, v);
                break;
            case R.id.close:
                dismiss();
                break;
        }
    }
}
