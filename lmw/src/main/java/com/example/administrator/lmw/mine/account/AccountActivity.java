package com.example.administrator.lmw.mine.account;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.mine.account.fragment.BuyFragment;
import com.example.administrator.lmw.mine.account.fragment.CompleteFragment;
import com.example.administrator.lmw.mine.account.fragment.OtherFragment;
import com.example.administrator.lmw.mine.account.fragment.RechargeFragment;
import com.example.administrator.lmw.mine.account.fragment.SectionFragment;
import com.example.administrator.lmw.mine.account.fragment.WithdrawFragment;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 可用余额
 * <p>
 * Created by Administrator on 2016/9/3 0003.
 */
public class AccountActivity extends BaseActivity {

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
    @InjectView(R.id.account_complete_tv)
    TextView accountCompleteTv;
    @InjectView(R.id.account_buy_tv)
    TextView accountBuyTv;
    @InjectView(R.id.account_section_tv)
    TextView accountSectionTv;
    @InjectView(R.id.account_recharge_tv)
    TextView accountRechargeTv;
    @InjectView(R.id.account_withdraw_tv)
    TextView accountWithdrawTv;
    @InjectView(R.id.account_other_tv)
    TextView accountOtherTv;
    @InjectView(R.id.account_select_iv)
    ImageView accountSelectIv;
    @InjectView(R.id.viewpager_vp)
    ViewPager viewpagerVp;
    private Intent intent;
    private int showType;

    @Override
    protected void initializeData() {
        intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.FILL_RESULT_TO_ACCOUNT_DETAIL_FILL)) {
            showType = intent.getIntExtra(PreferenceCongfig.FILL_RESULT_TO_ACCOUNT_DETAIL_FILL, -1);
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_account;
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
        barTitle.setText("账户明细");
        barIconBack.setImageResource(R.drawable.nav_back);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(AccountActivity.this);
            }
        });
    }

    /**
     * 初始化标签名
     */
    public void InitTextView() {
        accountCompleteTv.setOnClickListener(new txListener(0));
        accountBuyTv.setOnClickListener(new txListener(1));
        accountSectionTv.setOnClickListener(new txListener(2));
        accountRechargeTv.setOnClickListener(new txListener(3));
        accountWithdrawTv.setOnClickListener(new txListener(4));
        accountOtherTv.setOnClickListener(new txListener(5));
        accountCompleteTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            showCurrentFragment(index);

        }
    }

    private void showCurrentFragment(int index) {
        viewpagerVp.setCurrentItem(index);
        resetImage();
        switch (index) {
            case 0:
                accountCompleteTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 1:
                accountBuyTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 2:
                accountSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 3:
                accountRechargeTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 4:
                accountWithdrawTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 5:
                accountOtherTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
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
        offset = (screenW / 6 - bmpW) / 2;

        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        accountSelectIv.setImageMatrix(matrix);


    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        fragmentList = new ArrayList<Fragment>();
        CompleteFragment complete = new CompleteFragment();
        BuyFragment buy = new BuyFragment();
        SectionFragment section = new SectionFragment();
        RechargeFragment recharge = new RechargeFragment();
        WithdrawFragment withdraw = new WithdrawFragment();
        OtherFragment other = new OtherFragment();
        fragmentList.add(complete);
        fragmentList.add(buy);
        fragmentList.add(section);
        fragmentList.add(recharge);
        fragmentList.add(withdraw);
        fragmentList.add(other);

        //给ViewPager设置适配器
        viewpagerVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        if (showType == PreferenceCongfig.fill_result_to_account_detail_fill) {
            showCurrentFragment(3);
        } else
            viewpagerVp.setCurrentItem(0);//设置当前显示标签页为第一页v
        viewpagerVp.setOffscreenPageLimit(5);
        viewpagerVp.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {


        }

        @Override
        public void onPageSelected(int arg0) {
            show(arg0);
        }
    }

    private void show(int arg0) {
        int one = offset * 2 + bmpW;//两个相邻页面的偏移量
        Animation animation = new TranslateAnimation(currIndex * one, arg0 * one, 0, 0);//平移动画
        currIndex = arg0;
        animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
        animation.setDuration(200);//动画持续时间0.2秒
        accountSelectIv.startAnimation(animation);//是用ImageView来显示动画的
        int i = currIndex + 1;

        resetImage();
        switch (arg0) {
            case 0:
                accountCompleteTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 1:
                accountBuyTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 2:
                accountSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 3:
                accountRechargeTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 4:
                accountWithdrawTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
            case 5:
                accountOtherTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                break;
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
        accountCompleteTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        accountBuyTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        accountSectionTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        accountRechargeTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        accountWithdrawTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        accountOtherTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
    }
}
