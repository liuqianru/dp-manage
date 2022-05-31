/*
 * 项目名称:platform-plus
 * 类名称:SysOrgUpdatelogController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 13:52:13        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysOrgUpdatelogEntity;
import com.platform.modules.sys.service.SysOrgUpdatelogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-08-04 13:52:13
 */
@RestController
@RequestMapping("sys/orgupdatelog")
public class SysOrgUpdatelogController extends AbstractController {
    @Autowired
    private SysOrgUpdatelogService sysOrgUpdatelogService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("sys:orgupdatelog:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysOrgUpdatelogEntity> list = sysOrgUpdatelogService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:orgupdatelog:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = sysOrgUpdatelogService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param deptcode 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{deptcode}")
    @RequiresPermissions("sys:orgupdatelog:info")
    public RestResponse info(@PathVariable("deptcode") String deptcode) {
        SysOrgUpdatelogEntity sysOrgUpdatelog = sysOrgUpdatelogService.getById(deptcode);

        return RestResponse.success().put("orgupdatelog", sysOrgUpdatelog);
    }

    /**
     * 新增
     *
     * @param sysOrgUpdatelog sysOrgUpdatelog
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("sys:orgupdatelog:save")
    public RestResponse save(@RequestBody SysOrgUpdatelogEntity sysOrgUpdatelog) {

        sysOrgUpdatelogService.add(sysOrgUpdatelog);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param sysOrgUpdatelog sysOrgUpdatelog
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("sys:orgupdatelog:update")
    public RestResponse update(@RequestBody SysOrgUpdatelogEntity sysOrgUpdatelog) {

        sysOrgUpdatelogService.update(sysOrgUpdatelog);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param deptcodes deptcodes
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:orgupdatelog:delete")
    public RestResponse delete(@RequestBody String[] deptcodes) {
        sysOrgUpdatelogService.deleteBatch(deptcodes);

        return RestResponse.success();
    }
}
