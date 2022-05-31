/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberFansController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-09 14:02:22        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.controller;

import cn.emay.util.AES;
import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.platform.common.annotation.SysLog;
import com.platform.modules.quartz.entity.QrtzLastUpdateTimeEntity;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import com.platform.modules.quartz.entity.TmpQkjvipMemberFansEntity;
import com.platform.modules.quartz.service.QrtzMemberFansService;
import com.platform.modules.quartz.service.QrtzLastUpdateTimeService;
import com.platform.modules.quartz.service.TmpQkjvipMemberFansService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.RabbitMQUtil;
import com.platform.modules.util.RandomGUID;
import com.platform.modules.util.Vars;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.emay.util.Md5.md5;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2020-09-09 14:02:22
 */
@RestController
@Component("qrtzMemberFans")
@Slf4j
public class QrtzMemberFansController extends AbstractController {
    @Autowired
    private QrtzMemberFansService qrtzMemberFansService;
    @Autowired
    private TmpQkjvipMemberFansService tmpQkjvipMemberFansService;
    @Autowired
    private QrtzLastUpdateTimeService qrtzLastUpdateTimeService;

    /**
     * 粉丝定时任务
     *
     */
    @SysLog("粉丝读取定时任务")
    @RequestMapping("/getMemberFans")
    public void getMemberFans() throws IOException, NoSuchAlgorithmException, ParseException {
        String url = "http://api.zhongjiu.cn/datapool/userdetail";
        List<QrtzMemberFansEntity> fanList = new ArrayList<QrtzMemberFansEntity>();
        List<TmpQkjvipMemberFansEntity> tmpList = new ArrayList<TmpQkjvipMemberFansEntity>();
        RandomGUID myGUID = new RandomGUID();
        String urlParam = "";
        String resultPost = "";  //返回结果
        Map map = new HashMap();
        Map subMap = new HashMap();
        String updateTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map params = new HashMap();
        params.put("type", 4);
        List<QrtzLastUpdateTimeEntity> updateTimeList = qrtzLastUpdateTimeService.queryAll(params);
        QrtzLastUpdateTimeEntity updateTimeEntity = new QrtzLastUpdateTimeEntity();
        updateTimeEntity = updateTimeList.get(0);
        if (updateTimeList.get(0).getLastDatetime() == null) {
            updateTime = "2017-01-01";
        } else {
            updateTime = sdf.format(updateTimeList.get(0).getLastDatetime());
        }
        subMap.put("user", "");
        subMap.put("imei", "12312312312");
        subMap.put("version", 1);
        subMap.put("channel", "00000000");
        subMap.put("phonemodel", "hua wei");
        subMap.put("platform", 0);
        subMap.put("IsApp", 0);
        map.put("UpdateTime", updateTime);
        map.put("OriginId", "");
        map.put("meta", subMap);
        JSONObject json = new JSONObject(map);
        String requestJson = JsonHelper.toJsonString(json);
        byte[] bytes = requestJson.getBytes(Vars.ENCODE);
        //AES加密
        byte[] parambytes = AES.encrypt(bytes, Vars.AESKEY.getBytes(Vars.ENCODE), Vars.VECTOR.getBytes(Vars.ENCODE), Vars.ALGORITHM);
        //转base64位字符串
        String res = new BASE64Encoder().encode(parambytes);
        System.out.println("key:data" + " vlaue:" + res);
        String guid = myGUID.toString();
        String sign = md5((res + guid + Vars.SECRETKEY).getBytes(Vars.ENCODE));   //md5加密
        urlParam = "?v=1"
                + "&sign=" + sign
                + "&key=" + guid;
        System.out.println("key:sign" + " vlaue:" + sign);
        System.out.println("key:key" + " vlaue:" + guid);
        resultPost = HttpClient.sendPost(url + urlParam, res);
        JSONObject fanObject = JSON.parseObject(resultPost);
        if ("0".equals(fanObject.get("result").toString())) {
//            String listStr = JSON.toJSONString(fanObject.get("UserList"), SerializerFeature.WriteMapNullValue);  //Fastjson序列化Null字段丢失解决方法
            fanList = JSON.parseArray(fanObject.get("UserList").toString(), QrtzMemberFansEntity.class);
            map.clear();
            map.put("QueueData", fanList);
            String jsonData = JsonHelper.toJsonString(map, "yyyy-MM-dd HH:mm:ss");
            updateTimeEntity.setLastDatetime(fanList.get(fanList.size() -1).getUpdatetime());
            if (fanList.size() > 0) {
                tmpList = JSON.parseArray(fanObject.get("UserList").toString(), TmpQkjvipMemberFansEntity.class);
                System.out.println("result:" + fanList);
                List<QrtzMemberFansEntity> updList = new ArrayList<QrtzMemberFansEntity>();
                tmpQkjvipMemberFansService.addBatch(tmpList);  //批量插入粉丝临时表
                updList = qrtzMemberFansService.queryList();  //取出粉丝表与临时表的交集(插入是为重复数据，更新时即为要更新的数据)
                if (updList.size() > 0) {  //更新
                    qrtzMemberFansService.updateBatch(updList);
                }
                fanList.removeAll(updList);
                if (fanList.size() > 0) {
                    qrtzMemberFansService.addBatch(fanList);  //粉丝批量插入
                }
                //将最后更新时间存入数据库
                qrtzLastUpdateTimeService.updateLastDatetime(updateTimeEntity);
                //将数据存入队列
                RabbitMQUtil.getConnection("qkjvip_member_fans", jsonData);
            }
        }
    }
}
