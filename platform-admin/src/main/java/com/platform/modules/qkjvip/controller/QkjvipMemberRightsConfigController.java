/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberRightsConfigController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-30 09:23:01        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberRightsConfigEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberRightsConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-04-30 09:23:01
 */
@RestController
@RequestMapping("qkjvip/memberrightsconfig")
public class QkjvipMemberRightsConfigController extends AbstractController {
    @Autowired
    private QkjvipMemberRightsConfigService qkjvipMemberRightsConfigService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberrightsconfig:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberRightsConfigEntity> list = qkjvipMemberRightsConfigService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberrightsconfig:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberRightsConfigService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberrightsconfig:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberRightsConfigEntity qkjvipMemberRightsConfig = qkjvipMemberRightsConfigService.getById(id);

        return RestResponse.success().put("memberrightsconfig", qkjvipMemberRightsConfig);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberRightsConfig qkjvipMemberRightsConfig
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberrightsconfig:save")
    public RestResponse save(@RequestBody QkjvipMemberRightsConfigEntity qkjvipMemberRightsConfig) {
        qkjvipMemberRightsConfig.setConfigno(UUID.randomUUID().toString().replaceAll("-", ""));
        qkjvipMemberRightsConfig.setCreator(getUser().getUserName());
        qkjvipMemberRightsConfig.setCreateon(new Date());
        qkjvipMemberRightsConfigService.add(qkjvipMemberRightsConfig);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberRightsConfig qkjvipMemberRightsConfig
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberrightsconfig:update")
    public RestResponse update(@RequestBody QkjvipMemberRightsConfigEntity qkjvipMemberRightsConfig) {
        qkjvipMemberRightsConfigService.update(qkjvipMemberRightsConfig);

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
    @RequiresPermissions("qkjvip:memberrightsconfig:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberRightsConfigService.deleteBatch(ids);

        return RestResponse.success();
    }
}
