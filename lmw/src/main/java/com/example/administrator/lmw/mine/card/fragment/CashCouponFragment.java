package com.example.administrator.lmw.mine.card.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.card.CardHistoryListActivity;
import com.example.administrator.lmw.mine.card.adapter.CashAdapter;
import com.example.administrator.lmw.mine.card.cardhttp.CardHttp;
import com.example.administrator.lmw.mine.card.cardhttp.CouponType;
import com.example.administrator.lmw.mine.card.cardhttp.QueryStatus;
import com.example.administrator.lmw.mine.card.entity.DataCashEntity;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 现金券页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/15 14:25
 **/
public class CashCouponFragment extends BaseFragment implements XListView.IXListViewListener {

    public static final String CARD_COUPON_STATUS = "card_coupon_status";//卡券的状态
    public static final String CARD_COUPON_TYPE = "card_coupon_type";//卡券的类型

    @InjectView(R.id.lv_data)
    XListView mLvData;
    @InjectView(R.id.tv_bottom)
    TextView mTvBottom;
    @InjectView(R.id.fl_top_operate)
    FrameLayout mFlTop;
    @InjectView(R.id.fl_bottom)
    FrameLayout mFlBottom;


    private CashAdapter adapter;
    private IsEmptyAdapter mEmptyAdapter;
    private int pageIndex = 1;
    private int pageCount;
    private boolean isSetEmptyAdapter = false;//是否设置了空设配器显示无数据页面，默认设置是数据源的adapter
    private QueryStatus queryStatus = QueryStatus.UNUSED;// [取值 ALL:所有;UNUSED:未使用;INUSE:使用中;USED:已使用;INUSE_USED:使用中和已使用;EXPIRED:已过期]，[示例：UNUSED]
    private String mToken;
    private CouponType mCouponType = CouponType.CASH_COUPON;//卡券类型(0现金券;1:红包;2抵扣券;3加息券;4理财金)
    private String bottomText;//底部查看过期显示文本
    private String mUnuseNoDataText;//可使用时无数据提示
    private String mNoDataText;//无数据提示
    private String mInstructeUrl;//使用说明url

    @Override
    protected void initializeData() {
        if (getArguments() != null) {
            queryStatus = (QueryStatus) getArguments().getSerializable(CARD_COUPON_STATUS);
            mCouponType = (CouponType) getArguments().getSerializable(CARD_COUPON_TYPE);
        }
        initBottomText();
        mToken = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");
        adapter = new CashAdapter(mContext);
        mNoDataText = mContext.getResources().getString(R.string.is_empty_1);
        getInstructeUrl();
    }

    /**
     * 获取使用说明链接
     */
    private void getInstructeUrl() {
        Singlton.getInstance(CardHttp.class).getCouponInstruction(mContext, mCouponType, new OnResponseListener<String>() {
            @Override
            public void onSuccess(String result) {
                ALLog.i("snubee" + "使用说明1  " + result);
                mInstructeUrl = result;
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 初始化底部提示文字
     */
    private void initBottomText() {
        if (mCouponType == CouponType.CASH_COUPON) {
            bottomText = mContext.getResources().getString(R.string.txt_card_over_coupon);
            mUnuseNoDataText = mContext.getResources().getString(R.string.txt_no_data_cash);
        } else if (mCouponType == CouponType.VOUCHER) {
            bottomText = mContext.getResources().getString(R.string.txt_card_over_voucher);
            mUnuseNoDataText = mContext.getResources().getString(R.string.txt_no_data_voucher);
        } else if (mCouponType == CouponType.RAISE_COUPON) {
            bottomText = mContext.getResources().getString(R.string.txt_card_over_raise);
            mUnuseNoDataText = mContext.getResources().getString(R.string.txt_no_data_raise);
        } else if (mCouponType == CouponType.FINANCIAL_GOLD) {
            bottomText = mContext.getResources().getString(R.string.txt_card_over_gold);
            mUnuseNoDataText = mContext.getResources().getString(R.string.txt_no_data_gold);
        }
    }

    @Override
    protected void initializeView() {
        setTopAndBottom();
        initList();
        getCash(true, 1, 10, queryStatus, mToken);

    }

    /**
     * 设置顶部使用说明和底部的查看已使用
     */
    private void setTopAndBottom() {
        if (queryStatus == QueryStatus.UNUSED) {
            mTvBottom.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.btn_set_get, 0);
            mFlTop.setVisibility(View.VISIBLE);
            mFlBottom.setVisibility(View.VISIBLE);
            mTvBottom.setText(bottomText);
        } else {
            mTvBottom.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            mFlTop.setVisibility(View.GONE);
            mTvBottom.setVisibility(View.GONE);
            mFlBottom.setVisibility(View.GONE);
            mTvBottom.setText(R.string.txt_show_card_time_tips);
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_red_card_layout;
    }

    private void initList() {
        mLvData.setPullRefreshEnable(true);
        mLvData.setPullLoadEnable(false);
        mLvData.setXListViewListener(this);
        mLvData.setRefreshTime(DateUtil.getRefreshTime());
        mLvData.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLvData != null) {
                    mLvData.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    getCash(true, 1, 10, queryStatus, mToken);
                    mLvData.stopRefresh();
                }
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLvData != null) {
                    pageIndex++;
                    if (pageIndex > pageCount) {
                        ToastUtil.showToast(mContext, "没有更多记录");
                    } else {
                        getCash(false, pageIndex, 10, queryStatus, mToken);
                    }
                    mLvData.stopLoadMore();
                }
            }
        }, 1000);
    }

    /**
     * 我的现金券
     *
     * @param isRefreshOrFirst 是否刷新或第一次请求
     * @param pageIndex
     * @param pageSize
     * @param queryStatus
     * @param token
     */
    private void getCash(final boolean isRefreshOrFirst, final int pageIndex, int pageSize, final QueryStatus queryStatus, String token) {
        Singlton.getInstance(CardHttp.class).getCash(mContext, mCouponType, pageIndex, pageSize, queryStatus, token, new OnResponseListener<BaseResult<DataCashEntity>>() {

            @Override
            public void onSuccess(BaseResult<DataCashEntity> result) {
                if (result != null)
                    if (TextUtils.equals(result.getCode(), "0") && result.getData() != null && result.getData().getDatas() != null) {
                        if (result.getData().getDatas().size() > 0) {
                            //如果是查看已使用或者过期卡券则隐藏尾部
                            setPullLoadEnable(result.getData().getDatas().size() >= 10);

                            if (isSetEmptyAdapter) {//当前是空页面适配，需要重新切换数据源adapter
                                isSetEmptyAdapter = false;
                                mLvData.setAdapter(adapter);
                            }
                            if (isRefreshOrFirst)//修改卡券查看类型或者刷新
                                adapter.resetDatas(result.getData().getDatas());
                            else
                                adapter.addData(result.getData().getDatas());
                        } else if (isRefreshOrFirst) {
                            mEmptyAdapter = new IsEmptyAdapter(mContext, queryStatus == QueryStatus.UNUSED ? mUnuseNoDataText : mNoDataText);
                            mLvData.setAdapter(mEmptyAdapter);
                            setPullLoadEnable(false);
                            isSetEmptyAdapter = true;
                        }

                        pageCount = result.getData().getPageCount();// 最大页数
                    } else {
                        if (mLvData != null) {
                            isSetEmptyAdapter = true;
                            mEmptyAdapter = new IsEmptyAdapter(mContext, result.getMsg());
                            mLvData.setAdapter(mEmptyAdapter);

                            setPullLoadEnable(false);
                        }
                    }

            }

            @Override
            public void onFail(Throwable e) {
                if (mLvData != null) {
                    mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    mLvData.setAdapter(mEmptyAdapter);
                    setPullLoadEnable(false);
                }
            }
        });

    }

    /**
     * 设置是否可以加载更多
     */
    private void setPullLoadEnable(boolean enable) {
        mLvData.setPullLoadEnable(enable);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.fl_top_operate, R.id.tv_bottom})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_top_operate://使用说明 WebViewMore,startActivity(CardExplanation.class);
                Intent instruction = new Intent(mContext, WebViewMore.class);
                instruction.putExtra(WebViewMore.INTENT_KEY_TITLE, mContext.getResources().getString(R.string.txt_use));
                instruction.putExtra(WebViewMore.INTENT_KEY_URL, mInstructeUrl);
                startActivity(instruction);
                break;
            case R.id.tv_bottom://查看已使用和过期的优惠券
                if (queryStatus == QueryStatus.UNUSED) {//当前页面是未使用卡券页面
                    Intent intent = new Intent(mContext, CardHistoryListActivity.class);
                    intent.putExtra(CardHistoryListActivity.INTENT_KEY_TITLE, bottomText);
                    intent.putExtra(CARD_COUPON_TYPE, mCouponType);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
