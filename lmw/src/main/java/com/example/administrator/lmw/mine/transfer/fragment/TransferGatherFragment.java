package com.example.administrator.lmw.mine.transfer.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.mine.invest.utils.Arith;
import com.example.administrator.lmw.mine.transfer.adapter.TransferGatherAdapter;
import com.example.administrator.lmw.mine.transfer.entity.TransferCountEntity;
import com.example.administrator.lmw.mine.transfer.entity.TransferEntity;
import com.example.administrator.lmw.mine.transfer.http.TransferHttp;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.XListView;

import butterknife.InjectView;

/**
 * 预期回款 汇总
 * <p>
 * Created by Administrator on 2016/9/3 0003.
 */
public class TransferGatherFragment extends BaseFragment implements XListView.IXListViewListener {

    @InjectView(R.id.cumulative_xlv)
    XListView listview;
    private TextView headTv, headQuantity, headMoneys, financialQuantity, financialMoneys, claimsQuantity, claimsMoneys;
    private LinearLayout financialLin, claimsLin;
    private Handler handler;
    private TransferGatherAdapter adapter;
    private IsEmptyAdapter isEmptyAdapter;
    private int pageIndex = 1;
    private int pageCount = 0;
    private int pageSize = 10;

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        headview();
        xlist();
        fristhttp();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_cumulative_layout;
    }

    private void fristhttp() {
        httpCount(SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
        httpTransfer(pageIndex, pageSize, "", SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
    }

    /**
     * listview 头部实例化
     */
    private void headview() {
        View head = LayoutInflater.from(mContext).inflate(R.layout.transfer_head, null);
        headTv = (TextView) head.findViewById(R.id.transfer_head_tv);
        headQuantity = (TextView) head.findViewById(R.id.transfer_head_quantity);
        headMoneys = (TextView) head.findViewById(R.id.transfer_head_moneys);
        financialQuantity = (TextView) head.findViewById(R.id.transfer_head_financial_quantity);
        financialMoneys = (TextView) head.findViewById(R.id.transfer_head_financial_moneys);
        claimsQuantity = (TextView) head.findViewById(R.id.transfer_head_claims_quantity);
        claimsMoneys = (TextView) head.findViewById(R.id.transfer_head_claims_moneys);
        financialLin = (LinearLayout) head.findViewById(R.id.transfer_head_financial_lin);
        claimsLin = (LinearLayout) head.findViewById(R.id.transfer_head_claims_lin);
        listview.addHeaderView(head);
        headTv.setText("待回款共计");
        claimsLin.setVisibility(View.VISIBLE);
        financialLin.setVisibility(View.VISIBLE);

    }

    /**
     * xlistview实例化
     */
    private void xlist() {
        if (listview != null) {
            handler = new Handler();
            listview.setPullRefreshEnable(true);
            listview.setPullLoadEnable(true);
            listview.setXListViewListener(this);
            listview.setRefreshTime(DateUtil.getRefreshTime());
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listview != null) {
                    listview.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    fristhttp();
                    listview.stopRefresh();
                }
            }
        }, 1000);

    }

    /**
     * 加载
     */
    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listview != null) {
                    pageIndex++;
//                if (pageIndex > pageCount) {
//                    ToastUtil.showToast(mContext, "没有更多数据");
//                } else {
                    httpTransfer(pageIndex, pageSize, "", SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));
//                }
                    listview.stopLoadMore();
                }
            }
        }, 1000);
    }

    /**
     * @param token
     */
    private void httpCount(String token) {
        Singlton.getInstance(TransferHttp.class).getRepayment(mContext, token, new OnResponseListener<BaseResult<TransferCountEntity>>() {
            @Override
            public void onSuccess(BaseResult<TransferCountEntity> transferCountEntity) {
                if (transferCountEntity == null) return;
                if (transferCountEntity.getData() == null) return;
                if (transferCountEntity.getData().getDatas() == null) return;
                if (transferCountEntity.getCode().equals("0")) {
                    if (transferCountEntity.getData().getDatas().size() > 0) {
                        double quantity = 0;
                        double moneys = 0;
                        for (int i = 0; i < transferCountEntity.getData().getDatas().size(); i++) {
                            quantity = Arith.add(transferCountEntity.getData().getDatas().get(i).getRepayCount(), quantity);
                            moneys = Arith.add(transferCountEntity.getData().getDatas().get(i).getRepayAmt(), moneys);
                            if (transferCountEntity.getData().getDatas().get(i).getIsDebtRepay() == 0) {// 理财
                                financialQuantity.setText(transferCountEntity.getData().getDatas().get(i).getRepayCount() + "");
                                financialMoneys.setText(transferCountEntity.getData().getDatas().get(i).getRepayAmt() + "");
                            } else if (transferCountEntity.getData().getDatas().get(i).getIsDebtRepay() == 1) {// 债权
                                claimsQuantity.setText(transferCountEntity.getData().getDatas().get(i).getRepayCount() + "");
                                claimsMoneys.setText(transferCountEntity.getData().getDatas().get(i).getRepayAmt() + "");
                            }
                        }
                        headQuantity.setText((int) quantity + "");// 共多少笔
                        headMoneys.setText(moneys + "");// 总金额
                    } else {
                        headQuantity.setText("0");// 共多少笔
                        headMoneys.setText("0.00");// 总金额
                        financialQuantity.setText("0");
                        financialMoneys.setText("0.00");
                        claimsQuantity.setText("0");
                        claimsMoneys.setText("0.00");
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @param isDebt
     * @param token
     */
    private void httpTransfer(final int pageIndex, int pageSize, String isDebt, String token) {
        Singlton.getInstance(TransferHttp.class).getTransfer(mContext, pageIndex, pageSize, isDebt, token, new OnResponseListener<BaseResult<TransferEntity>>() {
            @Override
            public void onSuccess(BaseResult<TransferEntity> transferEntity) {
                if (listview == null) return;
                if (transferEntity == null) return;
                if (transferEntity.getData() == null) return;
                if (transferEntity.getData().getDatas() == null) return;
                if (transferEntity.getCode().equals("0")) {
                    if (pageIndex == 1) {
                        if (transferEntity.getData().getDatas().size() < 10) {
                            listview.setPullLoadEnable(false);
                        } else {
                            listview.setPullLoadEnable(true);
                        }
                        if (transferEntity.getData().getDatas().size() > 0) {
                            adapter = new TransferGatherAdapter(mContext, transferEntity.getData().getDatas(), R.layout.transfer_item);
                            listview.setAdapter(adapter);
                        } else {
                            isEmptyAdapter = new IsEmptyAdapter(mContext, getString(R.string.text_transfer));
                            listview.setAdapter(isEmptyAdapter);
                        }
                    } else {
                        if (transferEntity.getData().getDatas().size() > 0) {
                            adapter.addData(transferEntity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            showToast("没有更多数据");
                            listview.setPullLoadEnable(false);
                        }
                    }
                } else if (transferEntity.getCode().equals("150006")) {
                } else if (transferEntity.getCode().equals("1000")) {
                } else {
                    if (listview != null) {
                        isEmptyAdapter = new IsEmptyAdapter(mContext, transferEntity.getMsg());
                        listview.setAdapter(isEmptyAdapter);
                        listview.setPullLoadEnable(false);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (listview != null) {
                    isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    listview.setAdapter(isEmptyAdapter);
                    listview.setPullLoadEnable(false);
                }
            }
        });
    }
}
