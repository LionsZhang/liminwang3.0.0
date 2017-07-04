package com.example.administrator.lmw.mine.invite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseActivity;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.mine.cumulative.CumulativeActivity;
import com.example.administrator.lmw.mine.invite.activity.FriendsListActivity;
import com.example.administrator.lmw.mine.invite.entity.FriendMoneyEntity;
import com.example.administrator.lmw.mine.invite.entity.ShareBean;
import com.example.administrator.lmw.mine.invite.http.Friendshttp;
import com.example.administrator.lmw.utils.PicassoManager;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.ShareUtils;
import com.example.administrator.lmw.utils.Singlton;
import com.umeng.socialize.UMShareAPI;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 邀请好友
 * <p>
 * Created by Administrator on 2016/9/14 0014.
 */
public class InviteFriendsActivity extends BaseActivity {
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.bar_icon_back)
    ImageView barIconBack;
    @InjectView(R.id.back_layout)
    LinearLayout backLayout;
    @InjectView(R.id.right_title)
    TextView rightTitle;
    @InjectView(R.id.right_icon)
    ImageView rightIcon;
    @InjectView(R.id.title_bar_right_layout)
    LinearLayout titleBarRightLayout;
    @InjectView(R.id.friends_money)
    TextView friendsMoney;
    @InjectView(R.id.friends_earnings)
    LinearLayout friendsEarnings;
    @InjectView(R.id.friends_no)
    TextView friendsNo;
    @InjectView(R.id.friends_list)
    LinearLayout friendsList;
    @InjectView(R.id.friends_picture)
    ImageView friendsPicture;
    @InjectView(R.id.friends_share)
    Button friendsShare;
    private BaseResult<ShareBean> share;

    @Override
    protected void initializeData() {
        getShare();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.acitivty_invite_friends;
    }

    @Override
    protected void initializeView() {
        title();
        getFriendMoney();

    }

    /**
     * 实例化标题栏
     */
    private void title() {
        barTitle.setText("邀请好友");
        barIconBack.setImageResource(R.drawable.nav_back);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(InviteFriendsActivity.this);
            }
        });
    }

    @OnClick({R.id.friends_earnings, R.id.friends_list, R.id.friends_share})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.friends_earnings: // 累计收益
                toCumulative(1);
                break;
            case R.id.friends_list: // 好友列表
                startActivity(FriendsListActivity.class);
                break;
            case R.id.friends_share: // 分享
                if (share != null) {
                    Singlton.getInstance(ShareUtils.class).getShareUtils(InviteFriendsActivity.this, share.getData().getImageUrl(), share.getData().getShareContent(),
                            share.getData().getShareTitle(), share.getData().getTargetUrl());
                }
                break;
        }
    }

    private void toCumulative(int indexs) {
        Intent intent = new Intent(mContext, CumulativeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("indexs", indexs);
        bundle.putInt("cumindexs", 3);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 获取个人信息
     */
    private void getFriendMoney() {
        Singlton.getInstance(Friendshttp.class).getFriendMoney(mContext, new OnResponseListener<BaseResult<FriendMoneyEntity>>() {
            @Override
            public void onSuccess(BaseResult<FriendMoneyEntity> friendMoneyEntity) {
                if (friendMoneyEntity == null) return;
                if (friendMoneyEntity.getData() == null) return;
                if (friendMoneyEntity.getCode().equals("0")) {
                    find(friendMoneyEntity);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 获取分享内容
     */
    private void getShare() {
        Singlton.getInstance(Friendshttp.class).getShareContent(mContext, "1", new OnResponseListener<BaseResult<ShareBean>>() {
            @Override
            public void onSuccess(BaseResult<ShareBean> shareEntity) {
                if (shareEntity == null) return;
                if (shareEntity.getData() == null) return;
                if (shareEntity.getCode().equals("0")) {
                    share = new BaseResult<ShareBean>();
                    share = shareEntity;
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    private void find(BaseResult<FriendMoneyEntity> entity) {
        // 累计收益
        friendsMoney.setText(entity.getData().getEarningsSum() + "");
        // 邀请好友个数
        friendsNo.setText(entity.getData().getRecommendSum() + "");
        // 图片二维码
        if (!TextUtils.isEmpty(entity.getData().getQrcodeUrl())) {
            PicassoManager.getInstance().display(mContext, friendsPicture, entity.getData().getQrcodeUrl());
        }
    }
}
