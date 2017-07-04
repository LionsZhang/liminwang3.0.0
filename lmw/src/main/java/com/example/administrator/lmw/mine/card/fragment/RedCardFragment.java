package com.example.administrator.lmw.mine.card.fragment;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.card.RedCardSeparateActivity;
import com.example.administrator.lmw.mine.card.adapter.RedCardAdapter;
import com.example.administrator.lmw.mine.card.cardhttp.CardHttp;
import com.example.administrator.lmw.mine.card.cardhttp.CouponType;
import com.example.administrator.lmw.mine.card.cardhttp.QueryStatus;
import com.example.administrator.lmw.mine.card.entity.DataRedBean;
import com.example.administrator.lmw.mine.card.entity.RedBean;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 红包页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/16 11:43
 **/
public class RedCardFragment extends BaseFragment implements XListView.IXListViewListener {

    @InjectView(R.id.fl_top_operate)
    FrameLayout mTvTopOperate;
    @InjectView(R.id.lv_data)
    XListView mLvData;
    @InjectView(R.id.tv_bottom)
    TextView mTvBottom;
    @InjectView(R.id.tv_no_data)
    TextView mTvNoData;


    private RedCardAdapter adapter;
    private int pageIndex = 1;
    private int pageCount;
    private IsEmptyAdapter mEmptyAdapter;
    private QueryStatus queryStatus = QueryStatus.UNUSED;//默认没有拆开的红包
    private boolean isSetEmptyAdapter = false;//是否设置了空设配器显示无数据页面，默认设置是数据源的adapter
    private String mToken;
    private OnCountListener mOnCountListener;
    private String mInstructeUrl;//使用说明url

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null && context instanceof OnCountListener)
            mOnCountListener = (OnCountListener) context;
    }

    @Override
    protected void initializeData() {
        mToken = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");
        adapter = new RedCardAdapter(mContext);
        getInstructeUrl();
    }

    /**
     * 获取使用说明链接
     */
    private void getInstructeUrl() {
        Singlton.getInstance(CardHttp.class).getCouponInstruction(mContext, CouponType.RED_PACKET, new OnResponseListener<String>() {
            @Override
            public void onSuccess(String result) {
                mInstructeUrl = result;
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    @Override
    protected void initializeView() {
        mTvBottom.setText(R.string.txt_red_separate);
        mTvBottom.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_coupon_red, 0, 0, 0);
        initList();
        getCardRed(true, 1, 10, queryStatus, mToken);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_red_card_layout;
    }

    @Override
    public void onRefresh() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLvData != null) {
                    mLvData.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;

                    getCardRed(true, 1, 10, queryStatus, mToken);
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
                        getCardRed(false, pageIndex, 10, queryStatus, mToken);
                    }
                    mLvData.stopLoadMore();
                }
            }
        }, 1000);

    }

    /**
     * list实例化
     */
    private void initList() {
        if (mLvData != null) {
            mLvData.setPullLoadEnable(false);
            mLvData.setPullRefreshEnable(false);
            mLvData.setXListViewListener(this);
            mLvData.setRefreshTime(DateUtil.getRefreshTime());
        }

        mLvData.setAdapter(adapter);
        mLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!isSetEmptyAdapter) {
                    RedBean bean = (RedBean) parent.getItemAtPosition(position);
                    if (Singlton.getInstance(DespositAccountManager.class).isOpenDesposit(mContext) && bean != null && queryStatus == QueryStatus.UNUSED) {
                        getRed(bean.getId(), bean.getTitle(), bean.getAmount(), mToken);
                    }
                }
            }
        });

    }


    /**
     * 获取红包列表
     *
     * @param isRefreshOrFirst 是否第一次请求或者是刷新
     * @param pageIndex
     * @param pageSize
     * @param token
     */
    private void getCardRed(final boolean isRefreshOrFirst, final int pageIndex, int pageSize, QueryStatus queryStatus, String token) {
        Singlton.getInstance(CardHttp.class).getCardRed(mContext, pageIndex, pageSize, queryStatus, token, new OnResponseListener<BaseResult<DataRedBean>>() {
            @Override
            public void onSuccess(BaseResult<DataRedBean> dataRedPacket) {
                if (dataRedPacket != null && TextUtils.equals(dataRedPacket.getCode(), "0") && dataRedPacket.getData() != null && dataRedPacket.getData().getDatas() != null) {
                    if (dataRedPacket.getData().getDatas().size() > 0) {

                        setPullLoadEnable(dataRedPacket.getData().getDatas().size() >= 10);

                        if (isSetEmptyAdapter) {//空数据适配器切换成有源适配器
                            isSetEmptyAdapter = false;
                            mLvData.setAdapter(adapter);
                        }
                        if (isRefreshOrFirst)//修改查看类型或者刷新
                            adapter.resetDatas(dataRedPacket.getData().getDatas());
                        else
                            adapter.addData(dataRedPacket.getData().getDatas());
                    } else if (isRefreshOrFirst) {
                        isSetEmptyAdapter = true;
                        mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));
                        mLvData.setAdapter(mEmptyAdapter);
                        setPullLoadEnable(false);
                    }

                    pageCount = dataRedPacket.getData().getPageCount();// 最大页数
                } else {
                    if (mLvData != null) {
                        mEmptyAdapter = new IsEmptyAdapter(mContext, dataRedPacket.getMsg());
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
     * 拆红包
     *
     * @param id
     * @param token
     */
    private void getRed(String id, final String title, final double money, String token) {
        Singlton.getInstance(CardHttp.class).getRed(mContext, id, token, new OnResponseListener<BaseResult<Void>>() {
            @Override
            public void onSuccess(BaseResult<Void> result) {
                int code = result != null ? NumberParserUtil.parserInt(result.getCode()) : -1;
                if (code == 0) {
                    if (mOnCountListener != null) {
                        mOnCountListener.onCount(adapter.getCount() - 1);
                    }
                    onRefresh();
                    buyRedcard("恭喜您！" + title + "红包，" + money + "元现金，已返现至您的账户");

                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 提示实名认证提示框
     */
    private void buyRedcard(String str) {
        Singlton.getInstance(PopWindowManager.class).
                showOneButtonDialog(mContext, "提示", str, "知道了",
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 2) {

                                }
                            }
                        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @OnClick({R.id.fl_top_operate, R.id.tv_bottom})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_top_operate://查看操作 startActivity(CardExplanation.class);
                Intent instruction = new Intent(mContext, WebViewMore.class);
                instruction.putExtra(WebViewMore.INTENT_KEY_TITLE, mContext.getResources().getString(R.string.txt_use));
                instruction.putExtra(WebViewMore.INTENT_KEY_URL, mInstructeUrl);
                startActivity(instruction);
                break;
            case R.id.tv_bottom://切换查看红包类型
                startActivity(RedCardSeparateActivity.class);
                break;
        }
    }


    public interface OnCountListener {
        void onCount(int count);
    }

}
