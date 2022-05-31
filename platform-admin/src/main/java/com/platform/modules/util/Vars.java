package com.platform.modules.util;

public class Vars {

	//public static String TO_USER = "01451537149439";
	public static String TO_USER = "01672211281464";
	public static String TO_PARTY = "";
	public static String AGENT_ID = "206338972";
	public static String SENDER = "01451537149439";
	public static String CID = "";//cid需要通过jsapi获取，具体详情请查看开放平台文档--->客户端文档--->会话

	public static String APP_KEY = "open100010fb1n94ev9";

	//AES加密用常量start
	public static String ENCODE = "UTF-8";
	// 密钥
	public static String AESKEY = "2139226343519743";
	//向量
	public static String VECTOR = "4370627107694550";
	// 加密算法
	public static String ALGORITHM = "AES/CBC/NoPadding";
	//PWD
	public static String SECRETKEY = "5E2#BD40&FAE^7";
	//AES加密用常量end

	// SCRM新接口
	public static String APIURL = "http://api.dc.crm.qkj.com.cn/";
	// SCRM旧接口
//	public static String APIURL = "http://api.scrm.qkj.com.cn/";
	// SCRM测试接口
//	public static String APIURL = "http://123.56.178.176:8086/";
	// 小程序接口
	public static String MALLAPIURL = "http://api.mall.qkj.com.cn/";

	//数据清洗url start
	public static String MEMBER_ADD_URL = APIURL + "api/Member/AddMember";
	public static String MEMBER_IMPORT_URL = APIURL + "api/Member/BatchImportMember";
	public static String MEMBER_UPDATE_URL = APIURL + "api/Member/UpdateMember?asyncToElastic=false";
	public static String MEMBER_DELETE_URL = APIURL + "api/Member/DeleteMemberList";
	public static String MEMBER_IMPORTINFO_URL = APIURL + "api/Member/GetMemberImportInfo";
	public static String MEMBER_ADDUSER_URL = APIURL + "api/Member/AddCollaborativeOrgUser";
	//数据清洗url end

	//会员检索url start
	public static String MEMBER_GETLIST_URL = APIURL + "api/Member/GetMemberList";
	public static String MEMBER_GETINFO_URL = APIURL + "api/Member/GetMemberEntity";
	public static String MEMBER_GETLEVEL_URL = APIURL + "api/MemberLevel/GetMemberLevel";
	public static String MEMBER_SETLEVEL_URL = APIURL + "api/MemberLevel/SetMemberLevel";
	public static String MEMBER_GETLEVEL_FROM_SCAN = APIURL + "api/MemberLevel/ReceiveMemberSendLevel";
	public static String MEMBER_VALIDCHECK_FROM_SCAN = APIURL + "api/MemberLevel/ValidateMemberReceiveLevel";
	public static String MEMBER_ADDPERM_URL = APIURL + "api/Member/IsAddPermission";
	public static String MEMBER_GETLIST_MOBILE_URL = APIURL + "api/Member/CheckMobiles";
	//会员检索url end

	// 群发积分
	public static String MEMBER_INTEGRAL_SEND_URL = APIURL + "api/Integral/SetBatchMemberIntegral";
	// 根据unionid获取积分
	public static String MEMBER_INTEGRAL_GEYBYUNIONID_URL = APIURL + "api/Integral/GetUserIntegralForUnionId";
	// 推送积分规则
	public static String INTEGRAL_RULE_PUSH_URL = "https://api.mall.qkj.com.cn/MobileApi/Coupon/SetIntegralRule";
	// 获取公众号
	public static String APPID_GETLIST_URL = "http://scrm-wxcb.ym.qkj.com.cn/WeiXinPublish/GetPublishList";
	//群发图文消息接口
	public static String MESSAGE_SEND = "http://scrm-wxcb.ym.qkj.com.cn/WeiXinMsg/MassMultiNewsMessage";
	// 取消定时发送图文
	public static String MESSAGE_CANCEL_SEND = "http://scrm-wxcb.ym.qkj.com.cn/WeiXinMsg/CancelMassTask";
	// 微信url接口
	public static String APPLETS_URL_GET = "https://scrm-wxcb.ym.qkj.com.cn/Share/ToMiniProgram";
	// 给管理员打微信标签
	public static String APPID_GETLABEL_URL = "https://scrm-wxcb.ym.qkj.com.cn/WeiXinTag/AddTagToUser";
	// 获取短信小程序短链接
	public static String APPLETS_SHORTLINK_URL = "http://api.mall.qkj.com.cn/mobileapi/common/InsertParam";
	//优惠券查询
	public static String MEMBER_CPON_LIST_URl = "http://api.mall.qkj.com.cn/MobileAPI/Coupon/GetCouponList";
	// 福利查询
	public static String MEMBER_WELFARE_LIST_URl = "http://api.mall.qkj.com.cn/mobileapi/Promotion/GetWelfareList";
	//优惠券发送
	public static String MEMBER_CPON_SEND_URl = APIURL + "api/Coupon/SendMemberCoupon";

	/*会员画像start*/
	//性别统计
	public static String MEMBER_PORTRAIT_SEX_URl = APIURL + "api/Report/GetMemberSexReport";
	//年龄统计
	public static String MEMBER_PORTRAIT_AGE_URl = APIURL + "api/Report/GetMemberAgeReport";
	//地区统计
	public static String MEMBER_PORTRAIT_AREA_URl = APIURL + "api/Report/GetMemberCityReport";
	// 高频统计-按渠道
	public static String MEMBER_PORTRAIT_HF_CHANNEL_URl = APIURL + "api/Report/GetMemberChannelRateReport";
	// 高频统计-按地区
	public static String MEMBER_PORTRAIT_HF_CITY_URl = APIURL + "api/Report/GetMemberCityRateReport";
	// 高价值统计-按渠道
	public static String MEMBER_PORTRAIT_HV_CHANNEL_URl = APIURL + "api/Report/GetMemberChannelAmountReport";
	// 高价值统计-按地区
	public static String MEMBER_PORTRAIT_HV_CITY_URl = APIURL + "api/Report/GetMemberCityAmountReport";
	// 价值区间统计
	public static String MEMBER_PORTRAIT_VALUERANGE_URl = APIURL + "api/Report/GetMemberAmountRangeReport";
	// 新增趋势统计
	public static String MEMBER_PORTRAIT_ADDTREND_URl = APIURL + "api/Report/GetMemberTrendReport";
	// 销量大区
	public static String MEMBER_PORTRAIT_SALE_URl = APIURL + "api/Report/GetMemberAreaSaleReport";
	//销量办事处
	public static String MEMBER_PORTRAIT_SALELEAVEURl = APIURL + "api/Report/GetMemberOrgSaleReport";
	// 客户大区统计
	public static String MEMBER_PORTRAIT_AREAURl = APIURL + "api/Report/GetMemberArea";
	// 客户统计大区-等级
	public static String MEMBER_PORTRAIT_AREALEVELURl = APIURL + "api/Report/GetMemberLevelAndTargetCount";
	// 同比环比
	public static String MEMBER_PORTRAIT_RATIOURl = APIURL + "api/Report/GetMemberCount";
	// 客户所属部门统计
	public static String MEMBER_PORTRAIT_GROUPORGURl = APIURL + "api/Report/GetMemberVirtualDep";
	// 客户统计部门-等级
	public static String MEMBER_PORTRAIT_GROUPORGLEVELURl = APIURL + "api/Report/GetMemberLevelAndAreaCount";
	// 客户地图
	public static String MEMBER_PORTRAIT_MAPURl = APIURL + "api/Report/GetMemberCity";
	// 按区域汇总
	public static String MEMBER_PORTRAIT_AREAONE = APIURL + "api/Report/GetMemberCountByAreaOne";
	// 按等级类型汇总
	public static String MEMBER_PORTRAIT_INDENTITYGROUP = APIURL + "api/Report/GetMemberCountByIdentityGroup";
	// 按客户所属汇总
	public static String MEMBER_PORTRAIT_GROUPORG = APIURL + "api/Report/GetMemberCountByGroupOrg";
	/*会员画像end*/

	// 小程序首页新闻
	public static String NEWS_LIST_URl = APIURL + "api/News/GetNewsList";  // 废弃
	public static String NEWS_INFO_URl = APIURL + "api/News/GetNewsInfo";  // 废弃
	public static String PRODUCT_LIST_URL = "https://api.mall.qkj.com.cn/MobileAPI/product/GetIntegralProductList";
	// 分享得积分
	public static String CONTENT_SHARE_URL = APIURL + "api/Integral/SetCRMMemberIntegral";
	public static String CONTENT_INTEGRALBATCH_URL = APIURL + "api/Integral/SetCRMMemberIntegralBatch";
	/**
	 * 订单
	 */
	//订单添加
	public static String MEMBER_ORDER_ORDER_ADD = APIURL + "api/Order/SubmitOrder";
	//订单添加
	public static String MEMBER_ORDER_ORDER_MDYSTATUS = APIURL + "api/Order/SetOrderStatus";
	//订单列表
	public static String MEMBER_ORDER_ORDER_LIST = APIURL + "api/Order/GetOrderList";
	//订单详情
	public static String MEMBER_ORDER_ORDER_LISTDETILE = APIURL + "api/Order/GetOrderDetail";
	//订单删除
	public static String MEMBER_ORDER_ORDER_DELTET = APIURL + "api/Order/AbandonedOrder";


	/**
	 * 小程序接口
	 */
	// 根据unionid获取积分
	public static String MALL_INTEGRAL_SELECTBYUNIONID = MALLAPIURL + "mobileapi/Integral/GetIntegralUnionId";
	// 批量添加积分
	public static String MALL_INTEGRAL_BATCHSEAL = MALLAPIURL + "mobileapi/Integral/BatchDealIntegral";
}
