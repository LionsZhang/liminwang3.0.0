package com.example.administrator.lmw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.utils.ALLog;


/**
 * 没有右标题图标的控件
 *
 * @author lion
 * @Description:TODO
 * @Date 2016-08-24
 */
public class SettingNextItemNotIconView extends RelativeLayout {
    private ImageView iconIV;

    private ImageView arrowIV, hintIcon;

    private TextView titleTV, hintTV;

    private View divider;

    private boolean isShowDivider = false;

    protected boolean isEnable = true;

    private RigthTextViewListener rightTextListener;

    int showLongLineType = 0;// 0不显示，1显示顶部长线，2显示底部长线,3都显示

    private View topLine, bottomLine;
    private View divide_1;
    private TextView tv_title_r;
    public SettingNextItemNotIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SettingNextView);
        String title = arr.getString(R.styleable.SettingNextView_next_title);
        int iconId = arr.getResourceId(R.styleable.SettingNextView_next_icon, -1);
        int iconHintId = arr.getResourceId(R.styleable.SettingNextView_next_hint_icon, -1);
        int arrowId = arr.getResourceId(R.styleable.SettingNextView_next_arrow, -1);
        String hint = arr.getString(R.styleable.SettingNextView_next_hint);
        isShowDivider = arr.getBoolean(R.styleable.SettingNextView_next_show_divider, false);
        showLongLineType = arr.getInt(R.styleable.SettingNextView_next_long_divider_type, 0);
        int titleTextColor = arr.getColor(R.styleable.SettingNextView_next_title_color, Color.WHITE);
        int hintTextColor = arr.getColor(R.styleable.SettingNextView_next_hint_color, Color.WHITE);
        arr.recycle();

        if (iconId == -1) {
            iconIV.setVisibility(View.GONE);
        } else {
            iconIV.setImageResource(iconId);
        }
        if (arrowId == -1) {
            arrowIV.setVisibility(View.INVISIBLE);
        } else {
            arrowIV.setImageResource(arrowId);
        }
        if (iconHintId==-1){
            hintIcon.setVisibility(View.INVISIBLE);
            hintTV.setVisibility(View.VISIBLE);
        } else {
            hintIcon.setVisibility(View.VISIBLE);
            hintIcon.setImageResource(iconHintId);
            hintTV.setVisibility(View.INVISIBLE);

        }
        titleTV.setText(title);
        setTitleTextColor(titleTextColor);
        hintTV.setText(hint);
        if (Color.WHITE != hintTextColor) {
            setHintTextColor(hintTextColor);
        }
        isShowDivider(isShowDivider);
        switch (showLongLineType) {
            case 0://沒有分割線
                topLine.setVisibility(View.GONE);
                bottomLine.setVisibility(View.GONE);
                break;
            case 1://頂部
                topLine.setVisibility(View.VISIBLE);
                bottomLine.setVisibility(View.GONE);
                break;
            case 2://底部
                topLine.setVisibility(View.GONE);
                bottomLine.setVisibility(View.VISIBLE);
                break;
            case 3://所有
                topLine.setVisibility(View.VISIBLE);
                bottomLine.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }

    }

    public void hideArrow() {
        arrowIV.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        View.inflate(getContext(), R.layout.setting_next_item_not_icon_view, SettingNextItemNotIconView.this);
        iconIV = (ImageView) this.findViewById(R.id.iv_icon);
        titleTV = (TextView) this.findViewById(R.id.tv_title);
        hintTV = (TextView) this.findViewById(R.id.tv_hint);
        hintIcon = (ImageView) this.findViewById(R.id.iv_hint);
        divider = this.findViewById(R.id.bottom_divider);
        divide_1 = this.findViewById(R.id.divide_1);
        tv_title_r = (TextView) this.findViewById(R.id.tv_title_r);
        arrowIV = (ImageView) this.findViewById(R.id.iv_arrow);
        titleTV.setTextColor(getResources().getColor(R.color.gray));
        topLine = this.findViewById(R.id.line_top);
        bottomLine = this.findViewById(R.id.line_bottom);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightTextListener != null) {
                    rightTextListener.onClick(v);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setViewEnabled(boolean isEnable) {
        this.isEnable = isEnable;
        if (isEnable) {
            titleTV.setTextColor(getResources().getColor(R.color.gray));
            titleTV.setClickable(false);
            this.setEnabled(true);
        } else {
            titleTV.setTextColor(Color.GRAY);
            titleTV.setClickable(true);
            this.setEnabled(false);
        }
    }

    /**
     * 设置右边的按钮的背景
     */
    public void setArrowBackground(Drawable d) {
        // arrowIV.setBackground(d);
    }

    /**
     * 设置提示字体的颜色
     *
     * @param color
     */
    public void setHintTextColor(int color) {
        hintTV.setTextColor(color);
    }

    public void setHintBackground(Drawable d) {
        // hintTV.setBackground(d);
    }

    public void setHintBackground(int res) {
        hintTV.setBackgroundResource(res);
    }

    public void setHintIconBackground(Drawable d) {
        if (d == null) {
            hintIcon.setVisibility(View.GONE);
        } else {
            hintIcon.setVisibility(View.VISIBLE);
        }
        //  hintIcon.setBackground(d);
    }


    /**
     * 是否显示分割线
     *
     * @param isShow
     */
    public void isShowDivider(boolean isShow) {
        if (isShow) {
            divider.setVisibility(View.VISIBLE);
        } else {
            divider.setVisibility(View.GONE);
        }

    }
    /**
     * 是否显示分割线
     *
     * @param isShow
     */
    public void showLeftDivider(boolean isShow) {
        if (isShow) {
            divide_1.setVisibility(View.VISIBLE);
        } else {
            divide_1.setVisibility(View.GONE);
        }

    }

    public void setTitleTextColor(int color) {
        titleTV.setTextColor(color);
    }

    /**
     * 设置条目提示文本
     *
     * @param txt
     */
    public void setHintTxt(String txt) {
        if (!TextUtils.isEmpty(txt)) {
            hintTV.setText(txt);
        }
    }

    /**
     * 设置条目标题
     *
     * @param text
     */
    public void setTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            titleTV.setText(text);
        }
    }
    /**
     * 设置条目标题右边文字
     *
     * @param text
     */
    public void setTitleRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tv_title_r.setText(text);
        }
        tv_title_r.setVisibility(View.VISIBLE);
    }
    /**
     * 设置条目标题右边文字颜色
     *
     * @param
     */
    public void setTitleRightTextColor(int color) {
        tv_title_r.setTextColor(getResources().getColor(color));
    }

    /**
     * 右边文字点击事件
     * <p/>
     * void
     */
    public void setOnRightTextOnclick(RigthTextViewListener listener) {
        rightTextListener = listener;
        if (listener != null) {
            listener.onRightTextView(hintTV);
        }
    }

    /**
     * 获取标题字符串
     *
     * @return String
     * @auther snubee
     */
    public String getTitleText() {
        return titleTV.getText().toString().trim();
    }

    /**
     * 获取右边标题字符串
     *
     * @return String
     * @auther snubee
     */
    public String getHintTitleText() {
        return hintTV.getText().toString().trim();
    }

    /**
     * 右边文字监听
     *
     * @author LION
     * @Description:TODO
     * @Date 2015-9-30
     */
    public interface RigthTextViewListener {
        void onRightTextView(TextView v);

        void onClick(View v);
    }
}
