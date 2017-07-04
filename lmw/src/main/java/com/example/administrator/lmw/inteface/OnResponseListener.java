package com.example.administrator.lmw.inteface;

/**
 * Created by lion on 2016/8/25.
 */
public interface OnResponseListener<T> {

    void onSuccess(T result);
    void onFail(Throwable e);

}
