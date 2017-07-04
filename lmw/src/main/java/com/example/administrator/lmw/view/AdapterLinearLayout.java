package com.example.administrator.lmw.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 *  线性布局动态适配器添加控件
 *  @author snubee
 *  @email snubee96@gmail.com
 *  @time 2017/3/8 16:18
 *
**/
public class AdapterLinearLayout extends LinearLayout {

    private BaseAdapter mAdapter;
    private AdapterDataSetObserver mDataSetObserver;

    public AdapterLinearLayout(Context context) {
        super(context);
    }

    public AdapterLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdapterLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setAdapter(BaseAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        this.mAdapter = adapter;
        if(mAdapter != null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
        bindDataToView();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAdapter != null && mDataSetObserver == null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    private void bindDataToView() {
        if(mAdapter == null ) return;
        this.removeAllViews();
        for(int i=0;i<mAdapter.getCount();i++) {
            View child = mAdapter.getView(i, null, this);
            this.addView(child,i);
        }
    }




    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            bindDataToView();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }

}
