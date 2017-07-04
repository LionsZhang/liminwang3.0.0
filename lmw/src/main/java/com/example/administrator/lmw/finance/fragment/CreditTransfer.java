package com.example.administrator.lmw.finance.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.finance.activity.DetailActivity;
import com.example.administrator.lmw.finance.adapter.CreditTransferAdapter;
import com.example.administrator.lmw.finance.entity.CreditDatasBean;
import com.example.administrator.lmw.finance.entity.CreditEntity;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.inteface.OnXScrollOreitationListener;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 债权转让列表
 * <p>
 * Created by Administrator on 2016/8/23 0023.
 */
public class CreditTransfer extends BaseFragment implements XListView.IXListViewListener {

    private CreditTransferAdapter creditTransferAdapter;
    private IsEmptyAdapter isEmptyAdapter;
    private float mPosX, mPosY, mCurPosX, mCurPosY;
    private int pageIndex = 1;
    private int orderType = 1;
    private int sortType = 2;
    private int pageCount;
    private boolean isVisible = false;
    private boolean isPrepared = false;
    @InjectView(R.id.credit_earnings_iv)
    ImageView creditEarningsIv;
    @InjectView(R.id.credit_earnings_lin)
    LinearLayout creditEarningsLin;
    @InjectView(R.id.credit_period_iv)
    ImageView creditPeriodIv;
    @InjectView(R.id.credit_period_lin)
    LinearLayout creditPeriodLin;
    @InjectView(R.id.credit_default_lin)
    LinearLayout creditDefaultLin;
    @InjectView(R.id.credit_earnings_title)
    LinearLayout creditEarningsTitle;
    @InjectView(R.id.xlistview_lv)
    XListView xlistviewLv;
    @InjectView(R.id.credit_earnings)
    TextView creditEarnings;
    @InjectView(R.id.credit_data)
    TextView creditData;
    @InjectView(R.id.credit_default)
    TextView creditDefault;
    private OnXScrollOreitationListener onXScrollOreitationListener;
    private boolean isEmptyAdatpter = false;

    /**
     * Set listener.
     *
     * @param listener
     */
    public void setOnXScrollOreitationListener(OnXScrollOreitationListener listener) {
        onXScrollOreitationListener = listener;
    }

    @Override
    protected void initializeData() {

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            isVisible = true;
//            lazyLoad();
//        } else {
//            isVisible = false;
//        }
//    }

    private void lazyLoad() {
//        if (isVisible && isPrepared) {
        credithttp(1, 1, 10, 2);
//        }
    }

    @Override
    protected void initializeView() {
        xlists();
        picture();
        isPrepared = true;
        lazyLoad();
        setGestureListener();

        /**
         * listview 点击事件
         */
        xlistviewLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CreditDatasBean bean = (CreditDatasBean) adapterView.getItemAtPosition(i);
                if (bean != null) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, DetailActivity.class);
                    intent.putExtra("subjectId", bean.getDebtId());// 标的标识
                    intent.putExtra("type", "1");// 0定期宝1是债权
                    startActivity(intent);
                }
            }
        });
        // 默认按钮
        creditDefaultLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture();
                textviewfind();
                creditDefault.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_buy));
                pageIndex = 1;
                orderType = 1;
                sortType = 2;
                credithttp(1, 1, 10, 2);


            }
        });
        // 年华收益按钮
        creditEarningsLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture();
                textviewfind();
                creditEarnings.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_buy));
                if (orderType != 2) {
                    credithttp(2, 1, 10, 2);
                    PicassoManager.getInstance().display(mContext, creditEarningsIv, R.drawable.credit_down);
                    sortType = 2;
                } else if (sortType == 2) {
                    credithttp(2, 1, 10, 1);
                    PicassoManager.getInstance().display(mContext, creditEarningsIv, R.drawable.credit_up);
                    sortType = 1;
                } else {
                    credithttp(2, 1, 10, 2);
                    PicassoManager.getInstance().display(mContext, creditEarningsIv, R.drawable.credit_down);
                    sortType = 2;
                }
                orderType = 2;
                pageIndex = 1;
            }
        });
        // 剩余期限按钮
        creditPeriodLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture();
                textviewfind();
                creditData.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_buy));
                if (orderType != 3) {
                    credithttp(3, 1, 10, 1);
                    PicassoManager.getInstance().display(mContext, creditPeriodIv, R.drawable.credit_up);
                    sortType = 1;
                } else if (sortType == 2) {
                    credithttp(3, 1, 10, 1);
                    PicassoManager.getInstance().display(mContext, creditPeriodIv, R.drawable.credit_up);
                    sortType = 1;
                } else {
                    credithttp(3, 1, 10, 2);
                    PicassoManager.getInstance().display(mContext, creditPeriodIv, R.drawable.credit_down);
                    sortType = 2;
                }
                orderType = 3;
                pageIndex = 1;
            }
        });


    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_credit_transfer_layout;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (xlistviewLv != null) {
                    xlistviewLv.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    picture();
                    credithttp(orderType, pageIndex, 10, sortType);
                    xlistviewLv.stopRefresh();
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (xlistviewLv != null) {
                    pageIndex++;
                    if (pageIndex > pageCount) {
                        xlistviewLv.setPullLoadEnable(false);
                        ToastUtil.showToast(mContext, "没有更多数据");
                    } else {
                        credithttp(orderType, pageIndex, 10, sortType);
                    }
                    xlistviewLv.stopLoadMore();
                }
            }
        }, 1000);

    }

    /**
     * Xlistview 实例化
     */
    private void xlists() {
        if (xlistviewLv != null) {
            if (onXScrollOreitationListener != null)
                xlistviewLv.setOnXScrollOreitationListener(onXScrollOreitationListener);
            xlistviewLv.setPullRefreshEnable(true);// 刷新
            xlistviewLv.setPullLoadEnable(true);// 加载
            xlistviewLv.setXListViewListener(this);// 监听
            xlistviewLv.setRefreshTime(DateUtil.getRefreshTime());

            creditTransferAdapter = new CreditTransferAdapter(mContext);
            xlistviewLv.setAdapter(creditTransferAdapter);
        }

    }

    /**
     * 实例化文字
     */
    private void textviewfind() {
        creditEarnings.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        creditData.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        creditDefault.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));

    }

    /**
     * 图片按钮实例化
     */
    private void picture() {
        PicassoManager.getInstance().display(mContext, creditEarningsIv, R.drawable.credit_default);
        PicassoManager.getInstance().display(mContext, creditPeriodIv, R.drawable.credit_default);
    }



    /**
     * 访问网络
     *
     * @param orderType
     * @param pageIndex
     * @param pageSize
     * @param sortType
     */
    private void credithttp(int orderType, final int pageIndex, int pageSize, int sortType) {
        Singlton.getInstance(FinanceHttp.class).getCreditTrannsfer(mContext, orderType, pageIndex, pageSize, sortType, new OnResponseListener<CreditEntity>() {
            @Override
            public void onSuccess(CreditEntity creditEntity) {
                if (xlistviewLv == null) return;
                if (xlistviewLv == null) return;
                if (creditEntity == null) return;
                if (creditEntity.getData() == null) return;
                if (creditEntity.getData().getDatas() == null) return;
                if (creditEntity.getCode().equals("0")) {
                    if (isEmptyAdatpter) {
                        xlistviewLv.setAdapter(creditTransferAdapter);
                        isEmptyAdatpter = false;
                    }

                    if (pageIndex == 1) {
                        if (creditEntity.getData().getDatas().size() < 10) {
                            xlistviewLv.setPullLoadEnable(false);
                        } else {
                            xlistviewLv.setPullLoadEnable(true);
                        }
                        creditTransferAdapter.resetDatas(creditEntity.getData().getDatas());
                    } else {
                        if (creditEntity.getData().getDatas().size() > 0) {
                            creditTransferAdapter.addData(creditEntity.getData().getDatas());
                        } else {
                            xlistviewLv.setPullLoadEnable(false);
                            ToastUtil.showToast(mContext, "没有更多数据");
                        }
                    }
                    pageCount = creditEntity.getData().getPageCount();// 添加最多页数标识
                } else {
                    isEmptyAdatpter = true;
                    isEmptyAdapter = new IsEmptyAdapter(mContext, creditEntity.getMsg());
                    xlistviewLv.setAdapter(isEmptyAdapter);
                    xlistviewLv.setPullLoadEnable(false);
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (xlistviewLv != null) {
                    isEmptyAdatpter = true;
                    isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    xlistviewLv.setAdapter(isEmptyAdapter);
                    xlistviewLv.setPullLoadEnable(false);
                }
            }
        });
    }


    /**
     * 设置上下滑动作监听器
     *
     * @author jczmdeveloper
     */
    private void setGestureListener() {
        xlistviewLv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 0)) {
                            //手势向下滑動
//                            creditEarningsTitle.setVisibility(View.VISIBLE);
                        } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 0)) {
                            //手势向上滑动
//                            creditEarningsTitle.setVisibility(View.GONE);
                        }

                        break;
                }
                return false;
            }

        });
    }

}
