package com.example.administrator.lmw.mine.invest.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.administrator.lmw.R;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class SelectDialog extends AlertDialog {

    public SelectDialog(Context context, int theme) {
        super(context, theme);
    }

    public SelectDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_investment_four);
    }
}
