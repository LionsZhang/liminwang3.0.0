package com.example.administrator.lmw.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 倒计时TextView
 *
 * @author snubee
 * @time 2016/10/9 16:44
 */
public class TimerTextView extends TextView {
    //倒计进行时
    private static final int HANDLER_TIME_DOWN = 0;
    //倒计结束
    private static final int HANDLER_TIME_OVER = 1;
    // 时间变量
    private int day, hour, minute, second;
    // 当前计时器是否运行
    private boolean isRun = false;
    //倒计时提示,默认购买倒计时
    private String timeTips = "购买倒计时 ";
    //是否显示天
    private boolean isDayTimes = true;
    //是否显示文字时间
    private boolean isTextTimes = true;
    //剩余倒计时间
    private long millisInFuture;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_TIME_DOWN:
                    mHandler.removeMessages(HANDLER_TIME_DOWN);
                    countdown();
                    setText(showTime());
                    millisInFuture = millisInFuture - 1000;
                    mHandler.sendEmptyMessageDelayed(HANDLER_TIME_DOWN, 1000);
                    break;
                case HANDLER_TIME_OVER:
                    callBackFinish();
                    break;
            }
        }
    };

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context) {
        super(context);
    }

    /**
     * 获取剩余时间
     *
     * @return
     */
    public long getMillisInFuture() {
        return millisInFuture;
    }

    /**
     * 设置倒计时提示
     *
     * @param timeTips
     */
    public void setTimeTips(String timeTips) {
        this.timeTips = timeTips;
    }

    /**
     * 设置显示倒计时的样式
     *
     * @param isTextTimes trur 0天01时30分05秒  false 30:04:59
     * @param isDayTimes  是否显示天
     */
    public void setTimeStyle(boolean isTextTimes, boolean isDayTimes) {
        this.isTextTimes = isTextTimes;
        this.isDayTimes = isDayTimes;
    }

    /**
     * 将倒计时时间毫秒数转换为自身变量
     *
     * @param time 时间间隔毫秒数
     */
    public void setTimes(long time) {
        if (time < 0)
            time = 0;
        this.millisInFuture = time;
        //将毫秒数转化为时间
        this.second = (int) (time / 1000) % 60;
        this.minute = (int) (time / (60 * 1000) % 60);
        this.hour = (int) (time / (60 * 60 * 1000) % 24);
        if (isDayTimes)
            this.day = (int) (time / (60 * 60 * 1000 * 24));

        setText(showTime());
    }

    /**
     * 显示当前时间
     *
     * @return
     */
    public String showTime() {
        StringBuilder time = new StringBuilder();
        time.append(timeTips);
        if (isDayTimes)
            time.append(day).append("天");
        if (hour < 10) {
            time.append("0");
        }
        time.append(hour).append(isTextTimes ? "时" : ":");
        if (minute < 10) {
            time.append("0");
        }
        time.append(minute).append(isTextTimes ? "分" : ":");
        if (second < 10) {
            time.append("0");
        }
        time.append(second).append(isTextTimes ? "秒" : ":");


        return time.toString();
    }

    /**
     * 实现倒计时
     */
    private void countdown() {
        if (second == 0) {
            if (minute == 0) {
                if (hour == 0) {
                    //当时间归零时停止倒计时
                    isRun = false;
                    mHandler.sendEmptyMessage(HANDLER_TIME_OVER);
                    return;
                } else {
                    hour--;
                }
                minute = 59;
            } else {
                minute--;
            }
            second = 60;
        }

        second--;
    }

    public boolean isRun() {
        return isRun;
    }

    /**
     * 开始计时
     */
    public void start() {
        isRun = true;
        run();
    }

    /**
     * 结束计时
     */
    public void stop() {
        //重置所有参数配置
        isRun = false;
        day = 0;
        hour = 0;
        minute = 0;
        second = 0;
        millisInFuture = 0;
        if (mHandler != null) {
            mHandler.removeMessages(HANDLER_TIME_DOWN);
            mHandler.removeCallbacks(null);
        }
    }

    /**
     * 实现计时循环
     */
    private void run() {
        if (isRun) {
            mHandler.sendEmptyMessage(HANDLER_TIME_DOWN);
        } else {
            mHandler.sendEmptyMessage(HANDLER_TIME_OVER);
        }
    }

    private void callBackFinish() {
        millisInFuture = 0;
        if (mOnTimeDownListener != null) {
            mOnTimeDownListener.isFinish(TimerTextView.this);
        }
        mHandler.removeCallbacks(null);
    }


    private OnTimeDownListener mOnTimeDownListener;

    /**
     * 设置倒计时是否完成监听
     *
     * @param onTimeDownListener
     */
    public void setOnTimeDownListener(OnTimeDownListener onTimeDownListener) {
        mOnTimeDownListener = onTimeDownListener;
    }

    /**
     * 倒计时监听器
     */
    public interface OnTimeDownListener {
        void isFinish(TimerTextView v);
    }

}
