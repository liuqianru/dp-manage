/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderProController.java
 * 包名称:com.platform.modules.qkjcus.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-23 16:50:14             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjcus.entity.QkjcusOrderProEntity;
import com.platform.modules.qkjcus.service.QkjcusOrderProService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 
 * @date 2021-12-23 16:50:14
 */
@RestController
@RequestMapping("qkjcus/orderpro")
public class QkjcusOrderProController extends AbstractController {
    @Autowired
    private QkjcusOrderProService qkjcusOrderProService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjcus:orderpro:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjcusOrderProEntity> list = qkjcusOrderProService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjcus:orderpro:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjcusOrderProService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjcus:orderpro:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjcusOrderProEntity qkjcusOrderPro = qkjcusOrderProService.getById(id);

        return RestResponse.success().put("orderpro", qkjcusOrderPro);
    }

    /**
     * 新增
     *
     * @param qkjcusOrderPro qkjcusOrderPro
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjcus:orderpro:save")
    public RestResponse save(@RequestBody QkjcusOrderProEntity qkjcusOrderPro) {

        qkjcusOrderProService.add(qkjcusOrderPro);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjcusOrderPro qkjcusOrderPro
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjcus:orderpro:update")
    public RestResponse update(@RequestBody QkjcusOrderProEntity qkjcusOrderPro) {

        qkjcusOrderProService.update(qkjcusOrderPro);

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
    @RequiresPermissions("qkjcus:orderpro:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjcusOrderProService.deleteBatch(ids);

        return RestResponse.success();
    }
}
