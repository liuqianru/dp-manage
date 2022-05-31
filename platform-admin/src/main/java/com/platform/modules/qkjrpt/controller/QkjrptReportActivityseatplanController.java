/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivityseatplanController.java
 * 包名称:com.platform.modules.qkjrpt.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-28 11:31:01        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrpt.entity.QkjrptReportActivityseatplanEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportActivityseatplanService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-09-28 11:31:01
 */
@RestController
@RequestMapping("qkjrpt/reportactivityseatplan")
public class QkjrptReportActivityseatplanController extends AbstractController {
    @Autowired
    private QkjrptReportActivityseatplanService qkjrptReportActivityseatplanService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrpt:reportactivityseatplan:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrptReportActivityseatplanEntity> list = qkjrptReportActivityseatplanService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrpt:reportactivityseatplan:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrptReportActivityseatplanService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrpt:reportactivityseatplan:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrptReportActivityseatplanEntity qkjrptReportActivityseatplan = qkjrptReportActivityseatplanService.getById(id);

        return RestResponse.success().put("reportactivityseatplan", qkjrptReportActivityseatplan);
    }

    /**
     * 新增
     *
     * @param qkjrptReportActivityseatplan qkjrptReportActivityseatplan
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrpt:reportactivityseatplan:save")
    public RestResponse save(@RequestBody QkjrptReportActivityseatplanEntity qkjrptReportActivityseatplan) {

        qkjrptReportActivityseatplanService.add(qkjrptReportActivityseatplan);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrptReportActivityseatplan qkjrptReportActivityseatplan
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrpt:reportactivityseatplan:update")
    public RestResponse update(@RequestBody QkjrptReportActivityseatplanEntity qkjrptReportActivityseatplan) {

        qkjrptReportActivityseatplanService.update(qkjrptReportActivityseatplan);

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
    @RequiresPermissions("qkjrpt:reportactivityseatplan:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrptReportActivityseatplanService.deleteBatch(ids);

        return RestResponse.success();
    }
}
