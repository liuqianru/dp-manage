/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMembertempController.java
 * 包名称:com.platform.modules.qkjrpt.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 10:51:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjrpt.entity.*;
import com.platform.modules.qkjrpt.service.QkjrptReportMembertempService;
import com.platform.modules.qkjvip.entity.MemberPortraitSexEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.util.GeoLntLatUtil;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@RestController
@RequestMapping("qkjrpt/reportsales")
public class QkjrptReportSalesController extends AbstractController {
    @Autowired
    private QkjrptReportMembertempService qkjrptReportMembertempService;

    @SysLog("销售统计-大区")
    @PostMapping("/getMemberAreaSaleReport")
    public RestResponse getMemberAreaSaleReport(@RequestBody QkjrptReportSalesAreaEntity qkjrptReportSalesAreaEntity) throws IOException {
        List<QkjrptReportSalesAreaEntity> list = new ArrayList<>();
        qkjrptReportSalesAreaEntity.setListorgno(ContextHelper.getPermitDepts(""));
        Object obj = JSONArray.toJSON(qkjrptReportSalesAreaEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_SALE_URl, queryJsonStr);
        System.out.println("销售统计-大区检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"), QkjrptReportSalesAreaEntity.class);
            }
        }
        return RestResponse.success().put("list", list);
    }

    @SysLog("销售统计-大区-等级")
    @PostMapping("/getMemberOrgSaleReport")
    public RestResponse getMemberOrgSaleReport(@RequestBody QkjrptReportSalesAreaLevelEntity qkjrptReportSalesAreaLevelEntity) throws IOException {
        List<QkjrptReportSalesAreaLevelEntity> list = new ArrayList<>();
        qkjrptReportSalesAreaLevelEntity.setListorgno(ContextHelper.getPermitDepts(""));
        Object obj = JSONArray.toJSON(qkjrptReportSalesAreaLevelEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_SALELEAVEURl, queryJsonStr);
        System.out.println("销售统计-大区-等级检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"), QkjrptReportSalesAreaLevelEntity.class);
            }
        }
        return RestResponse.success().put("list", list);
    }

    @SysLog("销售统计-产品")
    @PostMapping("/getMemberProductReport")
    public RestResponse getMemberProductReport(@RequestBody QkjrptReportSalesAreaEntity qkjrptReportSalesAreaEntity) throws IOException {
        List<QkjrptReportSalesAreaEntity> list = new ArrayList<>();
        qkjrptReportSalesAreaEntity.setListorgno(ContextHelper.getPermitDepts(""));
        Object obj = JSONArray.toJSON(qkjrptReportSalesAreaEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost("http://scrm-api.ym.qkj.com.cn/Report/Order/GetSumByProduct", queryJsonStr);
        System.out.println("销售统计-大区检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("0".equals(resultObject.get("respCode").toString())) {  //调用成功
            if (resultObject.get("data") != null) {
                list = JSON.parseArray(resultObject.getString("data"), QkjrptReportSalesAreaEntity.class);
            }
        }
        return RestResponse.success().put("list", list);
    }
}
