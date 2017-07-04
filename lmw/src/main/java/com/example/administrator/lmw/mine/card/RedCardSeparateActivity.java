package com.example.administrator.lmw.mine.card;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.card.adapter.RedCardAdapter;
import com.example.administrator.lmw.mine.card.cardhttp.CardHttp;
import com.example.administrator.lmw.mine.card.cardhttp.QueryStatus;
import com.example.administrator.lmw.mine.card.entity.DataRedBean;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import butterknife.InjectView;

/**
 * 已拆红包页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/23 17:38
 **/
public class RedCardSeparateActivity extends BaseTitleActivity implements XListView.IXListViewListener {

    @InjectView(R.id.fl_top_operate)
    FrameLayout mTvTopOperate;
    @InjectView(R.id.lv_data)
    XListView mLvData;
    @InjectView(R.id.tv_bottom)
    TextView mTvBottom;
    @InjectView(R.id.tv_no_data)
    TextView mTvNoData;
    @InjectView(R.id.fl_bottom)
    FrameLayout mFlBottom;


    private RedCardAdapter adapter;
    private int pageIndex = 1;
    private int pageCount;
    private IsEmptyAdapter mEmptyAdapter;
    private QueryStatus queryStatus = QueryStatus.USED;//默认已拆开的红包
    private boolean isSetEmptyAdapter = false;//是否设置了空设配器显示无数据页面，默认设置是数据源的adapter
    private String mToken;

    @Override
    protected void initializeData() {
        mToken = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");
        adapter = new RedCardAdapter(mContext);
    }

    @Override
    protected void initializeView() {
        mTvTopOperate.setVisibility(View.GONE);
        mFlBottom.setVisibility(View.GONE);
        initList();
        getCardRed(true, 1, 10, queryStatus, mToken);
    }

    @Override
    protected void setTitleContentView() {
        initView(R.string.txt_red_separate_1, R.layout.fragment_red_card_layout);
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
                if (mLvData != null)
                    if (dataRedPacket != null)
                        if (dataRedPacket.getData() != null && dataRedPacket.getData().getDatas() != null) {
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
                            } else {
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

}
