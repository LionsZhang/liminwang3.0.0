package com.example.administrator.lmw.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.finance.entity.DataFinancialCategoryBean;
import com.example.administrator.lmw.finance.utils.ContinuousDialog;
import com.example.administrator.lmw.finance.utils.CustomizeDialog;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.ViewOnClick;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.view.DialogPlus;
import com.example.administrator.lmw.view.FinanceProductSortDialog;
import com.example.administrator.lmw.view.NormalDialog;
import com.example.administrator.lmw.view.NormalLeftDialog;
import com.example.administrator.lmw.view.ShowAnnouncementDialog;
import com.example.administrator.lmw.view.ShowLineDialog;
import com.example.administrator.lmw.view.ShowUpgradeDialog;
import com.example.administrator.lmw.view.ViewHolder;

import java.util.List;


/**
 * @Description:TODO 弹窗管理类
 * @create by lion
 * @created at 2016/8/26
 */
public class PopWindowManager {


    private NormalDialog normalDialog;
    private ShowLineDialog showLineDialog;
    private Context mContext;
    private ViewOnClick listener;
    private DialogPlus dialog;
    private ShowUpgradeDialog showUpgradeDialog;
    private CustomizeDialog customizeDialog;
    private ContinuousDialog continuousDialog;

    /**
     * 两个按钮的弹窗
     *
     * @param context   上下文对象
     * @param content   弹窗内容
     * @param leftText  左按钮文本
     * @param rightText 右按钮文本
     * @param listener  点击监听
     */
    public void showTwoButtonDialog(Context context, String titleText, String content, String leftText, String rightText, OnDialogClickListener listener) {
        if (normalDialog != null && !normalDialog.isShowing()) {
            normalDialog.show();
        } else {
            new NormalDialog(context, titleText, content, leftText, rightText, listener);
        }
    }

    /**
     * 两个按钮的弹窗
     *
     * @param context       上下文对象
     * @param content       弹窗内容
     * @param oneButtonText 左按钮文本
     * @param listener      点击监听
     */
    public void showOneButtonDialog(Context context, String titleText, String content, String oneButtonText, OnDialogClickListener listener) {
        if (normalDialog != null && !normalDialog.isShowing()) {
            normalDialog.show();
        } else {
            new NormalDialog(context, titleText, content, oneButtonText, listener);
        }
    }

    /**
     * 两个按钮的弹窗
     *
     * @param context       上下文对象
     * @param content       弹窗内容
     * @param oneButtonText 左按钮文本
     * @param listener      点击监听
     */
    public void showOneButtonDialogNotCancel(Context context, String titleText, String content, String oneButtonText, OnDialogClickListener listener) {
        if (normalDialog != null && !normalDialog.isShowing()) {
            normalDialog.show();
        } else {
            normalDialog = new NormalDialog(context, titleText, content, oneButtonText, listener);
        }
        normalDialog.setCancelable(false);
        normalDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 两个按钮的弹窗
     *
     * @param context   上下文对象
     * @param content   弹窗内容
     * @param leftText  左按钮文本
     * @param rightText 右按钮文本
     * @param listener  点击监听
     */
    public void showRegistSuccussDialog(Context context, String title, String content, String leftText, String rightText, OnDialogClickListener listener) {
        if (showLineDialog != null && !showLineDialog.isShowing()) {
            showLineDialog.show();
        } else {
            new ShowLineDialog(context, title, content, leftText, rightText, null,
                    false, true, listener);
        }
    }

    /**
     * 一个按钮的弹窗
     *
     * @param context  上下文对象
     * @param listener 点击监听
     */
    public void showUpgradeDialog(Context context, String title, String contentTxt, String version, int isForceUpgrade, OnDialogClickListener listener) {
//        if (showUpgradeDialog != null) {
//            showUpgradeDialog.show();
//        } else {
        new ShowUpgradeDialog(context, title, contentTxt, version, isForceUpgrade,
                listener);
//        }
    }

    /**
     * 一个按钮的弹窗实名认证弹窗，显示图片内容
     * <p/>
     *
     * @param context  上下文对象
     * @param content  弹窗内容
     * @param listener 点击监听
     */

    public void showRealAutuDialog(Context context, String title, String content, String oneButtonText, OnDialogClickListener listener) {

        if (showLineDialog != null && !showLineDialog.isShowing()) {
            showLineDialog.show();
        } else {
            new ShowLineDialog(context, title, content, null, null, oneButtonText,
                    true, false, listener);
        }
    }

    /**
     * 一个按钮的弹窗实名认证弹窗 显示文本内容
     *
     * @param context  上下文对象
     * @param content  弹窗内容
     * @param listener 点击监听
     */
    public void showBindBankCardDialog(Context context, String title, String content, String oneButtonText, OnDialogClickListener listener) {
        if (showLineDialog != null && !showLineDialog.isShowing()) {
            showLineDialog.show();
        } else {
            new ShowLineDialog(context, title, content, null, null, oneButtonText,
                    false, false, listener);
        }
    }

    /**
     * 选择设置交易密码类型弹窗
     *
     * @param listener void
     * @auther lion
     * @params contex
     */
    public void showTradePswDialog(final Context context, String forgetText, String knowText, String cancelText, final ViewOnClick listener) {
        mContext = context;
        this.listener = listener;
        View view = View.inflate(context, R.layout.dialog_photo_add, null);
        ViewHolder holder = new ViewHolder(view);
        dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder).setCancelable(true)
                .setBackgroundColorResourceId(R.drawable.transparent)
                .setCancelable(false).setGravity(DialogPlus.Gravity.BOTTOM).create();
        TextView forget_psw = (TextView) view.findViewById(R.id.forget_psw);
        TextView know_psw = (TextView) view.findViewById(R.id.know_psw);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
        if (!TextUtils.isEmpty(forgetText))
            forget_psw.setText(forgetText);
        if (!TextUtils.isEmpty(knowText))
            know_psw.setText(knowText);
        if (!TextUtils.isEmpty(cancelText))
            btn_cancel.setText(cancelText);

        // 忘记密码
        view.findViewById(R.id.forget_psw).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onClick(0, v);
                        }
                    }
                });
        // 记得密码
        view.findViewById(R.id.know_psw).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onClick(1, v);
                        }
                    }
                });
        // 取消
        view.findViewById(R.id.btn_cancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onClick(2, v);
                        }
                    }
                });

        // 删除
        TextView delete = (TextView) view.findViewById(R.id.btn_del);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onClick(3, v);
                }
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    /**
     * 我的投资选择框
     *
     * @param context
     * @param investone
     * @param investtwo
     * @param investthree
     * @param listener
     */
    public void showInvestDialog(final Context context, String investone, String investtwo, String investthree, final ViewOnClick listener) {
        mContext = context;
        this.listener = listener;
        View view = View.inflate(context, R.layout.dialog_totals, null);
        ViewHolder holder = new ViewHolder(view);
        dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder).setCancelable(true)
                .setBackgroundColorResourceId(R.drawable.transparent)
                .setCancelable(false).setGravity(DialogPlus.Gravity.CENTER).create();
        TextView dialog_total_title = (TextView) view.findViewById(R.id.dialog_total_title);
        TextView dialog_total_content = (TextView) view.findViewById(R.id.dialog_total_content);
        TextView dialog_total_btn = (TextView) view.findViewById(R.id.dialog_total_btn);

        /**
         * 初始化文字
         */
        if (!TextUtils.isEmpty(investone))
            dialog_total_title.setText(investone);
        if (!TextUtils.isEmpty(investtwo))
            dialog_total_content.setText(investtwo);
        if (!TextUtils.isEmpty(investthree))
            dialog_total_btn.setText(investthree);

        // 我知道了
        view.findViewById(R.id.dialog_total_btn).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onClick(2, v);
                        }
                    }
                });

        dialog.show();

    }

    /**
     * 满屏的文字弹出框
     *
     * @param context
     * @param title
     * @param content
     * @param listener
     */
    private PopupWindow full_popupwindow;

    public void showFullDialog(final Context context, View show, String title, String content) {
        final View view = View.inflate(context, R.layout.full_dialog, null);
        full_popupwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        full_popupwindow.setFocusable(false);
        full_popupwindow.setOutsideTouchable(false);
        full_popupwindow.setBackgroundDrawable(new BitmapDrawable());
        full_popupwindow.showAsDropDown(show);
        TextView full_title = (TextView) view.findViewById(R.id.full_title);
        TextView full_content = (TextView) view.findViewById(R.id.full_content);
        ImageView full_shut = (ImageView) view.findViewById(R.id.full_shut);

        /**
         * 初始化文字
         */
        if (!TextUtils.isEmpty(title))
            full_title.setText(title);
        if (!TextUtils.isEmpty(content))
            full_content.setText(content);

        // 关闭
        full_shut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                full_popupwindow.dismiss();
            }
        });

    }

    /************************************************ popupwindow *****************************************************************/
    /**
     * popwindow弹窗 显示在控件下面
     *
     * @param context  上下文对象
     * @param show     触发pop的控件
     * @param icons    item的图标资源数组
     * @param titles   item的标字符串数组
     * @param resetdata 是否重置数据源
     * @param listener item的点击监听
     * @author lion
     */
    private PopupWindow popupWindowContact;

    public void showPopWindow(final Context context, View show, final ViewOnClick listener) {
        final View view = View.inflate(context, R.layout.item_list_pop, null);
        popupWindowContact = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowContact.setFocusable(true);
        popupWindowContact.setOutsideTouchable(true);
        popupWindowContact.setBackgroundDrawable(new BitmapDrawable());

        popupWindowContact.showAsDropDown(show, -20, 0);
        view.findViewById(R.id.invite_tv).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindowContact.dismiss();
                        if (listener != null) {
                            listener.onClick(0, v);
                        }
                    }
                });
        view.findViewById(R.id.request_service).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindowContact.dismiss();
                        if (listener != null) {
                            listener.onClick(1, v);
                        }
                    }
                });

        view.findViewById(R.id.question).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindowContact.dismiss();
                        if (listener != null) {
                            listener.onClick(2, v);
                        }
                    }
                });
        view.findViewById(R.id.finance_caculate_tv).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindowContact.dismiss();
                        if (listener != null) {
                            listener.onClick(3, v);
                        }
                    }
                });
    }

    /**
     * popwindow弹窗 显示在控件下面
     *
     * @param context  上下文对象
     * @param show     触发pop的控件
     * @param icons    item的图标资源数组
     * @param titles   item的标字符串数组
     * @param resetdata 是否重置数据源
     * @param listener item的点击监听
     * @author lion
     */
    private FinanceProductSortDialog financeProductSortDialog;

    public void showPopWindowProductListSort(final Context context, List<DataFinancialCategoryBean> datas, final ViewOnClick listener) {
        financeProductSortDialog =
                new FinanceProductSortDialog(context, datas, listener);
    }

    /**
     * 说明弹窗
     *
     * @param context  上下文对象
     * @param listener 点击监听
     */
    private PopupWindow instructionPopupWindow;

    public void showInstructionDetailPopupWindow(final Context context, View show, String title, CharSequence contentTxt) {
        final View view = View.inflate(context, R.layout.instruction_detail_dialog_layout, null);
        instructionPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        instructionPopupWindow.setFocusable(false);
        instructionPopupWindow.setOutsideTouchable(false);
        instructionPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        instructionPopupWindow.showAsDropDown(show);
        TextView instruction_title = (TextView) view.findViewById(R.id.instruction_title);
        TextView instruction_txt = (TextView) view.findViewById(R.id.instruction_txt);
        ImageView close_iv = (ImageView) view.findViewById(R.id.close_iv);
        if (!TextUtils.isEmpty(title))
            instruction_title.setText(title);
        if (!TextUtils.isEmpty(contentTxt))
            instruction_txt.setText(contentTxt);
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionPopupWindow.dismiss();
            }
        });
    }

    /**
     * 单个按钮左对齐弹出框
     *
     * @param context
     * @param title    标题
     * @param center   内容
     * @param btn_one  按钮文字
     * @param listener
     */
    public void showLeftOnebtnDialog(Context context, String title, String center, String btn_one, OnDialogClickListener listener) {
        if (customizeDialog != null && !customizeDialog.isShowing()) {
            customizeDialog.show();
        } else {
            new CustomizeDialog(context, title, center, btn_one, listener);
        }

    }

    /**
     * 单个按钮左对齐弹出框
     *
     * @param context
     * @param title    标题
     * @param center   内容
     * @param btn_one  按钮文字
     * @param listener
     */
    public void showNormalLeftOneBtnDialog(Context context, String title, String center, String btn_one, OnDialogClickListener listener) {
        NormalLeftDialog normalLeftDialog = null;
        normalLeftDialog = new NormalLeftDialog(context, title, center, btn_one, listener);

    }

    /**
     * 单个按钮左对齐弹出框
     *
     * @param context
     * @param title    标题
     * @param center   内容
     * @param leftBtn  rightBtn 按钮文字
     * @param listener
     */
    public void showNormalLeftTwoBtnDialog(Context context, String title, String center, String leftBtn, String rightBtn, boolean isShowProgressBar, OnDialogClickListener listener) {
        NormalLeftDialog normalLeftDialog = null;
        normalLeftDialog = new NormalLeftDialog(context, title, center, leftBtn, rightBtn, isShowProgressBar, listener);

    }

    private ShowAnnouncementDialog showAnnouncementDialog;

    /**
     * 停服公告弹窗
     *
     * @param context
     * @param code    返回码
     * @param url     地址url
     */
    public boolean showStopDialogShow(final Context context, String code, @NonNull final String url) {
        if (TextUtils.equals("1000", code)) {
            if (showAnnouncementDialog == null) {
                showAnnouncementDialog = new ShowAnnouncementDialog(context, url, new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        if (id == 1) {
                            ActivityManage.create().AppExit();
                        }
                    }
                }, true);
            } else {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed() && !showAnnouncementDialog.isShowing())
                    showAnnouncementDialog.show();
            }
            return true;
        }
        return false;
    }

    /**
     * 续投提示
     *
     * @param context
     * @param titleStr  标题
     * @param headStr   头部文字
     * @param centerStr 中间文字
     * @param btn_one   点击事件文字
     * @param listener
     */
    public void showContinuousDialog(Context context, String titleStr, String headStr, String centerStr, String btn_one, String continuousUrl, OnDialogClickListener listener) {
//        if (continuousDialog != null && !continuousDialog.isShowing()) {
//            continuousDialog.show();
//        } else {
//            continuousDialog = new ContinuousDialog(context, titleStr, headStr, centerStr, btn_one, continuousUrl, listener);
//        }
        new ContinuousDialog(context, titleStr, headStr, centerStr, btn_one, continuousUrl, listener);
    }


    /**
     * 通用两个按钮弹窗，样式为 标题--内容--取消--确定
     *
     * @param context
     * @param gravity  内容的显示对齐方式
     * @param title    标题 文本
     * @param content  内容 文本
     * @param left     左按钮文本
     * @param ringht   有按钮文本
     * @param listener 回调监听
     * @param args     args[0] 是否点击屏幕可以消失 false点击不消失，否则反之
     */
    public void showNormolDialog(final Context context, int gravity, final String title, String content, final String left, String ringht, final OnDialogClickListener listener, boolean... args) {
        mContext = context;
        final boolean cancelable = args != null && args.length > 0 ? args[0] : true;
        View view = View.inflate(context, R.layout.dialog_content_left, null);
        ViewHolder holder = new ViewHolder(view);
        dialog = new DialogPlus.Builder(context)
                .setContentHolder(holder).setCancelable(cancelable)
                .setBackgroundColorResourceId(R.drawable.transparent)
                .setMargins(100, 20, 100, 20)
                .setGravity(DialogPlus.Gravity.CENTER).create();

        TextView titleTv = (TextView) view.findViewById(R.id.tv_title);
        TextView contentTv = (TextView) view.findViewById(R.id.tv_center);
        TextView leftTv = (TextView) view.findViewById(R.id.left_text);
        TextView rightTv = (TextView) view.findViewById(R.id.right_text);

        contentTv.setGravity(gravity);
        titleTv.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        titleTv.setText(TextUtils.isEmpty(title) ? "" : title);
        contentTv.setText(TextUtils.isEmpty(content) ? "" : Html.fromHtml(content));
        if (!TextUtils.isEmpty(left))
            leftTv.setText(left);
        if (!TextUtils.isEmpty(ringht))
            rightTv.setText(ringht);

        // 左按钮事件
        leftTv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (listener != null)
                            listener.onClick(0, v);
                    }
                });
        //右按钮事件
        rightTv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cancelable)
                            dialog.dismiss();
                        if (listener != null)
                            listener.onClick(1, v);
                    }
                });

        dialog.show();

    }

    /**
     * 通用两个按钮弹窗 默认文本居中对齐
     *
     * @param context
     * @param title
     * @param content
     * @param left
     * @param ringht
     * @param listener
     */
    public void showNormolDialog(final Context context, final String title, String content, final String left, String ringht, final OnDialogClickListener listener, boolean... args) {
        showNormolDialog(context, Gravity.CENTER, title, content, left, ringht, listener, args);
    }

    /**
     * 通用两个按钮 取消和确定弹窗 默认文本居中对齐
     *
     * @param context
     * @param title
     * @param content
     * @param listener
     */
    public void showNormolDialog(final Context context, final String title, String content, final OnDialogClickListener listener, boolean... args) {
        showNormolDialog(context, Gravity.CENTER, title, content, null, null, listener, args);
    }

    /**
     * 活动弹窗
     *
     * @param context
     * @param title
     * @param link            活动详情链接
     * @param activityContent
     */
    public void showActivityDialog(Context context, final String title, String activityContent, final String link, boolean cancleable) {

        showNormolDialog(context, Gravity.LEFT | Gravity.CENTER_VERTICAL, title, activityContent, context.getResources().getString(R.string.txt_close),
                context.getResources().getString(R.string.text_check_activity_detail),
                new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        if (id == 1) {
                            Intent intent = new Intent(mContext, WebViewMore.class);
                            intent.putExtra(WebViewMore.INTENT_KEY_TITLE, title);
                            intent.putExtra(WebViewMore.INTENT_KEY_URL, link);
                            mContext.startActivity(intent);
                        }
                    }
                }, cancleable);

    }

    /**
     * 提示是否拨打电话弹窗
     */
    public void showCall(final Context context) {
        showNormolDialog(context, context.getResources().getString(R.string.txt_tips),
                context.getResources().getString(R.string.more_photo),
                new OnDialogClickListener() {
                    @Override
                    public void onClick(int id, View v) {
                        if (id == 1) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4008318999"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });
    }

}
