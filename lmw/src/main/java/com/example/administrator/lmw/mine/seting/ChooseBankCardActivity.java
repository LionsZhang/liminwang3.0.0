package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.ChoseBankEvent;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.mine.seting.entity.BankCardBean;
import com.example.administrator.lmw.mine.seting.entity.BankCardEntity;
import com.example.administrator.lmw.mine.seting.entity.BankCardInfo;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.StringUtils;
import com.example.administrator.lmw.view.XListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lion on 2016/9/5.
 */
public class ChooseBankCardActivity extends BaseActivity {//implements XListView.IXListViewListener {
    @InjectView(R.id.x_listView)
    XListView listView_;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.title_layout)
    RelativeLayout title_layout;
    private CommonAdapter<BankCardInfo> adapter_;
    private List<BankCardInfo> data_ = new ArrayList<>();
    private BankCardBean bankCardBean;
    private BankCardInfo bankCardInfo;
    private Intent intent;
    private int pageType;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_choose_bank_card_layout;
    }


    @Override
    protected void initializeData() {
        initPram();
        adapter_ = new CommonAdapter<BankCardInfo>(mContext, data_, R.layout.activity_choose_bank_card_item) {

            @Override
            public void convert(CommonViewHolder holder, BankCardInfo item, int position) {
                if (item == null) return;
   /*             switch (item.getBankId()) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4://中国工商银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_gs);
                        showFlag(holder);
                        break;
                    case 5://中国农业银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_ny);
                        showFlag(holder);
                        break;
                    case 6://中国银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_zg);
                        showFlag(holder);
                        break;
                    case 7://中国建设银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_js);
                        showFlag(holder);
                        break;
                    case 8://中国邮政储蓄银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_yz);//
                        hideFlag(holder);
                        break;
                    case 9://平安银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_pa);
                        hideFlag(holder);
                        break;
                    case 10://中国民生银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_ms);
                        hideFlag(holder);
                        break;
                    case 11://中国光大银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_gd);
                        hideFlag(holder);
                        break;
                    case 12://广发银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_gf);
                        hideFlag(holder);
                        break;
                    case 13://中信银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_zx);
                        hideFlag(holder);
                        break;
                    case 14://兴业银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_xy);
                        hideFlag(holder);
                        break;
                    case 15://华夏银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_hx);
                        hideFlag(holder);
                        break;
                    case 16://交通银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_jt);
                        hideFlag(holder);
                        break;
                    case 17://招商银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_zs);
                        hideFlag(holder);
                        break;
                    case 18://上海浦东发展银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_pf);
                        hideFlag(holder);
                        break;
                    case 24://上海银行
                        holder.setImageResource(R.id.bank_iv, R.drawable.ic_bank_shyh);
                        hideFlag(holder);
                        break;
                }*/
                if (!TextUtils.isEmpty(item.getBankImg()))
                    PicassoManager.getInstance().display(mContext,(ImageView) holder.getView(R.id.bank_iv), item.getBankImg());
                if (!TextUtils.isEmpty(item.getBankName()))
                    holder.setText(R.id.bank_tv, item.getBankName());
                if (item.getDailyQuotaAmount() == -1)
                    holder.setText(R.id.limit_num_tv, String.format(getString(R.string.bind_card_limit_credit_not_limit),
                            StringUtils.mathLeftMove4(item.getEachQuotaAmout() + "")));
                else
                    holder.setText(R.id.limit_num_tv, String.format(getString(R.string.bind_card_limit_credit),
                            StringUtils.mathLeftMove4(item.getEachQuotaAmout() + ""),
                            StringUtils.mathLeftMove4(item.getDailyQuotaAmount() + "")));

            }
        };
    }

    private void initPram() {
        intent = getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null)
            pageType = bundle.getInt(PreferenceCongfig.ChoseBankCard, 0);
    }

    private void showFlag(CommonViewHolder holder) {

        holder.getView(R.id.speed_iv).setVisibility(View.VISIBLE);
        holder.getView(R.id.stable_iv).setVisibility(View.VISIBLE);

    }

    private void hideFlag(CommonViewHolder holder) {

        holder.getView(R.id.speed_iv).setVisibility(View.GONE);
        holder.getView(R.id.stable_iv).setVisibility(View.GONE);

    }

    private void getBankList() {
        Singlton.getInstance(SetLogic.class).getBankList(mContext, new OnResponseListener<BankCardEntity>() {
            @Override
            public void onSuccess(BankCardEntity response) {
                hideLoadingDialog();
                if (response == null)
                    return;
                int code = Integer.valueOf(response.code);
                if (code == 0) {
                    if (response.getData() != null) {
                        bankCardBean = response.getData();
                        if (data_ != null) {
                            data_.clear();
                            data_.addAll(bankCardBean.getBankList());
                            adapter_.setData(data_);
                            adapter_.notifyDataSetChanged();
                        }
                        //onLoad();
                    }
                } else if (code == 150006) {
                    showToast(response.msg);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else {
                    //onLoad();
                    showToast(response.msg);
                }

            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });
    }


    @Override
    protected void initializeView() {
        showLoadingDialog();
        getBankList();
        initBarTitle();
        listView_.setAdapter(adapter_);
        listView_.setPullRefreshEnable(false);
        listView_.setPullLoadEnable(false);
        // listView_.setXListViewListener(this);
        listView_.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (pageType == PreferenceCongfig.ChoseBankCard_chose_card) {
                    if (data_ != null && data_.size() > 0)
                        bankCardInfo = data_.get(position - 1);
                    ChoseBankEvent choseBankEvent = new ChoseBankEvent();
                    choseBankEvent.setBankCardInfo(bankCardInfo);
                    EventBus.getDefault().post(choseBankEvent);
                    finishActivity(ChooseBankCardActivity.this);
                }
            }
        });
    }

    private void initBarTitle() {
        barIconBack.setImageResource(R.drawable.nav_back);
        title_layout.setBackgroundColor(getResources().getColor(R.color.text_blue));
        if (pageType == PreferenceCongfig.ChoseBankCard_chose_card) {
            barTitle.setText("选择银行卡");

        } else if (pageType == PreferenceCongfig.ChoseBankCard_limit_detail){
            barTitle.setText("限额说明");
        } else if (pageType == PreferenceCongfig.ChoseBankCard_support_card)
            barTitle.setText("支持银行卡");
    }


    @OnClick({R.id.back_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finishActivity(this);
                break;
        }

    }
/*
    @Override
    public void onRefresh() {

        getBankList();
    }

    @Override
    public void onLoadMore() {
        getBankList();

    }

    private void onLoad() {
        listView_.stopRefresh();
        listView_.stopLoadMore();
        listView_.setRefreshTime(DateUtil.getSimpleDateFormat());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            listView_.autoRefresh();
        }
    }*/

}








