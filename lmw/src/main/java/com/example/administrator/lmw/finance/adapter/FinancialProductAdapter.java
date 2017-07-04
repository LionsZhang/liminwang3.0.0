package com.example.administrator.lmw.finance.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.finance.activity.ProductItem;
import com.example.administrator.lmw.finance.entity.FinancialDatasBean;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.view.InnerListView;

import java.util.List;


/**
 * 理财页面适配器
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/8 17:45
 **/
public class FinancialProductAdapter extends CommonBaseAdapter<FinancialDatasBean, FinancialProductAdapter.ViewHolder> {


    public FinancialProductAdapter(Context context) {
        super(context);
    }

    public FinancialProductAdapter(Context context, List<FinancialDatasBean> datas) {
        super(context, datas);
    }


    @Override
    public ViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(mContext, parent, R.layout.fragment_financial_credit_item_test, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    @Override
    public void convert(ViewHolder holder, final FinancialDatasBean item, int position) {
        if (item != null) {
            holder.typesTv.setText(item.getCate_name());
            holder.typeParametersTv.setText("更多");
            ProductItemAdapter adapter = (ProductItemAdapter) getTagCashe(item.getCate_id());
            if (adapter == null) {
                adapter = new ProductItemAdapter(mContext, item.getDatas());
                adapter.setType(0);
                ALLog.e("--1--" + item.getCate_id() + "----->" + (adapter != null ? adapter.toString() : "空") + "   " + getCacheSize());
                putTagCashe(item.getCate_id(), adapter);
            }
            ALLog.e("--2--" + item.getCate_id() + "----->" + (adapter != null ? adapter.toString() : "空") + "   " + getCacheSize());

            holder.allItem.setAdapter(adapter);
//            setListViewHeightBasedOnChildren(holder.allItem);

            holder.finanRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(String.valueOf(item.getAppType()))) {// 1:定期宝2:散标
                        Intent intent = new Intent(mContext, ProductItem.class);
                        intent.putExtra(PreferenceCongfig.INTENT_KEY_TITLE, item.getCate_name());
                        intent.putExtra("type", item.getCate_id());
                        intent.putExtra("appType", item.getAppType());
                        mContext.startActivity(intent);
                    }
                }
            });

        }

    }

//    /**
//     * @params listview
//     * 此方法是本次listview嵌套listview的核心方法：计算parentlistview item的高度。
//     * 如果不使用此方法，无论innerlistview有多少个item，则只会显示一个item。
//     **/
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        // 获取ListView对应的Adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
//            // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            // 计算子项View 的宽高
//            listItem.measure(0, 0);
//            // 统计所有子项的总高度
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }


    public static class ViewHolder extends CommonViewHolder {
        private TextView typesTv, typeParametersTv;
        private InnerListView allItem;
        private RelativeLayout finanRl;

        public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            super(context, parent, layoutId, position);

            allItem = getView(R.id.all_item);
            finanRl = getView(R.id.rl_financial_title);
            typesTv = getView(R.id.types_tv);
            typeParametersTv = getView(R.id.type_Parameters_tv);

        }
    }


}
