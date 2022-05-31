/*
 * 项目名称:platform-plus
 * 类名称:ListToStringUtil.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/12/22 16:46            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ListToStringUtil
 *
 * @author liuqianru
 * @date 2020/12/22 16:46
 */
public class ListToStringUtil {
    /**
     * 拼接sql in后的逗号隔开的字符串用
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        StringBuffer sb = new StringBuffer();
        String resultStr = "";
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                sb.append("'" + list.get(i) + "',");
            }
            resultStr = sb.toString();
            resultStr = "(" + resultStr.substring(0, resultStr.length() - 1) + ")";
        }
        return resultStr;
    }

    /**
     * list类型转分隔符隔开的字符串
     * @param list
     * @param separator 分隔符
     * @return
     */
    public static String listToString(List<String> list, String separator) {
        StringBuffer sb = new StringBuffer();
        String resultStr = "";
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i) + separator);
            }
            resultStr = sb.toString();
            resultStr = resultStr.substring(0, resultStr.length() - separator.length());
        }
        return resultStr;
    }

    /**
     * 数组类型转分隔符隔开的字符串
     * @param attrStr
     * @return
     */
    public static String attrToString(String[] attrStr, String separator) {
        StringBuffer sb = new StringBuffer();
        String resultStr = "";
        if (attrStr.length > 0) {
            for (int i = 0; i < attrStr.length; i++) {
                sb.append(attrStr[i] + separator);
            }
            resultStr = sb.toString();
            resultStr = resultStr.substring(0, resultStr.length() - separator.length());
        }
        return resultStr;
    }

    public static Map<String, String> entityToMap(List<QkjvipMemberMessageUserQueryEntity> userList) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        String userStr = "('')";
        String openidStr = "('')";
        if (userList.size() > 0) {
            for (QkjvipMemberMessageUserQueryEntity user : userList) {
                if (StringUtils.isNotBlank(user.getMemberId())) {
                    sb.append("'" + user.getMemberId() + "',");
                }
                if (StringUtils.isNotBlank(user.getOpenid())) {
                    sb2.append("'" + user.getOpenid() + "',");
                }
            }
            userStr = sb.toString();
            if (!"".equals(userStr)) {
                userStr = "(" + userStr.substring(0, userStr.length() - 1) + ")";
            } else {
                userStr = "('')";
            }
            openidStr = sb2.toString();
            if (!"".equals(openidStr)) {
                openidStr = "(" + openidStr.substring(0, openidStr.length() - 1) + ")";
            } else {
                openidStr = "('')";
            }
        }
        Map map = new HashMap();
        map.put("userStr", userStr);
        map.put("openidStr", openidStr);

        return map;
    }

    public static Map<String, String> entityToMap2(List<MemberEntity> userList) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        String userStr = "('')";
        String openidStr = "('')";
        if (userList.size() > 0) {
            for (MemberEntity user : userList) {
                if (StringUtils.isNotBlank(user.getMemberId())) {
                    sb.append("'" + user.getMemberId() + "',");
                }
                if (StringUtils.isNotBlank(user.getOpenid())) {
                    sb2.append("'" + user.getOpenid() + "',");
                }
            }
            userStr = sb.toString();
            if (!"".equals(userStr)) {
                userStr = "(" + userStr.substring(0, userStr.length() - 1) + ")";
            } else {
                userStr = "('')";
            }
            openidStr = sb2.toString();
            if (!"".equals(openidStr)) {
                openidStr = "(" + openidStr.substring(0, openidStr.length() - 1) + ")";
            } else {
                openidStr = "('')";
            }
        }
        Map map = new HashMap();
        map.put("userStr", userStr);
        map.put("openidStr", openidStr);

        return map;
    }
}
