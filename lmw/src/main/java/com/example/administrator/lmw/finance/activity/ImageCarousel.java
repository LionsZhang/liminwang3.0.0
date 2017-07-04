package com.example.administrator.lmw.finance.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.GuidePageAdapter;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.utils.PicassoManager;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageCarousel extends BaseActivity implements ViewPager.OnPageChangeListener {
    private Context context;
    private ViewPager viewPager;
    private GuidePageAdapter pagerAdapter;
    private LinearLayout indicatorLayout;
    private ArrayList<View> views;
    private ImageView[] indicators = null;
    private String[] images;
    private int selectNum = 0;
    private Intent intent;

    private void initDot() {
        views = new ArrayList<View>();
        indicators = new ImageView[images.length]; // 定义指示器数组大小
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 0, 0);
        for (int i = 0; i < images.length; i++) {
            // 循环加入图片
            PhotoView imageView = new PhotoView(context);
            imageView.setLayoutParams(params);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            PicassoManager.getInstance().display(context, imageView, images[i]);
//            imageView.setBackgroundResource(images[i]);
            views.add(imageView);
            // 循环加入指示器
            indicators[i] = new ImageView(context);
            indicators[i].setBackgroundResource(R.drawable.yuandian_h);
            indicators[i].setLayoutParams(params);
            if (i == 0) {
                indicators[i].setBackgroundResource(R.drawable.yuandian_l);
            }
            indicatorLayout.addView(indicators[i]);
            imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finishActivity(ImageCarousel.this);
                }
            });
        }
    }


    @Override
    protected void initializeData() {
        intent = getIntent();
        intents();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_image_carousel;
    }

    @Override
    protected void initializeView() {
        context = this;
        // 设置引导图片,仅需在这设置图片 指示器和page自动添加
//        images.add("http://pic10.nipic.com/20101103/5063545_000227976000_2.jpg");
//        images.add("http://desk.fd.zol-img.com.cn/t_s960x600c5/g4/M00/0D/01/Cg-4y1ULoXCII6fEAAeQFx3fsKgAAXCmAPjugYAB5Av166.jpg");
//        images.add("http://pic63.nipic.com/file/20150328/9448607_153955535000_2.jpg");
//        images.add("http://f.hiphotos.baidu.com/zhidao/pic/item/8b13632762d0f703645993fa0dfa513d2697c529.jpg");
        viewPager = (ViewPager) findViewById(R.id.carousel_viewpage);
        indicatorLayout = (LinearLayout) findViewById(R.id.carousel_indicator);
        initDot();
        pagerAdapter = new GuidePageAdapter(views);
        viewPager.setAdapter(pagerAdapter); // 设置适配器
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(selectNum);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 更改指示器图片
        for (int i = 0; i < indicators.length; i++) {
            indicators[position].setBackgroundResource(R.drawable.yuandian_l);
            if (position != i) {
                indicators[i].setBackgroundResource(R.drawable.yuandian_h);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }

    private void intents() {
        if (intent.hasExtra("selectNum"))
            selectNum = intent.getIntExtra("selectNum", 0);
        if (intent.hasExtra("images")) {
            images = new String[intent.getStringArrayExtra("images").length];
            for (int i = 0; i < intent.getStringArrayExtra("images").length; i++) {
                images[i] = intent.getStringArrayExtra("images")[i];
            }
        }
    }
}
