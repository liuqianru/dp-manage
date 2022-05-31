/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMiddleinputController.java
 * 包名称:com.platform.modules.qkjrpt.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-16 09:36:36        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrpt.entity.QkjrptReportMiddleinputEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportMiddleinputService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-11-16 09:36:36
 */
@RestController
@RequestMapping("qkjrpt/reportmiddleinput")
public class QkjrptReportMiddleinputController extends AbstractController {
    @Autowired
    private QkjrptReportMiddleinputService qkjrptReportMiddleinputService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrpt:reportmiddleinput:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrptReportMiddleinputEntity> list = qkjrptReportMiddleinputService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrpt:reportmiddleinput:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrptReportMiddleinputService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrpt:reportmiddleinput:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrptReportMiddleinputEntity qkjrptReportMiddleinput = qkjrptReportMiddleinputService.getById(id);

        return RestResponse.success().put("reportmiddleinput", qkjrptReportMiddleinput);
    }

    /**
     * 新增
     *
     * @param qkjrptReportMiddleinput qkjrptReportMiddleinput
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrpt:reportmiddleinput:save")
    public RestResponse save(@RequestBody QkjrptReportMiddleinputEntity qkjrptReportMiddleinput) {

        qkjrptReportMiddleinputService.add(qkjrptReportMiddleinput);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrptReportMiddleinput qkjrptReportMiddleinput
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrpt:reportmiddleinput:update")
    public RestResponse update(@RequestBody QkjrptReportMiddleinputEntity qkjrptReportMiddleinput) {

        qkjrptReportMiddleinputService.update(qkjrptReportMiddleinput);

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
    @RequiresPermissions("qkjrpt:reportmiddleinput:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrptReportMiddleinputService.deleteBatch(ids);

        return RestResponse.success();
    }
}
