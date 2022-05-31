/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareController.java
 * 包名称:com.platform.modules.qkjrts.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:28        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjrts.entity.*;
import com.platform.modules.qkjrts.service.QkjrtsWelfareDetailsService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareMemberService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareOtherobjectsService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrts.service.QkjrtsWelfareService;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.service.SysDictService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:28
 */
@RestController
@RequestMapping("qkjrts/welfare")
public class QkjrtsWelfareController extends AbstractController {
    @Autowired
    private QkjrtsWelfareService qkjrtsWelfareService;
    @Autowired
    private QkjrtsWelfareMemberService qkjrtsWelfareMemberService;
    @Autowired
    private QkjrtsWelfareDetailsService qkjrtsWelfareDetailsService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private QkjrtsWelfareOtherobjectsService qkjrtsWelfareOtherobjectsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrts:welfare:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrtsWelfareEntity> list = qkjrtsWelfareService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrts:welfare:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrtsWelfareService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrts:welfare:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrtsWelfareEntity qkjrtsWelfare = qkjrtsWelfareService.getById(id);

        // 会员列表
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("mainid", id);
        qkjrtsWelfare.setMemberlist(qkjrtsWelfareMemberService.queryAll(map));
        // 福利列表
        List<QkjrtsWelfareDetailsEntity> welfareDetails = new ArrayList<>();
        welfareDetails = qkjrtsWelfareDetailsService.queryAll(map);
        qkjrtsWelfare.setWelfarelist(welfareDetails);
        // 核心店团购商
        map.put("objecttype", 1);
        List<QkjrtsWelfareOtherobjectsEntity> outobjectslist = qkjrtsWelfareOtherobjectsService.queryAll(map);
        qkjrtsWelfare.setObjectslist1(outobjectslist);
        // 内部其他管理员
        map.put("objecttype", 2);
        List<QkjrtsWelfareOtherobjectsEntity> inobjectslist = qkjrtsWelfareOtherobjectsService.queryAll(map);
        qkjrtsWelfare.setObjectslist2(inobjectslist);

        map.clear();
        map.put("code", "WELFARETYPE");
        List<SysDictEntity> dictList = new ArrayList<>();
        dictList = sysDictService.queryByCode(map);

        List<QkjrtsWelfareObjectEntity> welfareObjects = new ArrayList<>();
        for (SysDictEntity dict : dictList) {
            QkjrtsWelfareObjectEntity welfareObject = new QkjrtsWelfareObjectEntity();
            List<QkjrtsWelfareDetailsEntity> welfaresonlist = new ArrayList<>();
            welfareObject.setType(Integer.parseInt(dict.getValue()));
            welfareObject.setTypename(dict.getName());
            for (QkjrtsWelfareDetailsEntity welfareDetail : welfareDetails) {
                // 福利类型相同
                if (welfareDetail.getWelfaretype() != null && ((welfareDetail.getWelfaretype() + "").equals(dict.getValue()))) {
                    welfaresonlist.add(welfareDetail);
                    if ("2".equals(dict.getValue())) {  // 积分的情况下
                        qkjrtsWelfare.setIntegral(welfareDetail.getIntegral());
                    }
                }
            }
            welfareObject.setItemchecked(welfaresonlist.size() > 0 ? true : false);
            welfareObject.setWelfaresonlist(welfaresonlist);
            welfareObjects.add(welfareObject);
        }

        return RestResponse.success().put("welfare", qkjrtsWelfare).put("welfareObjects", welfareObjects);
    }

    @RequestMapping("/getInfo")
    @RequiresPermissions("qkjrts:welfare:info")
    public RestResponse getInfo() {
        QkjrtsWelfareEntity qkjrtsWelfare = new QkjrtsWelfareEntity();

        Map<String, Object> map=new HashMap<String,Object>();
        map.clear();
        map.put("code", "WELFARETYPE");
        List<SysDictEntity> dictList = new ArrayList<>();
        dictList = sysDictService.queryByCode(map);

        List<QkjrtsWelfareObjectEntity> welfareObjects = new ArrayList<>();
        for (SysDictEntity dict : dictList) {
            QkjrtsWelfareObjectEntity welfareObject = new QkjrtsWelfareObjectEntity();
            List<QkjrtsWelfareDetailsEntity> welfaresonlist = new ArrayList<>();
            welfareObject.setType(Integer.parseInt(dict.getValue()));
            welfareObject.setTypename(dict.getName());
            welfareObject.setItemchecked(false);
            welfareObject.setWelfaresonlist(welfaresonlist);
            welfareObjects.add(welfareObject);
        }

        return RestResponse.success().put("welfare", qkjrtsWelfare).put("welfareObjects", welfareObjects);
    }

    /**
     * 新增
     *
     * @param qkjrtsWelfare qkjrtsWelfare
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrts:welfare:save")
    public RestResponse save(@RequestBody QkjrtsWelfareEntity qkjrtsWelfare) {
        qkjrtsWelfare.setCreateuser(getUserId());
        qkjrtsWelfare.setCreatetime(new Date());
        qkjrtsWelfareService.add(qkjrtsWelfare);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrtsWelfare qkjrtsWelfare
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrts:welfare:update")
    public RestResponse update(@RequestBody QkjrtsWelfareEntity qkjrtsWelfare) {

        qkjrtsWelfareService.update(qkjrtsWelfare);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjrts:welfare:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrtsWelfareService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 发送福利
     */
    @SysLog("发送福利")
    @RequestMapping("/send/{id}")
    public RestResponse sendCpon(@PathVariable("id") String id)throws IOException {
        qkjrtsWelfareService.send(id);
        return RestResponse.success();
    }

    /**
     * 获取青稞荟里福利列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/getWelfareList")
    public RestResponse getWelfareList(@RequestParam Map<String, Object> params) throws IOException {
        if (!params.containsKey("welfareType") || "".equals(params.get("welfareType"))) {
            return RestResponse.error("welfareType不允许为空");
        }
        List<QkjrtsWelfareInfoEntity> welfareList = this.getWelfareListByType(params);
        return RestResponse.success().put("welfareList", welfareList);
    }

    public List<QkjrtsWelfareInfoEntity> getWelfareListByType(Map<String, Object> params) throws IOException {
        String queryJsonStr = JsonHelper.toJsonString(params);
        String resultPost = HttpClient.sendPost(Vars.MEMBER_WELFARE_LIST_URl, queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        List<QkjrtsWelfareInfoEntity> welfareList = new ArrayList<>();
        if ("0".equals(resultObject.get("code").toString())) {
            welfareList = JSON.parseArray(resultObject.getString("list"),QkjrtsWelfareInfoEntity.class);
        }
        return welfareList;
    }
}
