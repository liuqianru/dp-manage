/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntentionorderController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-14 09:44:56        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntentionorderEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntentionorderService;
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
 * @date 2021-01-14 09:44:56
 */
@RestController
@RequestMapping("qkjvip/memberintentionorder")
public class QkjvipMemberIntentionorderController extends AbstractController {
    @Autowired
    private QkjvipMemberIntentionorderService qkjvipMemberIntentionorderService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberintentionorder:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberIntentionorderEntity> list = qkjvipMemberIntentionorderService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberintentionorder:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberIntentionorderService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberintentionorder:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberIntentionorderEntity qkjvipMemberIntentionorder = qkjvipMemberIntentionorderService.getById(id);

        return RestResponse.success().put("memberintentionorder", qkjvipMemberIntentionorder);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberIntentionorder qkjvipMemberIntentionorder
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberintentionorder:save")
    public RestResponse save(@RequestBody QkjvipMemberIntentionorderEntity qkjvipMemberIntentionorder) {
        qkjvipMemberIntentionorder.setAddUser(getUserId());
        qkjvipMemberIntentionorder.setAddDept(getOrgNo());
        qkjvipMemberIntentionorder.setAddTime(new Date());
        qkjvipMemberIntentionorderService.add(qkjvipMemberIntentionorder);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberIntentionorder qkjvipMemberIntentionorder
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberintentionorder:update")
    public RestResponse update(@RequestBody QkjvipMemberIntentionorderEntity qkjvipMemberIntentionorder) {

        qkjvipMemberIntentionorderService.update(qkjvipMemberIntentionorder);

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
    @RequiresPermissions("qkjvip:memberintentionorder:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberIntentionorderService.deleteBatch(ids);

        return RestResponse.success();
    }
}
