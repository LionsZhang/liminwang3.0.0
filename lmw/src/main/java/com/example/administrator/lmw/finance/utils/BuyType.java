package com.example.administrator.lmw.finance.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 购买类型
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/31 15:34
 **/

@StringDef
@Retention(RetentionPolicy.SOURCE)
public @interface BuyType {
    String PROBLEM = "0";//标的
    String CREDIT = "1";//债权
}
