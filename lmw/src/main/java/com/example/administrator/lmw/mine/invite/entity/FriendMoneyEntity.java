package com.example.administrator.lmw.mine.invite.entity;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class FriendMoneyEntity {

    private double earningsSum;
    private int recommendSum;
    private String recommendCode;
    private String qrcodeUrl;
    private String inviteUrl;

    public double getEarningsSum() {
        return earningsSum;
    }

    public void setEarningsSum(double earningsSum) {
        this.earningsSum = earningsSum;
    }

    public int getRecommendSum() {
        return recommendSum;
    }

    public void setRecommendSum(int recommendSum) {
        this.recommendSum = recommendSum;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }
}
