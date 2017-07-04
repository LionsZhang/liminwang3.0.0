package com.example.administrator.lmw.select.entity.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * 数字工具类
 * <p/>
 * Created by Administrator on 2016/8/29 0029.
 */
public class NumberDeal {

    //此方法返回的数字类型如：45,869,558.00
    public static String NumberDeal(String num) {
        double str = Double.valueOf(num).doubleValue();
        NumberFormat number_format = NumberFormat.getInstance(Locale.CHINA);
        return number_format.format(str);
    }
}
