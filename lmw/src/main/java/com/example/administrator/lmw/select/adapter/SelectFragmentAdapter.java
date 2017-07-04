package com.example.administrator.lmw.select.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.lmw.R;
import com.example.administrator.lmw.adapter.CommonBaseAdapter;
import com.example.administrator.lmw.adapter.CommonViewHolder;
import com.example.administrator.lmw.finance.utils.DateUtils;
import com.example.administrator.lmw.select.entity.SelectlistDataBean;
import com.example.administrator.lmw.utils.ALLog;
import com.example.administrator.lmw.utils.BigDecemalCalculateUtil;
import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.view.TimerTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 3.0 首页适配器
 * <p>
 * Created by Vasile216 on 2017/5/10.
 */

public class SelectFragmentAdapter extends CommonBaseAdapter<SelectlistDataBean, CommonViewHolder> {
    private Map<String, TimerTextView> timerCache = new HashMap<>();

    public SelectFragmentAdapter(Context context) {
        super(context);
    }


    @Override
    public CommonViewHolder onCreateViewHolder(int position, View convertView, ViewGroup parent) {
        return CommonViewHolder.get(mContext, convertView, parent, R.layout.fragment_select_list_item, position);
    }

    @Override
    public void convert(CommonViewHolder holder, final SelectlistDataBean item, final int position) {
        if (item == null) return;
        if (item.datas == null) return;
        if (item.datas.get(0) == null) return;

        if (!TextUtils.isEmpty(item.datas.get(0).borrowTitle))
            holder.setText(R.id.title_tv, item.datas.get(0).borrowTitle);//标题
        if (!TextUtils.isEmpty(item.datas.get(0).minTenderedMoney))
            holder.setText(R.id.investment_amount,
                    String.format(getString(R.string.first_min_tendered_money), item.datas.get(0).minTenderedMoney));//起投金额
        showAnualRate(holder, item);//显示年化率
        showDeadLineValue(holder, item);//投资期限
        showLabel(holder, item);//显示标签
        showTimer(holder, item);
        showStatus(holder, item);// 根据属性显示不同的文字

        if (!TextUtils.isEmpty(item.datas.get(0).repayModeStr))//还款方式
            holder.setText(R.id.repay_mode_tv, item.datas.get(0).repayModeStr);
    }

    /**
     * 显示属性状态
     * @param holder
     * @param item
     */
    private void showStatus(CommonViewHolder holder, SelectlistDataBean item){
        if (!TextUtils.isEmpty(item.datas.get(0).status)) {
            int statue = Integer.valueOf(item.datas.get(0).status);
            ALLog.e("STATUE=====================================" + statue);
            //   status	0 标的状态   -2作废 -1-产品删除(0-(初始)待审核 1-审核失败 2-自动队列(待预售)3-(在售)正在招标中 4-（售罄）已满标  5-募集中
            //  6-募集失败 7-募集成功 8-下架(停售) 9-已经结束 10-定时发售（预售） )
            switch (statue) {
                case -2:
                    showRemayInvestTotal(holder, item);//显示剩余总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "作废");
                    break;
                case -1:
                    showRemayInvestTotal(holder, item);//显示剩余总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "产品删除");
                    break;
                case 0:
                    showRemayInvestTotal(holder, item);//显示剩余总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "待审核");
                    break;
                case 1:
                    showRemayInvestTotal(holder, item);//显示剩余总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "审核失败");
                    break;
                case 2:
                    showInvestedToatl(holder, item);//显示融资总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "待预售");
                    break;
                case 3:
                    showRemayInvestTotal(holder, item);
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_blue_solid_btn);// 蓝色背景
                    holder.setText(R.id.select_btn, "立即投资");
                    break;
                case 4:
                    showInvestedToatl(holder, item);//显示融资总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    showRepayStatus(holder,item);
                    break;
                case 5:
                    showRemayInvestTotal(holder, item);
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_blue_solid_btn);// 蓝色背景
                    holder.setText(R.id.select_btn, "立即投资");
                    break;
                case 6:
                    showRemayInvestTotal(holder, item);//显示剩余总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "募集失败");
                    break;
                case 7:
                    showInvestedToatl(holder, item);//显示融资总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    showRepayStatus(holder,item);
                    break;
                case 8:
                    showRemayInvestTotal(holder, item);//显示剩余总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "停售");
                    break;
                case 9:
                    showInvestedToatl(holder, item);//显示融资总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_gray_solid_btn);// 灰色背景
                    holder.setText(R.id.select_btn, "已结束");
                    break;
                case 10:
                    showInvestedToatl(holder, item);//显示融资总额
                    holder.setTextBackground(R.id.select_btn, R.drawable.ring_blue_solid_btn);// 蓝色背景
                    showTimer(holder, item);
                    break;
            }
        }
    }

    /**
     * 文字的状态 10-立即抢购11-已满标 12-计息中 13-还款中 14-已还款
     *
     * @param holder
     * @param item
     */

    private void showRepayStatus(CommonViewHolder holder, SelectlistDataBean item){
        if (!TextUtils.isEmpty(item.datas.get(0).repayStatus)){
            int repayStatus = Integer.valueOf(item.getDatas().get(0).getRepayStatus());
            switch (repayStatus){
                case 11:
                    holder.setText(R.id.select_btn, "已满标");
                    break;
                case 12:
                    holder.setText(R.id.select_btn, "计息中");
                    break;
                case 13:
                    holder.setText(R.id.select_btn, "还款中");
                    break;
                case 14:
                    holder.setText(R.id.select_btn, "已还款");
                    break;
            }
        }

    }

    private void showRemayInvestTotal(CommonViewHolder holder, SelectlistDataBean item) {
        if (!TextUtils.isEmpty(item.datas.get(0).remaMoneyNumber) && !TextUtils.isEmpty(item.datas.get(0).remaMoneyUnit))//剩余投标金额
            holder.setText(R.id.remaining_amount_tv,
                    String.format(mContext.getString(R.string.remanent_money),
                            item.datas.get(0).remaMoneyNumber, item.datas.get(0).remaMoneyUnit));
    }

    /**
     * 倒计时
     */
    private void showTimer(CommonViewHolder holder, final SelectlistDataBean item) {
        final TimerTextView timerTextView = holder.getView(R.id.select_btn);
        timerTextView.stop();

        if (item.datas.get(0).status.equals("10") && !TextUtils.isEmpty(item.datas.get(0).saleTimeStart) && !TextUtils.isEmpty(item.datas.get(0).nowTime)) {
            timerTextView.setTimeTips("离开始认购还剩：");
            long diff = DateUtils.getStringToDate(item.datas.get(0).saleTimeStart) - DateUtils.getStringToDate(item.datas.get(0).nowTime);
            TimerTextView temp = timerCache.get(item.datas.get(0).borrowId);
            if (temp != null) {
                diff = temp.getMillisInFuture();
                if (diff == 0) {//清楚缓存，更新数据
                    timerCache.remove(item.datas.get(0).borrowId);
                }
            } else {
                temp = new TimerTextView(mContext);
                temp.setTimes(diff);
                temp.start();
                timerCache.put(item.datas.get(0).borrowId, temp);
            }
            timerTextView.setVisibility(diff > 0 ? View.VISIBLE : View.GONE);
            timerTextView.setTimes(diff);
            timerTextView.start();
            timerTextView.setOnTimeDownListener(new TimerTextView.OnTimeDownListener() {
                @Override
                public void isFinish(TimerTextView v) {
                    v.setVisibility(View.GONE);
                    if (onTimeOverListener != null) {
                        onTimeOverListener.isFinish();
                    }
                }
            });
        } else {
            holder.setText(R.id.select_btn, "");
        }
    }


    private void showInvestedToatl(CommonViewHolder holder, SelectlistDataBean item) {
        if (!TextUtils.isEmpty(item.datas.get(0).buyTotalAmountNumber) && !TextUtils.isEmpty(item.datas.get(0).buyTotalAmountUnit))
            holder.setText(R.id.remaining_amount_tv,
                    String.format(mContext.getString(R.string.total_money),
                            item.datas.get(0).buyTotalAmountNumber, item.datas.get(0).buyTotalAmountUnit));
    }

    private void showLabel(CommonViewHolder holder, SelectlistDataBean item) {
        String[] labelsText;
        ALLog.e("标签" + item.datas.get(0).labels);
        if (!TextUtils.isEmpty(item.datas.get(0).labels)) {//标签显示
            labelsText = item.datas.get(0).labels.split(",");
            if (labelsText.length == 1) {
                holder.setText(R.id.invest_own_tv, labelsText[0]);
                holder.getView(R.id.invest_own_tv).setVisibility(View.VISIBLE);
                holder.getView(R.id.limit_num_tv).setVisibility(View.INVISIBLE);
                holder.getView(R.id.add_rate_tv).setVisibility(View.INVISIBLE);
            }
            if (labelsText.length == 2) {
                holder.setText(R.id.invest_own_tv, labelsText[0]);
                holder.setText(R.id.limit_num_tv, labelsText[1]);
                holder.getView(R.id.invest_own_tv).setVisibility(View.VISIBLE);
                holder.getView(R.id.limit_num_tv).setVisibility(View.VISIBLE);
                holder.getView(R.id.add_rate_tv).setVisibility(View.INVISIBLE);
            }
            if (labelsText.length == 3) {
                holder.setText(R.id.invest_own_tv, labelsText[0]);
                holder.setText(R.id.limit_num_tv, labelsText[1]);
                holder.setText(R.id.add_rate_tv, labelsText[2]);
                holder.getView(R.id.invest_own_tv).setVisibility(View.VISIBLE);
                holder.getView(R.id.limit_num_tv).setVisibility(View.VISIBLE);
                holder.getView(R.id.add_rate_tv).setVisibility(View.VISIBLE);
            }
        } else {
            holder.getView(R.id.invest_own_tv).setVisibility(View.INVISIBLE);
            holder.getView(R.id.limit_num_tv).setVisibility(View.INVISIBLE);
            holder.getView(R.id.add_rate_tv).setVisibility(View.INVISIBLE);
        }

    }

    private void showAnualRate(CommonViewHolder holder, SelectlistDataBean item) {
        if (TextUtils.equals(item.getDatas().get(0).getIsFlow(), "2")) {// 是否浮动利率(1=固定利率|2=浮动利率)
            // 显示月增利加息显示
            holder.getView(R.id.select_lin).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(item.getDatas().get(0).getFlowMinAnnualRate())) {
                holder.setText(R.id.earnings_tv, item.getDatas().get(0).getFlowMinAnnualRate().substring(0, item.getDatas().get(0).getFlowMinAnnualRate().indexOf(".")));
                holder.setText(R.id.earnings_percent_tv, item.getDatas().get(0).getFlowMinAnnualRate().substring(item.getDatas().get(0).getFlowMinAnnualRate().indexOf("."), item.getDatas().get(0).getFlowMinAnnualRate().length()) + "%");
            }
            if (!TextUtils.isEmpty(item.getDatas().get(0).getFlowMaxAnnualRate())) {
                holder.setText(R.id.select_yields_tv, "~" + item.getDatas().get(0).getFlowMaxAnnualRate().substring(0, item.getDatas().get(0).getFlowMaxAnnualRate().indexOf(".")));
                holder.setText(R.id.select_yields_tv_per, item.getDatas().get(0).getFlowMaxAnnualRate().substring(item.getDatas().get(0).getFlowMaxAnnualRate().indexOf("."), item.getDatas().get(0).getFlowMaxAnnualRate().length()) + "%");
            }
            if (BigDecemalCalculateUtil.compareTo(item.getDatas().get(0).getIncreaseRate(), "0") == 0) {
                holder.getView(R.id.select_head_tv).setVisibility(View.GONE);
            } else {
                holder.getView(R.id.select_head_tv).setVisibility(View.VISIBLE);
                holder.setText(R.id.select_head_tv, "+" + item.getDatas().get(0).getIncreaseRate() + "%");
            }
        } else {
            // 隐藏月增利加息显示
            holder.getView(R.id.select_head_tv).setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.datas.get(0).annualRate)) {
                int j = item.datas.get(0).annualRate.indexOf(".");
                holder.setText(R.id.earnings_tv, item.datas.get(0).annualRate.substring(0, j));
                holder.setText(R.id.earnings_percent_tv, item.datas.get(0).annualRate.substring(j, item.datas.get(0).annualRate.length()) + "%");//年化+加息
                if (NumberParserUtil.parserDouble(item.datas.get(0).increaseRate, 0) > 0) {
                    holder.getView(R.id.select_lin).setVisibility(View.VISIBLE);
                    holder.setText(R.id.select_yields_tv, "");
                    holder.setText(R.id.select_yields_tv_per, "+" + item.datas.get(0).increaseRate.substring(0, item.datas.get(0).increaseRate.indexOf(".")) +  item.datas.get(0).increaseRate.substring(item.datas.get(0).increaseRate.indexOf("."), item.datas.get(0).increaseRate.length()) + "%");
                } else {
                    holder.getView(R.id.select_lin).setVisibility(View.GONE);
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
    private void showDeadLineValue(CommonViewHolder holder, SelectlistDataBean item) {
        if (TextUtils.equals(item.datas.get(0).getIsFlowDead(), "2")) {// 是否浮动期限(1=固定期限|2=浮动期限)
            if (!TextUtils.isEmpty(item.datas.get(0).getCollectLineMinValue()) && !TextUtils.isEmpty(item.datas.get(0).getCollectLineMaxValue()))
                holder.setText(R.id.investment_time_tv, item.datas.get(0).getCollectLineMinValue() + "~" + item.datas.get(0).getCollectLineMaxValue());
        } else {
            if (!TextUtils.isEmpty(String.valueOf(item.datas.get(0).getDeadLineValue()).trim()))
                holder.setText(R.id.investment_time_tv, item.datas.get(0).getDeadLineValue() + "");
        }
        if (!TextUtils.isEmpty(item.getDatas().get(0).getDeadLineType())) {
            if (item.datas.get(0).deadLineType.equals("1")) {
                holder.setText(R.id.investment_time_type_tv, "天");
            } else if (item.datas.get(0).deadLineType.equals("2")) {
                holder.setText(R.id.investment_time_type_tv, "个月");
            } else if (item.datas.get(0).deadLineType.equals("3")) {
                holder.setText(R.id.investment_time_type_tv, "年");
            } else {
                holder.setText(R.id.investment_time_type_tv, "天");
            }
        }
    }

    private SelectFragmentAdapter.OnTimeOverListener onTimeOverListener;

    public void setOnTimeOverListener(SelectFragmentAdapter.OnTimeOverListener onTimeOverListener) {
        this.onTimeOverListener = onTimeOverListener;
    }


    public interface OnTimeOverListener {
        void isFinish();
    }

    /**
     * 清楚缓存
     */
    public void clearCache() {
        if (timerCache != null) {
            timerCache.clear();
        }
    }
}