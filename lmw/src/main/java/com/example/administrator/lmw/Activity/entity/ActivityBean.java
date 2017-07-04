package com.example.administrator.lmw.Activity.entity;

/**
 * 活动实体
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/4/20 18:04
 **/
public class ActivityBean {


    /**
     * activityPic : http://www.limin.com/pages/1.jpg
     * activityTitle : 开宝箱活动
     * activityUrl : http://www.limin.com/pages/index.html
     * promptStatus : 0
     * content : 你达到开宝箱活动参与条件，获得50元现金券奖励
     */

    private String activitySharePicUrl;
    private String shareContent;
    private String activityTitle;
    private String activityAppUrl;
    private int promptStatus;//
    private String content;
    private int activityTpye;//活动提醒类型(1:卡券活动;2:礼包活动;)

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityAppUrl() {
        return activityAppUrl;
    }

    public void setActivityAppUrl(String activityAppUrl) {
        this.activityAppUrl = activityAppUrl;
    }

    public int getPromptStatus() {
        return promptStatus;
    }

    public void setPromptStatus(int promptStatus) {
        this.promptStatus = promptStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getActivityTpye() {
        return activityTpye;
    }

    public void setActivityTpye(int activityTpye) {
        this.activityTpye = activityTpye;
    }

    public String getActivitySharePicUrl() {
        return activitySharePicUrl;
    }

    public void setActivitySharePicUrl(String activitySharePicUrl) {
        this.activitySharePicUrl = activitySharePicUrl;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    /**
     * 是否显示弹窗
     *
     * @return
     */
    public boolean isShow() {
        return getPromptStatus() == 1;
    }

}
