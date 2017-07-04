package com.example.administrator.lmw.finance.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.config.PreferenceCongfig;
import com.example.administrator.lmw.finance.activity.DetailProductActivity;
import com.example.administrator.lmw.finance.activity.ProductItem;
import com.example.administrator.lmw.finance.entity.ProductItemDataBean;
import com.example.administrator.lmw.finance.utils.DateUtils;
import com.example.administrator.lmw.finance.utils.TextViewUtils;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.view.NumberProgressBar;
import com.example.administrator.lmw.view.TimerTextView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 标的子列表  Vasile
 * <p>
 * Created by Administrator on 2016/8/31 0031.
 */
public class ProductItemAdapter extends CommonBaseAdapter<ProductItemDataBean, CommonViewHolder> {

    private TextView groupTypesTv, groupTypeParametersTv;
    private RelativeLayout groupLayoutItem;

    TimerTextView timerTextView;
    NumberProgressBar progresssRb;
    int type = 1;//0是理财产品列表调用，没有分割线，1是理财产品子列表


    public ProductItemAdapter(Context context) {
        super(context);
    }

    public ProductItemAdapter(Context context, List<ProductItemDataBean> datas) {
        super(context, datas);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, parent, R.layout.item_financial_product, position);
        timerTextView = holder.getView(R.id.title_time_tv);// 初始化倒计时
        progresssRb = holder.getView(R.id.number_progresss_bar);
        //头部相关控件
        groupLayoutItem = holder.getView(R.id.rl_financial_title);
        groupTypesTv = holder.getView(R.id.types_tv);
        groupTypeParametersTv = holder.getView(R.id.type_Parameters_tv);

        return holder;
    }

    @Override
    public void convert(final CommonViewHolder holder, final ProductItemDataBean item, final int position) {
        if (item == null) return;

        setGroupTitle(item);
        showInvestProgress(holder, item);// 进度条
        showLabel(holder, item);// 标签
        showIncreaseRate(holder, item); // 年利率
        showDeadLineValue(holder, item);//投资期限
        holder.getView(R.id.yields_view).setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(item.getBorrowTitle()))// 标题
            holder.setText(R.id.financial_title_tv, item.getBorrowTitle());

        // 还款方式
        if (!TextUtils.isEmpty(item.getRepayModeStr()))
            holder.setText(R.id.investment_time_tv, item.getRepayModeStr());

        // 剩余金额 或者 融资总额
        if (item.getStatus() == 3 || item.getStatus() == 5) {
            holder.setText(R.id.remaining_text_tv, "剩余");
            holder.setText(R.id.remaining_money_tv, item.getRemaMoneyNumber());
            holder.setText(R.id.money_unit_tv, item.getRemaMoneyUnit());
        } else {
            holder.setText(R.id.remaining_text_tv, "融资总额");
            holder.setText(R.id.remaining_money_tv, item.getBuyTotalAmountNumber());
            holder.setText(R.id.money_unit_tv, item.getBuyTotalAmountUnit());
        }

        /**
         * 标的状态
         * -1-产品删除
         * 0-(初始)待审核
         * 1-审核失败
         * 2-自动队列
         * 3-(在售)正在招标中
         * 4-（售罄）已满标
         * 5-募集中
         * 6-募集失败
         * 7-募集成功
         * 8-下架(停售)
         * 9-已经结束
         * 10-定时发售（预售）
         *
         */
        switch (item.getStatus()) {
            case -1:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
            case 0:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
                break;
            case 1:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
                break;
            case 2:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
                break;
            case 3:
                findBlueView(holder);
                break;
            case 4:
//                repayStatus	    满标之后的状态	number	11-投资中 12-计息中 13-还款中 14-已还款
                if (!TextUtils.isEmpty(item.getRepayStatus()))
                    switch (NumberParserUtil.parserInt(item.getRepayStatus())) {
                        case 11:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                            break;
                        case 12:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_jxz);
                            break;
                        case 13:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_jxz);
                            break;
                        case 14:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_yhk);
                            break;
                    }
                findGregView(holder);
                break;
            case 5:
                findBlueView(holder);
                break;
            case 6:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
                break;
            case 7:
//                repayStatus	    满标之后的状态	number	11-投资中 12-计息中 13-还款中 14-已还款
                if (!TextUtils.isEmpty(item.getRepayStatus()))
                    switch (NumberParserUtil.parserInt(item.getRepayStatus())) {
                        case 11:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                            break;
                        case 12:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_jxz);
                            break;
                        case 13:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_jxz);
                            break;
                        case 14:
                            holder.setImageResource(R.id.buys_succee, R.drawable.grag_yhk);
                            break;
                    }
                findGregView(holder);
                break;
            case 8:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
                break;
            case 9:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
                break;
            case 10:
                findBlueView(holder);
                break;
            default:
                holder.setImageResource(R.id.buys_succee, R.drawable.grag_ymb);
                findGregView(holder);
                break;
        }

        /**
         * 倒计时
         */
        showTimeDown(item, position);

        itemClick(holder, item);
    }

    /**
     * 设置头部标题
     *
     * @param item
     */
    private void setGroupTitle(final ProductItemDataBean item) {
        groupLayoutItem.setVisibility(item.isGroupItem() ? View.VISIBLE : View.GONE);
        groupTypesTv.setText(item.getCate_name());
        groupTypeParametersTv.setText(getString(R.string.text_more));

        groupLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(String.valueOf(item.getAppType()))) {// 1:定期宝2:散标
                    Intent intent = new Intent(mContext, ProductItem.class);
                    intent.putExtra(PreferenceCongfig.INTENT_KEY_TITLE, item.getCate_name());
                    intent.putExtra("type", item.getCate_id());
                    intent.putExtra("isSearch", item.getIsSearch());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void showTimeDown(ProductItemDataBean item, int position) {
        timerTextView.stop();
        ALLog.e("--1--" + item.getCate_id() + "----->" + getCacheSize());
        if (item.getStatus() == 10 && !TextUtils.isEmpty(item.getSaleTimeStart()) && !TextUtils.isEmpty(item.getNowTime())) {
            progresssRb.setVisibility(View.GONE);
            TimerTextView temp = (TimerTextView) getTagCashe(item.getBorrowId());
            long diff = DateUtils.getStringToDate(item.getSaleTimeStart()) - DateUtils.getStringToDate(item.getNowTime());
            if (temp != null) {
                diff = temp.getMillisInFuture();
                if (diff == 0) {//清楚缓存，更新数据
                    removeCasheByey(item.getBorrowId());
                    item.setSaleTimeStart(item.getNowTime());
                    updateItem(item, position);
                }
            } else {
                temp = new TimerTextView(mContext);
                temp.setTimes(diff);
                temp.start();
                putTagCashe(item.getBorrowId(), temp);
            }
            ALLog.e("--2--" + item.getCate_id() + "----->" + getCacheSize());
            timerTextView.setVisibility(diff > 0 ? View.VISIBLE : View.GONE);
            timerTextView.setTimes(diff);
            timerTextView.start();
            timerTextView.setOnTimeDownListener(new TimerTextView.OnTimeDownListener() {
                @Override
                public void isFinish(TimerTextView v) {
                    v.setVisibility(View.GONE);
                }
            });
        } else {
            progresssRb.setVisibility(View.VISIBLE);
            timerTextView.setTimes(0);
            timerTextView.setVisibility(View.GONE);
        }
    }

    /**
     * item点击跳转
     *
     * @param holder
     * @param item
     */
    private void itemClick(CommonViewHolder holder, final ProductItemDataBean item) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("subjectId", item.getBorrowId());// 标的标识
                intent.putExtra("type", "0");//0标1债权
                intent.putExtra("cateId", item.getCateId());
                intent.putExtra("mobileAddInfo", item.getMobileAddInfo());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 年华利率
     *
     * @param holder
     * @param item
     */
    private void showIncreaseRate(CommonViewHolder holder, ProductItemDataBean item) {
        if (TextUtils.equals(item.getIsFlow(), "2")) {// 是否浮动利率(1=固定利率|2=浮动利率)
            // 显示月增利加息显示
            holder.getView(R.id.increase_lin).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(item.getFlowMinAnnualRate())) {
                holder.setText(R.id.yields_tv, item.getFlowMinAnnualRate().substring(0, item.getFlowMinAnnualRate().indexOf(".")));
                holder.setText(R.id.yields_tv_per, item.getFlowMinAnnualRate().substring(item.getFlowMinAnnualRate().indexOf("."), item.getFlowMinAnnualRate().length()) + "%");
            }
            if (!TextUtils.isEmpty(item.getFlowMaxAnnualRate())) {
                holder.setText(R.id.increase_yields_tv, "~" + item.getFlowMaxAnnualRate().substring(0, item.getFlowMaxAnnualRate().indexOf(".")));
                holder.setText(R.id.increase_yields_tv_per, item.getFlowMaxAnnualRate().substring(item.getFlowMaxAnnualRate().indexOf("."), item.getFlowMaxAnnualRate().length()) + "%");
            }
            if (BigDecemalCalculateUtil.compareTo(item.getIncreaseRate(), "0") == 0) {
                holder.getView(R.id.increase_head_tv).setVisibility(View.GONE);
            } else {
                holder.getView(R.id.increase_head_tv).setVisibility(View.VISIBLE);
                holder.setText(R.id.increase_head_tv, "+" + item.getIncreaseRate() + "%");
            }
        } else {
            // 隐藏月增利加息显示
            holder.getView(R.id.increase_lin).setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getAnnualRate()))
                holder.setText(R.id.yields_tv, item.getAnnualRate().substring(0, item.getAnnualRate().indexOf(".")));
            if (TextUtils.isEmpty(item.getIncreaseRate())) {
                holder.setText(R.id.yields_tv_per, item.getAnnualRate().substring(item.getAnnualRate().indexOf("."), item.getAnnualRate().length()) + "%");
            } else {
                if (NumberParserUtil.parserDouble(item.getIncreaseRate(), 0) > 0) {
                    holder.setText(R.id.yields_tv_per, item.getAnnualRate().substring(item.getAnnualRate().indexOf("."), item.getAnnualRate().length())
                            + "%+" + item.getIncreaseRate() + "%");
                } else {
                    holder.setText(R.id.yields_tv_per, item.getAnnualRate().substring(item.getAnnualRate().indexOf("."), item.getAnnualRate().length()) + "%");
                }
            }
        }
    }

    /**
     * 投资期限
     *
     * @param holder
     * @param item
     */
    private void showDeadLineValue(CommonViewHolder holder, ProductItemDataBean item) {
        if (TextUtils.equals(item.getIsFlowDead(), "2")) {// 是否浮动期限(1=固定期限|2=浮动期限)
            if (!TextUtils.isEmpty(item.getCollectLineMinValue()) && !TextUtils.isEmpty(item.getCollectLineMaxValue()))
                holder.setText(R.id.investment_tv, item.getCollectLineMinValue() + "~" + item.getCollectLineMaxValue());
        } else {
            if (!TextUtils.isEmpty(String.valueOf(item.getDeadLineValue()).trim()))
                holder.setText(R.id.investment_tv, item.getDeadLineValue() + "");
        }
        if (!TextUtils.isEmpty(String.valueOf(item.getDeadLineType()).toString()))
            TextViewUtils.setDeadLineType((TextView) holder.getView(R.id.investment_tv_p), item.getDeadLineType());

    }

    /**
     * 标签
     *
     * @param holder
     * @param item
     */
    private void showLabel(CommonViewHolder holder, ProductItemDataBean item) {
        if (item.getLabelStrArr() != null)
            switch (item.getLabelStrArr().size()) {
                case 0:
                    holder.getView(R.id.title_tv_bg_one).setVisibility(View.GONE);
                    holder.getView(R.id.title_tv_bg_two).setVisibility(View.GONE);
                    holder.getView(R.id.title_tv_bg_three).setVisibility(View.GONE);
                    break;
                case 1:
                    if (!TextUtils.isEmpty(item.getLabelStrArr().get(0))) {
                        holder.getView(R.id.title_tv_bg_one).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.title_tv_bg_one).setVisibility(View.GONE);
                    }
                    holder.setText(R.id.title_tv_bg_one, item.getLabelStrArr().get(0));
                    holder.getView(R.id.title_tv_bg_two).setVisibility(View.GONE);
                    holder.getView(R.id.title_tv_bg_three).setVisibility(View.GONE);
                    break;
                case 2:
                    if (!TextUtils.isEmpty(item.getLabelStrArr().get(0))) {
                        holder.getView(R.id.title_tv_bg_one).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.title_tv_bg_one).setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(item.getLabelStrArr().get(1))) {
                        holder.getView(R.id.title_tv_bg_two).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.title_tv_bg_two).setVisibility(View.GONE);
                    }
                    holder.setText(R.id.title_tv_bg_one, item.getLabelStrArr().get(0));
                    holder.setText(R.id.title_tv_bg_two, item.getLabelStrArr().get(1));
                    holder.getView(R.id.title_tv_bg_three).setVisibility(View.GONE);
                    break;
                case 3:
                    if (!TextUtils.isEmpty(item.getLabelStrArr().get(0))) {
                        holder.getView(R.id.title_tv_bg_one).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.title_tv_bg_one).setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(item.getLabelStrArr().get(1))) {
                        holder.getView(R.id.title_tv_bg_two).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.title_tv_bg_two).setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(item.getLabelStrArr().get(2))) {
                        holder.getView(R.id.title_tv_bg_three).setVisibility(View.VISIBLE);
                    } else {
                        holder.getView(R.id.title_tv_bg_three).setVisibility(View.GONE);
                    }
                    holder.setText(R.id.title_tv_bg_one, item.getLabelStrArr().get(0));
                    holder.setText(R.id.title_tv_bg_two, item.getLabelStrArr().get(1));
                    holder.setText(R.id.title_tv_bg_three, item.getLabelStrArr().get(2));
                    break;
            }
    }

    /**
     * 进度条
     *
     * @param holder
     * @param item
     */
    private void showInvestProgress(CommonViewHolder holder, ProductItemDataBean item) {
        // 进度条
        float progress = 0;
        if (!TextUtils.isEmpty(item.getHasInvestMoney()) && !TextUtils.isEmpty(item.getBuyTotalAmount())) {
            progress = BigDecemalCalculateUtil.divide(item.getBuyTotalAmount(), item.getHasInvestMoney(), 1);
        }
        progresssRb.setProgress(progress);
    }


    /**
     * 已满标 已结束 准备抢购 界面显示
     *
     * @param holder
     */
    private void findGregView(CommonViewHolder holder) {
        holder.setTextColor(R.id.yields_tv, R.color.finance_pro);
        holder.setTextColor(R.id.yields_tv_per, R.color.finance_pro);
        holder.setTextColor(R.id.increase_yields_tv, R.color.finance_pro);
        holder.setTextColor(R.id.increase_yields_tv_per, R.color.finance_pro);
        holder.setTextColor(R.id.increase_head_tv, R.color.finance_pro);
        holder.setTextBackground(R.id.increase_head_tv, R.drawable.maow);
        holder.setTextColor(R.id.investment_tv, R.color.finance_pro);
        holder.setTextColor(R.id.investment_tv_p, R.color.select_list_earnings);
        holder.setTextColor(R.id.title_tv_bg_one, R.color.finance_pro);
        holder.setTextColor(R.id.title_tv_bg_two, R.color.finance_pro);
        holder.setTextColor(R.id.title_tv_bg_three, R.color.finance_pro);
        holder.setTextBackground(R.id.title_tv_bg_one, R.drawable.ringgreatextview);
        holder.setTextBackground(R.id.title_tv_bg_two, R.drawable.ringgreatextview);
        holder.setTextBackground(R.id.title_tv_bg_three, R.drawable.ringgreatextview);
        holder.setTextColor(R.id.remaining_money_tv, R.color.finance_pro);
        holder.setTextColor(R.id.remaining_text_tv, R.color.select_list_earnings);
        holder.setTextColor(R.id.money_unit_tv, R.color.finance_pro);
        holder.getView(R.id.buys_succee).setVisibility(View.VISIBLE);
        progresssRb.setReachedBarColor(ContextCompat.getColor(mContext, R.color.progress_bg_gray));
    }

    /**
     * 立即购买 界面显示
     *
     * @param holder
     */
    private void findBlueView(CommonViewHolder holder) {
        holder.setTextColor(R.id.yields_tv, R.color.select_list_detail);
        holder.setTextColor(R.id.yields_tv_per, R.color.select_list_detail);
        holder.setTextColor(R.id.increase_yields_tv, R.color.select_list_detail);
        holder.setTextColor(R.id.increase_yields_tv_per, R.color.select_list_detail);
        holder.setTextColor(R.id.increase_head_tv, R.color.select_list_detail);
        holder.setTextBackground(R.id.increase_head_tv, R.drawable.maop);
        holder.setTextColor(R.id.investment_tv, R.color.announcement_tv);
        holder.setTextColor(R.id.investment_tv_p, R.color.select_list_earnings);
        holder.setTextColor(R.id.title_tv_bg_one, R.color.select_list_buy);
        holder.setTextColor(R.id.title_tv_bg_two, R.color.select_list_buy);
        holder.setTextColor(R.id.title_tv_bg_three, R.color.select_list_buy);
        holder.setTextBackground(R.id.title_tv_bg_one, R.drawable.ringbluetextview);
        holder.setTextBackground(R.id.title_tv_bg_two, R.drawable.ringbluetextview);
        holder.setTextBackground(R.id.title_tv_bg_three, R.drawable.ringbluetextview);
        holder.setTextColor(R.id.remaining_money_tv, R.color.announcement_tv);
        holder.setTextColor(R.id.remaining_text_tv, R.color.select_list_earnings);
        holder.setTextColor(R.id.money_unit_tv, R.color.announcement_tv);
        holder.getView(R.id.buys_succee).setVisibility(View.GONE);
        progresssRb.setReachedBarColor(ContextCompat.getColor(mContext, R.color.text_blue));
    }

    /**
     * 资源回收
     */
    public void clearCache() {
        Set<Map.Entry<Object, Object>> s = getCacheMap().entrySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                TimerTextView cdt = (TimerTextView) pairs.getValue();
                cdt.stop();
                cdt = null;
            } catch (Exception e) {
            }
        }
        it = null;
        s = null;
        getCacheMap().clear();
    }

}
