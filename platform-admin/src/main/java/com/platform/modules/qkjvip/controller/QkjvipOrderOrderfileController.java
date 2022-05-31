/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderOrderfileController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-10 11:37:09        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipOrderOrderfileEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderOrderfileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-03-10 11:37:09
 */
@RestController
@RequestMapping("qkjvip/orderorderfile")
public class QkjvipOrderOrderfileController extends AbstractController {
    @Autowired
    private QkjvipOrderOrderfileService qkjvipOrderOrderfileService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:orderorderfile:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderOrderfileEntity> list = qkjvipOrderOrderfileService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:orderorderfile:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipOrderOrderfileService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:orderorderfile:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipOrderOrderfileEntity qkjvipOrderOrderfile = qkjvipOrderOrderfileService.getById(id);

        return RestResponse.success().put("orderorderfile", qkjvipOrderOrderfile);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderOrderfile qkjvipOrderOrderfile
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:orderorderfile:save")
    public RestResponse save(@RequestBody QkjvipOrderOrderfileEntity qkjvipOrderOrderfile) {

        qkjvipOrderOrderfileService.add(qkjvipOrderOrderfile);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderOrderfile qkjvipOrderOrderfile
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:orderorderfile:update")
    public RestResponse update(@RequestBody QkjvipOrderOrderfileEntity qkjvipOrderOrderfile) {

        qkjvipOrderOrderfileService.update(qkjvipOrderOrderfile);

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
    @RequiresPermissions("qkjvip:orderorderfile:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipOrderOrderfileService.deleteBatch(ids);

        return RestResponse.success();
    }
}
