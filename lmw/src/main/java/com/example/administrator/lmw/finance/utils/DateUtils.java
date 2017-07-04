package com.example.administrator.lmw.finance.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * <p/>
 * Created by Administrator on 2016/9/10 0010.
 */
public class DateUtils {

    private static SimpleDateFormat simpleDateFormat;

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getStringToDateDay(String time) {

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
