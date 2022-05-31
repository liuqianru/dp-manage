/*
 * 项目名称:platform-plus
 * 类名称:MD5Utils.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/7 11:49            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * MD5Utils
 *
 * @author liuqianru
 * @date 2020/8/7 11:49
 */
public class MD5Utils {
    /// <summary>
    /// 生成接口验签sign值
    /// </summary>
    /// <param name="dictionary">不包括 sign 的所有参数键值对集合</param>
    /// <returns></returns>
    public static String getMD5Sign(Map<String, String> map)
    {
        String appSecret = "oasd4igv9afu6exquz12s";//此值为私有不放入参数
        StringBuilder builder = new StringBuilder();
        for (String key : map.keySet()) {
            String value = map.get(key).toString();//
            System.out.println("key:" + key + " vlaue:" + value);
            builder.append(key+value);
        }
        String waitsignstr = builder.toString()+ appSecret;
        byte[] buffer = null;
        try {
            buffer = MessageDigest.getInstance("md5").digest(waitsignstr.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder(0x20);
        if (buffer == null || buffer.length <= 0) {
            return null;
        }
        for (int i = 0; i < buffer.length; i++) {
            int v = buffer[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
}
