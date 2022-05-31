/*
 * 项目名称:platform-plus
 * 类名称:SysLoginController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;

import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;

import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.entity.SysUserRegEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.sys.service.SysUserRedService;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录相关
 *
 * @author 李鹏军
 */
@RestController
public class SysUserRegController extends AbstractController {
    @Autowired
    private SysUserRedService sysUserRedService;
    @Autowired
    private SysSmsLogService sysSmsLogService;

    @GetMapping("/sys/userreglist")
    @RequiresPermissions("sys:userreg:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {

        //如需数据权限，在参数中添加DataScope
        params.put("dataScope", getDataScope());

        Page page = sysUserRedService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @GetMapping("/sys/userreginfo/{id}")
    @RequiresPermissions("sys:userreg:list")
    public RestResponse info(@PathVariable("id") String id) {
        SysUserRegEntity userreg = sysUserRedService.getById(id);
        return RestResponse.success().put("userreg", userreg);
    }
    /**
     * 注冊
     *
     * @param form 注冊
     * @return RestResponse
     */
    @SysLog("注册")
    @PostMapping("/sys/userreg")
    public RestResponse userreg(@RequestBody SysUserRegEntity form) {
        Map<String, Object> params = new HashMap<>(2);
        //params.put("dataScope", getDataScope());

        form.setAddTime(new Date());
        ValidatorUtils.validateEntity(form);
        sysUserRedService.add(form,params);
        return RestResponse.success();
    }

    /**
     * 修改用户
     *
     * @param user user
     * @return RestResponse
     */
    @SysLog("修改用户状态并发送短信")
    @PostMapping("/sys/userregupdate")
    @RequiresPermissions("sys:userreg:update")
    public RestResponse update(@RequestBody SysUserRegEntity user) {
        Map<String, Object> params = new HashMap<>(2);
        user.setCheckUser(getUserId());
        sysUserRedService.updateState(user);
        SysSmsLogEntity smsLog=new SysSmsLogEntity();
        smsLog.setContent("您好，您申请的供应商账号已通过审核。账户为："+user.getUserName()+"初始密码为：888888");
        SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSms(smsLog);
        return RestResponse.success();
    }
    /**
     * 删除供应商
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @PostMapping("/sys/userregdelete")
    @RequiresPermissions("sys:userreg:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        sysUserRedService.deleteBatch(ids);
        return RestResponse.success();
    }


}
