package com.example.administrator.lmw.mine.invite.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.entity.BaseResult;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.invite.adapter.FriendsOneAdapter;
import com.example.administrator.lmw.mine.invite.entity.FriendListDatasBean;
import com.example.administrator.lmw.mine.invite.entity.FriendListEntity;
import com.example.administrator.lmw.mine.invite.entity.ShareBean;
import com.example.administrator.lmw.mine.invite.http.Friendshttp;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.ShareUtils;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class FriendsOneFragment extends BaseFragment implements XListView.IXListViewListener {

    @InjectView(R.id.cumulative_xlv)
    XListView cumulativeXlv;
    @InjectView(R.id.cumulative_tv)
    TextView cumulativeTv;
    @InjectView(R.id.cumulative_btn)
    Button cumulativeBtn;
    @InjectView(R.id.cumulative_lin)
    LinearLayout cumulativeLin;
    private Handler handler;
    private View view;
    private TextView title;
    private FriendsOneAdapter adapter;
    private int level = 1;// 1:一级好友;2:二级好友
    private int pageIndex = 1;
    private int pageSize = 10;
    private int pageCount = 0;
    private BaseResult<ShareBean> share;

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        xlist();
        titleview();
        getShare();
        httpfriendlist(level, pageIndex, pageSize);
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
                    httpfriendlist(level, pageIndex, pageSize);
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
                    if (pageIndex > pageCount) {
                        showToast("没有更多数据");
                    } else {
                        httpfriendlist(level, pageIndex, pageSize);
                    }
                    cumulativeXlv.stopLoadMore();
                }
            }
        }, 1000);

    }

    private void xlist() {
        if (cumulativeXlv != null) {
            handler = new Handler();
            cumulativeXlv.setPullRefreshEnable(true);// 刷新
            cumulativeXlv.setPullLoadEnable(true);// 加载
            cumulativeXlv.setXListViewListener(this);
            cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
        }
    }

    private void titleview() {
        view = LayoutInflater.from(mContext).inflate(R.layout.friends_title, null);
        title = (TextView) view.findViewById(R.id.friends_title_item);
        title.setText("一级好友名称：共0人");
        cumulativeTv.setText("啥也没有，赶紧行动赚取收益吧");
        cumulativeBtn.setText("速去邀请好友");
        cumulativeXlv.addHeaderView(view);

    }


    private void httpfriendlist(int level, final int pageIndex, int pageSize) {
        Singlton.getInstance(Friendshttp.class).getFriend(mContext, level, pageIndex, pageSize, new OnResponseListener<BaseResult<FriendListEntity>>() {
            @Override
            public void onSuccess(BaseResult<FriendListEntity> friendListEntity) {
                if (cumulativeXlv == null) return;
                if (friendListEntity == null) return;
                if (friendListEntity.getData() == null) return;
                if (friendListEntity.getData().getDatas() == null) return;
                if (friendListEntity.getCode().equals("0")) {
                    if (pageIndex == 1) {
                        if (friendListEntity.getData().getDatas().size() <= 0) {
                            cumulativeXlv.setPullLoadEnable(false);
                            cumulativeLin.setVisibility(View.VISIBLE);
                        } else if (friendListEntity.getData().getDatas().size() < 10) {
                            cumulativeXlv.setPullLoadEnable(false);
                            cumulativeLin.setVisibility(View.GONE);
                        } else {
                            cumulativeXlv.setPullLoadEnable(true);
                            cumulativeLin.setVisibility(View.GONE);
                        }
                        adapter = new FriendsOneAdapter(mContext, friendListEntity.getData().getDatas(), R.layout.friends_item);
                        cumulativeXlv.setAdapter(adapter);
                    } else {
                        if (friendListEntity.getData().getDatas().size() > 0) {
                            adapter.addData(friendListEntity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            cumulativeXlv.setPullLoadEnable(false);
                            showToast("没有更多数据");
                        }
                    }
                    title.setText("一级好友名称：共" + friendListEntity.getData().getTotalCount() + "人");
                    pageCount = friendListEntity.getData().getPageCount();
                } else {
                    List<FriendListDatasBean> friendListDatas = new ArrayList<FriendListDatasBean>();
                    adapter = new FriendsOneAdapter(mContext, friendListDatas, R.layout.friends_item);
                    cumulativeXlv.setAdapter(adapter);
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (cumulativeXlv != null) {
                    List<FriendListDatasBean> friendListDatas = new ArrayList<FriendListDatasBean>();
                    adapter = new FriendsOneAdapter(mContext, friendListDatas, R.layout.friends_item);
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
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

}
