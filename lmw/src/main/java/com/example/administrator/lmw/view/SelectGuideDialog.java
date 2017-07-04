package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.lmw.R;


public class SelectGuideDialog extends Dialog {
    private View dialog;
    private Context mContext;

    public SelectGuideDialog(Context context) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        dialog = LayoutInflater.from(context).inflate(R.layout.dialog_mask, null);
        this.show();
    }

    public SelectGuideDialog(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(dialog);
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        //   l.dimAmount = 0.0f;
        l.gravity = Gravity.TOP;
        l.width = WindowManager.LayoutParams.MATCH_PARENT;
        //  wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }
}
