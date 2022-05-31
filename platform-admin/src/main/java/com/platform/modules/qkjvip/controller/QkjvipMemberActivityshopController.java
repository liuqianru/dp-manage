/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityshopController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 16:21:27             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivityshopEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActivityshopService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 
 * @date 2022-04-22 16:21:27
 */
@RestController
@RequestMapping("qkjvip/memberactivityshop")
public class QkjvipMemberActivityshopController extends AbstractController {
    @Autowired
    private QkjvipMemberActivityshopService qkjvipMemberActivityshopService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActivityshopEntity> list = qkjvipMemberActivityshopService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberActivityshopService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberactivityshop:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberActivityshopEntity qkjvipMemberActivityshop = qkjvipMemberActivityshopService.getById(id);

        return RestResponse.success().put("memberactivityshop", qkjvipMemberActivityshop);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberActivityshop qkjvipMemberActivityshop
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberactivityshop:save")
    public RestResponse save(@RequestBody QkjvipMemberActivityshopEntity qkjvipMemberActivityshop) {

        qkjvipMemberActivityshopService.add(qkjvipMemberActivityshop);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberActivityshop qkjvipMemberActivityshop
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberactivityshop:update")
    public RestResponse update(@RequestBody QkjvipMemberActivityshopEntity qkjvipMemberActivityshop) {

        qkjvipMemberActivityshopService.update(qkjvipMemberActivityshop);

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
    @RequiresPermissions("qkjvip:memberactivityshop:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberActivityshopService.deleteBatch(ids);

        return RestResponse.success();
    }
}
