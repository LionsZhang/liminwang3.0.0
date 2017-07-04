package com.example.administrator.lmw.select.Activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.FreshMessageEvent;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.select.BannerHttp;
import com.example.administrator.lmw.select.fragment.AnnouncementFragment;
import com.example.administrator.lmw.select.fragment.NoticeFragment;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 消息中心
 * <p/>
 * Created by Administrator on 2016/8/30 0030.
 */
public class MessageCenterActivity extends BaseActivity implements NoticeFragment.NoticeListener, AnnouncementFragment.AnnouncementListener {

    @InjectView(R.id.message_notice_item_tv)
    TextView messageNoticeItemTv;
    @InjectView(R.id.message_notice_rel)
    RelativeLayout messageNoticeRel;
    @InjectView(R.id.message_announcement_item_tv)
    TextView messageAnnouncementItemTv;
    @InjectView(R.id.message_announcement_rel)
    RelativeLayout messageAnnouncementRel;
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
    @InjectView(R.id.message_notice_tv)
    TextView messageNoticeTv;
    @InjectView(R.id.message_announcement_tv)
    TextView messageAnnouncementTv;
    @InjectView(R.id.message_line_iv)
    ImageView messageLineIv;
    @InjectView(R.id.viewpager_vp)
    ViewPager viewpagerVp;

    @Override
    protected void initializeData() {


    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initializeView() {
        titlefind();
        InitTextView();
        initializeData();
        InitImage();
        InitViewPager();
        titlebar();


        // 返回
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(MessageCenterActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new FreshMessageEvent());
                    }
                });
            }
        });
        // 一键读取
        rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMessage(1, "", SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finishActivity(MessageCenterActivity.this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new FreshMessageEvent());
                }
            });
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barTitle.setText(R.string.message_center);
        barIconBack.setImageResource(R.drawable.nav_back);
        rightTitle.setText("一键读取");
    }

    private void titlefind() {
        if (!PreferenceCongfig.isLogin) {
            messageAnnouncementRel.setVisibility(View.GONE);
            messageLineIv.setVisibility(View.GONE);
            rightTitle.setVisibility(View.GONE);
            messageNoticeTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        } else {
            messageAnnouncementRel.setVisibility(View.VISIBLE);
            messageLineIv.setVisibility(View.VISIBLE);
            messageAnnouncementTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        }
    }

    /**
     * 初始化标签名
     */
    public void InitTextView() {
        messageAnnouncementTv.setOnClickListener(new txListener(0));
        messageNoticeTv.setOnClickListener(new txListener(1));

    }

    @Override
    public void notice(String str) {
        if (Double.parseDouble(str) > 0) {
            if (Double.parseDouble(str) > 99) {
                messageAnnouncementItemTv.setText("99+");
                messageAnnouncementItemTv.setVisibility(View.VISIBLE);
            } else {
                messageAnnouncementItemTv.setText(str);
                messageAnnouncementItemTv.setVisibility(View.VISIBLE);
            }
        } else {
            messageAnnouncementItemTv.setText("0");
            messageAnnouncementItemTv.setVisibility(View.GONE);
        }

    }

    @Override
    public void announcement(String str) {
        if (Double.parseDouble(str) > 0) {
            if (Double.parseDouble(str) > 99) {
                messageNoticeItemTv.setText("99+");
                messageNoticeItemTv.setVisibility(View.VISIBLE);
            } else {
                messageNoticeItemTv.setText(str);
                messageNoticeItemTv.setVisibility(View.VISIBLE);
            }

        } else {
            messageNoticeItemTv.setText("0");
            messageNoticeItemTv.setVisibility(View.GONE);
        }

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
                    messageAnnouncementTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                    rightTitle.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    messageNoticeTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                    rightTitle.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /*
     * 初始化图片的位移像素
     */
    public void InitImage() {
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.white_triangle).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 2 - bmpW) / 2;

        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        messageLineIv.setImageMatrix(matrix);
    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        fragmentList = new ArrayList<Fragment>();
        if (!PreferenceCongfig.isLogin) {
            AnnouncementFragment announcement = new AnnouncementFragment();
            fragmentList.add(announcement);
        } else {
            NoticeFragment notice = new NoticeFragment();
            AnnouncementFragment announcement = new AnnouncementFragment();
            fragmentList.add(notice);
            fragmentList.add(announcement);
        }


        //给ViewPager设置适配器
        viewpagerVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewpagerVp.setCurrentItem(0);//设置当前显示标签页为第一页
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
            messageLineIv.startAnimation(animation);//是用ImageView来显示动画的
            int i = currIndex + 1;

            resetImage();
            switch (arg0) {
                case 0:
                    messageAnnouncementTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                    rightTitle.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    messageNoticeTv.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
                    rightTitle.setVisibility(View.GONE);
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

    // TODO: 2016/8/23 0023   需要补全数据
    private void resetImage() {
        messageNoticeTv.setTextColor(ContextCompat.getColor(mContext, R.color.finance_pro));
        messageAnnouncementTv.setTextColor(ContextCompat.getColor(mContext, R.color.finance_pro));
    }

    /**
     * 一键读取
     *
     * @param readAll
     * @param statusList
     * @param token
     */
    private void getMessage(int readAll, String statusList, String token) {
        Singlton.getInstance(BannerHttp.class).getMessage(mContext, readAll, statusList, token, new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        if (baseResponse != null) {
                            int code = Integer.valueOf(baseResponse.code);
                            if (code == 0) {
                                NoticeFragment notice = (NoticeFragment) getSupportFragmentManager().getFragments().get(0);
                                if (notice != null) {// 刷新fragment界面
                                    notice.loadPage();
                                }
                                messageAnnouncementItemTv.setText("0");
                                messageAnnouncementItemTv.setVisibility(View.GONE);
                            } else if (code == 150006) {
                                showToast(baseResponse.getMsg());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(LoginActivity.class);
                                    }
                                }, 1000);
                            } else {
                                showToast(baseResponse.msg);
                            }
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                }
        );
    }

}
