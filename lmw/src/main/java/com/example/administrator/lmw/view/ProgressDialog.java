package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.administrator.lmw.R;

/**
 * Created by lion on 2016/9/27.
 */
public class ProgressDialog extends Dialog {
    private View view;
    private ImageView  progressIv;

    public ProgressDialog(Context context) {
        this(context, R.style.ProgressDialogStyle);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null);
        progressIv = (ImageView) view.findViewById(R.id.progress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(view);
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        l.gravity = Gravity.CENTER;
        //  wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
       if ( progressIv  != null)
           progressIv.setBackgroundResource(R.drawable.loading_process_dialog);
        AnimationDrawable anim = (AnimationDrawable) progressIv .getBackground();
        anim.start();

    }
}
