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
import com.example.administrator.lmw.finance.entity.DetailFragmentEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.finance.fragment.DetailProductFragment;
import com.example.administrator.lmw.finance.fragment.MoreFragment;
import com.example.administrator.lmw.finance.utils.DateUtils;
import com.example.administrator.lmw.finance.utils.DragLayout;
import com.example.administrator.lmw.finance.utils.OnPayPasswordDialogListener;
import com.example.administrator.lmw.finance.utils.PayPasswordDialog;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invite.entity.ShareBean;
import com.example.administrator.lmw.utils.ShareUtils;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;
import com.umeng.socialize.UMShareAPI;

import butterknife.InjectView;

/**
 * 定期宝详情
 * <p>
 * Created by Administrator on 2016/9/1 0001.
 */
public class DetailProductActivity extends BaseActivity implements DetailProductFragment.DetailProductFragmentLister {
    private DetailProductFragment detailProductFragment;
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
    private DetailFragmentEntity entity;
    private Intent intent;
    private BaseResult<ShareBean> share;

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
                    if (!PreferenceCongfig.isLogin || (PreferenceCongfig.isLogin && Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext))) {
                        if (entity.getData().getStatus().equals("3") || entity.getData().getStatus().equals("5")) {
                            Intent intent = new Intent(mContext, ProblemBuy.class);
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
    public void http() {
        producthttp(subjectId, SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
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
                finishActivity(DetailProductActivity.this);
            }
        });
        // 分享
        titleBarRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (share != null && share.getData() != null)
                    Singlton.getInstance(ShareUtils.class).getShareUtils(DetailProductActivity.this, share.getData().getImageUrl(), share.getData().getShareContent(),
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
        detailProductFragment = new DetailProductFragment();
        moreFragment = new MoreFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_layout, detailProductFragment).add(R.id.detail_more, moreFragment)
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
    private void producthttp(String borrowId, String token) {
        Singlton.getInstance(FinanceHttp.class).getDetailProductFragment(mContext, borrowId, token, new OnResponseListener<DetailFragmentEntity>() {
            @Override
            public void onSuccess(DetailFragmentEntity detailFragmentEntity) {
                if (detailFragmentEntity == null) return;
                if (detailFragmentEntity.getData() == null) return;
                if (detailFragmentEntity.getCode().equals("0")) {
                    if (!TextUtils.isEmpty(detailFragmentEntity.getData().getBorrowTitle())) {
                        entity = new DetailFragmentEntity();
                        entity = detailFragmentEntity;
                        barTitle.setText(detailFragmentEntity.getData().getBorrowTitle());
                        find(detailFragmentEntity);
                    }
                } else {
                    ToastUtil.showToast(mContext, detailFragmentEntity.getMsg());
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

    private void find(DetailFragmentEntity entity) {
        if (!TextUtils.isEmpty(entity.getData().getStatus()))
            switch (Integer.parseInt(entity.getData().getStatus())) {
                case -1:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                case 0:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
                case 1:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
                case 2:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
                case 3:
                    detailBtn.setText("立即购买");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_btn));
                    break;
                case 4:
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    if (!entity.getData().getStartInterestTime().equals("") && !entity.getData().getNowTime().equals("")) {
                        if (DateUtils.getStringToDateDay(entity.getData().getStartInterestTime()) > DateUtils.getStringToDateDay(entity.getData().getNowTime())) {
                            detailBtn.setText("已满标");
                        } else if (DateUtils.getStringToDateDay(entity.getData().getStartInterestTime()) <= DateUtils.getStringToDateDay(entity.getData().getNowTime())) {
                            if (entity.getData().getIsRepay() == 0) {
                                detailBtn.setText("计息中");
                            } else {
                                detailBtn.setText("已回款");
                            }
                        }
                    } else {
                        detailBtn.setText("已满标");
                    }
                    break;
                case 5:
                    detailBtn.setText("立即购买");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_btn));
                    break;
                case 6:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
                case 7:
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    if (!entity.getData().getStartInterestTime().equals("") && !entity.getData().getNowTime().equals("")) {
                        if (DateUtils.getStringToDateDay(entity.getData().getStartInterestTime()) > DateUtils.getStringToDateDay(entity.getData().getNowTime())) {
                            detailBtn.setText("已满标");
                        } else if (DateUtils.getStringToDateDay(entity.getData().getStartInterestTime()) <= DateUtils.getStringToDateDay(entity.getData().getNowTime())) {
                            if (entity.getData().getIsRepay() == 0) {
                                detailBtn.setText("计息中");
                            } else {
                                detailBtn.setText("已回款");
                            }
                        }
                    } else {
                        detailBtn.setText("已满标");
                    }
                    break;
                case 8:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
                case 9:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
                case 10:
                    detailBtn.setText("准备抢购");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
                default:
                    detailBtn.setText("已结束");
                    detailBtn.setBackground(ContextCompat.getDrawable(mContext, R.drawable.select_panic_buying_white_btn));
                    break;
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
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

