package com.example.administrator.lmw.mine.more;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.ConfigManager;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.CommonInfo;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.more.activity.FeedbackActivity;
import com.example.administrator.lmw.mine.more.activity.MessageSettingsActivity;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.mine.more.entity.MoreEntity;
import com.example.administrator.lmw.mine.more.http.Available;
import com.example.administrator.lmw.mine.more.http.Morehttp;
import com.example.administrator.lmw.select.BannerHttp;
import com.example.administrator.lmw.select.entity.BannerDatasBean;
import com.example.administrator.lmw.select.entity.BannerEntity;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 更多
 * <p/>
 * Created by Administrator on 2016/9/14 0014.
 */
public class MoreActivity extends BaseActivity {
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
    @InjectView(R.id.more_banner)
    Banner banner;
    @InjectView(R.id.more_about)
    LinearLayout moreAbout;
    @InjectView(R.id.more_security)
    LinearLayout moreSecurity;
    @InjectView(R.id.more_wechat_no)
    TextView moreWechatNo;
    @InjectView(R.id.more_wechat)
    LinearLayout moreWechat;
    @InjectView(R.id.more_customer_service_photo)
    TextView moreCustomerServicePhoto;
    @InjectView(R.id.more_customer_service)
    LinearLayout moreCustomerService;
    @InjectView(R.id.more_qq_no)
    TextView moreQqNo;
    @InjectView(R.id.more_qq)
    LinearLayout moreQq;
    @InjectView(R.id.more_problem)
    LinearLayout moreProblem;
    @InjectView(R.id.more_reward)
    LinearLayout moreReward;
    @InjectView(R.id.more_feedback)
    LinearLayout moreFeedback;
    @InjectView(R.id.more_notice)
    LinearLayout moreNotice;
    private ArrayList bannerlist;
    private List<BannerDatasBean> bannerBean;
    private String lmw_product_faq, lmw_product_safe, lmw_about_us, lmw_qq_group, lmw_wechat_group;
    private CommonInfo commonInfo;

    @Override
    protected void initializeData() {
        commonInfo = ConfigManager.getInstance().getCommonInfo(mContext);

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_more;
    }

    @Override
    protected void initializeView() {
        titlebar();
        setBanner();
        getBanner();
        getMore();

        /**
         * banner图点击事件
         */
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                Intent intent = new Intent(mContext, WebViewMore.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", bannerBean.get(position - 1).bannerUrl);
                bundle.putString("title", bannerBean.get(position - 1).bannerTitle);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    /**
     * 标题初始化
     */
    private void titlebar() {
        barIconBack.setImageResource(R.drawable.nav_back);
        barTitle.setText("更多");
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(MoreActivity.this);
            }
        });
    }

    private void setBanner() {
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR); // banner样式
        banner.setDelayTime(5000); // banner轮播时间

    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.more_about, R.id.more_security, R.id.more_wechat, R.id.more_customer_service,
            R.id.more_qq, R.id.more_problem, R.id.more_reward, R.id.more_feedback, R.id.more_notice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_about:// 关于我们
                Intent intentab = new Intent(mContext, WebViewMore.class);
                Bundle bundleab = new Bundle();
                if (commonInfo != null) {
                    bundleab.putString("url", commonInfo.getLmw_about_us());
                } else {
                    bundleab.putString("url", "");
                }
                bundleab.putString("title", "关于我们");
                intentab.putExtras(bundleab);
                startActivity(intentab);
                break;
            case R.id.more_security:// 安全保障
                Intent intentse = new Intent(mContext, WebViewMore.class);
                Bundle bundlese = new Bundle();
                if (commonInfo != null) {
                    bundlese.putString("url", commonInfo.getLmw_product_safe() + "?t=" + System.currentTimeMillis());
                } else {
                    bundlese.putString("url", "");
                }
                bundlese.putString("title", "安全保障");
                intentse.putExtras(bundlese);
                startActivity(intentse);
                break;
            case R.id.more_wechat:// 官方微信群
                diaglogWeixin(R.string.more_cat, "去关注");
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(lmw_wechat_group);

                break;
            case R.id.more_customer_service:// 客服热线
                diaglog(R.string.more_photo, "确定");
                break;
            case R.id.more_qq:// 官方QQ群
                diaglogQQClient(R.string.more_QQ, "加QQ群");
                ClipboardManager cmb1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb1.setText(lmw_qq_group);
                break;
            case R.id.more_problem:// 常见问题
                Intent intentc = new Intent(mContext, WebViewMore.class);
                Bundle bundlec = new Bundle();
                if (commonInfo != null) {
                    bundlec.putString("url", commonInfo.getLmw_product_faq());
                } else {
                    bundlec.putString("url", lmw_product_faq);
                }
                bundlec.putString("title", "常见问题");
                intentc.putExtras(bundlec);
                startActivity(intentc);
                break;
            case R.id.more_reward:// 打赏好评
                break;
            case R.id.more_feedback:// 意见反馈
                startActivity(FeedbackActivity.class);
                break;
            case R.id.more_notice:
                startActivity(MessageSettingsActivity.class);
                break;

        }
    }

    /**
     * 获取banner条
     */
    private void getBanner() {
        Singlton.getInstance(BannerHttp.class).getBanner(mContext, 0, new OnResponseListener<BannerEntity>() {
            @Override
            public void onSuccess(BannerEntity response) {
                if (response.code.equals("0")) {
                    bannerBean = new ArrayList<BannerDatasBean>();
                    bannerBean.addAll(response.data.datas);
                    bannerlist = new ArrayList<String>();
                    if (response.data.datas.size() > 0) {
                        for (int i = 0; i < response.data.datas.size(); i++) {
                            bannerlist.add(response.data.datas.get(i).bannerImage);
                        }
                        banner.setImages(bannerlist);
                    }
                } else if (response.code.equals("1000")) {
                    Singlton.getInstance(PopWindowManager.class).showStopDialogShow(mContext, response.code, response.msg);
                } else {
                    showToast(response.code + "");
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 页面数据访问
     */
    private void getMore() {
        Singlton.getInstance(Morehttp.class).getMore(mContext, new OnResponseListener<BaseResult<MoreEntity>>() {
            @Override
            public void onSuccess(BaseResult<MoreEntity> moreEntity) {
                if (moreEntity.getCode().equals("0")) {
                    findview(moreEntity.getData());
                }

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 实例化界面数据
     *
     * @param moreEntity
     */
    private void findview(MoreEntity moreEntity) {
        // 微信群
        moreWechatNo.setText(moreEntity.getLmw_wechat_group());
        // 客服热线
        moreCustomerServicePhoto.setText(moreEntity.getLmw_product_tel());
        // 官方QQ群
        moreQqNo.setText(moreEntity.getLmw_qq_group());
        // url
        lmw_product_faq = moreEntity.getLmw_product_faq();// 帮助中心
        lmw_product_safe = moreEntity.getLmw_product_safe();// 安全保障
        lmw_about_us = moreEntity.getLmw_about_us();// 关于我们
        lmw_qq_group = moreEntity.getLmw_qq_group();// QQ
        lmw_wechat_group = moreEntity.getLmw_wechat_group();// 微信

    }

    /**
     * 弹出框
     *
     * @param id     内容
     * @param cancel cancel键
     */
    private void diaglogWeixin(int id, String cancel) {
        String str = getResources().getString(id);
        Singlton.getInstance(PopWindowManager.class).
                showTwoButtonDialog(mContext, "提示", str, "取消", cancel,
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 1) {
                                    if (Available.isWeixinAvilible(mContext)) {
                                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                                        startActivity(intent);
                                    } else {
                                        showToast("您没有安装微信");
                                    }

                                }
                            }
                        });
    }

    /**
     * 弹出框
     *
     * @param id     内容
     * @param cancel cancel键
     */
    private void diaglogQQClient(int id, String cancel) {
        String str = getResources().getString(id);
        Singlton.getInstance(PopWindowManager.class).
                showTwoButtonDialog(mContext, "提示", str, "取消", cancel,
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 1) {
                                    if (Available.isQQClientAvailable(mContext)) {
                                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                                        startActivity(intent);
                                    } else {
                                        showToast("您没有安装QQ");
                                    }

                                }
                            }
                        });
    }

    /**
     * 弹出框
     *
     * @param id     内容
     * @param cancel cancel键
     */
    private void diaglog(int id, String cancel) {
        String str = getResources().getString(id);
        Singlton.getInstance(PopWindowManager.class).
                showTwoButtonDialog(mContext, "提示", str, "取消", cancel,
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 1) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4008318999"));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
    }
}
