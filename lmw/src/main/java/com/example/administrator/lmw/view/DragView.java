package com.example.administrator.lmw.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.administrator.lmw.R;

/**
 * 浮动窗口layout
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/14 11:34
 **/
public class DragView extends LinearLayout {

    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private int startX;
    private int startY;
    private int screenWidth;
    private int screenHeight;
    boolean isShow = false;
    private OnClickListener mClickListener;

    private WindowManager windowManager;

    private WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams();
    private ImageView iv;
    private LinearLayout root;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static DragView mDragView;

    public WindowManager.LayoutParams getWindowManagerParams() {
        return windowManagerParams;
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context c) {
        super(c);
        sharedPreferences = c.getSharedPreferences("APPSET", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView(c);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
    }

    // 初始化窗体
    public void initView(Context c) {
        windowManager = (WindowManager) c
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        View v = LayoutInflater.from(c).inflate(R.layout.layout_dragview, null);
        iv = (ImageView) v.findViewById(R.id.iv_drag);
        root = (LinearLayout) v.findViewById(R.id.root);
        this.addView(v);
        windowManagerParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        windowManagerParams.format = PixelFormat.RGBA_8888; // 背景透明
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值
        int x = screenWidth - v.getMeasuredWidth();
        int y = screenHeight >> 1;
//        /**
//         * 初始化之前保存的位置
//         */
//        if (sharedPreferences.getInt("x", -1) != -1) {
//            x = sharedPreferences.getInt("x", -1);
//        }
//        if (sharedPreferences.getInt("y", -1) != -1) {
//            y = sharedPreferences.getInt("y", -1);
//        }
        windowManagerParams.x = x;
        windowManagerParams.y = y;
        // 设置悬浮窗口长宽数据
        windowManagerParams.width = LayoutParams.WRAP_CONTENT;
        windowManagerParams.height = LayoutParams.WRAP_CONTENT;

    }

    public ImageView getImageView() {
        return iv;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度
        Log.i("snubee", "currX" + x + "====currY" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchX = event.getX();
                mTouchY = event.getY();
                startX = (int) x;
                startY = (int) y;
                Log.i("snubee", "startX" + mTouchX + "====startY" + mTouchY);
                break;
            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                updateViewPosition();
                mTouchX = mTouchY = 0;
                if (Math.abs(x - startX) < 5 && Math.abs(y - startY) < 5) {
                    // 如果的纵坐标和横坐标都<5，则为点击事件
                    if (mClickListener != null) {
                        mClickListener.onClick(this);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    // 隐藏该窗体
    public void hide() {
        if (isShow) {
            windowManager.removeView(this);
            isShow = false;
        }

    }

    // 显示该窗体
    public void show() {
        if (isShow == false) {
            windowManager.addView(this, windowManagerParams);
            isShow = true;
        }

    }

    public void showAtLocation(int[] location) {
        show();
        windowManagerParams.x = location[0];
        windowManagerParams.y = location[1];
        windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    private void updateViewPosition() {
        // 更新浮动窗口位置参数
        windowManagerParams.x = (int) (x - mTouchX);
        windowManagerParams.y = (int) (y - mTouchY);
//        editor.putInt("x", (int) (x - mTouchX));
//        editor.putInt("y", (int) (y - mTouchY));
//        editor.commit();
        Log.i("snubee", windowManagerParams.x + "  -----  " + windowManagerParams.y);
        windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }

    public static DragView getInstance(Context mContext) {
        if (mDragView == null) {
            synchronized (DragView.class) {
                if (mDragView == null) {
                    mDragView = new DragView(mContext);
                }
            }
        }

        return mDragView;
    }


}