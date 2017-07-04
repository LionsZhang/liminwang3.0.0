package com.example.administrator.lmw.finance.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

/**
 * 自定义倒计时器
 * Created by Administrator on 2016/1/14.
 */
@SuppressLint({ "ViewConstructor", "SimpleDateFormat" })
public class Anticlockwise extends Chronometer {
    private long mTime;
    private long mNextTime;
    private OnTimeCompleteListener mListener;
    private SimpleDateFormat mTimeFormat, mTimeFormats;


    public Anticlockwise(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTimeFormat = new SimpleDateFormat("dd 天 HH 时 mm 分 ss 秒");
        mTimeFormats = new SimpleDateFormat("HH 时 mm 分 ss 秒");
        this.setOnChronometerTickListener(listener);
    }

    public Anticlockwise(Context context) {
        super(context);

    }

    // 重新启动计时
    public void reStart(long _time_s) {
        if (_time_s == -1) {
            mNextTime = mTime;
        } else {
            mTime = mNextTime = _time_s;
        }
        this.start();
    }

    public void reStart() {
        reStart(-1);
    }

    // 不建议方法名用onResume()或onPause()，容易和activity生命周期混淆
    // 继续计时
    public void onResume() {
        this.start();
    }

    // 暂停计时
    public void onPause() {
        this.stop();
    }


    public void setOnTimeCompleteListener(OnTimeCompleteListener l) {
        mListener = l;
    }

    OnChronometerTickListener listener = new OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (mNextTime <= -115200) {
                if (mNextTime == -115200) {
                    Anticlockwise.this.stop();
                    if (null != mListener)
                        mListener.onTimeComplete();
                }
                mNextTime = -115200;
                updateTimeText();
                return;
            }

            mNextTime--;

            updateTimeText();
        }
    };

    // 初始化时间(秒)
    public void initTime(long _time_s) {
        mTime = mNextTime = _time_s;
        updateTimeText();
    }

    /**
     * // 初始化时间（分秒）
     * @param _time_m
     * @param _time_s
     */
    public void initTime(long _time_m, long _time_s) {
        initTime(_time_m * 60 + _time_s);
    }

    private void updateTimeText() {
        if (mNextTime < -28800) {
            this.setText(mTimeFormats.format(new Date(mNextTime * 1000)));
        } else {
            this.setText(mTimeFormat.format(new Date(mNextTime * 1000)));
        }

    }

    public interface OnTimeCompleteListener {
        void onTimeComplete();
    }

}
