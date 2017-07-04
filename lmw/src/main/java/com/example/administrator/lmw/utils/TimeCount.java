package com.example.administrator.lmw.utils;

import android.content.Context;
import android.os.CountDownTimer;


/**

 **/
public class TimeCount extends CountDownTimer {
    private Context mContext;
    private TimeDownListener mListener;
    private long mCountDownInterval;


    public TimeCount(Context context, long millisInFuture, long countDownInterval, TimeDownListener listener) {
        super(millisInFuture, countDownInterval);
        this.mContext = context;
        this.mCountDownInterval = countDownInterval;
        this.mListener = listener;
    }

    @Override
    public void onFinish() {
        if (mListener != null) {
            mListener.onTimerFininsh();
        }
        cancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // TODO Auto-generated method stub
        if (mContext == null) {
            cancel();
        } else {
            if (mListener != null) {
                mListener.onTimerFininsh();
            }

        }
    }


    public interface TimeDownListener {

        void onTimerFininsh();
    }


}
