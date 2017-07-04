package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;

import butterknife.OnClick;

/**
 * 银行卡页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/15 15:53
 **/
public class BankCardActivity extends BaseTitleActivity {


    @Override
    protected void setTitleContentView() {
        initView(R.string.txt_bankcard, R.layout.activity_bank_card);

        setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ChooseBankCardActivity.class);
                intent.putExtra(PreferenceCongfig.ChoseBankCard, PreferenceCongfig.ChoseBankCard_chose_card);
                startActivity(intent);
            }
        }, R.string.txt_support_bank);
    }

    @Override
    protected void initializeData() {
        super.initializeData();
        Intent intent = getIntent();
        boolean isAddCard = intent.getBooleanExtra(PreferenceCongfig.KEY_BANK_CARD_ADD, false);
        showToast("银行卡" + "---------" + isAddCard);
    }

    /**
     * 添加银行卡
     *
     * @param v
     */
    @OnClick(R.id.fl_add_bankcard)
    public void addBankCard(View v) {
        showToast("添加银行卡");
    }


}
