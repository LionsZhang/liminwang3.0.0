package com.example.administrator.lmw.mine.card;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.entity.ChoseCouponEvent;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.card.adapter.CashCouponAdapter;
import com.example.administrator.lmw.mine.card.cardhttp.CardHttp;
import com.example.administrator.lmw.mine.card.entity.CashCouponBeanInfo;
import com.example.administrator.lmw.mine.card.entity.DataCashCoupon;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.XListView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.InjectView;

/**
 * 选择现金券
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/3/15 19:22
 **/
public class CashCouponActivity extends BaseTitleActivity {

    @InjectView(R.id.x_listView)
    XListView x_listView;
    private CashCouponAdapter adapter_;
    public static final String BUS_ID = "busId";//业务id，标的Id或者债权Id
    public static final String BUS_TYPE = "busType";//业务类型，0-标的、1-债权
    public static final String INVEST_AMOUNT = "investAmount";//投资金额
    public static final String SELECTED_COUPON_ID = "selected_coupon_id";//已选择的现金券Id
    private String selectedCouponId;//已选择的现金券Id
    private Intent intent;
    private String busId;
    private String busType;
    private String investAmount;
    private int mCanUseCouponNum;
    private ChoseCouponEvent choseCouponEvent;

    @Override
    protected void initializeData() {
        initPara();
        adapter_ = new CashCouponAdapter(mContext);
    }

    private void initPara() {
        intent = getIntent();
        if (intent.hasExtra(BUS_ID))
            busId = intent.getStringExtra(BUS_ID);
        if (intent.hasExtra(BUS_TYPE))
            busType = intent.getStringExtra(BUS_TYPE);
        if (intent.hasExtra(INVEST_AMOUNT)) {
            investAmount = intent.getStringExtra(INVEST_AMOUNT);//注册
            if (TextUtils.isEmpty(investAmount))
                investAmount = "0";
        }


        if (intent.hasExtra(SELECTED_COUPON_ID))
            selectedCouponId = intent.getStringExtra(SELECTED_COUPON_ID);//卡券id
    }

    @Override
    protected void initializeView() {
        initXlistView();
        getCashCouponList();
    }

    @Override
    protected void setTitleContentView() {
        initView(R.string.txt_card_select, R.layout.activity_cash_coupon);

        //返回事件
        setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choseCouponEvent != null) {
                    EventBus.getDefault().post(choseCouponEvent);
                }
                ActivityManage.create().finishActivity();
            }
        });

    }

    private void initXlistView() {
        x_listView.setAdapter(adapter_);
        x_listView.setPullRefreshEnable(false);
        x_listView.setPullLoadEnable(false);
    }


    private void getCashCouponList() {
        String nonceStr = "request_cash_coupon_" + System.currentTimeMillis();//现金券接口请求Id唯一标示
        Singlton.getInstance(CardHttp.class).getcashCouponList(mContext, busId, busType,
                investAmount, nonceStr, new OnResponseListener<BaseResult<DataCashCoupon>>() {
                    @Override
                    public void onSuccess(BaseResult<DataCashCoupon> response) {
                        hideLoadingDialog();
                        if (response == null) return;
                        int code = NumberParserUtil.parserInt(response.getCode(), -1);
                        if (code == 0) {
                            if (response.getData() != null) {
                                DataCashCoupon cashCouponBean = response.getData();
                                mCanUseCouponNum = cashCouponBean.getNum();
                                adapter_.setCashCouponNum(mCanUseCouponNum);
                                if (cashCouponBean.getDatas() != null) {
                                    adapter_.resetDatas(handleListDatas(cashCouponBean.getDatas()));
                                    //onLoad();*/
                                }
                            }
                        } else {
                            //onLoad();
                            showToast(response.getMsg());
                        }

                    }

                    @Override
                    public void onFail(Throwable e) {
                        hideLoadingDialog();
                    }
                });
    }

    /**
     * @param datas
     */
    private List<CashCouponBeanInfo> handleListDatas(List<CashCouponBeanInfo> datas) {
        for (int i = 0; i < datas.size(); i++) {
            CashCouponBeanInfo bean = datas.get(i);
            if (TextUtils.equals(selectedCouponId, bean.getCouponId())) {
                choseCouponEvent = new ChoseCouponEvent();
                choseCouponEvent.setCashCouponBeanInfo(bean);

                bean.setSelect(true);
                datas.remove(i);
                datas.add(0, bean);
                if (bean.getGroupItemIndex() == 0 && i != datas.size() - 1) {
                    CashCouponBeanInfo nextBean = datas.get(i + 1);
                    nextBean.setGroupItemIndex(0);
                    datas.remove(i + 1);
                    datas.add(i + 1, nextBean);
                }
                break;
            }
        }

        return datas;
    }

    /**
     * 返回监控
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (choseCouponEvent != null) {
            EventBus.getDefault().post(choseCouponEvent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
