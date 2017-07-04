package com.example.administrator.lmw.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.inteface.OnInputDialogListener;


public class InputTradePswDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private Activity mContext;
    private TextView tv_content, left_text, right_text, tv_center, tv_title;
    private LinearLayout psw_container;
    private OnInputDialogListener onInputDialogListener;
    private int inputPswType = -1;
    private TradePasswardEditText passwardEditText;
    private final int left = 0;
    private final int right = 1;
    private final int inputCompulte = 2;
    private int inputType;
    public static final int input_fill = 100;
    public static final int input_credit = 101;
    public static final int input_licence = 102;
    private String cashNum;
    private ImageView close;

    public InputTradePswDialog(Activity context, int inputType, String cashNum, OnInputDialogListener onInputDialogListener) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        this.onInputDialogListener = onInputDialogListener;
        this.inputType = inputType;
        this.cashNum = cashNum;
        dialog = LayoutInflater.from(context).inflate(R.layout.trade_psw_show_dialog_layout, null);
        tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        psw_container = (LinearLayout) dialog.findViewById(R.id.psw_container);
        left_text = (TextView) dialog.findViewById(R.id.left_text);
        tv_center = (TextView) dialog.findViewById(R.id.tv_center);
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        close = (ImageView) dialog.findViewById(R.id.close);

        setView();
        left_text.setOnClickListener(this);
        close.setOnClickListener(this);
        this.show();
    }

    private void setView() {
        passwardEditText = new TradePasswardEditText(mContext);
        psw_container.addView(passwardEditText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        passwardEditText.setOnEditTextListener(new TradePasswardEditText.OnEditTextListener() {
            @Override
            public void inputComplete(int state, String password) {
                onInputDialogListener.onClick(inputCompulte, password);

            }
        });
        if (inputType == input_fill) {
            tv_content.setVisibility(View.GONE);
            left_text.setVisibility(View.VISIBLE);
            tv_center.setText("充值");
            if (!TextUtils.isEmpty(cashNum)) {
                tv_content.setText(cashNum);
            }
        } else if (inputType == input_credit) {
            tv_content.setVisibility(View.VISIBLE);
            left_text.setVisibility(View.VISIBLE);
            tv_center.setText("提现");
            if (!TextUtils.isEmpty(cashNum)) {
                tv_content.setText(cashNum);
            }
        } else if (inputType == input_licence) {
            tv_content.setVisibility(View.GONE);
            left_text.setVisibility(View.GONE);
            tv_title.setText("身份验证");
            tv_center.setText("请输入您的身份证后6位数字");
        }

    }

    public InputTradePswDialog(Context context, int theme) {
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
            case R.id.left_text:
                dismiss();
                onInputDialogListener.onClick(left, null);
                break;
            case R.id.close:
                dismiss();
                break;

        }
    }

    public void clearTradePsw() {
        passwardEditText.clearEditText();
    }

}
