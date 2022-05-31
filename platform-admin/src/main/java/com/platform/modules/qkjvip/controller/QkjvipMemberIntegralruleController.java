/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegralruleController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-12 10:05:39        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegralruleEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegralruleService;
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
 * @date 2021-01-12 10:05:39
 */
@RestController
@RequestMapping("qkjvip/memberintegralrule")
public class QkjvipMemberIntegralruleController extends AbstractController {
    @Autowired
    private QkjvipMemberIntegralruleService qkjvipMemberIntegralruleService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberintegralrule:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberIntegralruleEntity> list = qkjvipMemberIntegralruleService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberintegralrule:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberIntegralruleService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberintegralrule:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberIntegralruleEntity qkjvipMemberIntegralrule = qkjvipMemberIntegralruleService.getById(id);

        return RestResponse.success().put("memberintegralrule", qkjvipMemberIntegralrule);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberIntegralrule qkjvipMemberIntegralrule
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberintegralrule:save")
    public RestResponse save(@RequestBody QkjvipMemberIntegralruleEntity qkjvipMemberIntegralrule) {
        qkjvipMemberIntegralrule.setAddTime(new Date());
        qkjvipMemberIntegralruleService.add(qkjvipMemberIntegralrule);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberIntegralrule qkjvipMemberIntegralrule
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberintegralrule:update")
    public RestResponse update(@RequestBody QkjvipMemberIntegralruleEntity qkjvipMemberIntegralrule) throws IOException {

        qkjvipMemberIntegralruleService.update(qkjvipMemberIntegralrule);

        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("integraltype", qkjvipMemberIntegralrule.getRuleid());
        map.put("count", qkjvipMemberIntegralrule.getIntegral());
        list.add(map);
        String queryJsonStr = JsonHelper.toJsonString(list);
        String resultPost = HttpClient.sendPost(Vars.INTEGRAL_RULE_PUSH_URL, queryJsonStr);
        System.out.println("积分规则推送：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if (!"0".equals(resultObject.get("code").toString())) {  //调用成功
            return RestResponse.error("积分规则推送失败！");
        }
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
    @RequiresPermissions("qkjvip:memberintegralrule:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberIntegralruleService.deleteBatch(ids);

        return RestResponse.success();
    }
}
