package com.example.administrator.lmw.mine.account.fragment;

import android.os.Handler;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.account.adapter.WithdrawAdapter;
import com.example.administrator.lmw.mine.account.entity.AccountEntity;
import com.example.administrator.lmw.mine.account.http.AccountHttp;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import butterknife.InjectView;

/**
 * 可用余额 提现
 * <p>
 * Created by Administrator on 2016/9/3 0003.
 */
public class WithdrawFragment extends BaseFragment implements XListView.IXListViewListener {

    private Handler handler;
    private WithdrawAdapter adapter;
    private IsEmptyAdapter isEmptyAdapter;
    private int pageIndex = 1;// 翻页
    private int pageCount = 0;// 总共页数
    private int pageSize = 10;// 每页数量
    private int appFundsType = 5;// 类型  1全部,2购买 ,3回款,4充值,5提现,6其他
    @InjectView(R.id.cumulative_xlv)
    XListView cumulativeXlv;

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        xlist();
        getWithdraw(appFundsType, 1, pageSize);

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
                    getWithdraw(appFundsType, 1, pageSize);
                    cumulativeXlv.stopRefresh();
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        cumulativeXlv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    pageIndex++;
                    if (pageIndex > pageCount) {
                        ToastUtil.showToast(mContext, "没有更多数据");
                        cumulativeXlv.setPullLoadEnable(false);
                    } else {
                        getWithdraw(appFundsType, pageIndex, pageSize);
                    }
                    cumulativeXlv.stopLoadMore();
                }
            }
        }, 1000);

    }

    /**
     * xlist 实例化
     */
    private void xlist() {
        if (cumulativeXlv != null) {
            handler = new Handler();
            cumulativeXlv.setPullLoadEnable(true);
            cumulativeXlv.setPullRefreshEnable(true);
            cumulativeXlv.setXListViewListener(this);
            cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
        }
    }

    private void getWithdraw(int appFundsType, final int pageIndex, int pageSize) {
        Singlton.getInstance(AccountHttp.class).getAccount(mContext, appFundsType, pageIndex, pageSize, new OnResponseListener<BaseResult<AccountEntity>>() {
            @Override
            public void onSuccess(BaseResult<AccountEntity> accountEntity) {
                if (cumulativeXlv == null) return;
                if (accountEntity == null) return;
                if (accountEntity.getData() == null) return;
                if (accountEntity.getData().getDatas() == null) return;
                if (accountEntity.getCode().equals("0")) {
                    if (pageIndex == 1) {
                        if (accountEntity.getData().getDatas().size() < 10) {
                            cumulativeXlv.setPullLoadEnable(false);
                        } else {
                            cumulativeXlv.setPullLoadEnable(true);
                        }
                        if (accountEntity.getData().getDatas().size() > 0) {
                            adapter = new WithdrawAdapter(mContext, accountEntity.getData().getDatas(), R.layout.account_item);
                            cumulativeXlv.setAdapter(adapter);
                        } else {
                            isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));
                            cumulativeXlv.setAdapter(isEmptyAdapter);
                        }
                    } else {
                        if (accountEntity.getData().getDatas().size() > 0) {
                            adapter.addWithdraw(accountEntity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast(mContext, "没有更多数据");
                            cumulativeXlv.setPullLoadEnable(false);
                        }
                    }
                    pageCount = accountEntity.getData().getPageCount();// 最大页数
                } else if (accountEntity.getCode().equals("150006")) {
                } else if (accountEntity.getCode().equals("1000")) {
                } else {
                    if (cumulativeXlv != null) {
                        isEmptyAdapter = new IsEmptyAdapter(mContext, accountEntity.getMsg());
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
