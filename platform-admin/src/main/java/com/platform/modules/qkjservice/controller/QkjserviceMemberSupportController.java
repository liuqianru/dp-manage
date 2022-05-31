/*
 * 项目名称:platform-plus
 * 类名称:QkjserviceMemberSupportController.java
 * 包名称:com.platform.modules.qkjservice.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-09 09:19:43        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjservice.entity.QkjserviceMemberSupportEntity;
import com.platform.modules.qkjservice.service.QkjserviceMemberSupportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-09-09 09:19:43
 */
@RestController
@RequestMapping("qkjservice/membersupport")
public class QkjserviceMemberSupportController extends AbstractController {
    @Autowired
    private QkjserviceMemberSupportService qkjserviceMemberSupportService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjservice:membersupport:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjserviceMemberSupportEntity> list = qkjserviceMemberSupportService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjservice:membersupport:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjserviceMemberSupportService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjservice:membersupport:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjserviceMemberSupportEntity qkjserviceMemberSupport = qkjserviceMemberSupportService.getById(id);

        return RestResponse.success().put("membersupport", qkjserviceMemberSupport);
    }

    /**
     * 新增
     *
     * @param qkjserviceMemberSupport qkjserviceMemberSupport
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjservice:membersupport:save")
    public RestResponse save(@RequestBody QkjserviceMemberSupportEntity qkjserviceMemberSupport) {
        qkjserviceMemberSupport.setAddtime(new Date());
        qkjserviceMemberSupport.setAdduser(getUserId());
        qkjserviceMemberSupportService.add(qkjserviceMemberSupport);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjserviceMemberSupport qkjserviceMemberSupport
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjservice:membersupport:update")
    public RestResponse update(@RequestBody QkjserviceMemberSupportEntity qkjserviceMemberSupport) {

        qkjserviceMemberSupportService.update(qkjserviceMemberSupport);

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
    @RequiresPermissions("qkjservice:membersupport:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjserviceMemberSupportService.deleteBatch(ids);

        return RestResponse.success();
    }
}
