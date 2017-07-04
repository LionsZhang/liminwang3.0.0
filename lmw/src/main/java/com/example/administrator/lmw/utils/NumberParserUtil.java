package com.example.administrator.lmw.utils;

/**
 * 数字转换工具类
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/7 9:42
 **/
public class NumberParserUtil {


    /**
     * 转换成int型数字
     *
     * @param value   转化的数字字符串
     * @param deafult 转化异常后返回的默认值
     * @return
     */
    public static int parserInt(String value, int deafult) {

        if (value == null || "".equals(value.toString().trim()))
            return deafult;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();

            try {
                return (int) Double.parseDouble(value);
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
                return deafult;
            }

        }

    }

    public static int parserInt(String value) {

        return parserInt(value, 0);
    }

    /**
     * 转换成double型数字
     *
     * @param value   转化的数字字符串
     * @param deafult 转化异常后返回的默认值
     * @return
     */
    public static double parserDouble(String value, double deafult) {

        if (value == null || "".equals(value.toString().trim()))
            return deafult;

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e1) {
            e1.printStackTrace();
            return deafult;
        }

    }

    public static double parserDouble(String value) {

        return parserDouble(value, 0);
    }

    /**
     * 转换成float型数字
     *
     * @param value   转化的数字字符串
     * @param deafult 转化异常后返回的默认值
     * @return
     */
    public static float parserFloat(String value, float deafult) {

        if (value == null || "".equals(value.toString().trim()))
            return deafult;

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e1) {
            e1.printStackTrace();
            return deafult;
        }

    }

    public static float parserFloat(String value) {

        return parserFloat(value, 0);
    }


}
