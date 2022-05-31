/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActothersignController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 15:07:44        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberActothersignEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActothersignService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-04-26 15:07:44
 */
@RestController
@RequestMapping("qkjvip/memberactothersign")
public class QkjvipMemberActothersignController extends AbstractController {
    @Autowired
    private QkjvipMemberActothersignService qkjvipMemberActothersignService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberactothersign:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActothersignEntity> list = qkjvipMemberActothersignService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberactothersign:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberActothersignService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberactothersign:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberActothersignEntity qkjvipMemberActothersign = qkjvipMemberActothersignService.getById(id);

        return RestResponse.success().put("memberactothersign", qkjvipMemberActothersign);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberActothersign qkjvipMemberActothersign
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberactothersign:save")
    public RestResponse save(@RequestBody QkjvipMemberActothersignEntity qkjvipMemberActothersign) {

        qkjvipMemberActothersignService.add(qkjvipMemberActothersign);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberActothersign qkjvipMemberActothersign
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberactothersign:update")
    public RestResponse update(@RequestBody QkjvipMemberActothersignEntity qkjvipMemberActothersign) {

        qkjvipMemberActothersignService.update(qkjvipMemberActothersign);

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
    @RequiresPermissions("qkjvip:memberactothersign:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberActothersignService.deleteBatch(ids);

        return RestResponse.success();
    }
}
