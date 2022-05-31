/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-23 15:23:32        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipProductEntity;
import com.platform.modules.qkjvip.service.QkjvipProductService;
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
 * @date 2021-08-23 15:23:32
 */
@RestController
@RequestMapping("qkjvip/product")
public class QkjvipProductController extends AbstractController {
    @Autowired
    private QkjvipProductService qkjvipProductService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
//    @RequiresPermissions("qkjvip:product:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipProductEntity> list = qkjvipProductService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
//    @RequiresPermissions("qkjvip:product:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipProductService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("qkjvip:product:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipProductEntity qkjvipProduct = qkjvipProductService.getById(id);

        return RestResponse.success().put("product", qkjvipProduct);
    }

    /**
     * 新增
     *
     * @param qkjvipProduct qkjvipProduct
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:product:save")
    public RestResponse save(@RequestBody QkjvipProductEntity qkjvipProduct) {
        qkjvipProduct.setCreateuser(getUserId());
        qkjvipProduct.setCreatetime(new Date());
        qkjvipProductService.add(qkjvipProduct);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipProduct qkjvipProduct
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:product:update")
    public RestResponse update(@RequestBody QkjvipProductEntity qkjvipProduct) {

        qkjvipProductService.update(qkjvipProduct);

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
    @RequiresPermissions("qkjvip:product:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipProductService.deleteBatch(ids);

        return RestResponse.success();
    }
}
