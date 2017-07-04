package com.example.administrator.lmw.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.finance.entity.DataFinancialCategoryBean;
import com.example.administrator.lmw.inteface.ViewOnClick;
import com.example.administrator.lmw.utils.DensityUtil;
import com.example.administrator.lmw.utils.PicassoManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FinanceProductSortDialog extends Dialog {
    private Context mContext;
    private ViewOnClick listener;
    private List<DataFinancialCategoryBean> datas;

    /**
     * 标的-图标键值对
     */
    private Map<String, Drawable> mStringDrawableMap = new HashMap<>();
    private View mDialog;

    private void initStringDrawableMap() {
        mStringDrawableMap.put("1004", mContext.getResources().getDrawable(R.drawable.ic_prd_carb));
        mStringDrawableMap.put("1005", mContext.getResources().getDrawable(R.drawable.ic_prd_ezd));
        mStringDrawableMap.put("1001", mContext.getResources().getDrawable(R.drawable.ic_prd_dingqb));
        mStringDrawableMap.put("1002", mContext.getResources().getDrawable(R.drawable.ic_prd_other));
        mStringDrawableMap.put("1006", mContext.getResources().getDrawable(R.drawable.yzl));
    }


    public FinanceProductSortDialog(Context context, List<DataFinancialCategoryBean> datas, final ViewOnClick listener) {
        this(context, R.style.couponDialogStyle);
        this.mContext = context;
        this.listener = listener;
        this.datas = datas;
        initStringDrawableMap();
        mDialog = LayoutInflater.from(context).inflate(R.layout.layout_product_sort_list_pop, null);

        initView(mDialog);

        this.setContentView(mDialog);
        this.show();
        Window wind = getWindow();
        WindowManager.LayoutParams l = wind.getAttributes();
        wind.setGravity(Gravity.TOP | Gravity.RIGHT);
        l.y = DensityUtil.dip2px(mContext, 43);
        wind.setAttributes(l);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void initView(View dialog) {
        LinearLayout ll_root = (LinearLayout) dialog.findViewById(R.id.ll_root);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(mContext, 43));
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        for (int i = 0; i < datas.size(); i++) {
            View view = View.inflate(mContext, R.layout.item_product_sort_list, null);
            view.findViewById(R.id.line_bottom).setVisibility(i != datas.size() - 1 ? View.VISIBLE : View.GONE);
            if (datas.get(i) != null) {
                ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);
                TextView title = (TextView) view.findViewById(R.id.tv_title);
                TextView num = (TextView) view.findViewById(R.id.tv_num);

//                icon.setBackground(getDrawable(mStringDrawableMap, datas.get(i)));
                if (!TextUtils.isEmpty(datas.get(i).getImgUrl()))
                    PicassoManager.getInstance().display(mContext, icon, datas.get(i).getImgUrl());
                title.setText(datas.get(i).getCateName());
                num.setText(datas.get(i).getCount() + "");
                num.setBackgroundResource(datas.get(i).getCount() == 0 ? R.drawable.sort__product_popuwindow_shape_gray :
                        R.drawable.sort__product_popuwindow_shape);

                view.setTag(datas.get(i));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataFinancialCategoryBean bean = (DataFinancialCategoryBean) v.getTag();
                        if (listener != null) {
                            listener.onClick(Integer.valueOf(bean.getCateId()), v, bean.getIsSearch());
                            dismiss();
                        }
                    }
                });
            }
            ll_root.addView(view, params);
        }


    }

    public FinanceProductSortDialog(Context context, int theme) {
        super(context, theme);
    }


    private Drawable getDrawable(Map<String, Drawable> stringDrawableMap, DataFinancialCategoryBean bean) {

        if (stringDrawableMap == null)
            return null;
        if (bean != null) {
            if (stringDrawableMap.containsKey(bean.getCateId()))
                return stringDrawableMap.get(bean.getCateId());
            else
                return stringDrawableMap.get("1002");//没有找到对应的id默认散标类型
        }
        return stringDrawableMap.get("1002");//没有找到对应的id默认散标类型
    }

}
