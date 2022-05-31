/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawAcitiityitemController.java
 * 包名称:com.platform.modules.qkjluck.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 17:26:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjluck.entity.QkjluckDrawAcitiityitemEntity;
import com.platform.modules.qkjluck.service.QkjluckDrawAcitiityitemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@RestController
@RequestMapping("qkjluck/drawacitiityitem")
public class QkjluckDrawAcitiityitemController extends AbstractController {
    @Autowired
    private QkjluckDrawAcitiityitemService qkjluckDrawAcitiityitemService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjluckDrawAcitiityitemEntity> list = qkjluckDrawAcitiityitemService.queryAll(params);

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
        Page page = qkjluckDrawAcitiityitemService.queryPage(params);

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
        QkjluckDrawAcitiityitemEntity qkjluckDrawAcitiityitem = qkjluckDrawAcitiityitemService.getById(id);

        return RestResponse.success().put("drawacitiityitem", qkjluckDrawAcitiityitem);
    }

    /**
     * 新增
     *
     * @param qkjluckDrawAcitiityitem qkjluckDrawAcitiityitem
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjluckDrawAcitiityitemEntity qkjluckDrawAcitiityitem) {

        qkjluckDrawAcitiityitemService.add(qkjluckDrawAcitiityitem);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjluckDrawAcitiityitem qkjluckDrawAcitiityitem
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjluckDrawAcitiityitemEntity qkjluckDrawAcitiityitem) {

        qkjluckDrawAcitiityitemService.update(qkjluckDrawAcitiityitem);

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
        qkjluckDrawAcitiityitemService.deleteBatch(ids);

        return RestResponse.success();
    }
}
