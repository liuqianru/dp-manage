/*
 * 项目名称:platform-plus
 * 类名称:GeoLntLatUtil.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/9/17 9:57            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import com.alibaba.fastjson.JSON;
import com.platform.common.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * GeoLntLatUtil
 *
 * @author liuqianru
 * @date 2021/9/17 9:57
 */
public class GeoLntLatUtil {
    public static Map<String, BigDecimal> getLatAndLngByAddress(String addr){
        String address = "";
        // 默认西宁
        String lat = "101.74";
        String lng = "36.56";
        try {
            address = java.net.URLEncoder.encode(addr,"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
                +"ak=Ed5GYHTqRm1E5VZgHB6jm7Qz2vckMfQg&output=json&address=%s",address);
        URL myURL = null;
        URLConnection httpsConn = null;
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {

        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    if (data.indexOf("\"lat\":") > -1) {
                        lat = data.substring(data.indexOf("\"lat\":")
                                + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    }
                    if (data.indexOf("\"lng\":") > -1) {
                        lng = data.substring(data.indexOf("\"lng\":")
                                + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                    }
                }
                insr.close();
            }
        } catch (IOException e) {

        }
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("lat", new BigDecimal(lat));
        map.put("lng", new BigDecimal(lng));
        return map;
    }
}
