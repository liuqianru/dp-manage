/*
 * 项目名称:platform-plus
 * 类名称:QkjCommonController.java
 * 包名称:com.platform.modules.qkjCommon
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022/4/27 17:02            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjCommon;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * QkjCommonController
 *
 * @author liuqianru
 * @date 2022/4/27 17:02
 */
public class QkjCommonUtil {
    public static String getShortAddress(String appletsurl, String appletsparam) throws IOException {
        String msg = "";
        String tmpUrl =  Vars.APPLETS_URL_GET + "?path=" + URLEncoder.encode(appletsurl, "UTF-8") + "&query=" + URLEncoder.encode(appletsparam, "UTF-8");
        System.out.println("获取小程序URL检索条件：" + tmpUrl);
        Map map = new HashMap<>();
        map.clear();
        map.put("parameter", tmpUrl);
        map.put("paramtype", "url");
        String resultPost = HttpClient.sendPost(Vars.APPLETS_SHORTLINK_URL, JsonHelper.toJsonString(map));
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("0".equals(resultObject.get("code").toString())) {  //调用成功
            msg = resultObject.get("redirecturl").toString();
        } else {
            System.out.println("获取小程序URL失败：" + resultObject.get("msg").toString());
        }
        return msg;
    }
}
