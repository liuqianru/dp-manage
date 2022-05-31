package cn.emay;

import java.util.HashMap;
import java.util.Map;

import cn.emay.eucp.inter.framework.dto.CustomSmsIdAndMobile;
import cn.emay.eucp.inter.framework.dto.CustomSmsIdAndMobileAndContent;
import cn.emay.eucp.inter.framework.dto.PersonalityParams;
import cn.emay.eucp.inter.framework.dto.TemplateSmsIdAndMobile;
import cn.emay.eucp.inter.http.v1.dto.request.BalanceRequest;
import cn.emay.eucp.inter.http.v1.dto.request.MoRequest;
import cn.emay.eucp.inter.http.v1.dto.request.ReportRequest;
import cn.emay.eucp.inter.http.v1.dto.request.ShortLinkRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsBatchOnlyRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsBatchRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsPersonalityAllRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsPersonalityRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsShortLinkBatchRequest;
import cn.emay.eucp.inter.http.v1.dto.request.SmsSingleRequest;
import cn.emay.eucp.inter.http.v1.dto.request.TemplateSmsSendRequest;
import cn.emay.eucp.inter.http.v1.dto.response.BalanceResponse;
import cn.emay.eucp.inter.http.v1.dto.response.MoResponse;
import cn.emay.eucp.inter.http.v1.dto.response.ReportResponse;
import cn.emay.eucp.inter.http.v1.dto.response.ShortLinkReportResponse;
import cn.emay.eucp.inter.http.v1.dto.response.SmsResponse;
import cn.emay.util.AES;
import cn.emay.util.GZIPUtils;
import cn.emay.util.JsonHelper;
import cn.emay.util.http.HttpClient;
import cn.emay.util.http.HttpRequest;
import cn.emay.util.http.HttpRequestBytes;
import cn.emay.util.http.HttpRequestParams;
import cn.emay.util.http.HttpResponseBytes;
import cn.emay.util.http.HttpResponseBytesPraser;
import cn.emay.util.http.HttpResultCode;
import cn.emay.util.http.HttpsRequestBytes;

@SuppressWarnings("unused")
public class Example {

	public static void main(String[] args) {
		// appId
		String appId = "EUCP-EMY-SMS1-BY170";// 请联系销售，或者在页面中 获取
		// 密钥
		String secretKey = "8A8214862B33F0A3";// 请联系销售，或者在页面中 获取
		// 接口地址
		String host = "http://emay.com";// 请联系销售获取
		host = "http://127.0.0.1:8999";
		// 加密算法
		String algorithm = "AES/ECB/PKCS5Padding";
		// 编码
		String encode = "UTF-8";
		// 是否压缩
		boolean isGizp = true;

		// // 获取余额
		// getBalance(appId, secretKey, host, algorithm, isGizp, encode);
		// // 获取状态报告
		// getReport(appId, secretKey, host, algorithm, isGizp, encode);
		// // 获取上行
		// getMo(appId, secretKey, host, algorithm, isGizp, encode);
		// // 发送单条短信
		// setSingleSms(appId, secretKey, host, algorithm,
		// "【某某公司】你好今天天气不错，挺风和日丽的", null, null, "12100000000", isGizp,
		// encode);// 如果通道已绑定签名，可以去掉【某某公司】
		// // // 发送批次短信[有自定义SMSID]
		// setBatchSms(appId, secretKey, host, algorithm,
		// "【某某公司】你好今天天气不错，挺风和日丽的", null, new CustomSmsIdAndMobile[] { new
		// CustomSmsIdAndMobile("1", "12100000000"),
		// new CustomSmsIdAndMobile("2", "12100000000") }, isGizp, encode);//
		// 如果通道已绑定签名，可以去掉【某某公司】
		// // // 发送批次短信[无自定义SMSID]
		// setBatchOnlySms(appId, secretKey, host, algorithm,
		// "【某某公司】你好今天天气不错，挺风和日丽的", null, new String[] { "12100000000",
		// "12100000001" }, isGizp, encode);// 如果通道已绑定签名，可以去掉【某某公司】
		// // // 发送个性短信
		// setPersonalitySms(appId, secretKey, host, algorithm, null, new
		// CustomSmsIdAndMobileAndContent[] { new
		// CustomSmsIdAndMobileAndContent("1", "12100000000",
		// "【某某公司】你好今天天气不错，挺风和日丽的"),
		// new CustomSmsIdAndMobileAndContent("2", "12100000001",
		// "【某某公司】你好今天天气不错，挺风和日丽的啊") }, isGizp, encode);// 如果通道已绑定签名，可以去掉【某某公司】
		// // // 发送全个性短信
		// setPersonalityAllSms(appId, secretKey, host, algorithm, new
		// PersonalityParams[] { new PersonalityParams("101", "12100000000",
		// "【某某公司】天气不错0", "01", null),
		// new PersonalityParams("102", "12100000001", "【某某公司】天气不错1", "02",
		// null) }, isGizp, encode);// 如果通道已绑定签名，可以去掉【某某公司】

		// 模板短信发送
		String templateId = "22222";
		String extendCode = "111";
		String timerTime = "2020-06-27 09:10:10";
		sendTemplateNormalSms(appId, secretKey, host, algorithm, templateId, extendCode, new TemplateSmsIdAndMobile[] { new TemplateSmsIdAndMobile("12100000000", "1"),
				new TemplateSmsIdAndMobile("12100000000", "2") }, isGizp, encode, timerTime);
		//
		// TemplateSmsIdAndMobile[] customSmsIdAndMobiles = new
		// TemplateSmsIdAndMobile[2];
		// Map<String, String> content1 = new HashMap<String, String>();
		// content1.put("name", "王先生");
		// content1.put("money", "10983.4");
		// customSmsIdAndMobiles[0] = new TemplateSmsIdAndMobile("15903201025",
		// System.currentTimeMillis() + "", content1);
		// Map<String, String> content2 = new HashMap<String, String>();
		// content2.put("name", "李先生");
		// content2.put("money", "26985.4");
		// customSmsIdAndMobiles[1] = new TemplateSmsIdAndMobile("15903201025",
		// System.currentTimeMillis() + "", content2);
		// sendTemplateVariableSms(appId, secretKey, host, algorithm,
		// templateId, extendCode, customSmsIdAndMobiles, isGizp, encode,
		// timerTime);

		// String content = "北京欢迎您，旅游详情请点击{#短网址#}";
		// String extendCode = "111";
		// CustomSmsIdAndMobile[] customSmsIdAndMobiles = new CustomSmsIdAndMobile[] {
		// new CustomSmsIdAndMobile("1", "12100000000"), new CustomSmsIdAndMobile("2", "12100000000") };
		// String shortLinkRule = "1";// 1普通 2精确
		// String longLingkUrl = "http://wwww.baidu.com";
		// String timerTime = "2020-06-27 09:10:10";
		// setBatchShortLinkSms(appId, secretKey, host, algorithm, content, extendCode, customSmsIdAndMobiles,
		// shortLinkRule, longLingkUrl, isGizp, encode, timerTime);
		//
		// // 获取短链点击详情
		// getShortLinkClickDetail(appId, secretKey, host, algorithm, isGizp, encode);

		// 模板创建
		Map<String, String> templateMap = new HashMap<String, String>();
		templateMap.put("templateContent", "ssssssssss${参数1}dddddddddddddd${参数2}dddddddddd");
		templateMap.put("requestTime", String.valueOf(System.currentTimeMillis()));
		templateMap.put("requestValidPeriod", "30");
		createTemplate(appId, secretKey, host, algorithm, templateMap, true, "utf-8");

		// 模板删除
		Map<String, String> deleteTemplateMap = new HashMap<String, String>();
		deleteTemplateMap.put("templateId", "12345678");
		deleteTemplateMap.put("requestTime", String.valueOf(System.currentTimeMillis()));
		deleteTemplateMap.put("requestValidPeriod", "30");
		deleteTemplate(appId, secretKey, host, algorithm, deleteTemplateMap, true, "utf-8");
		// 模板查询
		Map<String, String> queryTemplateMap = new HashMap<String, String>();
		queryTemplateMap.put("templateId", "12345678");
		queryTemplateMap.put("requestTime", String.valueOf(System.currentTimeMillis()));
		queryTemplateMap.put("requestValidPeriod", "30");
		queryTmplate(appId, secretKey, host, algorithm, queryTemplateMap, true, "utf-8");

	}

	/**
	 * 获取余额
	 * 
	 * @param isGzip
	 *            是否压缩
	 */

	private static void getBalance(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
		System.out.println("=============begin getBalance==================");
		BalanceRequest pamars = new BalanceRequest();
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/getBalance", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			BalanceResponse response = JsonHelper.fromJson(BalanceResponse.class, result.getResult());
			if (response != null) {
				System.out.println("result data : " + response.getBalance());
			}
		}
		System.out.println("=============end getBalance==================");
	}

	/**
	 * 获取状态报告
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	public static ResultModel getReport(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
		System.out.println("=============begin getReport==================");
		ReportRequest pamars = new ReportRequest();
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/getReport", isGzip, encode);
		System.out.println("result code :" + result.getCode());
//		if ("SUCCESS".equals(result.getCode())) {  // liuqianru del 2021-06-02
//			ReportResponse[] response = JsonHelper.fromJson(ReportResponse[].class, result.getResult());
//			if (response != null) {
//				for (ReportResponse d : response) {
//					System.out.println("result data : " + d.getExtendedCode() + "," + d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() + "," + d.getState() + "," + d.getDesc() + ","
//							+ d.getSubmitTime() + "," + d.getReceiveTime());
//				}
//			}
//		}
		System.out.println("=============end getReport==================");
		return result;
	}

	/**
	 * 获取上行
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void getMo(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
		System.out.println("=============begin getMo==================");
		MoRequest pamars = new MoRequest();
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/getMo", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			MoResponse[] response = JsonHelper.fromJson(MoResponse[].class, result.getResult());
			if (response != null) {
				for (MoResponse d : response) {
					System.out.println("result data:" + d.getContent() + "," + d.getExtendedCode() + "," + d.getMobile() + "," + d.getMoTime());
				}
			}
		}
		System.out.println("=============end getMo==================");
	}

	/**
	 * 发送单条短信
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	public static ResultModel setSingleSms(String appId, String secretKey, String host, String algorithm, String content, String customSmsId, String extendCode, String mobile, boolean isGzip, String encode) {
		System.out.println("=============begin setSingleSms==================");
		SmsSingleRequest pamars = new SmsSingleRequest();
		pamars.setContent(content);
		pamars.setCustomSmsId(customSmsId);
		pamars.setExtendedCode(extendCode);
		pamars.setMobile(mobile);
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendSingleSMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			SmsResponse response = JsonHelper.fromJson(SmsResponse.class, result.getResult());
			if (response != null) {
				System.out.println("data : " + response.getMobile() + "," + response.getSmsId() + "," + response.getCustomSmsId());
			}
		}
		System.out.println("=============end setSingleSms==================");
		return result;
	}

	/**
	 * 发送批次短信
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	public static ResultModel setBatchOnlySms(String appId, String secretKey, String host, String algorithm, String content, String extendCode, String[] mobiles, boolean isGzip, String encode) {
		System.out.println("=============begin setBatchOnlySms==================");
		SmsBatchOnlyRequest pamars = new SmsBatchOnlyRequest();
		pamars.setMobiles(mobiles);
		pamars.setExtendedCode(extendCode);
		pamars.setContent(content);
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendBatchOnlySMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
//		if ("SUCCESS".equals(result.getCode())) {  // liuqianru del 2021-06-02
//			SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
//			if (response != null) {
//				for (SmsResponse d : response) {
//					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
//				}
//			}
//		}
		System.out.println("=============end setBatchOnlySms==================");
		return result;
	}

	/**
	 * 发送批次短信
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void setBatchSms(String appId, String secretKey, String host, String algorithm, String content, String extendCode, CustomSmsIdAndMobile[] customSmsIdAndMobiles, boolean isGzip,
			String encode) {
		System.out.println("=============begin setBatchSms==================");
		SmsBatchRequest pamars = new SmsBatchRequest();
		pamars.setSmses(customSmsIdAndMobiles);
		pamars.setExtendedCode(extendCode);
		pamars.setContent(content);
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendBatchSMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
			if (response != null) {
				for (SmsResponse d : response) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end setBatchSms==================");
	}

	/**
	 * 发送个性短信
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void setPersonalitySms(String appId, String secretKey, String host, String algorithm, String extendCode, CustomSmsIdAndMobileAndContent[] customSmsIdAndMobileAndContents,
			boolean isGzip, String encode) {
		System.out.println("=============begin setPersonalitySms==================");
		SmsPersonalityRequest pamars = new SmsPersonalityRequest();
		pamars.setSmses(customSmsIdAndMobileAndContents);
		pamars.setExtendedCode(extendCode);
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendPersonalitySMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
			if (response != null) {
				for (SmsResponse d : response) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end setPersonalitySms==================");
	}

	/**
	 * 发送个性短信
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void setPersonalityAllSms(String appId, String secretKey, String host, String algorithm, PersonalityParams[] customSmsIdAndMobileAndContents, boolean isGzip, String encode) {
		System.out.println("=============begin setPersonalityAllSms==================");
		SmsPersonalityAllRequest pamars = new SmsPersonalityAllRequest();
		pamars.setSmses(customSmsIdAndMobileAndContents);
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendPersonalityAllSMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
			if (response != null) {
				for (SmsResponse d : response) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end setPersonalityAllSms==================");
	}

	/**
	 * 
	 * @Title: sendTemplateNormalSms
	 * @Description: 普通模板短信发送接口
	 * @param @param appId
	 * @param @param secretKey
	 * @param @param host
	 * @param @param algorithm
	 * @param @param templateId
	 * @param @param customSmsIdAndMobiles
	 * @param @param isGizp
	 * @param @param encode
	 * @return void
	 */
	private static void sendTemplateNormalSms(String appId, String secretKey, String host, String algorithm, String templateId, String extendedCode, TemplateSmsIdAndMobile[] customSmsIdAndMobiles,
			boolean isGizp, String encode, String timerTime) {
		System.out.println("=============begin sendTemplateNormalSms==================");
		TemplateSmsSendRequest pamars = new TemplateSmsSendRequest();
		pamars.setSmses(customSmsIdAndMobiles);
		pamars.setTemplateId(templateId);
		pamars.setExtendedCode(extendedCode);
		pamars.setRequestTime(System.currentTimeMillis());
		pamars.setTimerTime(timerTime);
		System.out.println(JsonHelper.toJsonString(pamars));
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendTemplateNormalSMS", isGizp, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
			if (response != null) {
				for (SmsResponse d : response) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end sendTemplateNormalSms==================");

	}

	private static void sendTemplateVariableSms(String appId, String secretKey, String host, String algorithm, String templateId, String extendedCode, TemplateSmsIdAndMobile[] customSmsIdAndMobiles,
			boolean isGizp, String encode, String timerTime) {
		System.out.println("=============begin sendTemplateVariableSms==================");
		TemplateSmsSendRequest pamars = new TemplateSmsSendRequest();
		pamars.setSmses(customSmsIdAndMobiles);
		pamars.setTemplateId(templateId);
		pamars.setExtendedCode(extendedCode);
		pamars.setRequestTime(System.currentTimeMillis());
		pamars.setTimerTime(timerTime);
		System.out.println(JsonHelper.toJsonString(pamars));
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendTemplateVariableSMS", isGizp, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
			if (response != null) {
				for (SmsResponse d : response) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end sendTemplateVariableSms==================");

	}

	/**
	 * 发送短链批次短信
	 * 
	 * @param isGzip
	 *            是否压缩
	 * @param timerTime
	 */
	private static void setBatchShortLinkSms(String appId, String secretKey, String host, String algorithm, String content, String extendCode, CustomSmsIdAndMobile[] customSmsIdAndMobiles,
			String shortLinkRule, String longLingkUrl, boolean isGzip, String encode, String timerTime) {
		System.out.println("=============begin setBatchShortLinkSms==================");
		SmsShortLinkBatchRequest pamars = new SmsShortLinkBatchRequest();
		pamars.setSmses(customSmsIdAndMobiles);
		pamars.setExtendedCode(extendCode);
		pamars.setContent(content);
		pamars.setShortLinkRule(shortLinkRule);
		pamars.setUrl(longLingkUrl);
		pamars.setTimerTime(timerTime);
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/sendShortLinkSMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, result.getResult());
			if (response != null) {
				for (SmsResponse d : response) {
					System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
				}
			}
		}
		System.out.println("=============end setBatchShortLinkSms==================");
	}

	/**
	 * 获取短链点击详情
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void getShortLinkClickDetail(String appId, String secretKey, String host, String algorithm, boolean isGzip, String encode) {
		System.out.println("=============begin getShortLinkClickDetail==================");
		ShortLinkRequest pamars = new ShortLinkRequest();
		ResultModel result = request(appId, secretKey, algorithm, pamars, host + "/inter/getShortLink", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			ShortLinkReportResponse[] response = JsonHelper.fromJson(ShortLinkReportResponse[].class, result.getResult());
			// if (response != null) {
			// for (ReportResponse d : response) {
			// System.out.println("result data : " + d.getExtendedCode() + "," +
			// d.getMobile() + "," + d.getCustomSmsId() + "," + d.getSmsId() +
			// "," + d.getState() + "," + d.getDesc() + ","
			// + d.getSubmitTime() + "," + d.getReceiveTime());
			// }
			// }
		}
		System.out.println("=============end getShortLinkClickDetail==================");
	}

	/**
	 * 模板创建
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void createTemplate(String appId, String secretKey, String host, String algorithm, Map<String, String> templateMap, boolean isGzip, String encode) {
		System.out.println("=============begin createTemplateSMS==================");
		ResultModel result = request(appId, secretKey, algorithm, templateMap, host + "/inter/createTemplateSMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			System.out.println(result.getResult());
		}
		System.out.println("=============end createTemplateSMS==================");
	}

	/**
	 * 模板删除
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void deleteTemplate(String appId, String secretKey, String host, String algorithm, Map<String, String> templateMap, boolean isGzip, String encode) {
		System.out.println("=============begin deleteTemplate==================");
		ResultModel result = request(appId, secretKey, algorithm, templateMap, host + "/inter/deleteTemplateSMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			System.out.println(result.getResult());
		}
		System.out.println("=============end deleteTemplate==================");
	}

	/**
	 * 模板查询
	 * 
	 * @param isGzip
	 *            是否压缩
	 */
	private static void queryTmplate(String appId, String secretKey, String host, String algorithm, Map<String, String> templateMap, boolean isGzip, String encode) {
		System.out.println("=============begin queryemplate==================");
		ResultModel result = request(appId, secretKey, algorithm, templateMap, host + "/inter/queryTemplateSMS", isGzip, encode);
		System.out.println("result code :" + result.getCode());
		if ("SUCCESS".equals(result.getCode())) {
			System.out.println(result.getResult());
		}
		System.out.println("=============end queryemplate==================");
	}

	/**
	 * 公共请求方法
	 */
	public static ResultModel request(String appId, String secretKey, String algorithm, Object content, String url, final boolean isGzip, String encode) {
		Map<String, String> headers = new HashMap<String, String>();
		HttpRequest<byte[]> request = null;
		try {
			headers.put("appId", appId);
			headers.put("encode", encode);
			String requestJson = JsonHelper.toJsonString(content);
			System.out.println("result json: " + requestJson);
			byte[] bytes = requestJson.getBytes(encode);
			System.out.println("request data size : " + bytes.length);
			if (isGzip) {
				headers.put("gzip", "on");
				bytes = GZIPUtils.compress(bytes);
				System.out.println("request data size [com]: " + bytes.length);
			}
			byte[] parambytes = AES.encrypt(bytes, secretKey.getBytes(), algorithm);
			System.out.println("request data size [en] : " + parambytes.length);
			HttpRequestParams<byte[]> params = new HttpRequestParams<byte[]>();
			params.setCharSet("UTF-8");
			params.setMethod("POST");
			params.setHeaders(headers);
			params.setParams(parambytes);
			params.setUrl(url);
			if (url.startsWith("https://")) {
				request = new HttpsRequestBytes(params, null);
			} else {
				request = new HttpRequestBytes(params);
			}
		} catch (Exception e) {
			System.out.println("加密异常");
			e.printStackTrace();
		}
		HttpClient client = new HttpClient();
		String code = null;
		String result = null;
		try {
			HttpResponseBytes res = client.service(request, new HttpResponseBytesPraser());
			if (res == null) {
				System.out.println("请求接口异常");
				return new ResultModel(code, result);
			}
			if (res.getResultCode().equals(HttpResultCode.SUCCESS)) {
				if (res.getHttpCode() == 200) {
					code = res.getHeaders().get("result");
					if (code.equals("SUCCESS")) {
						byte[] data = res.getResult();
						System.out.println("response data size [en and com] : " + data.length);
						data = AES.decrypt(data, secretKey.getBytes(), algorithm);
						if (isGzip) {
							data = GZIPUtils.decompress(data);
						}
						System.out.println("response data size : " + data.length);
						result = new String(data, encode);
						System.out.println("response json: " + result);
					}
				} else {
					System.out.println("请求接口异常,请求码:" + res.getHttpCode());
				}
			} else {
				System.out.println("请求接口网络异常:" + res.getResultCode().getCode());
			}
		} catch (Exception e) {
			System.out.println("解析失败");
			e.printStackTrace();
		}
		ResultModel re = new ResultModel(code, result);
		return re;
	}

}
