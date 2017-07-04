package com.example.administrator.lmw.finance.utils;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class CustViewPage extends ViewPager {

    public CustViewPage(Context context) {
        super(context);
    }

    public CustViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

//        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            View view = viewOfClickOnScreen(ev);
//            if (view != null) {
//                setCurrentItem(indexOfChild(view));
//            }
//        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param ev
     * @return
     */
    private View viewOfClickOnScreen(MotionEvent ev) {
        int childCount = getChildCount();
        int[] location = new int[2];
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.getLocationOnScreen(location);
            int minX = location[0];
            int minY = getTop();

            int maxX = location[0] + v.getWidth();
            int maxY = getBottom();

            float x = ev.getX();
            float y = ev.getY();

            if ((x > minX && x < maxX) && (y > minY && y < maxY)) {
                return v;
            }
        }
        return null;
    }

}
