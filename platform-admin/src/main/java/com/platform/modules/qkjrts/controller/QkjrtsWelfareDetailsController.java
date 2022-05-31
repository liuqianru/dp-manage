/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareDetailsController.java
 * 包名称:com.platform.modules.qkjrts.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareDetailsEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareDetailsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@RestController
@RequestMapping("qkjrts/welfaredetails")
public class QkjrtsWelfareDetailsController extends AbstractController {
    @Autowired
    private QkjrtsWelfareDetailsService qkjrtsWelfareDetailsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrts:welfaredetails:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrtsWelfareDetailsEntity> list = qkjrtsWelfareDetailsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrts:welfaredetails:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrtsWelfareDetailsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrts:welfaredetails:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrtsWelfareDetailsEntity qkjrtsWelfareDetails = qkjrtsWelfareDetailsService.getById(id);

        return RestResponse.success().put("welfaredetails", qkjrtsWelfareDetails);
    }

    /**
     * 新增
     *
     * @param qkjrtsWelfareDetails qkjrtsWelfareDetails
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrts:welfaredetails:save")
    public RestResponse save(@RequestBody QkjrtsWelfareDetailsEntity qkjrtsWelfareDetails) {

        qkjrtsWelfareDetailsService.add(qkjrtsWelfareDetails);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrtsWelfareDetails qkjrtsWelfareDetails
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrts:welfaredetails:update")
    public RestResponse update(@RequestBody QkjrtsWelfareDetailsEntity qkjrtsWelfareDetails) {

        qkjrtsWelfareDetailsService.update(qkjrtsWelfareDetails);

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
    @RequiresPermissions("qkjrts:welfaredetails:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrtsWelfareDetailsService.deleteBatch(ids);

        return RestResponse.success();
    }
}
