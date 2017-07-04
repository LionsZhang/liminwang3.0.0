package com.example.administrator.lmw.finance.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.finance.adapter.ProductItemAdapter;
import com.example.administrator.lmw.finance.entity.DataCateExtend;
import com.example.administrator.lmw.finance.entity.DataFinancialCategory;
import com.example.administrator.lmw.finance.entity.DataFinancialCategoryBean;
import com.example.administrator.lmw.finance.entity.DataProductItem;
import com.example.administrator.lmw.finance.entity.ProductItemDataBean;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.http.ResponseStatue;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.inteface.ViewOnClick;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ThreadManager;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import java.util.List;

import butterknife.InjectView;

/**
 * 理财 债权 子列表
 * <p/>
 * Created by Administrator on 2016/8/31 0031.
 */
public class ProductItem extends BaseTitleActivity implements XListView.IXListViewListener {

    @InjectView(R.id.select_xlistview)
    XListView selectXlistview;
    private ProductItemAdapter adapter;
    private IsEmptyAdapter mEmptyAdapter;
    private View view;
    private LinearLayout creditHeadLin, creditHeadEarningsLin, creditHeadPeriodLin, creditHeadDefaultLin;
    private ImageView creditHeadIv;
    private TextView tv_earnings, tv_period, tv_credit_def;
    private String type;
    private int pageCount;
    private int orderType = 0;// 排序条件1：年利率2：投资时间
    private int pageIndex = 1;// 页码
    private int sortType = 1; // 排序类型 1：升序 2：降序
    private int pageSize = 10;
    private int i = 1;// 标识
    private String isSearch;
    private boolean isEmptyAdapter = false;//是否为空适配器
    private String titleName = "";// 标题


    @Override
    protected void initializeData() {
        intents();
    }

    @Override
    protected void initializeView() {
        setTitleName(titleName);
        xlist();
        heads();
        https();
        click();
        adapter = new ProductItemAdapter(mContext);
        selectXlistview.setAdapter(adapter);
    }

    @Override
    protected void setTitleContentView() {
        initView("", R.layout.fragment_select_layout);
        setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortProductListPopWindow();
            }
        }, R.drawable.ic_main_nav_more);
    }

    /**
     * 弹出框
     * cateId  1004-车贷宝  1005-易优贷  1001-定期宝  1002-散标  1006-月增利
     * carLoanCount, easyToCount, regularCount, scatteredCount;
     */
    private void showSortProductListPopWindow() {
        if (datas != null)
            Singlton.getInstance(PopWindowManager.class).showPopWindowProductListSort(this,
                    datas, new ViewOnClick() {

                        @Override
                        public void onClick(int msgID, View v, Object... obj) {
                            String tag = null;
                            if (obj != null && obj.length > 0) {
                                tag = (String) obj[0];
                            }
                            ALLog.e("CATEID" + msgID + "  ---标记--" + tag);
                            showFilter(tag);
                            showPopWindowHttp(msgID + "");

                        }
                    });
    }

    /**
     * 是否显示筛选框判断
     *
     * @param isSearch //0不显示，1显示
     */
    private void showFilter(String isSearch) {
        Log.d("issearch----", isSearch + "");
        if (TextUtils.equals(isSearch, "0"))
            creditHeadLin.setVisibility(View.GONE);
        else
            creditHeadLin.setVisibility(View.VISIBLE);
    }

    /**
     * 筛选访问网络
     *
     * @param type
     */
    private void showPopWindowHttp(String type) {
        this.type = type;
        orderType = 0;
        pageIndex = 1;
        sortType = 1;
        headImage(type);
        borrowproducthttp(type, orderType, pageIndex, pageSize, sortType);
    }

    private void intents() {
        Intent intent = getIntent();
        if (intent.hasExtra("type"))
            type = intent.getStringExtra("type");
        if (intent.hasExtra("isSearch"))
            isSearch = intent.getStringExtra("isSearch");
        if (intent.hasExtra(PreferenceCongfig.INTENT_KEY_TITLE))
            titleName = intent.getStringExtra(PreferenceCongfig.INTENT_KEY_TITLE);
    }

    private void https() {
        headImage(type);
        borrowproducthttp(type, 0, 1, pageSize, 1);
        getHeadCategory();
    }

    private void click() {
        /**
         * listview点击
         */
        selectXlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductItemDataBean item = (ProductItemDataBean) parent.getItemAtPosition(position);
                if (item != null) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, DetailProductActivity.class);
                    intent.putExtra("subjectId", item.getBorrowId());// 标的标识
                    intent.putExtra("type", "0");// 0标，1债权
                    startActivity(intent);
                }
            }
        });
        /**
         * 预期年华收益
         */
        creditHeadEarningsLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderType != 1) {
                    borrowproducthttp(type, 1, 1, pageSize, 2);
                    picture(0, false, false);
                    sortType = 2;
                } else if (sortType == 2) {
                    borrowproducthttp(type, 1, 1, pageSize, 1);
                    picture(0, true, false);
                    sortType = 1;
                } else {
                    borrowproducthttp(type, 1, 1, pageSize, 2);
                    picture(0, false, false);
                    sortType = 2;
                }
                orderType = 1;
                pageIndex = 1;
            }
        });
        /**
         * 剩余期限
         */
        creditHeadPeriodLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1) {
                    borrowproducthttp(type, 2, 1, pageSize, 1);
                    picture(1, false, true);
                    sortType = 1;
                    i = 2;
                } else {
                    if (orderType != 2) {
                        borrowproducthttp(type, 2, 1, pageSize, 1);
                        picture(1, false, true);
                        sortType = 1;
                    } else if (sortType == 1) {
                        borrowproducthttp(type, 2, 1, pageSize, 2);
                        picture(1, false, false);
                        sortType = 2;
                    } else {
                        borrowproducthttp(type, 2, 1, pageSize, 1);
                        picture(1, false, true);
                        sortType = 1;
                    }
                }

                orderType = 2;
                pageIndex = 1;
            }
        });
        /**
         * 默认
         */
        creditHeadDefaultLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture(2, false, false);
                borrowproducthttp(type, 0, 1, pageSize, 1);
                orderType = 2;
                pageIndex = 1;
                sortType = 1;
                i = 1;
            }
        });
    }

    /**
     * 实例化listview
     */
    private void xlist() {
        if (selectXlistview != null) {
            selectXlistview.setPullLoadEnable(true);
            selectXlistview.setPullRefreshEnable(true);
            selectXlistview.setXListViewListener(this);
            selectXlistview.setRefreshTime(DateUtil.getRefreshTime());
        }
    }

    /**
     * 设置按钮颜色
     *
     * @param index     0预期年化收益，1剩余期限，2默认
     * @param earningUp
     * @param periodUp
     */
    private void picture(int index, boolean earningUp, boolean periodUp) {

        //默认
        tv_earnings.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.credit_default, 0);
        tv_period.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.credit_default, 0);
        tv_credit_def.setTextColor(ContextCompat.getColor(mContext, R.color.announcement_tv));
        switch (index) {
            case 0:
                tv_earnings.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                        earningUp ? R.drawable.credit_up : R.drawable.credit_down,
                        0);
                break;
            case 1:
                tv_period.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0,
                        periodUp ? R.drawable.credit_up : R.drawable.credit_down,
                        0);
                break;
            case 2:
                tv_credit_def.setTextColor(ContextCompat.getColor(mContext, R.color.select_list_buy));
                break;
        }


    }

    /**
     * 头部文件实例化
     */
    private void heads() {
        view = LayoutInflater.from(mContext).inflate(R.layout.credit_head_item, null);
        creditHeadIv = (ImageView) view.findViewById(R.id.credit_head_iv);
        creditHeadLin = (LinearLayout) view.findViewById(R.id.credit_head_lin);
        creditHeadEarningsLin = (LinearLayout) view.findViewById(R.id.credit_head_earnings_lin);
        creditHeadPeriodLin = (LinearLayout) view.findViewById(R.id.credit_head_period_lin);
        creditHeadDefaultLin = (LinearLayout) view.findViewById(R.id.credit_head_default_lin);
        tv_earnings = (TextView) view.findViewById(R.id.tv_earnings);
        tv_period = (TextView) view.findViewById(R.id.tv_period);
        tv_credit_def = (TextView) view.findViewById(R.id.tv_credit_def);
        selectXlistview.addHeaderView(view);
        showFilter(isSearch);
    }

    @Override
    public void onRefresh() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (selectXlistview != null) {
                    selectXlistview.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    borrowproducthttp(type, orderType, 1, pageSize, sortType);
                    selectXlistview.stopRefresh();
                    getHeadCategory();
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        ThreadManager.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (selectXlistview != null) {
                    pageIndex++;
                    if (pageIndex > pageCount) {
                        selectXlistview.setPullLoadEnable(false);
                        ToastUtil.showToast(mContext, "没有更多数据");
                    } else {
                        borrowproducthttp(type, orderType, pageIndex, pageSize, sortType);
                    }
                    selectXlistview.stopLoadMore();
                }
            }
        }, 1000);

    }

    /**
     * 访问网络
     *
     * @param cateId
     * @param orderType
     * @param pageIndex
     * @param pageSize
     * @param sortType
     */
    private void borrowproducthttp(String cateId, int orderType, final int pageIndex, int pageSize, int sortType) {
        Singlton.getInstance(FinanceHttp.class).getBorrowProduct(mContext, cateId, orderType, pageIndex, pageSize, sortType, new OnResponseListener<BaseResult<DataProductItem>>() {
            @Override
            public void onSuccess(BaseResult<DataProductItem> productItemEntity) {
                if (selectXlistview == null) return;
                if (productItemEntity == null) return;
                if (productItemEntity.getData() == null) return;
                if (productItemEntity.getData().getDatas() == null) return;
                if (productItemEntity.getCode().equals("0")) {
                    if (productItemEntity.getData().getDatas().size() == 0) {
                        isEmptyAdapter = true;
                        mEmptyAdapter = new IsEmptyAdapter(mContext, "利民网正为你努力上标中！");
                        selectXlistview.setAdapter(mEmptyAdapter);
                        selectXlistview.setPullLoadEnable(false);
                    } else {
                        if (isEmptyAdapter) {
                            isEmptyAdapter = false;
                            selectXlistview.setAdapter(adapter);
                        }
                        if (pageIndex == 1) {
                            if (productItemEntity.getData().getDatas().size() < 10) {
                                selectXlistview.setPullLoadEnable(false);
                            } else {
                                selectXlistview.setPullLoadEnable(true);
                            }
                            adapter.resetDatas(productItemEntity.getData().getDatas());
                        } else {
                            if (productItemEntity.getData().getDatas().size() > 0) {
                                adapter.addData(productItemEntity.getData().getDatas());
                            } else {
                                selectXlistview.setPullLoadEnable(false);
                                ToastUtil.showToast(mContext, "没有更多数据");
                            }
                        }
                        pageCount = productItemEntity.getData().getPageCount();// 添加最多页数标识
                    }
                } else {
                    mEmptyAdapter = new IsEmptyAdapter(mContext, productItemEntity.getMsg());
                    selectXlistview.setAdapter(mEmptyAdapter);
                    selectXlistview.setPullLoadEnable(false);
                    isEmptyAdapter = true;
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (selectXlistview != null) {
                    mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    selectXlistview.setAdapter(mEmptyAdapter);
                    selectXlistview.setPullLoadEnable(false);
                    isEmptyAdapter = true;
                }
            }
        });
    }

    /**
     * 头部图片
     *
     * @param cateId
     */
    private void headImage(String cateId) {
        Singlton.getInstance(FinanceHttp.class).getHeadImage(mContext, cateId, new OnResponseListener<BaseResult<DataCateExtend>>() {
            @Override
            public void onSuccess(BaseResult<DataCateExtend> cateExtend) {
                if (cateExtend != null && cateExtend.getData() != null && TextUtils.equals(ResponseStatue.STATE_SUCCESS, cateExtend.getCode())) {
                    setTitleName(cateExtend.getData().getCateName());
                    PicassoManager.getInstance().display(mContext, creditHeadIv, cateExtend.getData().getImageUrl());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    /**
     * 获取子列表在售数量
     * <p>
     * cateId  1004-车贷宝  1005-易优贷  1001-定期宝  1002-散标  1006-月增利
     * carLoanCount, easyToCount, regularCount, scatteredCount;
     */
    private List<DataFinancialCategoryBean> datas;

    private void getHeadCategory() {
        Singlton.getInstance(FinanceHttp.class).getHeadCategory(mContext, new OnResponseListener<BaseResult<DataFinancialCategory>>() {
            @Override
            public void onSuccess(BaseResult<DataFinancialCategory> categoryEntity) {

                if (categoryEntity != null && TextUtils.equals(categoryEntity.getCode(), "0") && categoryEntity.getData() != null) {
                    datas = categoryEntity.getData().getDatas();
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.clearCache();
        }
    }
}