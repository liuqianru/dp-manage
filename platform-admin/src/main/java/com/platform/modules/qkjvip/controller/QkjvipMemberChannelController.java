/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberChannelController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-09 10:55:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberChannelEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberChannelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2020-12-09 10:55:27
 */
@RestController
@RequestMapping("qkjvip/memberchannel")
public class QkjvipMemberChannelController extends AbstractController {
    @Autowired
    private QkjvipMemberChannelService qkjvipMemberChannelService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberchannel:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberChannelEntity> list = qkjvipMemberChannelService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberchannel:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberChannelService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberchannel:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberChannelEntity qkjvipMemberChannel = qkjvipMemberChannelService.getById(id);

        return RestResponse.success().put("memberchannel", qkjvipMemberChannel);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberChannel qkjvipMemberChannel
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberchannel:save")
    public RestResponse save(@RequestBody QkjvipMemberChannelEntity qkjvipMemberChannel) {

        qkjvipMemberChannelService.add(qkjvipMemberChannel);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberChannel qkjvipMemberChannel
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberchannel:update")
    public RestResponse update(@RequestBody QkjvipMemberChannelEntity qkjvipMemberChannel) {

        qkjvipMemberChannelService.update(qkjvipMemberChannel);

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
    @RequiresPermissions("qkjvip:memberchannel:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberChannelService.deleteBatch(ids);

        return RestResponse.success();
    }
}
