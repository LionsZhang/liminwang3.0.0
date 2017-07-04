package com.example.administrator.lmw.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 *  嵌套内部listview 
 *  @author snubee
 *  @email snubee96@gmail.com
 *  @time 2017/4/11 11:45
 *
**/
public class InnerListView extends ListView {

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    public InnerListView(Context context) {
        super(context);
    }

    public InnerListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e("snubee","onInterceptTouchEvent");

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("snubee","onTouchEvent");
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            //当手指在屏幕滑动的时候
            x2 = event.getX();
            y2 = event.getY();
            boolean isUpSlip = Math.abs(y1-y2)>Math.abs(x1-x2)&&Math.abs(y1-y2)>=5;
            boolean isDownSlip = Math.abs(y2-y1)>Math.abs(x2-x1)&&Math.abs(y2-y1)>=5;
            if(isUpSlip || isDownSlip) {//上下滑动
                // 通知父view是否要处理touch事件
                Log.e("snubee","通知父view是否要处理touch事件");
                getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                getParent().requestDisallowInterceptTouchEvent(false);

            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 此方法会造成感觉卡顿的感觉，使用适配器计算高度适配
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
