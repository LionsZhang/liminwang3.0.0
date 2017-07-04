package com.example.administrator.lmw.mine.transfer;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.mine.transfer.fragment.TransferDetailFragment;
import com.example.administrator.lmw.mine.transfer.fragment.TransferGatherFragment;
import com.example.administrator.lmw.mine.transfer.fragment.TransferProductFragment;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 回款查询
 * <p/>
 * Created by Administrator on 2016/9/3 0003.
 */
public class TransferActivity extends BaseActivity {

    private ArrayList<Fragment> fragmentList;
    private int currIndex;//当前页卡编号
    private int bmpW;//横线图片宽度
    private int offset;//图片移动的偏移量
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.right_title)
    TextView rightTitle;
    @InjectView(R.id.right_icon)
    ImageView rightIcon;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout titleBarRightLayout;
    @InjectView(R.id.transfer_during_tv)
    TextView transferDuringTv;
    @InjectView(R.id.transfer_success_tv)
    TextView transferSuccessTv;
    @InjectView(R.id.transfer_section_tv)
    TextView transferSectionTv;
    @InjectView(R.id.transfer_line_iv)
    ImageView transferLineIv;
    @InjectView(R.id.viewpager_vp)
    ViewPager viewpagerVp;

    @Override
    protected void initializeData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initializeView() {
        titlebar();
        InitTextView();
        initializeData();
        InitImage();
        InitViewPager();

    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barTitle.setText("回款查询");
        barIconBack.setImageResource(R.drawable.nav_back);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(TransferActivity.this);
            }
        });
    }

    /**
     * 初始化标签名
     */
    public void InitTextView() {
        transferDuringTv.setOnClickListener(new txListener(0));
        transferSuccessTv.setOnClickListener(new txListener(1));
        transferSectionTv.setOnClickListener(new txListener(2));
//        transferFailureTv.setOnClickListener(new txListener(3));
        transferDuringTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
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
                    transferDuringTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 1:
                    transferSuccessTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 2:
                    transferSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
//                case 3:
//                    transferFailureTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
//                    break;
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
        offset = (screenW / 3 - bmpW) / 2;

        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        transferLineIv.setImageMatrix(matrix);


    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        fragmentList = new ArrayList<Fragment>();
        TransferGatherFragment gather = new TransferGatherFragment();
        TransferProductFragment product = new TransferProductFragment();
        TransferDetailFragment detail = new TransferDetailFragment();
//        Failure failure = new Failure();
        fragmentList.add(gather);
        fragmentList.add(product);
        fragmentList.add(detail);
//        fragmentList.add(failure);

        //给ViewPager设置适配器
        viewpagerVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewpagerVp.setCurrentItem(0);//设置当前显示标签页为第一页
        viewpagerVp.setOffscreenPageLimit(2);
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
            transferLineIv.startAnimation(animation);//是用ImageView来显示动画的
            int i = currIndex + 1;

            resetImage();
            switch (arg0) {
                case 0:
                    transferDuringTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 1:
                    transferSuccessTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 2:
                    transferSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
//                case 3:
//                    transferFailureTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
//                    break;
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
        transferDuringTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        transferSuccessTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        transferSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
//        transferFailureTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
    }

}
