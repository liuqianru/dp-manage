/*
 * 项目名称:platform-plus
 * 类名称:MyTest2.java
 * 包名称:com.platform
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/9/23 14:03            liuqianru    初版做成
 *
 */
package com.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import com.platform.modules.util.RabbitMQUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * MyTest2
 *
 * @author liuqianru
 * @date 2020/9/23 14:03
 */
public class MyTest2 {
    @Test
    public static void main(String[] args)
    {
        String resultPost = "{\n" +
                "  \"UserList\": [\n" +
                "    {\n" +
                "      \"IsUnSubscribe\": 0,\n" +
                "      \"UnSubscribeTime\": null,\n" +
                "      \"ServiceName\": \"中酒红酒汇\",\n" +
                "      \"PhoneNo\": null,\n" +
                "      \"OpenId\": \"oYLgzwDokAP1EGQ_qZ0lwD18UrJY\",\n" +
                "      \"NickName\": \"james\",\n" +
                "      \"Sex\": 1,\n" +
                "      \"Language\": \"zh_CN\",\n" +
                "      \"City\": \"海淀\",\n" +
                "      \"Province\": \"北京\",\n" +
                "      \"Country\": \"中国\",\n" +
                "      \"HeadImgurl\": \"http://thirdwx.qlogo.cn/mmopen/ylRhrSjQb8jo4RWQozAaRO1KOUQYFNshWd0Khw37iaw83nt1AzOrAKyJUicQo1EhtVs1I3g0GCvpibJ2XAaDb3z6g/132\",\n" +
                "      \"SubscribeTime\": \"2020-06-08 14:07:12\",\n" +
                "      \"UnionId\": \"ooG8ytyXdv5Gs7ScDIAI0HuhcQmw\",\n" +
                "      \"UpdateTime\": \"2020-06-08 14:07:12\",\n" +
                "      \"AppId\": \"wxe1f07c6642296d2f\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"IsUnSubscribe\": 1,\n" +
                "      \"UnSubscribeTime\": \"2020-06-30 17:54:21\",\n" +
                "      \"ServiceName\": \"中酒红酒汇\",\n" +
                "      \"PhoneNo\": null,\n" +
                "      \"OpenId\": \"oYLgzwMbubFiEaoEeeyJc0YivJPA\",\n" +
                "      \"NickName\": \"匿名用户(*yJc0YivJPA)\",\n" +
                "      \"Sex\": 0,\n" +
                "      \"Language\": null,\n" +
                "      \"City\": null,\n" +
                "      \"Province\": null,\n" +
                "      \"Country\": null,\n" +
                "      \"HeadImgurl\": null,\n" +
                "      \"SubscribeTime\": \"2020-07-01 13:35:02\",\n" +
                "      \"UnionId\": null,\n" +
                "      \"UpdateTime\": \"2020-06-30 17:54:21\",\n" +
                "      \"AppId\": \"wxe1f07c6642296d2f\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"IsUnSubscribe\": 1,\n" +
                "      \"UnSubscribeTime\": \"2020-07-21 17:03:49\",\n" +
                "      \"ServiceName\": \"中酒红酒汇\",\n" +
                "      \"PhoneNo\": null,\n" +
                "      \"OpenId\": \"oYLgzwAu04pgk-_ANC8poM1bmH24\",\n" +
                "      \"NickName\": \"匿名用户(*8poM1bmH24)\",\n" +
                "      \"Sex\": 0,\n" +
                "      \"Language\": null,\n" +
                "      \"City\": null,\n" +
                "      \"Province\": null,\n" +
                "      \"Country\": null,\n" +
                "      \"HeadImgurl\": null,\n" +
                "      \"SubscribeTime\": \"1970-01-02 00:00:00\",\n" +
                "      \"UnionId\": null,\n" +
                "      \"UpdateTime\": \"2020-07-21 17:03:49\",\n" +
                "      \"AppId\": \"wxe1f07c6642296d2f\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"IsUnSubscribe\": 1,\n" +
                "      \"UnSubscribeTime\": \"2020-08-21 11:12:30\",\n" +
                "      \"ServiceName\": \"中酒红酒汇\",\n" +
                "      \"PhoneNo\": \"18301210054\",\n" +
                "      \"OpenId\": \"oYLgzwNcDS1S2oCpTI9TV3oN04qY\",\n" +
                "      \"NickName\": \"轻轻轻轻轻尘\",\n" +
                "      \"Sex\": 1,\n" +
                "      \"Language\": \"zh_CN\",\n" +
                "      \"City\": \"朝阳\",\n" +
                "      \"Province\": \"北京\",\n" +
                "      \"Country\": \"中国\",\n" +
                "      \"HeadImgurl\": \"http://thirdwx.qlogo.cn/mmopen/1jyv0E2ks7gzyAEgZJkEf5iaCygXCib2GXOrmwFZ6SoiaAyMtrnlrAvKnAo58Dx7a5qHEczqOPVXhIRQGokmJPiaGlicAd387kRXT/132\",\n" +
                "      \"SubscribeTime\": \"2020-05-15 14:44:01\",\n" +
                "      \"UnionId\": \"ooG8yt-bxcvvBdePRohtSFtHx1wA\",\n" +
                "      \"UpdateTime\": \"2020-08-21 11:12:30\",\n" +
                "      \"AppId\": \"wxe1f07c6642296d2f\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"IsUnSubscribe\": 0,\n" +
                "      \"UnSubscribeTime\": null,\n" +
                "      \"ServiceName\": \"中酒红酒汇\",\n" +
                "      \"PhoneNo\": null,\n" +
                "      \"OpenId\": \"oYLgzwKRlLIkgDwiAEIBY-ZAViek\",\n" +
                "      \"NickName\": \"匿名用户(*IBY-ZAViek)\",\n" +
                "      \"Sex\": 0,\n" +
                "      \"Language\": null,\n" +
                "      \"City\": null,\n" +
                "      \"Province\": null,\n" +
                "      \"Country\": null,\n" +
                "      \"HeadImgurl\": null,\n" +
                "      \"SubscribeTime\": \"1970-01-02 00:00:00\",\n" +
                "      \"UnionId\": null,\n" +
                "      \"UpdateTime\": \"2020-08-28 14:20:03\",\n" +
                "      \"AppId\": \"wxe1f07c6642296d2f\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"result\": 0,\n" +
                "  \"descr\": \"\"\n" +
                "}";
        JSONObject fanObject = JSON.parseObject(resultPost);
        String jsonStr = JSON.toJSONString(fanObject.get("UserList"), SerializerFeature.WriteMapNullValue);  //序列化对象，使null或空的字段不被过滤掉
        System.out.print(jsonStr);
    }
}
