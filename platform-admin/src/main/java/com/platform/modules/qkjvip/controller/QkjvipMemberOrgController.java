/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrgController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-24 09:57:29        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrgEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-09-24 09:57:29
 */
@RestController
@RequestMapping("qkjvip/memberorg")
public class QkjvipMemberOrgController extends AbstractController {
    @Autowired
    private QkjvipMemberOrgService qkjvipMemberOrgService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberorg:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberOrgEntity> list = qkjvipMemberOrgService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberorg:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberOrgService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param orgNo 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{orgNo}")
    @RequiresPermissions("qkjvip:memberorg:info")
    public RestResponse info(@PathVariable("orgNo") String orgNo) {
        QkjvipMemberOrgEntity qkjvipMemberOrg = qkjvipMemberOrgService.getById(orgNo);

        return RestResponse.success().put("memberorg", qkjvipMemberOrg);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberOrg qkjvipMemberOrg
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberorg:save")
    public RestResponse save(@RequestBody QkjvipMemberOrgEntity qkjvipMemberOrg) {

        qkjvipMemberOrgService.add(qkjvipMemberOrg);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberOrg qkjvipMemberOrg
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberorg:update")
    public RestResponse update(@RequestBody QkjvipMemberOrgEntity qkjvipMemberOrg) {

        qkjvipMemberOrgService.update(qkjvipMemberOrg);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param orgNos orgNos
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:memberorg:delete")
    public RestResponse delete(@RequestBody String[] orgNos) {
        qkjvipMemberOrgService.deleteBatch(orgNos);

        return RestResponse.success();
    }
}
