/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportGroupdistributeController.java
 * 包名称:com.platform.modules.qkjrpt.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-11 15:11:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjrpt.domain.QkjrptGroupDistributeStatic;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrpt.entity.QkjrptReportGroupdistributeEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportGroupdistributeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author hanjie
 * @date 2021-11-11 15:11:18
 */
@RestController
@RequestMapping("qkjrpt/reportgroupdistribute")
public class QkjrptReportGroupdistributeController extends AbstractController {
    @Autowired
    private QkjrptReportGroupdistributeService qkjrptReportGroupdistributeService;

    /**
     * 获取一级区域经销商统计列表
     *
     * @param params 0 经销商 1 核心店 2 异业联盟店
     * @return
     */
    @RequestMapping("/getPrimaryAreaStatic")
    public RestResponse getPrimaryAreaStatic(@RequestParam Map<String,Object> params) {
        List<QkjrptGroupDistributeStatic> list = qkjrptReportGroupdistributeService.getPrimaryAreaDst(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrptReportGroupdistributeEntity> list = qkjrptReportGroupdistributeService.queryAll(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数queryAllReport
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrptReportGroupdistributeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrpt:reportgroupdistribute:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrptReportGroupdistributeEntity qkjrptReportGroupdistribute = qkjrptReportGroupdistributeService.getById(id);

        return RestResponse.success().put("reportgroupdistribute", qkjrptReportGroupdistribute);
    }

    /**
     * 新增
     *
     * @param qkjrptReportGroupdistribute qkjrptReportGroupdistribute
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrpt:reportgroupdistribute:save")
    public RestResponse save(@RequestBody QkjrptReportGroupdistributeEntity qkjrptReportGroupdistribute) {
        qkjrptReportGroupdistribute.setCreatetime(new Date());
        qkjrptReportGroupdistribute.setCreateuser(getUserId());
        qkjrptReportGroupdistributeService.add(qkjrptReportGroupdistribute);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrptReportGroupdistribute qkjrptReportGroupdistribute
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrpt:reportgroupdistribute:update")
    public RestResponse update(@RequestBody QkjrptReportGroupdistributeEntity qkjrptReportGroupdistribute) {

        qkjrptReportGroupdistributeService.update(qkjrptReportGroupdistribute);

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
    @RequiresPermissions("qkjrpt:reportgroupdistribute:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrptReportGroupdistributeService.deleteBatch(ids);

        return RestResponse.success();
    }
}
