package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.utils.PicassoManager;
import com.squareup.picasso.Callback;

public class ShowActiveDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private Context mContext;
    private ImageView close, active_iv;
    private String url;
    private OnDialogClickListener onDialogClickListener;
    private final int click_img = 1;

    public ShowActiveDialog(Context context, String url, OnDialogClickListener onDialogClickListener) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        this.url = url;
        dialog = LayoutInflater.from(context).inflate(R.layout.active_dialog_layout, null);
        this.onDialogClickListener = onDialogClickListener;
        this.show();
    }

    public ShowActiveDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(dialog);
        findView();
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        l.gravity = Gravity.CENTER;
        wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setView();
    }

    private void setView() {
        if (!TextUtils.isEmpty(url)) {
            PicassoManager.getInstance().display(mContext, active_iv, url, new Callback() {
                @Override
                public void onSuccess() {
                    close.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    private void findView() {
        close = (ImageView) dialog.findViewById(R.id.close);
        active_iv = (ImageView) dialog.findViewById(R.id.active_iv);
        close.setOnClickListener(this);
        active_iv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.active_iv:
                onDialogClickListener.onClick(click_img, v);
                dismiss();
                break;
        }
    }


}
