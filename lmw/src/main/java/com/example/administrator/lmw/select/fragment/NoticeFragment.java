package com.example.administrator.lmw.select.fragment;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.login.LoginActivity;
import com.example.administrator.lmw.mine.cumulative.utils.IsEmptyAdapter;
import com.example.administrator.lmw.select.BannerHttp;
import com.example.administrator.lmw.select.adapter.NoticeAdapter;
import com.example.administrator.lmw.select.entity.NoticeDatasBean;
import com.example.administrator.lmw.select.entity.NoticeEntity;
import com.example.administrator.lmw.utils.DateUtil;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 通知
 * <p>
 * Created by Administrator on 2016/8/30 0030.
 */
public class NoticeFragment extends BaseFragment implements XListView.IXListViewListener {

    private NoticeAdapter adapter;
    private IsEmptyAdapter isEmptyAdapter;
    private ArrayList list;
    @InjectView(R.id.cumulative_xlv)
    XListView cumulativeXlv;
    private NoticeListener listener;
    private Handler handler;
    private List<NoticeDatasBean> bean;
    private int pageCount = 0;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int unreadMessageNum = 0;
    private boolean falg = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeListener) mContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeData() {

    }

    public void loadPage() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    noticehttp();
                    cumulativeXlv.stopRefresh();
                }
            }
        }, 100);
    }

    @Override
    protected void initializeView() {
        xlists();
        noticehttp();

        cumulativeXlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (falg)
                    if (position == 0)
                        return;
                    else {
                        adapter.Noticeclick(position - 1);
                        adapter.notifyDataSetChanged();
                        if (bean.get(position - 1).getMessageStatus() == 0) {// 0:未读;1:已读
                            getMessage(0, bean.get(position - 1).getId() + "", SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, ""));

                        }
                    }
            }
        });

    }

    /**
     * 访问网络
     */
    private void noticehttp() {
        getNotice(pageIndex, pageSize);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_cumulative_layout;
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

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cumulativeXlv != null) {
                    cumulativeXlv.setRefreshTime(DateUtil.getRefreshTime());
                    pageIndex = 1;
                    noticehttp();
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
                        noticehttp();
                    }
                    cumulativeXlv.stopLoadMore();
                }
            }
        }, 1000);

    }

    public interface NoticeListener {
        void notice(String str);
    }

    /**
     * 获取通知
     */
    private void getNotice(final int pageIndex, int pageSize) {
        Singlton.getInstance(BannerHttp.class).getNotice(mContext, pageIndex, pageSize, new OnResponseListener<NoticeEntity>() {
            @Override
            public void onSuccess(NoticeEntity noticeEntity) {
                if (cumulativeXlv == null) return;
                if (noticeEntity == null) return;
                if (noticeEntity.getData() == null) return;
                if (noticeEntity.getData().getDatas() == null) return;
                if (noticeEntity.getCode().equals("0")) {
                    if (pageIndex == 1) {
                        bean = new ArrayList<NoticeDatasBean>();
                        if (noticeEntity.getData().getDatas().size() < 10) {
                            cumulativeXlv.setPullLoadEnable(false);
                        } else {
                            cumulativeXlv.setPullLoadEnable(true);
                        }
                        if (noticeEntity.getData().getDatas().size() > 0) {
                            falg = true;
                            bean.addAll(noticeEntity.getData().getDatas());
                            adapter = new NoticeAdapter(getActivity(), noticeEntity.getData().getDatas(), R.layout.message_item);
                            cumulativeXlv.setAdapter(adapter);
                        } else {
                            falg = false;
                            isEmptyAdapter = new IsEmptyAdapter(mContext, getResources().getString(R.string.is_empty_1));
                            cumulativeXlv.setAdapter(isEmptyAdapter);
                        }
                    } else {
                        if (noticeEntity.getData().getDatas().size() > 0) {
                            bean.addAll(noticeEntity.getData().getDatas());
                            adapter.addNotice(noticeEntity.getData().getDatas());
                            adapter.notifyDataSetChanged();
                        } else {
                            showToast("没有更多数据");
                            cumulativeXlv.setPullLoadEnable(false);
                        }
                    }
                    pageCount = noticeEntity.getData().getPageCount();
                    unreadMessageNum = noticeEntity.getData().getUnreadMessageNum();
                    listener.notice(unreadMessageNum + "");
                } else if (noticeEntity.getCode().equals("150006")) {
                    showToast(noticeEntity.getMsg());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(LoginActivity.class);
                        }
                    }, 1000);
                } else {
                    if (cumulativeXlv != null) {
                        isEmptyAdapter = new IsEmptyAdapter(mContext, noticeEntity.getMsg());
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

    private void getMessage(int readAll, String statusList, String token) {
        Singlton.getInstance(BannerHttp.class).getMessage(mContext, readAll, statusList, token, new OnResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                if (baseResponse == null) return;
                int code = Integer.valueOf(baseResponse.code);
                if (code == 0) {
                    unreadMessageNum--;
                    listener.notice(unreadMessageNum + "");
                } else {
                    showToast(baseResponse.msg);
                }

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

}
