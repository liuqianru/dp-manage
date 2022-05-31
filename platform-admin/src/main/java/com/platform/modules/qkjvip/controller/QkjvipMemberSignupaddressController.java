/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupaddressController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-10-15 17:21:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberSignupaddressEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberSignupaddressService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-10-15 17:21:03
 */
@RestController
@RequestMapping("qkjvip/membersignupaddress")
public class QkjvipMemberSignupaddressController extends AbstractController {
    @Autowired
    private QkjvipMemberSignupaddressService qkjvipMemberSignupaddressService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:membersignupaddress:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberSignupaddressEntity> list = qkjvipMemberSignupaddressService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membersignupaddress:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberSignupaddressService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membersignupaddress:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberSignupaddressEntity qkjvipMemberSignupaddress = qkjvipMemberSignupaddressService.getById(id);

        return RestResponse.success().put("membersignupaddress", qkjvipMemberSignupaddress);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberSignupaddress qkjvipMemberSignupaddress
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:membersignupaddress:save")
    public RestResponse save(@RequestBody QkjvipMemberSignupaddressEntity qkjvipMemberSignupaddress) {

        qkjvipMemberSignupaddressService.add(qkjvipMemberSignupaddress);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberSignupaddress qkjvipMemberSignupaddress
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membersignupaddress:update")
    public RestResponse update(@RequestBody QkjvipMemberSignupaddressEntity qkjvipMemberSignupaddress) {

        qkjvipMemberSignupaddressService.update(qkjvipMemberSignupaddress);

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
    @RequiresPermissions("qkjvip:membersignupaddress:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberSignupaddressService.deleteBatch(ids);

        return RestResponse.success();
    }
}
