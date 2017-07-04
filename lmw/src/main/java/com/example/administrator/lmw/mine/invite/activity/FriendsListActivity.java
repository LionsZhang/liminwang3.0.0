package com.example.administrator.lmw.mine.invite.activity;

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
import com.example.administrator.lmw.mine.invite.fragment.FriendsOneFragment;
import com.example.administrator.lmw.mine.invite.fragment.FriendsTwoFragment;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 好友列表
 * <p>
 * Created by Administrator on 2016/9/14 0014.
 */
public class FriendsListActivity extends BaseActivity {

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
    @InjectView(R.id.friends_one_tv)
    TextView friendsOneTv;
    @InjectView(R.id.friends_two_tv)
    TextView friendsTwoTv;
    @InjectView(R.id.friends_line_iv)
    ImageView friendsLineIv;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void initializeData() {

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_friends_list;
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
        barTitle.setText("好友列表");
        barIconBack.setImageResource(R.drawable.nav_back);
        barIconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(FriendsListActivity.this);
            }
        });
    }

    /**
     * 初始化标签名
     */
    public void InitTextView() {
        friendsOneTv.setOnClickListener(new txListener(0));
        friendsTwoTv.setOnClickListener(new txListener(1));
        friendsOneTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            viewpager.setCurrentItem(index);
            resetImage();
            switch (index) {
                case 0:
                    friendsOneTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 1:
                    friendsTwoTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
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
        friendsLineIv.setImageMatrix(matrix);
    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        fragmentList = new ArrayList<Fragment>();
        FriendsOneFragment friendsOne = new FriendsOneFragment();
        FriendsTwoFragment friendsTwo = new FriendsTwoFragment();
        fragmentList.add(friendsOne);
        fragmentList.add(friendsTwo);

        //给ViewPager设置适配器
        viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewpager.setCurrentItem(0);//设置当前显示标签页为第一页
        viewpager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
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
            friendsLineIv.startAnimation(animation);//是用ImageView来显示动画的
            int i = currIndex + 1;

            resetImage();
            switch (currIndex) {
                case 0:
                    friendsOneTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
                    break;
                case 1:
                    friendsTwoTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_bule));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化文字
     */
    private void resetImage() {
        friendsOneTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        friendsTwoTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
    }
}
