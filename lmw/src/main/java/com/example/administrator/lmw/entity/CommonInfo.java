package com.example.administrator.lmw.entity;

import java.io.Serializable;

/**
 * Created by lion on 2016/9/14 0014.
 */
public class CommonInfo implements Serializable {
    /**
     * lmw_product_address	打赏好评、安卓/ios 地址	string
     * lmw_product_android	微信基本配置	string
     * lmw_product_faq	    常见问题	string
     * lmw_product_ios	    ios基本配置	string
     * lmw_product_pro	    注册用户协议地址	string
     * lmw_product_safe	    安全保障地址	string
     * lmw_product_tel	    客服电话	string
     * lmw_qq_group	        官方QQ群	string
     * lmw_wechat_group	    官方微信群	string
     * lmw_recharge_limit	充值限额页面Url	string
     * lmw_risk_warning	    风险提示页面Url	string
     * lmw_wechat_group	    官方微信群	string
     * lmw_wechat_number	微信公众号	string
     * lmy_cash_management_agreement	资金管理协议页面Url
     * <p>
     * free_withdrawals_count	    免费提现次数	string
     * lmw_max_withdrawals_amount	每次最高提现额度
     * withdrawal_fee               提现手续费
     * lmw_recharge_min_amount      最小充值金额
     * continue_invest_way_explain  续投方式说明
     * income_calculator            收益计算器
     * lmw_con_invest_url           关于续投
     * lmw_has_prize_king_url	    投资记录标王页面	string
     * lmw_has_prize_title	        标标有奖头部	string
     * lmw_has_prize_url	        标标有奖页面	string
     * liminDataUrl                 利民数据
     * repay_desc_url	            还款方式说明url	string
     * continue_invest_tips_url	    续投提示url	string
     * EsignatureAuthorize           电子授权页面
     */


    public String lmw_product_android;
    public String lmw_product_pro;
    public String lmw_wechat_number;
    public String lmw_product_address;
    public String lmw_product_safe;
    public String lmw_product_ios;
    public String lmw_wechat_group;
    public String lmw_product_tel;
    public String lmw_qq_group;
    public String lmw_product_faq;
    public String lmw_risk_warning;
    public String lmw_recharge_limit;
    public String lmy_cash_management_agreement;
    public String lmw_about_us;
    public String free_withdrawals_count;
    public String lmw_max_withdrawals_amount;
    public String withdrawal_fee;
    public String lmw_recharge_min_amount;
    public String lmw_product_prompt;
    public String continue_invest_way_explain;
    public String income_calculator;
    public String lmw_con_invest_url;
    public String lmw_has_prize_king_url;
    public String lmw_has_prize_title;
    public String lmw_has_prize_url;
    public String liminDataUrl;
    public String repay_desc_url;
    public String continue_invest_tips_url;
    public String esignature_authorize_url;
    private String riskCamuluateUrl;//风险评估Url
    private String publicKeyClient;//加密公钥
    private String privateKeyClient;//解密私钥
    private String enableRsa;// 参数值：1表示启用，0表示不启用加密
    private String change_bank_card_url;// 换卡须知



    public String getContinue_invest_tips_url() {
        return continue_invest_tips_url;
    }

    public void setContinue_invest_tips_url(String continue_invest_tips_url) {
        this.continue_invest_tips_url = continue_invest_tips_url;
    }

    public String getLiminDataUrl() {
        return liminDataUrl;
    }

    public void setLiminDataUrl(String liminDataUrl) {
        this.liminDataUrl = liminDataUrl;
    }

    public String getRepay_desc_url() {
        return repay_desc_url;
    }

    public void setRepay_desc_url(String repay_desc_url) {
        this.repay_desc_url = repay_desc_url;
    }

    public String getLmw_product_prompt() {
        return lmw_product_prompt;
    }

    public void setLmw_product_prompt(String lmw_product_prompt) {
        this.lmw_product_prompt = lmw_product_prompt;
    }

    public String getContinue_invest_way_explain() {
        return continue_invest_way_explain;
    }

    public void setContinue_invest_way_explain(String continue_invest_way_explain) {
        this.continue_invest_way_explain = continue_invest_way_explain;
    }

    public String getIncome_calculator() {
        return income_calculator;
    }

    public void setIncome_calculator(String income_calculator) {
        this.income_calculator = income_calculator;
    }

    public String getLmw_has_prize_king_url() {
        return lmw_has_prize_king_url;
    }

    public void setLmw_has_prize_king_url(String lmw_has_prize_king_url) {
        this.lmw_has_prize_king_url = lmw_has_prize_king_url;
    }

    public String getLmw_has_prize_title() {
        return lmw_has_prize_title;
    }

    public void setLmw_has_prize_title(String lmw_has_prize_title) {
        this.lmw_has_prize_title = lmw_has_prize_title;
    }

    public String getLmw_has_prize_url() {
        return lmw_has_prize_url;
    }

    public void setLmw_has_prize_url(String lmw_has_prize_url) {
        this.lmw_has_prize_url = lmw_has_prize_url;
    }

    public String getLmw_con_invest_url() {
        return lmw_con_invest_url;
    }

    public void setLmw_con_invest_url(String lmw_con_invest_url) {
        this.lmw_con_invest_url = lmw_con_invest_url;
    }

    public String getLmw_product_android() {
        return lmw_product_android;
    }

    public void setLmw_product_android(String lmw_product_android) {
        this.lmw_product_android = lmw_product_android;
    }

    public String getLmw_product_pro() {
        return lmw_product_pro;
    }

    public void setLmw_product_pro(String lmw_product_pro) {
        this.lmw_product_pro = lmw_product_pro;
    }

    public String getLmw_wechat_number() {
        return lmw_wechat_number;
    }

    public void setLmw_wechat_number(String lmw_wechat_number) {
        this.lmw_wechat_number = lmw_wechat_number;
    }

    public String getLmw_product_address() {
        return lmw_product_address;
    }

    public void setLmw_product_address(String lmw_product_address) {
        this.lmw_product_address = lmw_product_address;
    }

    public String getLmw_product_safe() {
        return lmw_product_safe;
    }

    public void setLmw_product_safe(String lmw_product_safe) {
        this.lmw_product_safe = lmw_product_safe;
    }

    public String getLmw_product_ios() {
        return lmw_product_ios;
    }

    public void setLmw_product_ios(String lmw_product_ios) {
        this.lmw_product_ios = lmw_product_ios;
    }

    public String getLmw_wechat_group() {
        return lmw_wechat_group;
    }

    public void setLmw_wechat_group(String lmw_wechat_group) {
        this.lmw_wechat_group = lmw_wechat_group;
    }

    public String getLmw_product_tel() {
        return lmw_product_tel;
    }

    public void setLmw_product_tel(String lmw_product_tel) {
        this.lmw_product_tel = lmw_product_tel;
    }

    public String getLmw_qq_group() {
        return lmw_qq_group;
    }

    public void setLmw_qq_group(String lmw_qq_group) {
        this.lmw_qq_group = lmw_qq_group;
    }

    public String getLmw_product_faq() {
        return lmw_product_faq;
    }

    public void setLmw_product_faq(String lmw_product_faq) {
        this.lmw_product_faq = lmw_product_faq;
    }

    public String getLmw_about_us() {
        return lmw_about_us;
    }

    public void setLmw_about_us(String lmw_about_us) {
        this.lmw_about_us = lmw_about_us;
    }

    public String getLmw_risk_warning() {
        return lmw_risk_warning;
    }

    public void setLmw_risk_warning(String lmw_risk_warning) {
        this.lmw_risk_warning = lmw_risk_warning;
    }

    public String getLmw_recharge_limit() {
        return lmw_recharge_limit;
    }

    public void setLmw_recharge_limit(String lmw_recharge_limit) {
        this.lmw_recharge_limit = lmw_recharge_limit;
    }

    public String getLmy_cash_management_agreement() {
        return lmy_cash_management_agreement;
    }

    public void setLmy_cash_management_agreement(String lmy_cash_management_agreement) {
        this.lmy_cash_management_agreement = lmy_cash_management_agreement;
    }

    public String getFree_withdrawals_count() {
        return free_withdrawals_count;
    }

    public void setFree_withdrawals_count(String free_withdrawals_count) {
        this.free_withdrawals_count = free_withdrawals_count;
    }

    public String getLmw_max_withdrawals_amount() {
        return lmw_max_withdrawals_amount;
    }

    public void setLmw_max_withdrawals_amount(String lmw_max_withdrawals_amount) {
        this.lmw_max_withdrawals_amount = lmw_max_withdrawals_amount;
    }

    public String getWithdrawal_fee() {
        return withdrawal_fee;
    }

    public void setWithdrawal_fee(String withdrawal_fee) {
        this.withdrawal_fee = withdrawal_fee;
    }

    public String getLmw_recharge_min_amount() {
        return lmw_recharge_min_amount;
    }

    public void setLmw_recharge_min_amount(String lmw_recharge_min_amount) {
        this.lmw_recharge_min_amount = lmw_recharge_min_amount;
    }

    public String getPublicKeyClient() {
        return publicKeyClient;
    }

    public void setPublicKeyClient(String publicKeyClient) {
        this.publicKeyClient = publicKeyClient;
    }

    public String getPrivateKeyClient() {
        return privateKeyClient;
    }

    public void setPrivateKeyClient(String privateKeyClient) {
        this.privateKeyClient = privateKeyClient;
    }

    public String getEnableRsa() {
        return enableRsa;
    }

    public void setEnableRsa(String enableRsa) {
        this.enableRsa = enableRsa;
    }

    public String getEsignature_authorize_url() {
        return esignature_authorize_url;
    }

    public void setEsignature_authorize_url(String esignature_authorize_url) {
        this.esignature_authorize_url = esignature_authorize_url;
    }

    public String getRiskCamuluateUrl() {
        return riskCamuluateUrl;
    }

    public void setRiskCamuluateUrl(String riskCamuluateUrl) {
        this.riskCamuluateUrl = riskCamuluateUrl;
    }

    public String getChange_bank_card_url() {
        return change_bank_card_url;
    }

    public void setChange_bank_card_url(String change_bank_card_url) {
        this.change_bank_card_url = change_bank_card_url;
    }
}
