package com.example.administrator.lmw.inteface;

/**
 * 网络接口返回类的拓展类
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/6/23 11:47
 **/
public class OnResponseListenerEx<T> implements OnResponseListener<T> {

    public void onEmptyData(String code, Object... msg) {

    }

    @Override
    public void onSuccess(T result) {

    }

    @Override
    public void onFail(Throwable e) {

    }
}
