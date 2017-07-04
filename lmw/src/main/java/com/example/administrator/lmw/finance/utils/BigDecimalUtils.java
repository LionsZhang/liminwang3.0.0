package com.example.administrator.lmw.finance.utils;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class BigDecimalUtils {

    /**
     * 向下取余两位
     *
     * @param size
     * @return
     */
    public String bigDecimaldouble(double size) {
        java.math.BigDecimal bd = new java.math.BigDecimal(size);
        String str = bd.setScale(2, java.math.BigDecimal.ROUND_DOWN).toString();
        return str;
    }

    /**
     * 向下取余一位
     *
     * @param size
     * @return
     */
    public String bigDecimalone(double size) {
        java.math.BigDecimal bd = new java.math.BigDecimal(size);
        String str = bd.setScale(1, java.math.BigDecimal.ROUND_DOWN).toString();
        return str;
    }

    /**
     * 向下取余整数
     *
     * @param size
     * @return
     */
    public String bigDecimalzoer(double size) {
        java.math.BigDecimal bd = new java.math.BigDecimal(size);
        String str = bd.setScale(0, java.math.BigDecimal.ROUND_DOWN).toString();
        return str;
    }
}
