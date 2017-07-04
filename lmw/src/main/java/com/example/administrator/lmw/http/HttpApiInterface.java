package com.example.administrator.lmw.http;

import com.example.administrator.lmw.entity.AppEntity;
import com.example.administrator.lmw.entity.BaseResponse;
import com.example.administrator.lmw.entity.CommonEntity;
import com.example.administrator.lmw.finance.entity.CashCouponsEntity;
import com.example.administrator.lmw.finance.entity.CreditEntity;
import com.example.administrator.lmw.finance.entity.DebtBuyEntity;
import com.example.administrator.lmw.finance.entity.DetailEntity;
import com.example.administrator.lmw.finance.entity.DetailFragmentEntity;
import com.example.administrator.lmw.finance.entity.ExpectedReturn;
import com.example.administrator.lmw.finance.entity.InvestmentEntity;
import com.example.administrator.lmw.finance.entity.MatchVerifyEntity;
import com.example.administrator.lmw.finance.entity.PurchaseEntity;
import com.example.administrator.lmw.finance.entity.UserMoenyEntity;
import com.example.administrator.lmw.login.entity.LoginEntity;
import com.example.administrator.lmw.login.entity.MineData;
import com.example.administrator.lmw.login.entity.TokenLoginEntity;
import com.example.administrator.lmw.login.entity.UserInfo;
import com.example.administrator.lmw.mine.account.entity.AccountEntity;
import com.example.administrator.lmw.mine.credit.Entity.CreditInfo;
import com.example.administrator.lmw.mine.credit.Entity.CreditResult;
import com.example.administrator.lmw.mine.cumulative.entity.CumulativeEntity;
import com.example.administrator.lmw.mine.cumulative.entity.TotalAssetsEntity;
import com.example.administrator.lmw.mine.fill.entity.FillEntity;
import com.example.administrator.lmw.mine.fill.entity.OderEntity;
import com.example.administrator.lmw.mine.fill.entity.UserBankEntity;
import com.example.administrator.lmw.mine.invest.entity.BackPlanEntity;
import com.example.administrator.lmw.mine.invest.entity.ConfirmTransferEntity;
import com.example.administrator.lmw.mine.invest.entity.ContinueSetEntity;
import com.example.administrator.lmw.mine.invest.entity.InvestmentCountEntity;
import com.example.administrator.lmw.mine.invest.entity.InvestmentTransferEntity;
import com.example.administrator.lmw.mine.invest.entity.PurchaseDebtEntity;
import com.example.administrator.lmw.mine.more.entity.MoreEntity;
import com.example.administrator.lmw.mine.seting.entity.BankCardEntity;
import com.example.administrator.lmw.mine.seting.entity.BindBankEntity;
import com.example.administrator.lmw.mine.seting.entity.SetData;
import com.example.administrator.lmw.mine.transfer.entity.TransferCountEntity;
import com.example.administrator.lmw.mine.transfer.entity.TransferEntity;
import com.example.administrator.lmw.select.entity.ActivityOrAnoucemnetEntity;
import com.example.administrator.lmw.select.entity.AnnouncementEntity;
import com.example.administrator.lmw.select.entity.BannerEntity;
import com.example.administrator.lmw.select.entity.NoticeEntity;
import com.example.administrator.lmw.select.entity.StatisticsEntity;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lionzhang on 2016/8/16.
 */
public interface HttpApiInterface {

    /**
     * get请求查询参数 map
     * 获取短信验证码
     **/
    @GET("lmw-product/lmw")
    Observable<BaseResponse> getVerifyCode(@QueryMap Map<String, String> options);


    /**
     * get请求查询参数 map
     * 校验图形验证码
     **/
    @GET("lmw-product/lmw")
    Observable<BaseResponse> checkPictureVerifyCode(@QueryMap Map<String, String> options);

    /**
     * 获取banner条数据
     **/
    @POST("lmw-product/lmw")
    Observable<BannerEntity> getBanner(@QueryMap Map<String, String> bannerPara);

    /**
     * 核对手机号是否注册
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> checkPhone(@QueryMap Map<String, String> checkPhonePara);

    /**
     * 核对验证码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> checkVerifyCode(@QueryMap Map<String, String> checkPhonePara);


    /**
     * 注册
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> regist(@QueryMap Map<String, String> registerPara);

    /**
     * 登录
     **/
    @POST("lmw-product/lmw")
    Observable<LoginEntity> login(@QueryMap Map<String, String> checkPhonePara);

    /**
     * 设置登录密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> resetLoginPsw(@QueryMap Map<String, String> resetPswPara);

    /**
     * 设置手势密码保存至服务器
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> setGestruePsw(@QueryMap Map<String, String> resetPswPara);

    /**
     * 验证手势密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> verifyGestruePsw(@QueryMap Map<String, String> resetPswPara);


    /**
     * 获取用户信息
     **/
    @POST("lmw-product/lmw")
    Observable<UserInfo> getUserInfo(@QueryMap Map<String, String> userPara);

    /**
     * 获取set 用户数据
     **/
    @POST("lmw-product/lmw")
    Observable<SetData> getSetData(@QueryMap Map<String, String> userPara);

    /**
     * 获取我的頁面數據
     **/
    @POST("lmw-product/lmw")
    Observable<MineData> getMineDataInfo(@QueryMap Map<String, String> userPara);

    /**
     * 获取首页公告数据
     **/
    @POST("lmw-product/lmw")
    Observable<AnnouncementEntity> getAnnouncement(@QueryMap Map<String, String> bannerPara);

    /**
     * 获取首页统计数据
     **/
    @POST("lmw-product/lmw")
    Observable<StatisticsEntity> getStatistics(@QueryMap Map<String, String> bannerPara);

    /**
     * 获取债权列表
     **/
    @POST("lmw-product/lmw")
    Observable<CreditEntity> getCreditTransfer(@QueryMap Map<String, String> bannerPara);

    /**
     * 获取投资记录列表
     **/
    @GET("lmw-product/lmw")
    Observable<InvestmentEntity> getInvestmentRecord(@QueryMap Map<String, String> bannerPara);

//   getBorrowProduct

    /**
     * 获取标详情
     **/
    @POST("lmw-product/lmw/")
    Observable<DetailFragmentEntity> getDetailFragment(@QueryMap Map<String, String> bannerPara);// DetailEntity

    /**
     * 获取债券详情
     **/
    @POST("lmw-product/lmw/")
    Observable<DetailEntity> getDetail(@QueryMap Map<String, String> bannerPara);

    /**
     * 获取累计收益
     **/
    @POST("lmw-product/lmw")
    Observable<CumulativeEntity> getCumulative(@QueryMap Map<String, String> bannerPara);

//    /**
//     * 我的红包列表
//     **/
//    @POST("lmw-product/lmw")
//    Observable<DataRedPacket> getCardRed(@QueryMap Map<String, String> bannerPara);

//    /**
//     * 我的现金券
//     **/
//    @POST("lmw-product/lmw")
//    Observable<CashEntity> getCash(@QueryMap Map<String, String> bannerPara);

    /**
     * 我的银行列表
     **/
    @POST("lmw-product/lmw")
    Observable<BankCardEntity> getBankInfoList(@QueryMap Map<String, String> bannerPara);

    /**
     * 绑定银行卡
     **/
    @POST("lmw-product/lmw")
    Observable<BindBankEntity> bindBankCard(@QueryMap Map<String, String> bannerPara);

    /**
     * 绑定银行卡2
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> bindBankCardTwo(@QueryMap Map<String, String> bannerPara);

//    /**
//     * 首页列表
//     **/
//    @POST("lmw-product/lmw")
//    Observable<SelectlistEnitity> getRecommend(@QueryMap Map<String, String> bannerPara);

    /**
     * 设置交易密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> setTradePsw(@QueryMap Map<String, String> bannerPara);

    /**
     * 修改交易密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> motifyTradePsw(@QueryMap Map<String, String> bannerPara);

    /**
     * 重置交易密码验证
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> resetTradePswVerify(@QueryMap Map<String, String> bannerPara);

    /**
     * 重置交易密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> resetTradePsw(@QueryMap Map<String, String> bannerPara);

    /**
     * 验证旧的交易密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> verifyOldTradePsw(@QueryMap Map<String, String> bannerPara);

    /**
     * 验证旧的登录密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> verifyOldLoginPsw(@QueryMap Map<String, String> bannerPara);

    /**
     * 修改登录密码
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> motifyLoginPsw(@QueryMap Map<String, String> bannerPara);

    /**
     * 首页列表
     **/
    @POST("lmw-product/lmw")
    Observable<UserMoenyEntity> getUserMoney(@QueryMap Map<String, String> bannerPara);

    /**
     * 现金券
     **/
    @POST("lmw-product/lmw")
    Observable<CashCouponsEntity> getCashMatch(@QueryMap Map<String, String> bannerPara);

    /**
     * 购买标的
     **/
    @POST("lmw-product/lmw")
    Observable<PurchaseEntity> getPurchase(@QueryMap Map<String, String> bannerPara);

    /**
     * 购买标的
     **/
    @POST("lmw-product/lmw")
    Observable<DebtBuyEntity> getDebtBuy(@QueryMap Map<String, String> bannerPara);

//    /**
//     * 理财产品子列表
//     **/
//    @POST("lmw-product/lmw")
//    Observable<ProductItemEntity> getBorrowProduct(@QueryMap Map<String, String> bannerPara);

    /**
     * 充值获取绑定银行信息
     **/
    @POST("lmw-product/lmw")
    Observable<UserBankEntity> getUserBankCardInfo(@QueryMap Map<String, String> bannerPara);

    /**
     * 充值获取绑定银行信息
     **/
    @POST("lmw-product/lmw")
    Observable<OderEntity> fillGetOder(@QueryMap Map<String, String> bannerPara);

    /**
     * 充值支付
     **/
    @POST("lmw-product/lmw")
    Observable<FillEntity> fillPay(@QueryMap Map<String, String> bannerPara);

//    /**
//     * 获取红包
//     **/
//    @POST("lmw-product/lmw")
//    Observable<BaseResponse> getRed(@QueryMap Map<String, String> bannerPara);

    /**
     * 账户明细
     **/
    @POST("lmw-product/lmw")
    Observable<AccountEntity> getAccount(@QueryMap Map<String, String> bannerPara);

    /**
     * 提现信息
     **/
    @POST("lmw-product/lmw")
    Observable<CreditInfo> getCreditInfo(@QueryMap Map<String, String> bannerPara);

    /**
     * 提现信息
     **/
    @POST("lmw-product/lmw")
    Observable<CreditResult> credit(@QueryMap Map<String, String> bannerPara);

    /**
     * 总资产
     **/
    @POST("lmw-product/lmw")
    Observable<TotalAssetsEntity> getTotalAssets(@QueryMap Map<String, String> bannerPara);

    /**
     * 更多
     **/
    @POST("lmw-product/lmw")
    Observable<MoreEntity> getMore(@QueryMap Map<String, String> bannerPara);

    /**
     * 登出
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> logout(@QueryMap Map<String, String> bannerPara);

    /**
     * 获取全局配置信息
     **/
    @POST("lmw-product/lmw")
    Observable<CommonEntity> getCommonInfo(@QueryMap Map<String, String> bannerPara);

    /**
     * 消息列表
     **/
    @POST("lmw-product/lmw")
    Observable<NoticeEntity> getNotice(@QueryMap Map<String, String> bannerPara);

    /**
     * 修改消息状态
     **/
    @POST("lmw-product/lmw")
    @FormUrlEncoded
    // 添加该注释 否则无法使用@FieldMap 进行表单提交
    Observable<BaseResponse> getMessage(@FieldMap Map<String, String> options);

//    /**
//     * 好友列表
//     **/
//    @POST("lmw-product/lmw")
//    Observable<FriendListEntity> getFriendList(@QueryMap Map<String, String> options);

    /**
     * 预回款列表统计
     **/
    @GET("lmw-product/lmw")
    Observable<TransferCountEntity> getTransferCount(@QueryMap Map<String, String> options);

    /**
     * 预回款列表
     **/
    @GET("lmw-product/lmw")
    Observable<TransferEntity> getTransfer(@QueryMap Map<String, String> options);

    /**
     * 我的投资
     **/
    @POST("lmw-product/lmw")
    Observable<InvestmentTransferEntity> getInvestmentTransfer(@QueryMap Map<String, String> options);

    /**
     * 我的投资
     **/
    @POST("lmw-product/lmw")
    Observable<InvestmentCountEntity> getInvestmentCount(@QueryMap Map<String, String> options);

    /**
     * 意见反馈
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> getSuggestion(@QueryMap Map<String, String> options);

    /**
     * 转让债权初始化
     **/
    @POST("lmw-product/lmw")
    Observable<PurchaseDebtEntity> getPurchaseDebt(@QueryMap Map<String, String> options);

    /**
     * 转让债权
     **/
    @POST("lmw-product/lmw")
    Observable<ConfirmTransferEntity> getDebtAdd(@QueryMap Map<String, String> options);

//    /**
//     * 收益汇总
//     **/
//    @POST("lmw-product/lmw")
//    Observable<FriendMoneyEntity> getFriendMoney(@QueryMap Map<String, String> options);

    /**
     * 校验token
     **/
    @POST("lmw-product/lmw")
    Observable<TokenLoginEntity> checkToken(@QueryMap Map<String, String> options);

//    /**
//     * 获取分享内容
//     **/
//    @POST("lmw-product/lmw")
//    Observable<ShareEntity> getShareContent(@QueryMap Map<String, String> options);

    /**
     * 校验身份证
     **/
    @GET("lmw-product/lmw")
    Observable<BaseResponse> getIdCard(@QueryMap Map<String, String> options);

    /**
     * 获取APP版本信息
     **/
    @GET("lmw-product/lmw")
    Observable<AppEntity> getVersionInfo(@QueryMap Map<String, String> options);

    /**
     * 获取公告或者活动信息
     **/
    @GET("lmw-product/lmw")
    Observable<ActivityOrAnoucemnetEntity> getAnouncementOrActivityPopinfo(@QueryMap Map<String, String> options);

//    /**
//     * 获取理财子列表头部图片
//     **/
//    @GET("lmw-product/lmw")
//    Observable<CateExtend> getHeadImage(@QueryMap Map<String, String> options);

    /**
     * 预期收益
     *
     * @param options
     * @return
     */
    @POST("lmw-product/lmw")
    Observable<ExpectedReturn> getExpectedReturn(@QueryMap Map<String, String> options);

//    /**
//     * 获取现金券
//     **/
//    @POST("lmw-product/lmw")
//    Observable<CashCouponEntity> getcashCouponList(@QueryMap Map<String, String> options);

//    /**
//     * 获取理财子列表在售数量
//     *
//     * @param options
//     * @return
//     */
//    @GET("lmw-product/lmw")
//    Observable<CategoryEntity> getHeadCategory(@QueryMap Map<String, String> options);

//    /**
//     * 卡券子项的数量
//     **/
//    @POST("lmw-product/lmw")
//    Observable<DataCouponCount> getCouponCount(@QueryMap Map<String, String> params);

    /**
     * app还款计划V3
     **/
    @POST("lmw-product/lmw")
    Observable<BackPlanEntity> getBackPlan(@QueryMap Map<String, String> params);

    /**
     * 续投设置初始化V3
     **/
    @POST("lmw-product/lmw")
    Observable<ContinueSetEntity> getContinueInitialization(@QueryMap Map<String, String> params);

    /**
     * 续投设置v3
     **/
    @POST("lmw-product/lmw")
    Observable<BaseResponse> getContinueInvestment(@QueryMap Map<String, String> params);

//    /**
//     * 待结奖励v3
//     **/
//    @POST("lmw-product/lmw")
//    Observable<TranferRewardEntity> getTranferReward(@QueryMap Map<String, String> params);

    /**
     * 查询卡券校验V3
     **/
    @POST("lmw-product/lmw")
    Observable<MatchVerifyEntity> getMatchVerify(@QueryMap Map<String, String> params);

//    /**
//     * 卡券使用说明
//     **/
//    @POST("lmw-product/lmw")
//    Observable<DataCouponInstructe> getCouponInstruction(@QueryMap Map<String, String> params);


    /**
     * post请求
     *
     * @param params
     * @return
     */
    @POST("lmw-product/lmw")
    @FormUrlEncoded
    Observable<JsonObject> post(@FieldMap Map<String, String> params);


    /**
     * get请求
     *
     * @param params
     * @return
     */
    @GET("lmw-product/lmw")
    Observable<JsonObject> get(@QueryMap Map<String, String> params);

    /**
     * get请求 活动请求专用
     *
     * @param params
     * @return
     */
    @GET("lmw-activity/activity")
    Observable<JsonObject> getActivity(@QueryMap Map<String, String> params);

}
