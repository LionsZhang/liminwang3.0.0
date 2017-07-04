package com.example.administrator.lmw.finance;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.base.BaseFragment;
import com.example.administrator.lmw.base.BasePagerAdapter;
import com.example.administrator.lmw.finance.fragment.CreditTransfer;
import com.example.administrator.lmw.finance.fragment.FinancialProduct;
import com.example.administrator.lmw.inteface.OnDialogClickListener;
import com.example.administrator.lmw.utils.PopWindowManager;
import com.example.administrator.lmw.utils.Singlton;
import com.example.administrator.lmw.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 理财
 * <p/>
 * Created by Administrator on 2016/8/22.
 */
public class FinanceFragment extends BaseFragment {
    @InjectView(R.id.tab_top)
    PagerSlidingTabStrip mTabTop;
    @InjectView(R.id.viewpager)
    ViewPager mViewpager;
    private List<Fragment> mFragmentList = new ArrayList<>(2);

    @Override
    protected void initializeData() {
    }
    @Override
    protected void initializeView() {
        initTab();
    }


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_finance_layout;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    /**
     * 初始化导航
     */
    private void initTab() {

        FinancialProduct financialProduct = new FinancialProduct();
        CreditTransfer creditTransfer = new CreditTransfer();

        mFragmentList.add(financialProduct);
        mFragmentList.add(creditTransfer);

        List<String> titles = new ArrayList<>(2);
        titles.add(getResources().getString(R.string.financial_product));
        titles.add(getResources().getString(R.string.credit_transfer));


        //给ViewPager设置适配器
        mViewpager.setAdapter(new BasePagerAdapter(getActivity().getSupportFragmentManager(), mFragmentList, titles));
        mViewpager.setCurrentItem(0);//设置当前显示标签页为第一页
        mTabTop.setViewPager(mViewpager);

    }

    /**
     * 弹出框
     *
     * @param id     内容
     * @param cancel cancel键
     */
    private void diaglog(int id, String cancel) {
        String str = getResources().getString(id);
        Singlton.getInstance(PopWindowManager.class).
                showTwoButtonDialog(mContext, "提示", str, "取消", cancel,
                        new OnDialogClickListener() {
                            @Override
                            public void onClick(int id, View v) {
                                if (id == 1) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4008318999"));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
    }


}
