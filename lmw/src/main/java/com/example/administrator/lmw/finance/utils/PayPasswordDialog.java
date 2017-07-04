package com.example.administrator.lmw.finance.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.lmw.R;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class PayPasswordDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private Activity mContext;
    private TextView sms_title, sms_tv_content, edit_countdown, sms_confirm;
    private ImageView sms_shut_down;
    private EditText edit_sms;
    private OnPayPasswordDialogListener onPayPasswordDialogListener;
    private TimeCount timeCount;
    private final int countdown = 0;
    private final int confirm = 1;
    private final int shut_down = 2;
    private boolean editFlag = false;

    public PayPasswordDialog(Activity context, String contentStr, OnPayPasswordDialogListener onPayPasswordDialogListener) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        this.onPayPasswordDialogListener = onPayPasswordDialogListener;
        dialog = LayoutInflater.from(context).inflate(R.layout.pay_pass_word_dialog_layout, null);
        sms_title = (TextView) dialog.findViewById(R.id.sms_title);
        sms_tv_content = (TextView) dialog.findViewById(R.id.sms_tv_content);
        edit_countdown = (TextView) dialog.findViewById(R.id.edit_countdown);
        sms_confirm = (TextView) dialog.findViewById(R.id.sms_confirm);
        sms_shut_down = (ImageView) dialog.findViewById(R.id.sms_shut_down);
        edit_sms = (EditText) dialog.findViewById(R.id.edit_sms);
        timeCount = new TimeCount(60000, 1000);

        sms_tv_content.setText(contentStr);
        edit_countdown.setOnClickListener(this);
        sms_confirm.setOnClickListener(this);
        sms_shut_down.setOnClickListener(this);
        getView();
        this.show();
    }


    public PayPasswordDialog(Context context, int theme) {
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

    private void getView() {
        edit_sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int editno = s.toString().trim().length();
                if (editno == 6) {
                    editFlag = true;
                    sms_confirm.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue));
                } else {
                    editFlag = false;
                    sms_confirm.setTextColor(ContextCompat.getColor(mContext, R.color.divide));
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_countdown:// 倒计时
                onPayPasswordDialogListener.onClick(countdown, null);
                break;
            case R.id.sms_confirm:// 确认
                if (editFlag) {
                    onPayPasswordDialogListener.onClick(confirm, edit_sms.getText().toString().trim());
                }
                break;
            case R.id.sms_shut_down:// 关闭按钮
                hide();
                onPayPasswordDialogListener.onClick(shut_down, null);
                break;

        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            edit_countdown.setClickable(false);
            edit_countdown.setText(millisUntilFinished / 1000 + "秒后重发");
        }

        @Override
        public void onFinish() {
            edit_countdown.setText("获取验证码");
            edit_countdown.setClickable(true);

        }
    }

    /**
     * 启动倒计时
     */
    public void start() {
        timeCount.start();
    }

}