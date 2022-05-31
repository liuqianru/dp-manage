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
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjrpt.entity.*;
import com.platform.modules.qkjrpt.service.QkjrptMemberTargetService;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrgEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrgService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrpt.service.QkjrptReportMembertempService;
import com.platform.modules.util.GeoLntLatUtil;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@RestController
@RequestMapping("qkjrpt/reportmembertemp")
public class QkjrptReportMembertempController extends AbstractController {
    @Autowired
    private QkjrptReportMembertempService qkjrptReportMembertempService;
    @Autowired
    private QkjrptMemberTargetService qkjrptMemberTargetService;

    @SysLog("客户统计-一级区域")
    @RequestMapping("/getMemberAreaReport")
    public RestResponse getMemberAreaReport(@RequestBody QkjrptReportMemberQueryEntity qkjrptReportMemberQueryEntity) throws IOException {
        List<QkjrptReportMemberResultEntity> list = new ArrayList<>();
//        if (!getUser().getUserName().contains("admin")) {
//            qkjrptReportMemberQueryEntity.setCurrentUserId(getUserId());
//        }
//        String permitDepts = ContextHelper.getPermitDepts("");
//        if (!("".equals(permitDepts) || "-1".equals(permitDepts))) { // 无权限或者管理员全部权限
//            qkjrptReportMemberQueryEntity.setPowerOrgs(permitDepts);
//        }
        String queryJsonStr = JsonHelper.toJsonString(qkjrptReportMemberQueryEntity, "yyyy-MM-dd HH:mm:ss");

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_AREAONE, queryJsonStr);
        System.out.println("客户区域统计条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("data") != null) {
                list = JSON.parseArray(resultObject.getString("data"), QkjrptReportMemberResultEntity.class);
            }
        }
        // 目标数 实际数
        Map ratioMap = new HashMap();
        resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_RATIOURl, queryJsonStr);
        resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("data") != null) {
                ratioMap = JSON.parseObject(resultObject.getString("data"));
            }
        }
        return RestResponse.success().put("list", list).put("ratioMap", ratioMap);
    }

    @SysLog("客户统计-等级类型")
    @RequestMapping("/getMemberIdentityGroupReport")
    public RestResponse getMemberIdentityGroupReport(@RequestBody QkjrptReportMemberQueryEntity qkjrptReportMemberQueryEntity) throws IOException {
        List<QkjrptReportMemberResultEntity> list = new ArrayList<>();
        if (!getUser().getUserName().contains("admin")) {
            qkjrptReportMemberQueryEntity.setCurrentUserId(getUserId());
        }
        String permitDepts = ContextHelper.getPermitDepts("");
        if (!("".equals(permitDepts) || "-1".equals(permitDepts))) { // 无权限或者管理员全部权限
            qkjrptReportMemberQueryEntity.setPowerOrgs(permitDepts);
        }
        String queryJsonStr = JsonHelper.toJsonString(qkjrptReportMemberQueryEntity, "yyyy-MM-dd HH:mm:ss");

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_INDENTITYGROUP, queryJsonStr);
        System.out.println("客户统计-等级类型统计条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("data") != null) {
                list = JSON.parseArray(resultObject.getString("data"), QkjrptReportMemberResultEntity.class);
            }
        }
        return RestResponse.success().put("list", list);
    }

    @SysLog("客户列表")
    @RequestMapping("/getMemberList")
    public RestResponse getMemberList(@RequestParam Map<String, Object> params) {
        Page page = qkjrptReportMembertempService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    @SysLog("客户统计-客户所属")
    @RequestMapping("/getMemberOrgReport")
    public RestResponse getMemberOrgReport(@RequestBody QkjrptReportMemberQueryEntity qkjrptReportMemberQueryEntity) throws IOException {
        List<QkjrptReportMemberResultEntity> list = new ArrayList<>();
        if (qkjrptReportMemberQueryEntity.getIsPower() != null && qkjrptReportMemberQueryEntity.getIsPower()) {
            if (!getUser().getUserName().contains("admin")) {
                qkjrptReportMemberQueryEntity.setCurrentUserId(getUserId());
            }
            String permitDepts = ContextHelper.getPermitDepts("");
            if (!("".equals(permitDepts) || "-1".equals(permitDepts))) { // 无权限或者管理员全部权限
                qkjrptReportMemberQueryEntity.setPowerOrgs(permitDepts);
            }
        }
        String queryJsonStr = JsonHelper.toJsonString(qkjrptReportMemberQueryEntity, "yyyy-MM-dd HH:mm:ss");

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_GROUPORG, queryJsonStr);
        System.out.println("客户统计-客户所属条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("data") != null) {
                list = JSON.parseArray(resultObject.getString("data"), QkjrptReportMemberResultEntity.class);
            }
        }
        return RestResponse.success().put("list", list);
    }

//    @SysLog("客户统计-部门-等级")
//    @RequestMapping("/getMemberOrgLevelReport")
//    public RestResponse getMemberOrgLevelReport(@RequestBody QkjrptReportMemberOrgLevelEntity qkjrptReportMemberOrgLevelEntity) throws IOException {
//        QkjrptReportMemberOrgLevelEntity memberOrgLevelEntity = new QkjrptReportMemberOrgLevelEntity();
//        if (!getUser().getUserName().contains("admin")) {
//            qkjrptReportMemberOrgLevelEntity.setCurrentUserId(getUserId());
//        }
//        String permitDepts = ContextHelper.getPermitDepts("");
//        if (!("".equals(permitDepts) || "-1".equals(permitDepts))) { // 无权限或者管理员全部权限
//            qkjrptReportMemberOrgLevelEntity.setPowerOrgs(permitDepts);
//        }
//        String queryJsonStr = JsonHelper.toJsonString(qkjrptReportMemberOrgLevelEntity, "yyyy-MM-dd HH:mm:ss");
//
//        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_GROUPORGLEVELURl, queryJsonStr);
//        System.out.println("客户部门-等级统计条件：" + queryJsonStr);
//        JSONObject resultObject = JSON.parseObject(resultPost);
//        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
//            if (resultObject.get("data") != null) {
//                memberOrgLevelEntity = JSON.parseObject(resultObject.getString("data"), QkjrptReportMemberOrgLevelEntity.class);
//            }
//        }
//        Map dataMap = new HashMap();
//        String starttime = qkjrptReportMemberOrgLevelEntity.getStartAddTime();
//        String endtime = qkjrptReportMemberOrgLevelEntity.getEndAddTime();
//        Date date = new Date();//获取当前的日期
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        if (!StringUtils.isEmpty(starttime)) {
//            if (StringUtils.isEmpty(endtime)) {
//                endtime = df.format(date);
//            }
//            if (starttime.substring(0, 4).equals(endtime.substring(0, 4))) {  // 同一个年
//                dataMap.put("yearRatio", memberOrgLevelEntity.getYoy() + "%");
//            } else {
//                dataMap.put("yearRatio", "--");
//            }
//            if (starttime.substring(0, 7).equals(endtime.substring(0, 7))) {  // 同一个月
//                dataMap.put("ringRatio", memberOrgLevelEntity.getMom() + "%");
//            } else {
//                dataMap.put("ringRatio", "--");
//            }
//        }
//        dataMap.put("targetCnt", memberOrgLevelEntity.getPlan());
//        dataMap.put("actualCnt", memberOrgLevelEntity.getActual());
//
//        return RestResponse.success().put("memberOrgLevelEntity", memberOrgLevelEntity).put("dataMap", dataMap);
//    }

    @SysLog("地图")
    @RequestMapping("/getChartMapReport")
    public RestResponse getChartMapReport(@RequestBody QkjrptReportChartMapEntity qkjrptReportChartMapEntity) throws IOException {
        List<QkjrptReportChartMapEntity> list = new ArrayList<>();
//        if (!getUser().getUserName().contains("admin")) {
//            qkjrptReportChartMapEntity.setOwnUserId(getUserId());
//        }
//        String permitDepts = ContextHelper.getPermitDepts("");
//        if (!("".equals(permitDepts) || "-1".equals(permitDepts))) { // 无权限或者管理员全部权限
//            qkjrptReportChartMapEntity.setOrganIds(Arrays.asList(permitDepts.split(",")));
//        }
        String queryJsonStr = JsonHelper.toJsonString(qkjrptReportChartMapEntity, "yyyy-MM-dd HH:mm:ss");

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_MAPURl, queryJsonStr);
        System.out.println("客户地图条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        int memberCount = 0;
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("data") != null) {
                list = JSON.parseArray(resultObject.getString("data"), QkjrptReportChartMapEntity.class);
            }
            if (resultObject.get("count") != null) {
                memberCount = Integer.parseInt(resultObject.getString("count"));
            }
        }
        for (QkjrptReportChartMapEntity chartMap : list) {
            String address = "";
            if (StringUtils.isNotBlank(chartMap.getProvince())) {
                address += chartMap.getProvince();
            }
            if (StringUtils.isNotBlank(chartMap.getCity())) {
                address += chartMap.getCity();
            }
            if (StringUtils.isNotBlank(chartMap.getDistrict())) {
                address += chartMap.getDistrict();
            }
            if (!"".equals(address)) {
                Map<String, BigDecimal> map = GeoLntLatUtil.getLatAndLngByAddress(address);
                if (map != null) {
                    chartMap.setLat(map.get("lat"));
                    chartMap.setLng(map.get("lng"));
                }
            }
        }
        return RestResponse.success().put("list", list).put("memberCount", memberCount);
    }

}
