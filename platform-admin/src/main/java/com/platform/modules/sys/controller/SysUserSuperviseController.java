/*
 * 项目名称:platform-plus
 * 类名称:SysUserSuperviseController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 09:26:18        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserSuperviseEntity;
import com.platform.modules.sys.service.SysUserSuperviseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-08-04 09:26:18
 */
@RestController
@RequestMapping("sys/usersupervise")
public class SysUserSuperviseController extends AbstractController {
    @Autowired
    private SysUserSuperviseService sysUserSuperviseService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysUserSuperviseEntity> list = sysUserSuperviseService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = sysUserSuperviseService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        SysUserSuperviseEntity sysUserSupervise = sysUserSuperviseService.getById(id);

        return RestResponse.success().put("usersupervise", sysUserSupervise);
    }

    /**
     * 新增
     *
     * @param sysUserSupervise sysUserSupervise
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody SysUserSuperviseEntity sysUserSupervise) {
        sysUserSupervise.setAdduser(getUserId());
        sysUserSupervise.setAddtime(new Date());
        sysUserSuperviseService.add(sysUserSupervise);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param sysUserSupervise sysUserSupervise
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody SysUserSuperviseEntity sysUserSupervise) {

        sysUserSuperviseService.update(sysUserSupervise);

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
    public RestResponse delete(@RequestBody String[] ids) {
        sysUserSuperviseService.deleteBatch(ids);

        return RestResponse.success();
    }
}
