/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegralController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-21 15:18:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegralEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegraluserEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegralService;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegraluserService;
import com.platform.modules.sys.controller.AbstractController;
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
 * @date 2020-12-21 15:18:24
 */
@RestController
@RequestMapping("qkjvip/memberintegral")
public class QkjvipMemberIntegralController extends AbstractController {
    @Autowired
    private QkjvipMemberIntegralService qkjvipMemberIntegralService;
    @Autowired
    private QkjvipMemberIntegraluserService qkjvipMemberIntegraluserService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberintegral:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:memberintegral:list", "T.add_user", "T.add_dept"));
        List<QkjvipMemberIntegralEntity> list = qkjvipMemberIntegralService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberintegral:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:memberintegral:list", "T.add_user", "T.add_dept"));
        Page page = qkjvipMemberIntegralService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberintegral:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberIntegralEntity qkjvipMemberIntegral = qkjvipMemberIntegralService.getById(id);

        Map<String, Object> map=new HashMap<String,Object>();
        map.put("integralId",id);
        qkjvipMemberIntegral.setMemberlist(qkjvipMemberIntegraluserService.queryAll(map));

        return RestResponse.success().put("memberintegral", qkjvipMemberIntegral);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberIntegral qkjvipMemberIntegral
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberintegral:save")
    public RestResponse save(@RequestBody QkjvipMemberIntegralEntity qkjvipMemberIntegral) {
        qkjvipMemberIntegral.setAddUser(getUserId());
        qkjvipMemberIntegral.setAddDept(getOrgNo());
        qkjvipMemberIntegral.setAddTime(new Date());
        qkjvipMemberIntegral.setSendStatus(0);  //默认0
        qkjvipMemberIntegralService.add(qkjvipMemberIntegral);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberIntegral qkjvipMemberIntegral
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberintegral:update")
    public RestResponse update(@RequestBody QkjvipMemberIntegralEntity qkjvipMemberIntegral) {
        qkjvipMemberIntegral.setLmUser(getUserId());
        qkjvipMemberIntegral.setLmDept(getOrgNo());
        qkjvipMemberIntegral.setLmTime(new Date());
        qkjvipMemberIntegralService.update(qkjvipMemberIntegral);

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
    @RequiresPermissions("qkjvip:memberintegral:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberIntegralService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 积分发放
     *
     * @param id id
     * @return RestResponse
     */
    @SysLog("发放积分")
    @RequestMapping("/sendIntegral/{id}")
    @RequiresPermissions("qkjvip:memberintegral:send")
    public RestResponse sendIntegral(@PathVariable("id") String id) throws IOException {
        List<String> memberids = new ArrayList<>();
        memberids = qkjvipMemberIntegraluserService.queryByIntegralId2(id);
        if (memberids.size() == 0) {
            return RestResponse.error(1, "没有发送对象，请添加！");
        }

        QkjvipMemberIntegralEntity qkjvipMemberIntegral = qkjvipMemberIntegralService.getById(id);
        qkjvipMemberIntegral.setId(id);
        qkjvipMemberIntegral.setSendStatus(1);  //改为积分已发状态
        qkjvipMemberIntegralService.sendIntegral(qkjvipMemberIntegral, memberids);

        return RestResponse.success();
    }

}
