package com.example.administrator.lmw.finance.utils;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.administrator.lmw.utils.ALLog;


/**
 * 倒计时 返回 02 02 02 的格式
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/27 15:56
 **/
public class TimeCount extends CountDownTimer {
    private String id;
    private Context mContext;
    private TimeDownListener mListener;
    private long mCountDownInterval;


    public TimeCount(Context context, long millisInFuture, long countDownInterval, TimeDownListener listener) {
        super(millisInFuture, countDownInterval);
        this.mContext = context;
        this.mCountDownInterval = countDownInterval;
        this.mListener = listener;
    }

    public TimeCount(Context context, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mContext = context;
        this.mCountDownInterval = countDownInterval;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setListener(TimeDownListener listener) {
        mListener = listener;
    }

    @Override
    public void onFinish() {
        if (mListener != null) {
            mListener.onTimer(id, "--", "--", "--", "--", true);
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
                mListener.onTimer(id, formatDays(millisUntilFinished), formatHours(millisUntilFinished), formatMinutes(millisUntilFinished % 3600000), formatSeconds(millisUntilFinished % 3600000 % 60000), false);
            }

        }
    }

    /**
     * 处理天
     *
     * @param millisUntilFinished
     * @return
     */
    private String formatDays(long millisUntilFinished) {
        int d = (int) millisUntilFinished / (3600 * 24) / (int) mCountDownInterval;
        ALLog.e("=========================" + d);
        if (d <= 0) {
            return String.format("%s", 0);
        }
        return String.valueOf(d);
    }

    /**
     * 处理小时
     *
     * @param millisUntilFinished
     * @return
     */
    private String formatHours(long millisUntilFinished) {
        int h = (int) millisUntilFinished / 3600 / (int) mCountDownInterval % 24;
        if (h < 10) {
            return String.format("0%s", h);
        }
        return String.valueOf(h);
    }

    /**
     * 处理分
     *
     * @param millisUntilFinished
     * @return
     */
    private String formatMinutes(long millisUntilFinished) {
        int m = (int) millisUntilFinished / 60 / (int) mCountDownInterval;
        if (m < 10) {
            return String.format("0%s", m);
        }
        return String.valueOf(m);
    }

    /**
     * 处理秒
     *
     * @param millisUntilFinished
     * @return
     */
    private String formatSeconds(long millisUntilFinished) {
        int s = (int) millisUntilFinished / (int) mCountDownInterval;
        if (s < 10) {
            return String.format("0%s", s);
        }
        return String.valueOf(s);
    }


    public interface TimeDownListener {
        /**
         * 02:02:02
         *
         * @param h        02
         * @param m        02
         * @param s        02
         * @param isFinish 是否结束
         */
         void onTimer(String msgID, String d, String h, String m, String s, boolean isFinish);
    }


}
