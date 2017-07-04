package com.example.administrator.lmw.mine.cumulative;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.mine.cumulative.fragment.AccumulatedEarningsFragment;
import com.example.administrator.lmw.mine.cumulative.fragment.TotalAssetsFragment;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 我的资产 总资产和累计收益
 * 累计收益 Activity
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/14 10:16
 **/
public class CumulativeActivity extends BaseTitleActivity {

    public static final String INTENT_KEY_INDEX = "intent_key_index";

    private ArrayList<Fragment> fragmentList;
    private int currIndex;//当前页卡编号
    private int bmpW;//横线图片宽度
    private int offset;//图片移动的偏移量


    @InjectView(R.id.cumulative_total_assets_tv)
    TextView cumulativeTotalTv;
    @InjectView(R.id.cumulative_investment_earnings_tv)
    TextView cumulativeInvestmentTv;
    @InjectView(R.id.cumulative_line_iv)
    ImageView cumulativeLineIv;
    @InjectView(R.id.viewpager_vp)
    ViewPager viewpagerVp;
    private int indexs = 0;
    private Intent intent;

    @Override
    protected void initializeData() {
        intent = getIntent();
        if (intent.hasExtra(INTENT_KEY_INDEX))
            indexs = intent.getIntExtra(INTENT_KEY_INDEX, 0);
    }


    @Override
    protected void initializeView() {
        InitTextView();
        InitImage();
        InitViewPager();


    }

    @Override
    protected void setTitleContentView() {
        initView(R.string.txt_mine_property, R.layout.activity_cumulative);
    }


    /**
     * 初始化标签名
     */
    public void InitTextView() {
        cumulativeTotalTv.setOnClickListener(new txListener(0));
        cumulativeInvestmentTv.setOnClickListener(new txListener(1));
        if (indexs == 0)
            cumulativeTotalTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
        else
            cumulativeInvestmentTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            viewpagerVp.setCurrentItem(index);
            resetImage();
            switch (index) {
                case 0:
                    cumulativeTotalTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 1:
                    cumulativeInvestmentTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
            }
        }
    }

    /*
     * 初始化图片的位移像素
     */
    public void InitImage() {
        // TODO
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.white_triangle).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 2 - bmpW) / 2;

        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cumulativeLineIv.setImageMatrix(matrix);
        Animation animation = new TranslateAnimation(currIndex * (offset * 2 + bmpW), indexs * (offset * 2 + bmpW), 0, 0);//平移动画
        animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
        animation.setDuration(200);//动画持续时间0.2秒
        cumulativeLineIv.startAnimation(animation);//是用ImageView来显示动画的


    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        fragmentList = new ArrayList<Fragment>();
        TotalAssetsFragment totalAssets = new TotalAssetsFragment();
        AccumulatedEarningsFragment accumulatedEarnings = new AccumulatedEarningsFragment();
        fragmentList.add(totalAssets);
        fragmentList.add(accumulatedEarnings);

        //给ViewPager设置适配器
        viewpagerVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewpagerVp.setCurrentItem(indexs);//设置当前显示标签页为第一页
        viewpagerVp.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int one = offset * 2 + bmpW;//两个相邻页面的偏移量

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            Animation animation = new TranslateAnimation(currIndex * one, arg0 * one, 0, 0);//平移动画
            currIndex = arg0;
            animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
            animation.setDuration(200);//动画持续时间0.2秒
            cumulativeLineIv.startAnimation(animation);//是用ImageView来显示动画的
            int i = currIndex + 1;

            resetImage();
            switch (arg0) {
                case 0:
                    cumulativeTotalTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 1:
                    cumulativeInvestmentTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
            }
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

    }

    private void resetImage() {
        cumulativeTotalTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        cumulativeInvestmentTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

}
