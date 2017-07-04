package com.example.administrator.lmw.select.utils;

import java.text.DecimalFormat;

/**
 * 首页人数格式化
 * Created by Administrator on 2016/11/15 0015.
 */
public class NumberWith {

    private static DecimalFormat df = new DecimalFormat("###");

    private static int NUMBER_ZERO = 0;

    private static int NUMBER_FOUR = 4;

    private static int NUMBER_EIGHT = 8;

    public static String dealWith(String number) {
        if (number.length() <= NUMBER_FOUR) {
            return number;
        } else if (number.length() <= NUMBER_EIGHT) {
            if (Double.parseDouble(number.substring(number.length() - NUMBER_FOUR, number.length())) == NUMBER_ZERO) {
                return number.substring(NUMBER_ZERO, number.length() - NUMBER_FOUR) + "万";
            } else {
                return number.substring(NUMBER_ZERO, number.length() - NUMBER_FOUR) +
                        "万" + df.format(Double.parseDouble(number.substring(number.length() - NUMBER_FOUR, number.length())));
            }
        } else {
            if (Double.parseDouble(number.substring(number.length() - NUMBER_EIGHT, number.length() - NUMBER_FOUR)) == NUMBER_ZERO) {
                if (Double.parseDouble(number.substring(number.length() - NUMBER_FOUR, number.length())) == NUMBER_ZERO) {
                    return number.substring(NUMBER_ZERO, number.length() - NUMBER_EIGHT);
                } else {
                    return number.substring(NUMBER_ZERO, number.length() - NUMBER_EIGHT) +
                            "亿" + df.format(Double.parseDouble(number.substring(number.length() - NUMBER_FOUR, number.length())));
                }
            } else {
                if (Double.parseDouble(number.substring(number.length() - NUMBER_FOUR, number.length())) == NUMBER_ZERO) {
                    return number.substring(NUMBER_ZERO, number.length() - NUMBER_EIGHT) +
                            "亿" + df.format(Double.parseDouble(number.substring(number.length() - NUMBER_EIGHT, number.length() - NUMBER_FOUR))) +
                            "万";
                } else {
                    return number.substring(NUMBER_ZERO, number.length() - NUMBER_EIGHT) +
                            "亿" + df.format(Double.parseDouble(number.substring(number.length() - NUMBER_EIGHT, number.length() - NUMBER_FOUR))) +
                            "万" + df.format(Double.parseDouble(number.substring(number.length() - NUMBER_FOUR, number.length())));
                }
            }

        }

    }
}
