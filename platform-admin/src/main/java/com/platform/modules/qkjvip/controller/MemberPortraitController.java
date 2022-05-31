/*
 * 项目名称:platform-plus
 * 类名称:SysUserController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2021-02-02 10:33    liuqianru      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.utils.RestResponse;
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-02-02 10:33:22
 */
@RestController
@RequestMapping("/qkjvip/memberportrait")
public class MemberPortraitController extends AbstractController {
    @Autowired
    private SysUserChannelService sysUserChannelService;

    /**
     * 性别统计
     *
     * @param memberPortraitSexEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberSexReport")
    public RestResponse getMemberSexReport(@RequestBody MemberPortraitSexEntity memberPortraitSexEntity) throws IOException {
        List<MemberPortraitSexEntity> list = new ArrayList<>();
        memberPortraitSexEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitSexEntity.setCurrentmemberid(getUserId());
            memberPortraitSexEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitSexEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitSexEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_SEX_URl, queryJsonStr);
        System.out.println("性别统计检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitSexEntity.class);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 年龄统计
     *
     * @param memberPortraitAgeEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberAgeReport")
    public RestResponse getMemberAgeReport(@RequestBody MemberPortraitAgeEntity memberPortraitAgeEntity) throws IOException {
        List<MemberPortraitAgeEntity> list = new ArrayList<>();
        memberPortraitAgeEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitAgeEntity.setCurrentmemberid(getUserId());
            memberPortraitAgeEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitAgeEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitAgeEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_AGE_URl, queryJsonStr);
        System.out.println("年龄统计检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitAgeEntity.class);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 地区统计
     *
     * @param memberPortraitAreaEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberCityReport")
    public RestResponse getMemberCityReport(@RequestBody MemberPortraitAreaEntity memberPortraitAreaEntity) throws IOException {
        List<MemberPortraitAreaEntity> list = new ArrayList<>();
        memberPortraitAreaEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitAreaEntity.setCurrentmemberid(getUserId());
            memberPortraitAreaEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitAreaEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitAreaEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_AREA_URl, queryJsonStr);
        System.out.println("地区统计检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitAreaEntity.class);
            }
            if (!memberPortraitAreaEntity.getIsall()) {  //true:分页查询
                Page page = new Page();
                page.setRecords(list);
                page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
                page.setSize(memberPortraitAreaEntity.getPagesize() == null? 0 : memberPortraitAreaEntity.getPagesize());
                page.setCurrent(memberPortraitAreaEntity.getPageindex() == null? 0 : memberPortraitAreaEntity.getPageindex());
                return RestResponse.success().put("page", page);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 高频统计-按渠道
     *
     * @param memberPortraitHfQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberChannelRateReport")
    public RestResponse getMemberChannelRateReport(@RequestBody MemberPortraitHfQueryEntity memberPortraitHfQueryEntity) throws IOException {
        List<MemberPortraitHfResultEntity> list = new ArrayList<>();
        memberPortraitHfQueryEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitHfQueryEntity.setCurrentmemberid(getUserId());
            memberPortraitHfQueryEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitHfQueryEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitHfQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_HF_CHANNEL_URl, queryJsonStr);
        System.out.println("高频统计-按渠道-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitHfResultEntity.class);
            }
            if (!memberPortraitHfQueryEntity.getIsall()) {  //true:分页查询
                Page page = new Page();
                page.setRecords(list);
                page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
                page.setSize(memberPortraitHfQueryEntity.getPagesize() == null? 0 : memberPortraitHfQueryEntity.getPagesize());
                page.setCurrent(memberPortraitHfQueryEntity.getPageindex() == null? 0 : memberPortraitHfQueryEntity.getPageindex());
                return RestResponse.success().put("page", page);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 高频统计-按地区
     *
     * @param memberPortraitHfQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberCityRateReport")
    public RestResponse getMemberCityRateReport(@RequestBody MemberPortraitHfQueryEntity memberPortraitHfQueryEntity) throws IOException {
        List<MemberPortraitHfResultEntity> list = new ArrayList<>();
        memberPortraitHfQueryEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitHfQueryEntity.setCurrentmemberid(getUserId());
            memberPortraitHfQueryEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitHfQueryEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitHfQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_HF_CITY_URl, queryJsonStr);
        System.out.println("高频统计-按地区-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitHfResultEntity.class);
            }
            if (!memberPortraitHfQueryEntity.getIsall()) {  //true:分页查询
                Page page = new Page();
                page.setRecords(list);
                page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
                page.setSize(memberPortraitHfQueryEntity.getPagesize() == null? 0 : memberPortraitHfQueryEntity.getPagesize());
                page.setCurrent(memberPortraitHfQueryEntity.getPageindex() == null? 0 : memberPortraitHfQueryEntity.getPageindex());
                return RestResponse.success().put("page", page);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 高价值统计-按渠道
     *
     * @param memberPortraitHvQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberChannelAmountReport")
    public RestResponse getMemberChannelAmountReport(@RequestBody MemberPortraitHvQueryEntity memberPortraitHvQueryEntity) throws IOException {
        List<MemberPortraitHvResultEntity> list = new ArrayList<>();
        memberPortraitHvQueryEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitHvQueryEntity.setCurrentmemberid(getUserId());
            memberPortraitHvQueryEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitHvQueryEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitHvQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_HV_CHANNEL_URl, queryJsonStr);
        System.out.println("高价值统计-按渠道-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitHvResultEntity.class);
            }
            if (!memberPortraitHvQueryEntity.getIsall()) {  //true:分页查询
                Page page = new Page();
                page.setRecords(list);
                page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
                page.setSize(memberPortraitHvQueryEntity.getPagesize() == null? 0 : memberPortraitHvQueryEntity.getPagesize());
                page.setCurrent(memberPortraitHvQueryEntity.getPageindex() == null? 0 : memberPortraitHvQueryEntity.getPageindex());
                return RestResponse.success().put("page", page);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 高价值统计-按地区
     *
     * @param memberPortraitHvQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberCityAmountReport")
    public RestResponse getMemberCityAmountReport(@RequestBody MemberPortraitHvQueryEntity memberPortraitHvQueryEntity) throws IOException {
        List<MemberPortraitHvResultEntity> list = new ArrayList<>();
        memberPortraitHvQueryEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitHvQueryEntity.setCurrentmemberid(getUserId());
            memberPortraitHvQueryEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitHvQueryEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitHvQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_HV_CITY_URl, queryJsonStr);
        System.out.println("高价值统计-按地区-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitHvResultEntity.class);
            }
            if (!memberPortraitHvQueryEntity.getIsall()) {  //true:分页查询
                Page page = new Page();
                page.setRecords(list);
                page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
                page.setSize(memberPortraitHvQueryEntity.getPagesize() == null? 0 : memberPortraitHvQueryEntity.getPagesize());
                page.setCurrent(memberPortraitHvQueryEntity.getPageindex() == null? 0 : memberPortraitHvQueryEntity.getPageindex());
                return RestResponse.success().put("page", page);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 价值分布统计
     *
     * @param memberPortraitValueRangeQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberAmountRangeReport")
    public RestResponse getMemberAmountRangeReport(@RequestBody MemberPortraitValueRangeQueryEntity memberPortraitValueRangeQueryEntity) throws IOException {
        List<MemberPortraitValueRangeResultEntity> list = new ArrayList<>();
        memberPortraitValueRangeQueryEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitValueRangeQueryEntity.setCurrentmemberid(getUserId());
            memberPortraitValueRangeQueryEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitValueRangeQueryEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitValueRangeQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_VALUERANGE_URl, queryJsonStr);
        System.out.println("价值区间统计-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitValueRangeResultEntity.class);
            }
            if (!memberPortraitValueRangeQueryEntity.getIsall()) {  //true:分页查询
                Page page = new Page();
                page.setRecords(list);
                page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
                page.setSize(memberPortraitValueRangeQueryEntity.getPagesize() == null? 0 : memberPortraitValueRangeQueryEntity.getPagesize());
                page.setCurrent(memberPortraitValueRangeQueryEntity.getPageindex() == null? 0 : memberPortraitValueRangeQueryEntity.getPageindex());
                return RestResponse.success().put("page", page);
            }
        }
        return RestResponse.success().put("list", list);
    }

    /**
     * 新增趋势统计
     *
     * @param memberPortraitAddtrendQueryEntity 查询参数
     * @return RestResponse
     */
    @PostMapping("/getMemberTrendReport")
    public RestResponse getMemberTrendReport(@RequestBody MemberPortraitAddtrendQueryEntity memberPortraitAddtrendQueryEntity) throws IOException {
        List<MemberPortraitAddtrendResultEntity> list = new ArrayList<>();
        List<String> servicenamelist = new ArrayList<>();
        List<String> datelist = new ArrayList<>();
        memberPortraitAddtrendQueryEntity.setListorgno(ContextHelper.getPermitDepts(""));
        if (!getUser().getUserName().contains("admin")) {
            memberPortraitAddtrendQueryEntity.setCurrentmemberid(getUserId());
            memberPortraitAddtrendQueryEntity.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId()));
        } else {
            memberPortraitAddtrendQueryEntity.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(memberPortraitAddtrendQueryEntity);
        String queryJsonStr = JsonHelper.toJsonString(obj);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_PORTRAIT_ADDTREND_URl, queryJsonStr);
        System.out.println("新增趋势统计-检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            if (resultObject.get("list") != null) {
                list = JSON.parseArray(resultObject.getString("list"),MemberPortraitAddtrendResultEntity.class);
            }
            if (resultObject.get("listservicename") != null) {
                servicenamelist = JSON.parseArray(resultObject.getString("listservicename"), String.class);
            }
            if (resultObject.get("listdate") != null) {
                datelist = JSON.parseArray(resultObject.getString("listdate"), String.class);
            }
        }
        return RestResponse.success().put("list", list).put("servicenamelist", servicenamelist).put("datelist", datelist);
    }
}
