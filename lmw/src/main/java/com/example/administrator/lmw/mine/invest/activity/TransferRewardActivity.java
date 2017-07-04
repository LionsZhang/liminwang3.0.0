package com.example.administrator.lmw.mine.invest.activity;

import android.content.res.Configuration;
import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.mine.invest.adapter.TransferRewardAdapter;
import com.example.administrator.lmw.mine.invest.entity.DataTranferReward;
import com.example.administrator.lmw.mine.invest.utils.InvestmentHttp;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.view.XListView;

import butterknife.InjectView;

/**
 * 待结卡券奖励页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/16 11:07
 **/
public class TransferRewardActivity extends BaseTitleActivity implements XListView.IXListViewListener {


    @InjectView(R.id.lv_data)
    XListView mLvData;


    private TransferRewardAdapter adapter;
    private IsEmptyAdapter mEmptyAdapter;
    private int pageIndex = 1;
    private boolean isSetEmptyAdapter = false;//是否设置了空设配器显示无数据页面，默认设置是数据源的adapter

    @Override
    protected void initializeData() {
        adapter = new TransferRewardAdapter(mContext);
    }

    @Override
    protected void initializeView() {
        initList();
        getTransferRewardList(true, 1, 10);
    }

    @Override
    protected void setTitleContentView() {
        initView(R.string.txt_transfer_reward, R.layout.activity_transfer_reward);
    }


    private void initList() {
        if (mLvData != null) {
            mLvData.setPullRefreshEnable(true);
            mLvData.setPullLoadEnable(false);
            mLvData.setXListViewListener(this);
            mLvData.setRefreshTime(DateUtil.getRefreshTime());
            mLvData.setAdapter(adapter);
        }
    }

    @Override
    public void onRefresh() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLvData != null) {
                    mLvData.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    getTransferRewardList(true, 1, 10);
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
                    getTransferRewardList(false, pageIndex, 10);
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
     */
    private void getTransferRewardList(final boolean isRefreshOrFirst, final int pageIndex, final int pageSize) {
        Singlton.getInstance(InvestmentHttp.class).getTranferReward(mContext, pageIndex, pageSize, new OnResponseListener<BaseResult<DataTranferReward>>() {

            @Override
            public void onSuccess(BaseResult<DataTranferReward> entity) {
                if (entity != null && TextUtils.equals(entity.getCode(), "0") && entity.getData() != null && entity.getData().getDatas() != null) {
                    //设置是否可以加载更多
                    mLvData.setPullLoadEnable(entity.getData().getDatas().size() >= pageSize);
                    if (entity.getData().getDatas().size() > 0) {
                        if (isSetEmptyAdapter) {//当前是空页面适配，需要重新切换数据源adapter
                            isSetEmptyAdapter = false;
                            mLvData.setAdapter(adapter);
                        }
                        if (isRefreshOrFirst)//刷新
                            adapter.resetDatas(entity.getData().getDatas());
                        else
                            adapter.addData(entity.getData().getDatas());
                    } else if (isRefreshOrFirst) {
                        mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));
                        mLvData.setAdapter(mEmptyAdapter);
                        isSetEmptyAdapter = true;
                    }
                } else if (entity.getCode().equals("150006")) {
                } else if (entity.getCode().equals("1000")) {
                } else {
                    if (mLvData != null) {
                        isSetEmptyAdapter = true;
                        mEmptyAdapter = new IsEmptyAdapter(mContext, entity.getMsg());
                        mLvData.setAdapter(mEmptyAdapter);

                    }
                }

            }

            @Override
            public void onFail(Throwable e) {
                if (mLvData != null) {
                    mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    mLvData.setAdapter(mEmptyAdapter);
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}


