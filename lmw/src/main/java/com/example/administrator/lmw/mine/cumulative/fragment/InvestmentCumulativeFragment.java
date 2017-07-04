package com.example.administrator.lmw.mine.cumulative.fragment;

import android.os.Handler;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.cumulative.adapter.InvestmentAdapter;
import com.example.administrator.lmw.mine.cumulative.entity.CumulativeEntity;
import com.example.administrator.lmw.mine.cumulative.utils.CumulativeHttp;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 投资收益 fragment
 * <p>
 * Created by Administrator on 2016/8/24 0024.
 */
public class InvestmentCumulativeFragment extends BaseFragment implements XListView.IXListViewListener {

    private Handler handler;
    @InjectView(R.id.cumulative_xlv)
    XListView cumulativeXlv;
    private int pageIndex = 1;
    private InvestmentAdapter adapter;
    private IsEmptyAdapter isEmptyAdapter;
    private boolean isVisible = false;
    private boolean isPrepared = false;

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        xlists();
        isPrepared = true;
        lazyLoad();
    }

    private void lazyLoad() {
        cumulativelhttp(1, 1);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_cumulative_layout;
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    cumulativelhttp(1, 1);
                    cumulativeXlv.stopRefresh();
                }
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    pageIndex++;
                    cumulativelhttp(1, pageIndex);
                    cumulativeXlv.stopLoadMore();
                }
            }
        }, 1000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * Xlistview 实例化
     */
    private void xlists() {
        if (cumulativeXlv != null) {
            handler = new Handler();
            cumulativeXlv.setPullRefreshEnable(true);// 刷新
            cumulativeXlv.setPullLoadEnable(true);// 加载
            cumulativeXlv.setXListViewListener(this);
            cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
        }
    }

    /**
     * 访问网络
     */
    private void cumulativelhttp(int incomeType, final int pageIndex) {
        Singlton.getInstance(CumulativeHttp.class).getCumulative(mContext, incomeType, pageIndex, new OnResponseListener<BaseResult<CumulativeEntity>>() {
            @Override
            public void onSuccess(BaseResult<CumulativeEntity> cumulativeEntity) {
                if (cumulativeXlv == null) return;
                if (cumulativeEntity == null) return;
                if (cumulativeEntity.getData() == null) return;
                if (cumulativeEntity.getData().getDatas() == null) return;
                if (cumulativeEntity.getCode().equals("0")) {
                    if (pageIndex == 1) {
                        if (cumulativeEntity.getData().getDatas().size() < 10) {
                            cumulativeXlv.setPullLoadEnable(false);
                        } else {
                            cumulativeXlv.setPullLoadEnable(true);
                        }
                        if (cumulativeEntity.getData().getDatas().size() > 0) {
                            adapter = new InvestmentAdapter(mContext, cumulativeEntity.getData().getDatas(), R.layout.cumulative_layout_item);
                            cumulativeXlv.setAdapter(adapter);
                        } else {
                            isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));
                            cumulativeXlv.setAdapter(isEmptyAdapter);
                        }
                    } else {
                        if (cumulativeEntity.getData().getDatas().size() > 0) {
                            adapter.addData(cumulativeEntity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast(mContext, "没有更多数据");
                            cumulativeXlv.setPullLoadEnable(false);
                        }
                    }
                } else if (cumulativeEntity.getCode().equals("150006")) {
                } else if (cumulativeEntity.getCode().equals("1000")) {
                } else {
                    if (cumulativeXlv != null) {
                        isEmptyAdapter = new IsEmptyAdapter(mContext, cumulativeEntity.getMsg());
                        cumulativeXlv.setAdapter(isEmptyAdapter);
                        cumulativeXlv.setPullLoadEnable(false);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (cumulativeXlv != null) {
                    isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    cumulativeXlv.setAdapter(isEmptyAdapter);
                    cumulativeXlv.setPullLoadEnable(false);
                }
            }
        });
    }
}
