/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitypeoplanController.java
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
import com.platform.modules.qkjrpt.entity.QkjrptReportActivitypeoplanEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportActivitypeoplanService;
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
@RequestMapping("qkjrpt/reportactivitypeoplan")
public class QkjrptReportActivitypeoplanController extends AbstractController {
    @Autowired
    private QkjrptReportActivitypeoplanService qkjrptReportActivitypeoplanService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrpt:reportactivitypeoplan:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrptReportActivitypeoplanEntity> list = qkjrptReportActivitypeoplanService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrpt:reportactivitypeoplan:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrptReportActivitypeoplanService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrpt:reportactivitypeoplan:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrptReportActivitypeoplanEntity qkjrptReportActivitypeoplan = qkjrptReportActivitypeoplanService.getById(id);

        return RestResponse.success().put("reportactivitypeoplan", qkjrptReportActivitypeoplan);
    }

    /**
     * 新增
     *
     * @param qkjrptReportActivitypeoplan qkjrptReportActivitypeoplan
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrpt:reportactivitypeoplan:save")
    public RestResponse save(@RequestBody QkjrptReportActivitypeoplanEntity qkjrptReportActivitypeoplan) {

        qkjrptReportActivitypeoplanService.add(qkjrptReportActivitypeoplan);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrptReportActivitypeoplan qkjrptReportActivitypeoplan
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrpt:reportactivitypeoplan:update")
    public RestResponse update(@RequestBody QkjrptReportActivitypeoplanEntity qkjrptReportActivitypeoplan) {

        qkjrptReportActivitypeoplanService.update(qkjrptReportActivitypeoplan);

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
    @RequiresPermissions("qkjrpt:reportactivitypeoplan:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrptReportActivitypeoplanService.deleteBatch(ids);

        return RestResponse.success();
    }
}
