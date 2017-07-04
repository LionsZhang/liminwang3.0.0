package com.example.administrator.lmw.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by lion on 2017/2/20.
 */

public class BigDecemalCalculateUtil {
    /**
     * 将数字向左移动四位
     *
     * @return
     */

    public static String mathLeftMove4(String math) {
        if (TextUtils.isEmpty(math)) return "";

        BigDecimal bg1, bg2;
        bg1 = new BigDecimal(math);
        bg2 = bg1.movePointLeft(4); // 4 points left
        String f1 = bg2.setScale(1, BigDecimal.ROUND_DOWN).toString();
        return f1;
    }

    /**
     * 截取数字字符串
     *
     * @param math  有小数的数字字符串
     * @param index 0 取整数部分，1取小数部分
     * @return
     */

    public static String subNumberString(String math, int index) {
        if (TextUtils.isEmpty(math)) return "";
        String[] labelsText;
        if (math.contains(".")) {
            labelsText = math.split("\\.");
            if (index == 0)
                return labelsText[0];
            if (index == 1)
                return "." + labelsText[1];
        }
        return "";

    }

    /**
     * 将数字向左移动四位
     *
     * @return
     */

    public static String showWan(String math) {
        if (TextUtils.isEmpty(math)) return "";

        BigDecimal bg1, bg2;
        if (Double.valueOf(math) > 10000) {
            bg1 = new BigDecimal(math);
            bg2 = bg1.movePointLeft(4); // 4 points left
            String f1 = bg2.setScale(2, BigDecimal.ROUND_DOWN).toString();
            return f1 + "万";
        }
        return math;
    }

    /**
     * @return 对数字大于1000 格式化
     */

    public static String showWanExact(String math) {
        if (TextUtils.isEmpty(math)) return "";
        double dmath = Double.valueOf(math);
        if (dmath > 10000) {
            if (dmath % 10000 == 0) {
                return getInteger(dmath / 10000 + "") + "万";
            } else {
                return dmath / 10000 + "万";
            }
        } else if (dmath > 1000) {
            if (dmath % 1000 == 0) {
                return getInteger(math);
            }
        }

        return math;
    }

    /**
     * @return
     */

    public static float BigDecemalDivide(String totalMoney, String remaMoney) {
        if (TextUtils.isEmpty(totalMoney)) return 0f;
        if (TextUtils.isEmpty(remaMoney)) return 0f;

        BigDecimal totalMoneyBd, remaMoneyBd, resultBd;
        if (Double.valueOf(totalMoney) != 0) {
            totalMoneyBd = new BigDecimal(totalMoney);
            remaMoneyBd = new BigDecimal(remaMoney);
            resultBd = (totalMoneyBd.subtract(remaMoneyBd)).divide(totalMoneyBd, 5, BigDecimal.ROUND_DOWN).
                    multiply(new BigDecimal("100"));
            return resultBd.setScale(1, BigDecimal.ROUND_DOWN)
                    .floatValue();
        } else
            return 0.0f;

    }

    /**
     * 取整
     *
     * @return
     */

    public static String getInteger(String math) {
        if (TextUtils.isEmpty(math)) return "";

        BigDecimal bg1;
        bg1 = new BigDecimal(math);
        String f1 = bg1.setScale(0, BigDecimal.ROUND_DOWN).toString();
        return f1;
    }

    /**
     * 取整
     *
     * @return
     */

    public static int formatInteger(String math) {
        if (TextUtils.isEmpty(math)) return 0;
        BigDecimal bg1;
        bg1 = new BigDecimal(math);
        return bg1.setScale(0, BigDecimal.ROUND_DOWN).intValue();
    }

    /**
     * 两数相减保留小数点
     *
     * @param minuend    减数
     * @param subtracted 被减数
     * @param digit      保留小数点
     * @return
     */
    public static float subtract(String minuend, String subtracted, int digit) {
        if (TextUtils.isEmpty(minuend)) return 0f;
        if (TextUtils.isEmpty(subtracted)) return 0f;

        BigDecimal minuendBd, subtractedBd, resultBd;

        minuendBd = new BigDecimal(minuend);
        subtractedBd = new BigDecimal(subtracted);
        resultBd = minuendBd.subtract(subtractedBd);
        return resultBd.setScale(digit, BigDecimal.ROUND_DOWN).floatValue();

    }

    /**
     * 两数相减保留小数点
     *
     * @param minuend    减数
     * @param subtracted 被减数
     * @param digit      保留小数点
     * @return
     */
    public static String subtractToString(String minuend, String subtracted, int digit) {
        if (TextUtils.isEmpty(minuend)) return "";
        if (TextUtils.isEmpty(subtracted)) return "";

        BigDecimal minuendBd, subtractedBd, resultBd;

        minuendBd = new BigDecimal(minuend);
        subtractedBd = new BigDecimal(subtracted);
        resultBd = minuendBd.subtract(subtractedBd);
        if (digit <= 0)
            return resultBd.toString();
        else
            return getDecimalFormat(digit).format(resultBd);

    }

    /**
     * 将数字向左移动四位
     *
     * @param divisor  除数
     * @param dividend 被除数
     * @param digit    保留位数,-1保留所有小数点后的位数
     * @return
     */
    public static float divide(String divisor, String dividend, int digit) {
        if (TextUtils.isEmpty(dividend)) return 0f;
        if (TextUtils.isEmpty(divisor)) return 0f;

        BigDecimal divisor_, dividend_, resultBd;
        if (Double.valueOf(divisor) != 0) {
            divisor_ = new BigDecimal(divisor);
            dividend_ = new BigDecimal(dividend);
            resultBd = dividend_.divide(divisor_, 5, BigDecimal.ROUND_DOWN).
                    multiply(new BigDecimal("100"));
            if (digit <= 0)
                return resultBd.floatValue();
            else
                return resultBd.setScale(digit, BigDecimal.ROUND_DOWN).floatValue();
        } else
            return 0.0f;

    }

    /**
     * 将数字向左移动四位
     *
     * @param divisor  除数
     * @param dividend 被除数
     * @param digit    保留位数,-1保留所有小数点后的位数
     * @return
     */
    public static float divide(long divisor, long dividend, int digit) {
        BigDecimal divisor_, dividend_, resultBd;
        if (Double.valueOf(divisor) != 0) {
            divisor_ = new BigDecimal(String.valueOf(divisor));
            dividend_ = new BigDecimal(String.valueOf(dividend));
            resultBd = dividend_.divide(divisor_, 5, BigDecimal.ROUND_DOWN).
                    multiply(new BigDecimal("100"));
            if (digit <= 0)
                return resultBd.floatValue();
            else
                return resultBd.setScale(digit, BigDecimal.ROUND_DOWN).floatValue();
        } else
            return 0.0f;

    }

    /**
     * 获取BigDecimal的小数位数格式
     *
     * @param digit
     * @return
     */
    public static DecimalFormat getDecimalFormat(int digit) {
        StringBuffer sb = new StringBuffer(6);
        sb.append("#0");
        for (int i = digit; i > 0; i--) {
            if (i == digit)
                sb.append(".");
            sb.append(0);
        }
        return new DecimalFormat(sb.toString());
    }


    /**
     * 返回格式化后的字符串不带单位，digit是小数点位数，如果要保留所有万后的位数传-1，传入2返回如5.21，传入-1，返回的格式有可能如下：5,5.1，5.12,5.1234会把后面的0剔除
     * 如10000会格式为1，调用formatValue（10000,4，-1）；
     * formatValue（10001,4，-1）则会返回1.0001
     * formatValue（10100,4，-1）则会返回1.01
     * formatValue（10001,4，2）则会返回1.00
     *
     * @param value  要格式化的数字字符串
     * @param offset 小数点左移的位数
     * @param digit  保留小数位数
     * @return
     */
    public static String formatValue(String value, int offset, int digit) {
        if (TextUtils.isEmpty(value)) return "";
        if ("null".equals(value)) return value;

        BigDecimal bg1, bg2;
        bg1 = new BigDecimal(value);
        if (offset == 0) {
            return getDecimalFormat(digit).format(bg1.setScale(digit, BigDecimal.ROUND_DOWN));
        } else {
            StringBuffer sb = new StringBuffer(8);
            sb.append(1);
            for (int i = 0; i < offset; i++) {
                sb.append(0);
            }
            if (bg1.compareTo(new BigDecimal(sb.toString())) == 1 || bg1.compareTo(new BigDecimal(sb.toString())) == 0) {
                bg2 = bg1.movePointLeft(offset);
                if (digit <= 0) {
//                    if (bg2.setScale(offset).intValue() == bg2.setScale(offset).floatValue())
                    return String.valueOf(bg2.setScale(offset).intValue());
//                    else
//                        return String.valueOf(bg2.setScale(offset).floatValue());
                } else
                    return getDecimalFormat(digit).format(bg2.setScale(digit, BigDecimal.ROUND_DOWN));
            }
        }
        return value;
    }

    /**
     * 返回格式化后的字符串不带单位，digit是小数点位数，如果要保留所有万后的位数传-1，传入2返回如5.21，传入-1，返回的格式有可能如下：5,5.1，5.12,5.1234会把后面的0剔除
     *
     * @param value  要格式化的数字
     * @param offset 小数点左移的位数
     * @param digit  保留小数位数
     * @return
     */
    public static String formatValue(long value, int offset, int digit) {

        return formatValue(String.valueOf(value), offset, digit);
    }

    /**
     * 首页万位截取并格式化
     *
     * @param value 要格式化的数字
     * @return
     */
    public static String formatMillionValue(String value) {

        String m = formatValue(value, 4, 0);
        if (!TextUtils.isEmpty(m) && m.length() > 4) {//过亿
            return formatValue(m.substring(m.length() - 4, m.length()), 0, 0);
        } else {
            return m;
        }
    }

    /**
     * 首页个位截取并格式化 保留两位小数
     *
     * @param value 要格式化的数字
     * @return
     */
    public static String formatThousandValue(String value) {

        String m = formatValue(value, 0, 2);
        if (!TextUtils.isEmpty(m) && m.length() > 7) {//过万
            return formatValue(m.substring(m.length() - 7, m.length()), 0, 2);
        } else {
            return m;
        }
    }

    /**
     * 比较两个数字大小
     *
     * @param num1
     * @param num2
     * @return 0相等 .1是num1>num2 ,-1是num1<num2 ,-2 数字异常无法比较
     */
    public static int compareTo(String num1, String num2) {
        if (!TextUtils.isEmpty(num1) && !TextUtils.isEmpty(num2))
            return new BigDecimal(num1).compareTo(new BigDecimal(num2));
        return -2;
    }

}
