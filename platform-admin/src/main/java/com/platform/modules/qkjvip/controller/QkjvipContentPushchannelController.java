/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentPushchannelController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-24 15:41:46        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipContentPushchannelEntity;
import com.platform.modules.qkjvip.service.QkjvipContentPushchannelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-03-24 15:41:46
 */
@RestController
@RequestMapping("qkjvip/contentpushchannel")
public class QkjvipContentPushchannelController extends AbstractController {
    @Autowired
    private QkjvipContentPushchannelService qkjvipContentPushchannelService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:contentpushchannel:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipContentPushchannelEntity> list = qkjvipContentPushchannelService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:contentpushchannel:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipContentPushchannelService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:contentpushchannel:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipContentPushchannelEntity qkjvipContentPushchannel = qkjvipContentPushchannelService.getById(id);

        return RestResponse.success().put("contentpushchannel", qkjvipContentPushchannel);
    }

    /**
     * 新增
     *
     * @param qkjvipContentPushchannel qkjvipContentPushchannel
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:contentpushchannel:save")
    public RestResponse save(@RequestBody QkjvipContentPushchannelEntity qkjvipContentPushchannel) {

        qkjvipContentPushchannelService.add(qkjvipContentPushchannel);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipContentPushchannel qkjvipContentPushchannel
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:contentpushchannel:update")
    public RestResponse update(@RequestBody QkjvipContentPushchannelEntity qkjvipContentPushchannel) {

        qkjvipContentPushchannelService.update(qkjvipContentPushchannel);

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
    @RequiresPermissions("qkjvip:contentpushchannel:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipContentPushchannelService.deleteBatch(ids);

        return RestResponse.success();
    }
}
