/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-23 16:37:04        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipContentEntity;
import com.platform.modules.qkjvip.service.QkjvipContentService;
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
 * @date 2021-03-23 16:37:04
 */
@RestController
@RequestMapping("qkjvip/content")
public class QkjvipContentController extends AbstractController {
    @Autowired
    private QkjvipContentService qkjvipContentService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:content:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:content:list", "T.add_user", "T.add_dept"));
        List<QkjvipContentEntity> list = qkjvipContentService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:content:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:content:list", "T.add_user", "T.add_dept"));
        Page page = qkjvipContentService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:content:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipContentEntity qkjvipContent = qkjvipContentService.getById(id);

        return RestResponse.success().put("content", qkjvipContent);
    }

    /**
     * 根据主键查询详情
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/getInfo")
    public RestResponse getInfo(@RequestParam Map<String, Object> params) {
        QkjvipContentEntity qkjvipContent = qkjvipContentService.getById(params.get("id").toString());

        return RestResponse.success().put("content", qkjvipContent);
    }

    /**
     * 新增
     *
     * @param qkjvipContent qkjvipContent
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:content:save")
    public RestResponse save(@RequestBody QkjvipContentEntity qkjvipContent) {
        qkjvipContent.setAddUser(getUserId());
        qkjvipContent.setAddDept(getOrgNo());
        qkjvipContent.setAddTime(new Date());
        qkjvipContentService.add(qkjvipContent);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipContent qkjvipContent
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:content:update")
    public RestResponse update(@RequestBody QkjvipContentEntity qkjvipContent) {
        qkjvipContent.setLmUser(getUserId());
        qkjvipContent.setLmDept(getOrgNo());
        qkjvipContent.setLmTime(new Date());
        qkjvipContentService.update(qkjvipContent);

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
    @RequiresPermissions("qkjvip:content:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipContentService.deleteBatch(ids);

        return RestResponse.success();
    }
}
