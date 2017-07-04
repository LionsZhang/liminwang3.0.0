package com.example.administrator.lmw.finance.fragment;

import android.text.TextUtils;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.finance.adapter.ProductItemAdapter;
import com.example.administrator.lmw.finance.entity.DataFinancial;
import com.example.administrator.lmw.finance.entity.FinancialDatasBean;
import com.example.administrator.lmw.finance.entity.ProductItemDataBean;
import com.example.administrator.lmw.finance.financehttp.FinanceHttp;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.inteface.OnResponseListenerEx;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 理财产品列表
 * Created by Administrator on 2016/8/23 0023.
 */
public class FinancialProduct extends BaseFragment implements XListView.IXListViewListener {

    private ProductItemAdapter financialProductAdapter;
    private IsEmptyAdapter mEmptyAdapter;
    @InjectView(R.id.xlistview_lv)
    XListView listview;

    private boolean isEmptyAdapter;//是否显示空布局
    private boolean isRefresh;


    private void lazyLoad() {
        producthttp();
    }

    @Override
    protected void initializeData() {
        financialProductAdapter = new ProductItemAdapter(mContext);
        financialProductAdapter.setType(0);
    }

    @Override
    protected void initializeView() {
        xlists();
        lazyLoad();

    }


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_financial_product_layout;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onRefresh() {
        listview.setRefreshTime(DateUtil.getRefreshTime());
        producthttp();
        isRefresh = true;
    }

    @Override
    public void onLoadMore() {

    }


    /**
     * Xlistview 实例化
     */
    private void xlists() {
        if (listview != null) {
            listview.setPullRefreshEnable(true);// 刷新
            listview.setPullLoadEnable(false);// 加载
            listview.setXListViewListener(this);
            listview.setRefreshTime(DateUtil.getRefreshTime());
            listview.setAdapter(financialProductAdapter);
        }

    }

    /**
     * 访问网络
     */
    private void producthttp() {

        Singlton.getInstance(FinanceHttp.class).getFinancialProduct(mContext, new OnResponseListenerEx<BaseResult<DataFinancial>>() {
            @Override
            public void onSuccess(BaseResult<DataFinancial> result) {
                listview.stopRefresh();
                if (listview == null) return;
                if (result != null && TextUtils.equals(result.getCode(), "0")) {
                    if (result.getData() != null && result.getData().getDatas() != null) {
                        if (isEmptyAdapter) {
                            isEmptyAdapter = false;
                            listview.setAdapter(financialProductAdapter);
                        }
                        financialProductAdapter.resetDatas(handleProductDatas(result.getData().getDatas()));
                    } else {
                        if (listview != null) {
                            mEmptyAdapter = new IsEmptyAdapter(mContext, result.getMsg());
                            listview.setAdapter(mEmptyAdapter);
                            isEmptyAdapter = true;
                        }
                    }
                } else {
                    if (listview != null) {
                        mEmptyAdapter = new IsEmptyAdapter(mContext, result.getMsg());
                        listview.setAdapter(mEmptyAdapter);
                        isEmptyAdapter = true;
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                listview.stopRefresh();
                if (listview != null) {
                    mEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    listview.setAdapter(mEmptyAdapter);
                    isEmptyAdapter = true;
                }

            }
        });
    }

    /**
     * 处理理财列表数据
     *
     * @param datasBeanList
     * @return
     */
    private List<ProductItemDataBean> handleProductDatas(List<FinancialDatasBean> datasBeanList) {
        List<ProductItemDataBean> list = new ArrayList<>();
        for (int i = 0; i < datasBeanList.size(); i++) {
            FinancialDatasBean datasBean = datasBeanList.get(i);
            List<ProductItemDataBean> datas = datasBean.getDatas();
            for (int j = 0; j < datas.size(); j++) {
                ProductItemDataBean bean = datas.get(j);
                if (j == 0) {
                    bean.setCate_id(datasBean.getCate_id());
                    bean.setCate_name(datasBean.getCate_name());
                    bean.setAppType(datasBean.getAppType());
                    bean.setIdentifier(datasBean.getIdentifier());
                    bean.setGroudDescription(datasBean.getDescription());
                    bean.setIsSearch(datasBean.getIsSearch());
                    bean.setGroupItem(true);
                }
                list.add(bean);
            }
        }


        return list;
    }

}
