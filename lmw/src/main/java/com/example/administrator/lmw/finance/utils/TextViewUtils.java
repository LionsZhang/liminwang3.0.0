package com.example.administrator.lmw.finance.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.utils.DensityUtil;

/**
 * Textview文本工具类
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/25 12:45
 **/
public class TextViewUtils {

    /**
     * 标的到期的天数
     *
     * @param deadLineType
     */
    public static void setDeadLineType(TextView v, int deadLineType) {
        switch (deadLineType) {
            case 1:
                v.setText("天");
                break;
            case 2:
                v.setText("个月");
                break;
            case 3:
                v.setText("年");
                break;
            default:
                v.setText("");
                break;
        }
    }

    /**
     * 标的天数
     *
     * @param deadLineType
     */
    public static void setDeadLineType(Context context, TextView v, String value, int deadLineType) {
        switch (deadLineType) {
            case 1:
                v.setText(String.format("%s%s", value, context.getResources().getString(R.string.txt_day)));
                break;
            case 2:
                v.setText(String.format("%s%s", value, context.getResources().getString(R.string.txt_month)));
                break;
            case 3:
                v.setText(String.format("%s%s", value, context.getResources().getString(R.string.txt_year)));
                break;
            default:
                v.setText(String.format("%s%s", value, context.getResources().getString(R.string.txt_day)));
                break;
        }
    }

    /**
     * 得到一个改变指定大小字体颜色的Spannable
     *
     * @param color    颜色 int
     * @param textSize 字体大小
     * @param allStr   需要设置的整个字符串
     * @param num      传入的指定改变颜色的字符
     * @return
     */
    public static SpannableStringBuilder getSizeSpannBuilder(Context context, int color, int textSize, String allStr, String... num) {
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(builder);
        if (num != null && num.length > 0) {
            int index = 0 - num[0].length();
            String last = "";
            for (int i = 0; i < num.length; i++) {
                if (i == 0) {
                    index = builder.indexOf(num[i], 0);
                } else {
                    index = builder.indexOf(num[i], index + last.length());
                }
                if (index != -1) {
                    builderSpannable.setSpan(new ForegroundColorSpan(color),
                            index,
                            index + num[i].length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builderSpannable.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(context,
                            textSize)),
                            index,
                            index + num[i].length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                last = num[i];
            }
        }
        return builderSpannable;
    }

    /**
     * 得到一个改变指定字体颜色的Spannable
     *
     * @param color  颜色 int
     * @param allStr 需要设置的整个字符串
     * @param num    传入的指定改变颜色的字符
     * @return
     */
    public static SpannableStringBuilder getColorSpannBuilder(int color, String allStr, String... num) {
        StringBuilder builder = new StringBuilder(allStr);
        SpannableStringBuilder builderSpannable = new SpannableStringBuilder(builder);

        if (num != null && num.length > 0) {
            int index = 0 - num[0].length();
            String last = "";
            for (int i = 0; i < num.length; i++) {
                if (i == 0) {
                    index = builder.indexOf(num[i], 0);
                } else {
                    index = builder.indexOf(num[i], index + last.length());
                }
                if (index != -1) {
                    builderSpannable.setSpan(new ForegroundColorSpan(color),
                            index,
                            index + num[i].length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                last = num[i];
            }

        }

        return builderSpannable;
    }


    /**
     * 去除字符串的括号和数组，如 现金券(10) --> 现金券
     * [\s|-]*\(?[\s|-]*[0-9]\d*\.?\d*[\s|-]*\)[\s|-]*?
     * @param text
     * @return
     */
    public static String getIgnoreNumber(String text) {
        if (!TextUtils.isEmpty(text)) {
            return text.replaceAll("\\(?[0-9]\\d*\\.?\\d*\\)?", "");
        }
        return "";
    }

    /**
     * 判断传入的对象是否为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(Object value) {
        if (value != null && !"".equals(value.toString()) && !"[]".equals(value.toString()) && !"null".equals(value.toString()))
            return false;
        return true;
    }

}
