package com.example.administrator.lmw.finance.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Method;

/**
 * webview 监听向下滑动是否到底部底部
 * Created by Vasile216 on 2017/4/26.
 */

public class CustBottomView extends WebView {
    private static final int TOUCH_IDLE = 0;
    private static final int TOUCH_INNER_CONSIME = 1; // touch事件由ScrollView内部消费
    private static final int TOUCH_DRAG_LAYOUT = 2; // touch事件由上层的DragLayout去消费

    private int scrollMode;
    private float downX, downY;

    boolean isAtBottom = true; // 如果是true，则允许拖动至底部的下一页
    private int mTouchSlop = 4; // 判定为滑动的阈值，单位是像素

    public CustBottomView(Context arg0) {
        this(arg0, null);
    }

    public CustBottomView(Context arg0, AttributeSet arg1) {
        this(arg0, arg1, 0);
    }

    public CustBottomView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        disableZoomController();

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    // 使得控制按钮不可用
    @SuppressLint("NewApi")
    private void disableZoomController() {
        // API version 大于11的时候，SDK提供了屏蔽缩放按钮的方法
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            this.getSettings().setBuiltInZoomControls(true);
            this.getSettings().setDisplayZoomControls(false);
        } else {
            // 如果是11- 的版本使用JAVA中的映射的办法
            getControlls();
        }
    }

    private void getControlls() {
        try {
            Class<?> webview = Class.forName("android.webkit.WebView");
            Method method = webview.getMethod("getZoomButtonsController");
            ZoomButtonsController zoomController = (ZoomButtonsController) method.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downY = ev.getRawY();
            isAtBottom = isAtBottom();
            scrollMode = TOUCH_IDLE;
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (scrollMode == TOUCH_IDLE) {
                float yOffset = downY - ev.getRawY();
                float yDistance = Math.abs(yOffset);
                if (yDistance > mTouchSlop) {
                    if (yOffset > 0 && isAtBottom) {
                        scrollMode = TOUCH_DRAG_LAYOUT;
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    } else {
                        scrollMode = TOUCH_INNER_CONSIME;
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断WebView是否在底部
     *
     * @return 是否在顶部
     */
    public boolean isAtBottom() {
        return getScrollY() + getMeasuredHeight() >= computeVerticalScrollRange() - 2;
    }
}
