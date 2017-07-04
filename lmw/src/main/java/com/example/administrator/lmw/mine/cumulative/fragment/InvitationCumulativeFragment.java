package com.example.administrator.lmw.mine.cumulative.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.cumulative.adapter.InvitationAdapter;
import com.example.administrator.lmw.mine.cumulative.entity.CumulativeDatasBean;
import com.example.administrator.lmw.mine.cumulative.entity.CumulativeEntity;
import com.example.administrator.lmw.mine.cumulative.utils.CumulativeHttp;
import com.example.administrator.lmw.mine.invite.entity.ShareBean;
import com.example.administrator.lmw.mine.invite.http.Friendshttp;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.ShareUtils;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.utils.ToastUtil;
import com.example.administrator.lmw.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 邀请奖励 fragment
 * <p>
 * Created by Administrator on 2016/8/24 0024.
 */
public class InvitationCumulativeFragment extends BaseFragment implements XListView.IXListViewListener {

    @InjectView(R.id.cumulative_tv)
    TextView cumulativeTv;
    @InjectView(R.id.cumulative_btn)
    Button cumulativeBtn;
    @InjectView(R.id.cumulative_lin)
    LinearLayout cumulativeLin;
    private Handler handler;
    @InjectView(R.id.cumulative_xlv)
    XListView cumulativeXlv;
    private int pageIndex = 1;
    private InvitationAdapter adapter;
    private BaseResult<ShareBean> share;
    private boolean isVisible = false;
    private boolean isPrepared = false;

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        xlists();
        isPrepared = true;
        lazyLoad();
        cumulativeTv.setText("啥也没有！空空如也！赶紧行动赚取收益吧！");
        cumulativeBtn.setText("速去邀请好友");
        cumulativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (share != null) {
                    Singlton.getInstance(ShareUtils.class).getShareUtils(getActivity(), share.getData().getImageUrl(), share.getData().getShareContent(),
                            share.getData().getShareTitle(), share.getData().getTargetUrl());
                }
            }
        });
    }

    private void lazyLoad() {
        getShare();
        cumulativelhttp(3, 1);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_cumulative_layout;
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    cumulativelhttp(3, 1);
                    cumulativeXlv.stopRefresh();
                }
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    pageIndex++;
                    cumulativelhttp(3, pageIndex);
                    cumulativeXlv.stopLoadMore();
                }
            }
        }, 1000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * Xlistview 实例化
     */
    private void xlists() {
        if (cumulativeXlv != null) {
            handler = new Handler();
            cumulativeXlv.setPullRefreshEnable(true);// 刷新
            cumulativeXlv.setPullLoadEnable(true);// 加载
            cumulativeXlv.setXListViewListener(this);
            cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
        }
    }

    /**
     * 访问网络
     */
    private void cumulativelhttp(int incomeType, final int pageIndex) {
        Singlton.getInstance(CumulativeHttp.class).getCumulative(mContext, incomeType, pageIndex, new OnResponseListener<BaseResult<CumulativeEntity>>() {
            @Override
            public void onSuccess(BaseResult<CumulativeEntity> cumulativeEntity) {
                if (cumulativeXlv == null) return;
                if (cumulativeEntity == null) return;
                if (cumulativeEntity.getData() == null) return;
                if (cumulativeEntity.getData().getDatas() == null) return;
                if (cumulativeEntity.getCode().equals("0")) {
                    if (pageIndex == 1) {
                        if (cumulativeEntity.getData().getDatas().size() > 0) {
                            cumulativeLin.setVisibility(View.GONE);
                        } else {
                            cumulativeLin.setVisibility(View.VISIBLE);
                        }
                        adapter = new InvitationAdapter(mContext, cumulativeEntity.getData().getDatas(), R.layout.cumulative_layout_item);
                        cumulativeXlv.setAdapter(adapter);
                    } else {
                        if (cumulativeEntity.getData().getDatas().size() > 0) {
                            adapter.addData(cumulativeEntity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast(mContext, "没有更多数据");
                        }
                    }
                } else if (cumulativeEntity.getCode().equals("150006")) {
                } else if (cumulativeEntity.getCode().equals("1000")) {
                } else {
                    ToastUtil.showToast(mContext, cumulativeEntity.getMsg());
                    List<CumulativeDatasBean> cumulativeDatas = new ArrayList<CumulativeDatasBean>();
                    adapter = new InvitationAdapter(mContext, cumulativeDatas, R.layout.cumulative_layout_item);
                    cumulativeXlv.setAdapter(adapter);
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (cumulativeXlv != null) {
                    List<CumulativeDatasBean> cumulativeDatas = new ArrayList<CumulativeDatasBean>();
                    adapter = new InvitationAdapter(mContext, cumulativeDatas, R.layout.cumulative_layout_item);
                    cumulativeXlv.setAdapter(adapter);
                }
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
                } else {
                    showToast(shareEntity.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

}
