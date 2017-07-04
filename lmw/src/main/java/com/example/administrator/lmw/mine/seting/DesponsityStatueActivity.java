package com.example.administrator.lmw.mine.seting;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.DespositAccountManager;
import com.example.administrator.lmw.config.DespositLogic;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.DespositStatus;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.utils.ActivityManage;
import com.example.administrator.lmw.utils.ActivityTools;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 开通存管状态页面
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/5/24 10:57
 **/
public class DesponsityStatueActivity extends BaseTitleActivity {
    @InjectView(R.id.tv_fail)
    TextView tv_fail;
    @InjectView(R.id.tv_hint)
    TextView tv_hint;
    @InjectView(R.id.tv_hint_msg)
    TextView tv_hint_msg;
    @InjectView(R.id.tv_guest)
    TextView tv_guest;
    @InjectView(R.id.tv_return)
    TextView tv_return;
    @InjectView(R.id.tv_fail_reason)
    TextView tv_fail_reason;
    @InjectView(R.id.tv_cantact_guest)
    TextView tv_cantact_guest;
    @InjectView(R.id.tv_know)
    TextView tv_know;
    @InjectView(R.id.tv_reopen)
    TextView tv_reopen;
    @InjectView(R.id.iv_face)
    ImageView iv_face;

    private String mOpenDpStatus = PreferenceCongfig.OPEN_DESPONSITY_AUDIT;//开通存管状态


    @Override
    protected void initializeData() {
        Intent intent = getIntent();
        if (intent.hasExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_STATE))
            mOpenDpStatus = intent.getStringExtra(PreferenceCongfig.INTENT_KEY_OPEN_DESPONSITY_STATE);
        showLoadingDialog();
        getDespositStatus();
    }


    @Override
    protected void setTitleContentView() {
        initView(R.string.open_desposit, R.layout.activity_desponsity_statu_layout);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        initView();
    }

    private void initView() {
        if (TextUtils.equals(mOpenDpStatus, PreferenceCongfig.OPEN_DESPONSITY_AUDIT)) {//审核中
            tv_fail.setText(R.string.txt_desposit_account_checking);
            iv_face.setImageResource(R.drawable.ic_desposit_shz);
            tv_return.setVisibility(View.GONE);
            tv_fail_reason.setVisibility(View.GONE);
            tv_reopen.setVisibility(View.GONE);
            tv_know.setVisibility(View.VISIBLE);
            tv_cantact_guest.setBackgroundResource(R.drawable.bg_blue);
            tv_cantact_guest.setTextColor(ActivityCompat.getColor(mContext, R.color.white));
            tv_fail.setTextColor(ActivityCompat.getColor(mContext, R.color.bg_bule));
        } else if (TextUtils.equals(mOpenDpStatus, PreferenceCongfig.OPEN_DESPONSITY_RETURN)) {//被退回
            tv_fail.setText(R.string.txt_desposit_account_sendback);
            iv_face.setImageResource(R.drawable.ic_desposit_th);
            tv_return.setVisibility(View.VISIBLE);
            tv_fail_reason.setVisibility(View.VISIBLE);
            tv_reopen.setVisibility(View.GONE);
            tv_know.setVisibility(View.VISIBLE);
//            tv_cantact_guest.setBackgroundResource(R.drawable.bg_transpant_blue_frame);
//            tv_cantact_guest.setTextColor(ActivityCompat.getColor(mContext, R.color.bg_bule));
            tv_cantact_guest.setBackgroundResource(R.drawable.bg_blue);
            tv_cantact_guest.setTextColor(ActivityCompat.getColor(mContext, R.color.white));
            tv_fail.setTextColor(ActivityCompat.getColor(mContext, R.color.yellow_text));
        } else if (TextUtils.equals(mOpenDpStatus, PreferenceCongfig.OPEN_DESPONSITY_REJECT)) {//被驳回
            tv_fail.setText(R.string.txt_desposit_account_reject);
            iv_face.setImageResource(R.drawable.ic_desposit_bh);
            tv_return.setVisibility(View.VISIBLE);
            tv_fail_reason.setVisibility(View.VISIBLE);
            tv_reopen.setVisibility(View.GONE);
            tv_know.setVisibility(View.VISIBLE);
            tv_cantact_guest.setBackgroundResource(R.drawable.bg_blue);
            tv_cantact_guest.setTextColor(ActivityCompat.getColor(mContext, R.color.white));
            tv_fail.setTextColor(ActivityCompat.getColor(mContext, R.color.txt_ff6666));
        }
    }


    @OnClick({R.id.tv_cantact_guest, R.id.tv_reopen, R.id.tv_guest, R.id.tv_know})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cantact_guest://联系客服
                ActivityTools.startUdeskGuest(mContext);
                break;
            case R.id.tv_reopen://重新开通
                Singlton.getInstance(DespositAccountManager.class).openBankDepository(mContext, PreferenceCongfig.OPEN_DESPONSITY_NOT);
                break;
            case R.id.tv_guest://打电话
                Singlton.getInstance(PopWindowManager.class).showCall(mContext);
                break;
            case R.id.tv_know://我知道了
                ActivityManage.create().finishActivity();
                break;
        }
    }


    /**
     * 获取开通存管状态
     */
    private void getDespositStatus() {
        Singlton.getInstance(DespositLogic.class).queryDespositStatus(mContext, new OnResponseListener<DespositStatus>() {
            @Override
            public void onSuccess(DespositStatus result) {
                hideLoadingDialog();
                if (result != null) {
                    mOpenDpStatus = result.getOpenDpStatus();
                    initView();
                    tv_fail_reason.setText(result.getReason());
                    tv_hint_msg.setText(result.getPrompt());
                }
            }

            @Override
            public void onFail(Throwable e) {
                hideLoadingDialog();
            }
        });
    }

}
