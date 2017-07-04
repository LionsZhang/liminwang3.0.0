package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.utils.DensityUtil;

/**
 * 红包分享 弹窗
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/13 17:09
 **/
public class RedPacketShareDialog extends Dialog {
    public static final int RIGHT_ONCLICK = 1;
    public static final int lEFT_ONCLICK = 0;

    private String num;
    private Context context;
    private OnDialogClickListener listener;
    private boolean mCancelable = true;//是否点击屏幕是否消失


    /**
     * id 0 左边按钮 1右边按钮
     *
     * @param context 上下文对象
     * @param num     红包数量
     */
    public RedPacketShareDialog(Context context, String num, final OnDialogClickListener listener, boolean... args) {
        super(context, R.style.Normal_dialog);
        this.mCancelable = args != null && args.length > 0 ? args[0] : true;
        this.context = context;
        this.num = num;
        this.listener = listener;
        View view = View.inflate(context, R.layout.dialog_redpacket_share, null);
        initView(view);

        this.setContentView(view);
        this.show();

        this.setCanceledOnTouchOutside(mCancelable);// 设置点击屏幕Dialog是否消失
        this.setCancelable(mCancelable);

        WindowManager.LayoutParams localLayoutParams = this.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        localLayoutParams.width = display.getWidth() - DensityUtil.dip2px(context, 70); //设置宽度
        this.getWindow().setAttributes(localLayoutParams);
    }


    private void initView(View view) {
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        TextView bt_left = (TextView) view.findViewById(R.id.left_text);
        TextView bt_right = (TextView) view.findViewById(R.id.right_text);


        //红包数
        if (!TextUtils.isEmpty(num)) {
            tv_num.setText(Html.fromHtml(num));
        }

        bt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onClick(lEFT_ONCLICK, v);
                }
            }
        });
        bt_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCancelable)
                    dismiss();
                if (listener != null) {
                    listener.onClick(RIGHT_ONCLICK, v);
                }
            }
        });


    }

}
