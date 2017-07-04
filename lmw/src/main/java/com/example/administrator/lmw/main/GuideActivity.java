package com.example.administrator.lmw.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.GuidePageAdapter;
import com.example.administrator.lmw.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by lion on 2016/8/19.
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private Context context;

    private ViewPager viewPager;

    private GuidePageAdapter pagerAdapter;

    private LinearLayout indicatorLayout;

    private TextView startImage;

    private ArrayList<View> views;
    private ImageView[] indicators = null;
    private int[] images;

    private void initDot() {
        views = new ArrayList<View>();
        indicators = new ImageView[images.length]; // 定义指示器数组大小
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 0, 0);
        for (int i = 0; i < images.length; i++) {
            // 循环加入图片
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(params);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundResource(images[i]);
            views.add(imageView);
            // 循环加入指示器
            indicators[i] = new ImageView(context);
            indicators[i].setBackgroundResource(R.drawable.indicator_dot_default);
            indicators[i].setLayoutParams(params);
            if (i == 0) {
                indicators[i].setBackgroundResource(R.drawable.indicator_dot_choice);
            }
            indicatorLayout.addView(indicators[i]);
        }
    }


    @Override
    protected void initializeData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initializeView() {
        context = this;
        // 设置引导图片,仅需在这设置图片 指示器和page自动添加
        images = new int[]{R.drawable.guide01, R.drawable.guide02, R.drawable.guide03, R.drawable.guide04};
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
        startImage = (TextView) findViewById(R.id.start);
        startImage.setOnClickListener(this);

        initDot();
        pagerAdapter = new GuidePageAdapter(views);
        viewPager.setAdapter(pagerAdapter); // 设置适配器
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                startActivity(MainActivity.class);
                finish();

                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 显示最后一个图片时显示按钮
        if (position == indicators.length - 1) {
//            indicatorLayout.setVisibility(View.GONE);
            startImage.setVisibility(View.VISIBLE);
        } else {
//            indicatorLayout.setVisibility(View.VISIBLE);
            startImage.setVisibility(View.GONE);
        }
        // 更改指示器图片
        for (int i = 0; i < indicators.length; i++) {
            indicators[position].setBackgroundResource(R.drawable.indicator_dot_choice);
            if (position != i) {
                indicators[i].setBackgroundResource(R.drawable.indicator_dot_default);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }
}
