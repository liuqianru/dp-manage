/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderthpController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-13 09:27:21        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderthpEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrderthpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-01-13 09:27:21
 */
@RestController
@RequestMapping("qkjvip/memberorderthp")
public class QkjvipMemberOrderthpController extends AbstractController {
    @Autowired
    private QkjvipMemberOrderthpService qkjvipMemberOrderthpService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberorderthp:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberOrderthpEntity> list = qkjvipMemberOrderthpService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberorderthp:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberOrderthpService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberorderthp:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberOrderthpEntity qkjvipMemberOrderthp = qkjvipMemberOrderthpService.getById(id);

        return RestResponse.success().put("memberorderthp", qkjvipMemberOrderthp);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberOrderthp qkjvipMemberOrderthp
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberorderthp:save")
    public RestResponse save(@RequestBody QkjvipMemberOrderthpEntity qkjvipMemberOrderthp) {

        qkjvipMemberOrderthpService.add(qkjvipMemberOrderthp);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberOrderthp qkjvipMemberOrderthp
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberorderthp:update")
    public RestResponse update(@RequestBody QkjvipMemberOrderthpEntity qkjvipMemberOrderthp) {

        qkjvipMemberOrderthpService.update(qkjvipMemberOrderthp);

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
    @RequiresPermissions("qkjvip:memberorderthp:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberOrderthpService.deleteBatch(ids);

        return RestResponse.success();
    }
}
