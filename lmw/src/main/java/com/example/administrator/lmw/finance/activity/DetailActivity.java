package com.example.administrator.lmw.finance.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.finance.entity.DetailEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.finance.fragment.DetailFragment;
import com.example.administrator.lmw.finance.fragment.MoreFragment;
import com.example.administrator.lmw.finance.utils.DragLayout;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invite.entity.ShareBean;
import com.example.administrator.lmw.utils.ShareUtils;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;

import butterknife.InjectView;

/**
 * 债权详情
 * <p>
 * Created by Administrator on 2016/8/26 0026.
 */
public class DetailActivity extends BaseActivity implements DetailFragment.DetailFragmentLister {
    private DetailFragment detailFragment;
    private MoreFragment moreFragment;
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
    @InjectView(R.id.detail_layout)
    FrameLayout detailLayout;
    @InjectView(R.id.detail_more)
    FrameLayout detailMore;
    @InjectView(R.id.draglayout)
    DragLayout draglayout;
    @InjectView(R.id.detail_btn)
    Button detailBtn;
    private String subjectId;
    private DetailEntity entity;
    private Intent intent;
    private BaseResult<ShareBean> share;
    private boolean falg = false;

    @Override
    protected void initializeData() {
        intent = getIntent();
        intents();
        getShare();
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initializeView() {
        http();
        initView();
        titlebar();

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entity == null) {
                    ToastUtil.showToast(mContext, getString(R.string.internet_error_code));
                } else {
                    if (falg) {
                        if (!PreferenceCongfig.isLogin || (PreferenceCongfig.isLogin && Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext))) {
                            Intent intent = new Intent(mContext, CreditBuy.class);
                            intent.putExtra("entity", entity);
                            intent.putExtra("subjectId", subjectId);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    /**
     * 网络访问
     */
    private void http() {
        detailhttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
    }

    /**
     * 标题初始化
     */
    private void titlebar() {

        barIconBack.setImageResource(R.drawable.nav_back);// 返回
        rightIcon.setImageResource(R.drawable.fx_fenxiang);// 分享
        // 返回
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(DetailActivity.this);
            }
        });
        // 分享
        titleBarRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (share != null && share.getData() != null)
                    Singlton.getInstance(ShareUtils.class).getShareUtils(DetailActivity.this, share.getData().getImageUrl(), share.getData().getShareContent(),
                            share.getData().getShareTitle(), share.getData().getTargetUrl());
            }
        });
    }

    private void intents() {
        if (intent.hasExtra("subjectId"))
            subjectId = intent.getStringExtra("subjectId");
    }

    /**
     * 初始化View
     */
    private void initView() {
        detailFragment = new DetailFragment();
        moreFragment = new MoreFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_layout, detailFragment).add(R.id.detail_more, moreFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 访问网络
     */
    private void detailhttp(String debtId, String token) {
        Singlton.getInstance(FinanceHttp.class).getDetailFragment(mContext, debtId, token, new OnResponseListener<DetailEntity>() {
            @Override
            public void onSuccess(DetailEntity detailEntity) {
                if (detailEntity == null) return;
                if (detailEntity.getData() == null) return;
                if (detailEntity.getCode().equals("0")) {
                    if (!TextUtils.isEmpty(detailEntity.getData().getDebtTitle())) {
                        entity = new DetailEntity();
                        entity = detailEntity;
                        barTitle.setText(detailEntity.getData().getDebtTitle());
                        find(detailEntity);
                    }
                } else {
                    ToastUtil.showToast(mContext, detailEntity.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 获取分享内容
     */
    private void getShare() {
        Singlton.getInstance(FinanceHttp.class).getShareContent(mContext, subjectId, "2", new OnResponseListener<BaseResult<ShareBean>>() {
            @Override
            public void onSuccess(BaseResult<ShareBean> shareEntity) {
                if (shareEntity == null) return;
                if (shareEntity.getCode().equals("0")) {
                    share = shareEntity;
                } else {
                    showToast(shareEntity.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 状态
     *
     * @param entity
     */
    private void find(DetailEntity entity) {
        /**
         * 0.转让中，1.转让成功,2.取消转让,3.时间过期,4.转让失败
         */
        if (!TextUtils.isEmpty(entity.getData().getStatus()))
            if (entity.getData().getStatus().equals("0")) {
                falg = true;
                detailBtn.setText("立即购买");
                detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_btn));
            } else if (entity.getData().getStatus().equals("1")) {
                falg = false;
                detailBtn.setText("转让成功");
                detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
            } else if (entity.getData().getStatus().equals("2")) {
                falg = false;
                detailBtn.setText("取消转让");
                detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
            } else if (entity.getData().getStatus().equals("3")) {
                falg = false;
                detailBtn.setText("转让结束");
                detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
            } else if (entity.getData().getStatus().equals("4")) {
                falg = false;
                detailBtn.setText("转让结束");
                detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //当手指按下的时候
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
//                detailBtn.setVisibility(View.GONE);
                break;
            case MotionEvent.ACTION_UP:
//                detailBtn.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }

    @Override
    public void lister(String str) {
        http();
    }
}
