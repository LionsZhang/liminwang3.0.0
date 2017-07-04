package com.example.administrator.lmw.view;

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
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.utils.PicassoManager;

public class ShowLineDialog extends Dialog implements View.OnClickListener {
    private View dialog;
    private final int left = 0;
    private final int right = 1;
    private final int oneButton = 2;
    private OnDialogClickListener onDialogClickListener;
    private String title, content, leftText, rightText, oneButtonText;
    private boolean isShowBanner, istwoButton;
    private TextView tv_title, tv_center, bt_left, bt_right, one_bt;
    private ImageView banner_iv;
    private LinearLayout two_tv_rl;
    private Context mContext;
    private View title_divider_line;

    public ShowLineDialog(Context context, String title, String content, String left, String right, String oneButtonText,
                          boolean isShowBanner, boolean istwoButton, OnDialogClickListener onDialogClickListener) {
        this(context, R.style.couponDialogStyle);
        this.onDialogClickListener = onDialogClickListener;
        this.mContext = context;
        this.title = title;
        this.content = content;
        this.leftText = left;
        this.rightText = right;
        this.oneButtonText = oneButtonText;
        this.isShowBanner = isShowBanner;
        this.istwoButton = istwoButton;
        dialog = LayoutInflater.from(context).inflate(R.layout.line_dialog_layout, null);
        this.show();

    }

    public ShowLineDialog(Context context, int theme) {
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
        l.gravity = Gravity.TOP;
        wind.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }


    private void findView() {
        tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_center = (TextView) dialog.findViewById(R.id.tv_center);
        bt_left = (TextView) dialog.findViewById(R.id.left_text);
        bt_right = (TextView) dialog.findViewById(R.id.right_text);
        banner_iv = (ImageView) dialog.findViewById(R.id.banner_iv);
        one_bt = (TextView) dialog.findViewById(R.id.one_bt);
        two_tv_rl = (LinearLayout) dialog.findViewById(R.id.two_tv_rl);
        title_divider_line = dialog.findViewById(R.id.divider_line);
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        one_bt.setOnClickListener(this);
    }

    private void setView() {
        if (isShowBanner) {
            banner_iv.setVisibility(View.VISIBLE);
            tv_center.setVisibility(View.GONE);//隐藏字符内容显示
            title_divider_line.setVisibility(View.VISIBLE);
           // PicassoManager.getInstance().display(mContext, banner_iv, content);//广告图片
        } else {
            title_divider_line.setVisibility(View.GONE);
            banner_iv.setVisibility(View.GONE);
            tv_center.setVisibility(View.VISIBLE);
        }
        if (istwoButton) {
            one_bt.setVisibility(View.GONE);
            two_tv_rl.setVisibility(View.VISIBLE);
        } else {
            one_bt.setVisibility(View.VISIBLE);
            two_tv_rl.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(title))
            tv_title.setText(title);//标题
        if (!TextUtils.isEmpty(content))
            tv_center.setText(content);//文本内容
        if (!TextUtils.isEmpty(leftText))
            bt_left.setText(leftText);//左边
        if (!TextUtils.isEmpty(rightText))
            bt_right.setText(rightText);//右边
        if (!TextUtils.isEmpty(oneButtonText))
            one_bt.setText(oneButtonText);//一个button


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_text:
                dismiss();
                onDialogClickListener.onClick(left, v);
                break;
            case R.id.right_text:
                dismiss();
                onDialogClickListener.onClick(right, v);
                break;
            case R.id.one_bt:
                dismiss();
                onDialogClickListener.onClick(oneButton, v);
                break;
        }
    }
}
