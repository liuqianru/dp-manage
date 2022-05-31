/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderPaylogController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-15 15:49:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipOrderPaylogEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderPaylogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
@RestController
@RequestMapping("qkjvip/orderpaylog")
public class QkjvipOrderPaylogController extends AbstractController {
    @Autowired
    private QkjvipOrderPaylogService qkjvipOrderPaylogService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:orderpaylog:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderPaylogEntity> list = qkjvipOrderPaylogService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:orderpaylog:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipOrderPaylogService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:orderpaylog:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipOrderPaylogEntity qkjvipOrderPaylog = qkjvipOrderPaylogService.getById(id);

        return RestResponse.success().put("orderpaylog", qkjvipOrderPaylog);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderPaylog qkjvipOrderPaylog
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:orderpaylog:save")
    public RestResponse save(@RequestBody QkjvipOrderPaylogEntity qkjvipOrderPaylog) {

        qkjvipOrderPaylogService.add(qkjvipOrderPaylog);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderPaylog qkjvipOrderPaylog
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:orderpaylog:update")
    public RestResponse update(@RequestBody QkjvipOrderPaylogEntity qkjvipOrderPaylog) {

        qkjvipOrderPaylogService.update(qkjvipOrderPaylog);

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
    @RequiresPermissions("qkjvip:orderpaylog:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipOrderPaylogService.deleteBatch(ids);

        return RestResponse.success();
    }
}
