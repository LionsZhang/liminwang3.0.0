package com.example.administrator.lmw.mine.card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BasePagerAdapter;
import com.example.administrator.lmw.base.BaseTitleActivity;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.entity.ShowFinanceFragmentEvent;
import com.example.administrator.lmw.finance.utils.TextViewUtils;
import com.example.administrator.lmw.inteface.OnResponseListener;
import com.example.administrator.lmw.main.MainActivity;
import com.example.administrator.lmw.mine.card.cardhttp.CardHttp;
import com.example.administrator.lmw.mine.card.cardhttp.CouponType;
import com.example.administrator.lmw.mine.card.cardhttp.QueryStatus;
import com.example.administrator.lmw.mine.card.entity.CouponCountBean;
import com.example.administrator.lmw.mine.card.entity.DataCouponCount;
import com.example.administrator.lmw.mine.card.fragment.CashCouponFragment;
import com.example.administrator.lmw.mine.card.fragment.RedCardFragment;
import com.example.administrator.lmw.utils.SharedPreference;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.PagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 卡券红包
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/16 17:12
 **/
public class CardActivity extends BaseTitleActivity implements RedCardFragment.OnCountListener {

    private ArrayList<Fragment> fragmentList;

    @InjectView(R.id.tab_top)
    PagerSlidingTabStrip mTabStrip;
    @InjectView(R.id.viewpager_vp)
    ViewPager viewpagerVp;
    private String mToken;
    private BasePagerAdapter mAdapter;
    private int mRedCount;//红包数量
    private List<String> mTitles;
    private int mRedIndex = -1;//红包列表的索引，默认-1不存在
    private CouponCountBean mRedCountBean = null;//红包的数量实体


    @Override
    protected void initializeData() {
        super.initializeData();
        mToken = SharedPreference.getInstance().getString(mContext, PreferenceCongfig.APP_TOKEN, "");
        fragmentList = new ArrayList<Fragment>(5);
        mTitles = new ArrayList<>();
    }

    @Override
    protected void initializeView() {
        getCouponCount();
        initListener();
    }


    @Override
    protected void setTitleContentView() {
        initView(R.string.txt_cash_coupon, R.layout.activity_card);
    }


    private void initListener() {
        setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
                EventBus.getDefault().post(new ShowFinanceFragmentEvent());
            }
        }, R.string.txt_invest);

        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitleName(TextViewUtils.getIgnoreNumber(mTitles.get(position)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 获取卡券项的数量
     */
    private void getCouponCount() {
        Singlton.getInstance(CardHttp.class).getCouponCount(mContext, mToken, new OnResponseListener<DataCouponCount>() {
            @Override
            public void onSuccess(DataCouponCount result) {
                if (result != null && result.getDatas() != null)
                    initTabViewPager(result.getDatas());
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 初始化tab和viewpager
     *
     * @param datas
     */
    private void initTabViewPager(List<CouponCountBean> datas) {
        for (int i = 0; i < datas.size(); i++) {
            CouponCountBean data = datas.get(i);
            if (data != null) {
                mTitles.add(String.format("%s(%s)", data.getCouponTypeName(), data.getUsableCount()));
                if (TextUtils.equals("1", data.getCouponType()) && data.getUsableCount() >= 0) {//红包
                    mRedCount = data.getUsableCount();
                    RedCardFragment redCardFragment = new RedCardFragment();
                    fragmentList.add(redCardFragment);
                    mRedIndex = i;
                    mRedCountBean = data;
                } else {
                    fragmentList.add(getFragmentInstance(data));
                }
            }
        }

        //给ViewPager设置适配器
        mAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList);
        viewpagerVp.setAdapter(mAdapter);
        viewpagerVp.setCurrentItem(0);//设置当前显示标签页为第一页
        mTabStrip.setViewPager(viewpagerVp);
        mTabStrip.setTitles(mTitles);
    }

    /**
     * 获取fragment
     *
     * @param data
     * @return
     */
    private Fragment getFragmentInstance(CouponCountBean data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CashCouponFragment.CARD_COUPON_STATUS, QueryStatus.UNUSED);

        if (TextUtils.equals("0", data.getCouponType()) && data.getUsableCount() >= 0) {//现金券
            bundle.putSerializable(CashCouponFragment.CARD_COUPON_TYPE, CouponType.CASH_COUPON);
        } else if (TextUtils.equals("2", data.getCouponType()) && data.getUsableCount() >= 0) {//抵扣券
            bundle.putSerializable(CashCouponFragment.CARD_COUPON_TYPE, CouponType.VOUCHER);
        } else if (TextUtils.equals("3", data.getCouponType()) && data.getUsableCount() >= 0) {//加息券
            bundle.putSerializable(CashCouponFragment.CARD_COUPON_TYPE, CouponType.RAISE_COUPON);
        } else if (TextUtils.equals("4", data.getCouponType()) && data.getUsableCount() >= 0) {//理财金
            bundle.putSerializable(CashCouponFragment.CARD_COUPON_TYPE, CouponType.FINANCIAL_GOLD);
        }

        return CashCouponFragment.newInstance(bundle, CashCouponFragment.class);
    }

    /**
     * 显示红包数量
     *
     * @param change
     */
    private void setRedCount(boolean change) {
        if (change)
            mRedCount--;
        if (mRedIndex == -1) return;
        if (mRedCountBean == null) return;

        if (mRedCount >= 0)
            mTabStrip.updateTitle(mRedIndex, String.format("%s(%s)", mRedCountBean.getCouponTypeName(), mRedCount));
        else
            mTabStrip.updateTitle(mRedIndex, String.format("%s(%s)", mRedCountBean.getCouponTypeName(), 0));
    }

    @Override
    public void onCount(int count) {
        setRedCount(true);
    }


}
