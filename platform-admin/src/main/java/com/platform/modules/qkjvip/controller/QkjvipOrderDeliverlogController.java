/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderDeliverlogController.java
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
import com.platform.datascope.ContextHelper;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipOrderDeliverlogEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderDeliverlogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
@RestController
@RequestMapping("qkjvip/orderdeliverlog")
public class QkjvipOrderDeliverlogController extends AbstractController {
    @Autowired
    private QkjvipOrderDeliverlogService qkjvipOrderDeliverlogService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:orderdeliverlog:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderDeliverlogEntity> list = qkjvipOrderDeliverlogService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:orderdeliverlog:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        // 仓库权限
        Set<String> warlist = new HashSet<>();
        warlist= ContextHelper.setWarelistsm("qkjvip:orderwarehouse:list",getUserId());
        if (warlist.size()>0) {
            params.put("warlists",warlist);
        }
        Page page = qkjvipOrderDeliverlogService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:orderdeliverlog:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipOrderDeliverlogEntity qkjvipOrderDeliverlog = qkjvipOrderDeliverlogService.getById(id);

        return RestResponse.success().put("orderdeliverlog", qkjvipOrderDeliverlog);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderDeliverlog qkjvipOrderDeliverlog
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:orderdeliverlog:save")
    public RestResponse save(@RequestBody QkjvipOrderDeliverlogEntity qkjvipOrderDeliverlog) {

        qkjvipOrderDeliverlogService.add(qkjvipOrderDeliverlog);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderDeliverlog qkjvipOrderDeliverlog
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:orderdeliverlog:update")
    public RestResponse update(@RequestBody QkjvipOrderDeliverlogEntity qkjvipOrderDeliverlog) {

        qkjvipOrderDeliverlogService.update(qkjvipOrderDeliverlog);

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
    @RequiresPermissions("qkjvip:orderdeliverlog:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipOrderDeliverlogService.deleteBatch(ids);

        return RestResponse.success();
    }
}
