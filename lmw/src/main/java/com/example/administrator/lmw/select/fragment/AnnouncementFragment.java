package com.example.administrator.lmw.select.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.mine.more.activity.WebViewMore;
import com.example.administrator.lmw.select.BannerHttp;
import com.example.administrator.lmw.select.adapter.AnnouncementAdapter;
import com.example.administrator.lmw.select.entity.AnnouncementDatasBean;
import com.example.administrator.lmw.select.entity.AnnouncementEntity;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公告
 * <p/>
 * Created by Administrator on 2016/8/30 0030.
 */
public class AnnouncementFragment extends BaseFragment implements XListView.IXListViewListener {

    private AnnouncementAdapter adapter;
    private IsEmptyAdapter isEmptyAdapter;
    private AnnouncementListener listener;
    private List<AnnouncementDatasBean> announcementDatasBeen;
    @InjectView(R.id.cumulative_xlv)
    XListView cumulativeXlv;
    private Handler handler;
    private int pageSize = 10;
    private int pageIndex = 1;
    private int pageCount = 0;
    private boolean falg = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AnnouncementListener) mContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeData() {

    }

    @Override
    protected void initializeView() {
        xlists();
        getAnnouncement(pageIndex, pageSize);
        /**
         *
         */
        cumulativeXlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (announcementDatasBeen != null && falg) {
                    if (position == 0)
                        return;
                    else {
                        Intent intent = new Intent(mContext, WebViewMore.class);
                        intent.putExtra("url", announcementDatasBeen.get(position - 1).getLinkUrl());
                        intent.putExtra("title", announcementDatasBeen.get(position - 1).getPostTitle());
                        startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_cumulative_layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    pageIndex = 1;
                    getAnnouncement(pageIndex, pageSize);
                    cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
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
                        cumulativeXlv.setPullLoadEnable(false);
                    } else {
                        getAnnouncement(pageIndex, pageSize);
                    }
                    cumulativeXlv.stopLoadMore();
                }
            }
        }, 1000);

    }

    public interface AnnouncementListener {
        void announcement(String str);

    }

    /**
     * Xlistview 实例化
     */
    private void xlists() {
        handler = new Handler();
        cumulativeXlv.setPullRefreshEnable(true);// 刷新
        cumulativeXlv.setPullLoadEnable(true);// 加载
        cumulativeXlv.setXListViewListener(this);
        cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());

    }

    /**
     * 获取首页公告
     */
    private void getAnnouncement(final int pageIndex, int pageSize) {
        Singlton.getInstance(BannerHttp.class).getAnnouncement(mContext, pageIndex, pageSize, new OnResponseListener<AnnouncementEntity>() {
            @Override
            public void onSuccess(AnnouncementEntity announcementEntity) {
                if (cumulativeXlv == null) return;
                if (announcementEntity == null) return;
                if (announcementEntity.getData() == null) return;
                if (announcementEntity.getData().getDatas() == null) return;
                if (announcementEntity.getCode().equals("0")) {
                    if (pageIndex == 1) {
                        announcementDatasBeen = new ArrayList<AnnouncementDatasBean>();
                        if (announcementEntity.getData().getDatas().size() < 10) {
                            cumulativeXlv.setPullLoadEnable(false);
                        } else {
                            cumulativeXlv.setPullLoadEnable(true);
                        }
                        if (announcementEntity.getData().getDatas().size() > 0) {
                            falg = true;
                            announcementDatasBeen.addAll(announcementEntity.getData().getDatas());
                            adapter = new AnnouncementAdapter(getActivity(), announcementEntity.getData().getDatas(), R.layout.message_item);
                            cumulativeXlv.setAdapter(adapter);
                        } else {
                            falg = false;
                            isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));
                            cumulativeXlv.setAdapter(isEmptyAdapter);
                        }
                    } else {
                        if (announcementEntity.getData().getDatas().size() > 0) {
                            announcementDatasBeen.addAll(announcementEntity.getData().getDatas());
                            adapter.addAnnouncement(announcementEntity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            showToast("没有更多数据");
                            cumulativeXlv.setPullLoadEnable(false);
                        }
                    }
                    pageCount = announcementEntity.getData().getPageCount();
                } else {
                    if (cumulativeXlv != null) {
                        isEmptyAdapter = new IsEmptyAdapter(mContext, announcementEntity.getMsg());
                        cumulativeXlv.setAdapter(isEmptyAdapter);
                        cumulativeXlv.setPullLoadEnable(false);
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                if (cumulativeXlv != null) {
                    isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_2));
                    cumulativeXlv.setAdapter(isEmptyAdapter);
                    cumulativeXlv.setPullLoadEnable(false);
                }
            }
        });

    }
}
