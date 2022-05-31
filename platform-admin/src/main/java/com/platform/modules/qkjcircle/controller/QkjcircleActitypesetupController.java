/*
 * 项目名称:platform-plus
 * 类名称:QkjcircleActitypesetupController.java
 * 包名称:com.platform.modules.qkjcircle.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-11 11:21:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcircle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjcircle.entity.QkjcircleActitypesetupEntity;
import com.platform.modules.qkjcircle.service.QkjcircleActitypesetupService;
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
 * @date 2021-08-11 11:21:03
 */
@RestController
@RequestMapping("qkjcircle/actitypesetup")
public class QkjcircleActitypesetupController extends AbstractController {
    @Autowired
    private QkjcircleActitypesetupService qkjcircleActitypesetupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjcircle:actitypesetup:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjcircleActitypesetupEntity> list = qkjcircleActitypesetupService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjcircle:actitypesetup:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjcircleActitypesetupService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjcircle:actitypesetup:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjcircleActitypesetupEntity qkjcircleActitypesetup = qkjcircleActitypesetupService.getById(id);

        return RestResponse.success().put("actitypesetup", qkjcircleActitypesetup);
    }

    /**
     * 新增
     *
     * @param qkjcircleActitypesetup qkjcircleActitypesetup
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjcircleActitypesetupEntity qkjcircleActitypesetup) {
        qkjcircleActitypesetup.setAddTime(new Date());
        qkjcircleActitypesetup.setAddUser(getUserId());
        qkjcircleActitypesetupService.add(qkjcircleActitypesetup);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjcircleActitypesetup qkjcircleActitypesetup
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjcircleActitypesetupEntity qkjcircleActitypesetup) {
        if (qkjcircleActitypesetup!=null&&(qkjcircleActitypesetup.getCirclelist()!=null && qkjcircleActitypesetup.getCirclelist().equals(""))) {
            qkjcircleActitypesetup.setCirclelist(null);
        }
        qkjcircleActitypesetupService.update(qkjcircleActitypesetup);

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
    @RequiresPermissions("qkjcircle:actitypesetup:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjcircleActitypesetupService.deleteBatch(ids);

        return RestResponse.success();
    }
}
