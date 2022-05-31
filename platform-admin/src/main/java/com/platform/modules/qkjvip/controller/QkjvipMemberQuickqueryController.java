/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberQuickqueryController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-26 14:05:17        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberQuickqueryEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberQuickqueryService;
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
 * @date 2021-07-26 14:05:17
 */
@RestController
@RequestMapping("qkjvip/memberquickquery")
public class QkjvipMemberQuickqueryController extends AbstractController {
    @Autowired
    private QkjvipMemberQuickqueryService qkjvipMemberQuickqueryService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberquickquery:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberQuickqueryEntity> list = qkjvipMemberQuickqueryService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberquickquery:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberQuickqueryService.queryPage(params);
        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberquickquery:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberQuickqueryEntity qkjvipMemberQuickquery = qkjvipMemberQuickqueryService.getById(id);

        return RestResponse.success().put("memberquickquery", qkjvipMemberQuickquery);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberQuickquery qkjvipMemberQuickquery
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberquickquery:save")
    public RestResponse save(@RequestBody QkjvipMemberQuickqueryEntity qkjvipMemberQuickquery) {
        qkjvipMemberQuickquery.setAdduser(getUserId());
        qkjvipMemberQuickquery.setAddtime(new Date());
        qkjvipMemberQuickqueryService.add(qkjvipMemberQuickquery);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberQuickquery qkjvipMemberQuickquery
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberquickquery:update")
    public RestResponse update(@RequestBody QkjvipMemberQuickqueryEntity qkjvipMemberQuickquery) {

        qkjvipMemberQuickqueryService.update(qkjvipMemberQuickquery);

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
    @RequiresPermissions("qkjvip:memberquickquery:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberQuickqueryService.deleteBatch(ids);

        return RestResponse.success();
    }
}
