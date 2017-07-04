package com.example.administrator.lmw.config;

/**
 * @ describe preference 管理常量类
 * Created by lion
 */
public class PreferenceCongfig {

    /*偏好设置常量key*/

    //用户第一次安装
    public static final String APP_ISFIRST_LAUCH = "app_isFirstLauch";

    //用户更新下载完成APP
    public static final String APP_DOWN_LOAD_COMPULTE = "APP_DOWN_LOAD_COMPULTE";
    public static final String APP_ISFIRST_LAUCH_SHOW_GUGIDE_DIALOG = "app_isfirst_lauch_show_gugide_dialog";
    //是否创建了桌面快捷方式
    public static final String APP_PREFERENCE_NAME_SHORTCUT = "app_preference_name_shortcut";
    //保存用户信息
    public static final String KEY_USE_INFO = "key_use_info";
    //保存用户信息
    public static final String KEY_MINE_DATA_INFO = "key_mine_data_info";
    //保存全局配置
    public static final String KEY_COMMON_INFO = "key_common_info";
    //保存token
    public static final String APP_TOKEN = "app_token";
    public static final String APP_UDESK_TOKEN = "app_udesk_token";
    public static final String APP_UDESK_TOKEN_GROOP = "app_udesk_token_groop";
    public static final String APP_UDESK_GROOP_ID = "24501";
    //保存是否是前台进程
    public static final String APP_IS_FORWARD = "app_is_forward";
    //保存是否是前台进程
    public static final String APP_TO_FORWARD_START = "app_to_forward_start";

    //保存是否是前台进程
    public static final String APP_IS_SCREEN_WAKE = "app_is_screen_wake";
    //保存账号
    public static final String APP_ACCOUNT = "app_account";
    //保存是否与上一次账号相同布尔值
    public static final String IS_LAST_ACCOUNT_LOGIN = "is_last_account_login";
    //保存密码
    public static final String APP_PSW = "app_psw";
    public static final String APP_TRADE_PSW = "app_trade_psw";
    // 是否第一次进入首页
    public static final String APP_FIRST_SELECT = "app_firstselect";
    // 是否第一次进入首页
    public static final String APP_AUTO_LOGIN = "app_auto_login";
    public static  boolean IS_APP_AUTO_LOGIN = false;
    // 是否第一次进入首页
    public static final String APP_IS_SET_GESTURE_PSW = "app_is_set_gesture_psw";
    // 保存极光推送的注册ID 设备的唯一标示符
    public static final String JPUSH_REGESTER_ID = "jpush_regester_id";
    // 是否显示活动弹窗
    public static final String IS_SHOW_ACTIVE_DIALOG = "is_show_active_dialog";
    // 是否显示公告弹窗
    public static final String IS_SHOW_ANOUCEMENT_DIALOG = "is_show_anoucement_dialog";
    // 上一次显示公告弹窗日期
    public static final String LAST_SHOW_ANOUCEMENT_DIALOG_DATE = "last_show_anoucement_dialog_date";
    // 上一次显示活动弹窗日期
    public static final String LAST_SHOW_ACTIVE_DIALOG_DATE = "last_show_active_dialog_date";
    // 活动弹窗ID
    public static final String ACTIVE_DIALOG_ID = "active_dialog_id";
    // 公告弹窗ID
    public static final String ANOUCEMENT_DIALOG_ID = "anoucement_dialog_id";
    //储存是否手势密码登录为当前界面
    public static final String IS_SHOW_CURRENT_ACTIVITY_FOR_GESTURE_LOGIN = "is_show_current_activity_for_gesture_login";
    //储存通知类型
    public static final String NOTICE_CONTENT = "notice_content";
    public static final String SHOW_RISK_CACULATER = "show_risk_caculater";

    //储存我的页面眼睛状态
    public static final String MINE_EYE_STATU = "mine_eye_statu";

    // 常见问题 帮助中心key
    //   public static final String APP_COMMMON_QUESTION= "app_commmon_question";




    /*全局传参常量key*/

    public static boolean isLogin = false;
    public static boolean isCancelLogin = false;
    public static boolean isRegister = false;
    public static final String VERIFY_CODE = "verifyCode";//验证码
    public static final String PHONE = "phone";//手机号
    public static final String RECONMENDER = "reconmender";//推荐人
    public static final String FROM_POSITION_TO_SET_PSW = "from_position_to_set_psw";//跳到设置密码页面类型



    public static int loginType;
    public static int RegistType;
    public static String ChoseBankCard;
    public static final int ChoseBankCard_limit_detail = 100;
    public static final int ChoseBankCard_chose_card = 101;
    public static final int ChoseBankCard_support_card = 102;

    public static final int FROM_LOGIN_TO_SET_PSW = 0;//从登录跳到设置密码页面类型
    public static final int FROM_REGISTER_TO_SET_PSW = 1;//从登录跳到设置密码页面类型
    public static final int FROM_SET_MOTIFY_LOGIN_PSW = 2;//修改登录密码
    public static final int FROM_SET_MOTIFY_GESTURE_PSW = 3;//修改手势密码
    public static final int FROM_SET_RESET_GESTURE_PSW = 5;//重置手势密码
    public static final int FROM_SET_SET_GESTURE_PSW = 4;//设置手势密码
    public static final int FROM_FINANCE_BANNAER_TO_LOGIN = 6;
    public static final int FROM_FINANCE_BANNAER_TO_REGISTER = 7;


    public static final String FROM_POSITION_TO_SET_TRADE_PSW = "from_position_to_set_trade_psw";//跳到设置交易密码页面类型
    public static final int FROM_FILL_TO_SET_TRADE_PSW = 1;//从充值去设置交易密码
    public static final int FROM_CREDIT_TO_SET_TRADE_PSW = 2;//从提现去设置交易密码

    /*     isBindBankCard	是否绑定银行卡(0:未绑定;1:已绑定;2:需重新绑定)	number	[示例：1]
        isBindEmail	是否绑定邮箱(0:未绑定;1:已绑定)	number	[示例：1]
	    isBindPhone	是否已绑定手机(0:未绑定;1:已绑定)	number	[示例：1]
	    isRealnameAuth	是否实名认证(0:未认证;1:已认证)	number	[示例：1]
	    isSetTradepasswd	是否已设置交易密码(0:未设置;1:已设置)*/
    public static final int IS_SET_TRADEPASSWD = 1;
    public static final int IS_NOT_SET_TRADEPASSWD = 0;
    public static final int IS_REAL_NAME_AUTH = 1;
    public static final int IS_NOT_REAL_NAME_AUTH = 0;
    public static final int IS_BIND_PHONE = 1;
    public static final int IS_NOT_BIND_PHONE = 0;
    public static final int IS_BIND_EMAIL = 1;
    public static final int IS_BIND_BANKCARD = 1;
    public static final int IS_NOT_BIND_BANKCARD = 0;
    public static final int IS_REBIND_BANKCARD = 2;
    public static final int IS_NEW_USER = 0;
    public static final int IS_OLD_EFFECTIVE_USER = 1;
    public static final int IS_OLD_UN_EFFECTIVE_USER = 2;


    /*设置账户系列key*/
    public static final String FIRST_INPUT_EXCHARGE_PSW = "first_input_excharge_psw";
    public static final String CONFIRM_INPUT_EXCHARGE_PSW = "confirm_input_excharge_psw";

    public static final String REGIST_ACCOUNT_VERIFY = "regist_account_verify";//修改登录密码
    public static final int regist_account_verify = 2;
    public static final String RESET_EXCHARGE_PSW = "reset_excharge_psw";
    public static final int reset_excharge_psw = 0;//重置交易密码
    public static final String MOTIFY_EXCHARGE_OLD_PSW = "motify_excharge_old_psw";
    public static final String MOTIFY_EXCHARGE_PSW = "motify_excharge_psw";
    public static final int motify_excharge_psw = 1;//修改交易密码
    public static final String SET_EXCHARGE_PSW = "set_excharge_psw";
    public static final int set_excharge_psw = 2;//设置置交易密码
    public static final String BIND_BANK_CARD_VERIFY = "bind_bank_card_verify";
    public static final int bind_bank_card_verify = 4;
    public static final String RESET_GESTURE_PSW = "reset_gesture_psw";//重置手势密码
    public static final int reset_gesture_psw = 1;
    public static final String SET_GESTURE_PSW = "set_gesture_psw";//设置手势密码
    public static final int set_gesture_psw = 2;
    public static final String MOTIFY_GESTURE_PSW = "motify_gesture_psw";//修改手势密码
    public static final int motify_gesture_psw = 3;


    public static final String ACTION_BIND_BANKCARD = "action_bind_bankcard";
    public static final String ACTION_MOTIFY_BIND_BANKCARD = "action_motify_bind_bankcard";
    public static final String LICENCE = "licence";
    public static final String BIND_PHONE = "bind_phone";//绑定银行卡手机号
    public static final String BIND_CARD_ODER_NO = "bind_card_oder_no";
    public static final String FILL_ODER_NO = "fill_oder_no";
    public static final String OLD_LOGIN_PSW = "old_login_psw";
    public static final String FILL_RESET_EXCHARGE_PSW = "fill_reset_excharge_psw";
    public static final int fill_reset_excharge_psw = 100;
    public static final String CREDIT_RESET_EXCHARGE_PSW = "credit_reset_excharge_psw";
    public static final int credit_reset_excharge_psw = 101;
    public static final String PURCHASE_RESET_EXCHARGE_PSW = "purchase_reset_excharge_psw";
    public static final int purchase_reset_excharge_psw = 102;
    public static final String DEBENTURE_RESET_EXCHARGE_PSW = "debenture_reset_excharge_psw";
    public static final int debenture_reset_excharge_psw = 103;

    public static final String FILL_RESULT_TO_ACCOUNT_DETAIL_PURCHASE = "fill_result_to_account_detail_purchase";
    public static final int fill_result_to_account_detail_purchase = 104;
    public static final String FILL_RESULT_TO_ACCOUNT_DETAIL_FILL = "fill_result_to_account_detail_fill";
    public static final int fill_result_to_account_detail_fill = 105;
    public static final String PURCHASE_TO_LOGIN = "purchase_to_login";
    public static final int purchase_to_login = 106;
    public static final String DEBENTURE_TO_LOGIN = "debenture_to_login";
    public static final int debenture_to_login = 107;
    public static final String IS_ESIGNATURE_AUTHORIZE = "1";// 是否已签署授权委托书(0:未授权, 1:已授权)
    public static final String IS_NOT_ESIGNATURE_AUTHORIZE = "0";// 是否已签署授权委托书(0:未授权, 1:已授权)


    public static final String KEY_NEW_USER_SET_PW = "key_new_user";//新用户设置交易密码
    public static final String KEY_BANK_CARD_ADD = "key_bank_card_add";//添加银行卡



    /**
     * intent传参key
     */
    /*key*/
    public static final String INTENT_KEY_TITLE = "intent_key_title";//标题

    /*value*/
    public static final int intent_key_bind_bank_succ = 100;//银行绑卡成功
    public static final int intent_key_credit_succ = 101;//债权挂售成功
    public static final int intent_key_motify_exchange_psw_succ = 102;//修改交易密码成功
    public static final int intent_key_clear_bind_bank_succ = 103;//解除银行卡绑定成功
    public static final int intent_key_open_desponsity_succ = 104;//开通存管成功
    public static final int intent_key_motify_phone_succ = 105;//修改手机号成功
    public static final int intent_key_bind_bank_fail = 106;//银行绑卡失败
    public static final int intent_key_credit_fail = 107;//债权挂售失败
    public static final int intent_key_motify_exchange_psw_fail = 108;//修改交易密码失败
    public static final int intent_key_clear_bind_bank_fail = 109;//解除银行卡绑定失败
    public static final int intent_key_open_desponsity_fail = 110;//开通存管失败
    public static final int intent_key_motify_phone_fail = 111;//修改手机号失败
    public static final int intent_key_invest_fail = 112;//投资失败

    /**
     * 开通存管状态
     * private String openDpStatus	;//存管状态(-1未开通；0待激活(存量用户迁移专用)；1审核中；2审核通过；3：审核回退；4审核拒绝)
     */
    public static final String INTENT_KEY_DESPONSIT_OPERATE = "intent_key_desponsit_operate";//存管操作类型
    public static final String INTENT_KEY_OPEN_DESPONSITY_STATE = "intent_key_open_desponsity_state";//开通存管状态
    public static final String INTENT_KEY_OPEN_DESPONSITY_FAIL = "intent_key_open_desponsity_fail";//存管操作失败信息
    public static final String OPEN_DESPONSITY_NOT = "-1";//未开通
    public static final String OPEN_DESPONSITY_ACTIVE = "0";//待激活
    public static final String OPEN_DESPONSITY_AUDIT = "1";//审核中
    public static final String OPEN_DESPONSITY_PASS = "2";//审核通过
    public static final String OPEN_DESPONSITY_RETURN = "3";//审核回退
    public static final String OPEN_DESPONSITY_REJECT = "4";//审核拒绝
    public static final String OPEN_DESPONSITY_NO_PERMISSION = "-5";//-5:表示未成年不可开通存管
    /**
     * 风险评估状态
     */
    public static final String RISK_CACULATE_ALREADY = "1";//已经评估
    public static final String RISK_CACULATE_NOT = "0";//没有评估

}
