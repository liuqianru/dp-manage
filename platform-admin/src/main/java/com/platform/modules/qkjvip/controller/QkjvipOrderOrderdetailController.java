/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderOrderdetailController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-10 11:37:08        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipOrderOrderdetailEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderOrderdetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-03-10 11:37:08
 */
@RestController
@RequestMapping("qkjvip/orderorderdetail")
public class QkjvipOrderOrderdetailController extends AbstractController {
    @Autowired
    private QkjvipOrderOrderdetailService qkjvipOrderOrderdetailService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:orderorderdetail:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderOrderdetailEntity> list = qkjvipOrderOrderdetailService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:orderorderdetail:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipOrderOrderdetailService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:orderorderdetail:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipOrderOrderdetailEntity qkjvipOrderOrderdetail = qkjvipOrderOrderdetailService.getById(id);

        return RestResponse.success().put("orderorderdetail", qkjvipOrderOrderdetail);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderOrderdetail qkjvipOrderOrderdetail
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:orderorderdetail:save")
    public RestResponse save(@RequestBody QkjvipOrderOrderdetailEntity qkjvipOrderOrderdetail) {

        qkjvipOrderOrderdetailService.add(qkjvipOrderOrderdetail);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderOrderdetail qkjvipOrderOrderdetail
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:orderorderdetail:update")
    public RestResponse update(@RequestBody QkjvipOrderOrderdetailEntity qkjvipOrderOrderdetail) {

        qkjvipOrderOrderdetailService.update(qkjvipOrderOrderdetail);

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
    @RequiresPermissions("qkjvip:orderorderdetail:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipOrderOrderdetailService.deleteBatch(ids);

        return RestResponse.success();
    }
}
