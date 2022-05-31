/*
 * 项目名称:platform-plus
 * 类名称:SysRoleController.java
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
import com.platform.common.utils.StringUtils;
import com.platform.common.validator.ValidatorUtils;
import com.platform.modules.qkjvip.entity.QkjvipMemberChannelEntity;
import com.platform.modules.qkjvip.entity.QkjvipOrderWarehouseEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberChannelService;
import com.platform.modules.qkjvip.service.QkjvipOrderWarehouseService;
import com.platform.modules.sys.entity.SysRoleEntity;
import com.platform.modules.sys.service.*;
import com.platform.modules.util.ToolsUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author 李鹏军
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleOrgService sysRoleOrgService;
    @Autowired
    private QkjvipMemberChannelService qkjvipMemberChannelService;
    @Autowired
    SysCacheService sysCacheService;
    @Autowired
    SysMenuService sysMenuService;

    /**
     * 角色列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {

        //如需数据权限，在参数中添加DataScope
        params.put("dataScope", getDataScope());

        Page page = sysRoleService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 角色列表
     *
     * @return RestResponse
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public RestResponse select() {
        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        // 取出角色列表
        List<SysRoleEntity> list = sysRoleService.selectListByMap(params);
        // 取出渠道列表
//        List<QkjvipMemberChannelEntity> channelList = qkjvipMemberChannelService.selectListByMap(params);

//        params.put("roleList", list);
//        params.put("channelList", channelList);
        return RestResponse.success().put("list", list);
    }

    /**
     * 根据主键查询详情
     *
     * @param roleId 主键
     * @return RestResponse
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public RestResponse info(@PathVariable("roleId") String roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);
        if(role!=null&&role.getOrdertype()!=null){
            role.setOrdertypes(role.getOrdertype().split(","));
        }
        if(role!=null&&role.getWarelist()!=null){
            role.setWarelists(role.getWarelist().split(","));
        }

        //查询角色对应的菜单
        List<String> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的机构
        List<String> orgNoList = sysRoleOrgService.queryOrgNoList(roleId);
        role.setOrgNoList(orgNoList);

        return RestResponse.success().put("role", role);
    }

    /**
     * 保存角色
     *
     * @param role role
     * @return RestResponse
     */
    @SysLog("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public RestResponse save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);
        role.setOrdertype(ToolsUtil.Array2String(role.getOrdertypes() == null ? new String[] {} : role.getOrdertypes(), ","));
        role.setWarelist(ToolsUtil.Array2String(role.getWarelists() == null ? new String[] {} : role.getWarelists(), ","));
        role.setCreateUserId(getUserId());
        role.setCreateUserOrgNo(getOrgNo());
        if (role!=null&&role.getOrgNoList()!=null&&role.getOrgNoList().size()>0) {
            StringBuilder str = new StringBuilder();
            for (String orgNo : role.getOrgNoList()) {
                if(StringUtils.isNotBlank(orgNo)){
                    str.append(orgNo + ",");
                }
            }
            role.setOrglist(str+"");//冗余字段：记录人员角色对应的部门权限
        }
        sysRoleService.add(role);

        return RestResponse.success();
    }

    /**
     * 修改角色
     *
     * @param role role
     * @return RestResponse
     */
    @SysLog("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public RestResponse update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);
        role.setOrdertype(ToolsUtil.Array2String(role.getOrdertypes() == null ? new String[] {} : role.getOrdertypes(), ","));
        role.setWarelist(ToolsUtil.Array2String(role.getWarelists() == null ? new String[] {} : role.getWarelists(), ","));
        if (role!=null&&role.getOrgNoList()!=null&&role.getOrgNoList().size()>0) {
            StringBuilder str = new StringBuilder();
            for (String orgNo : role.getOrgNoList()) {
               if(StringUtils.isNotBlank(orgNo)){
                   str.append(orgNo + ",");
               }
            }
            role.setOrglist(str+"");//冗余字段：记录人员角色对应的部门权限
        }
        role.setCreateUserId(getUserId());
        role.setCreateUserOrgNo(getOrgNo());
        sysRoleService.update(role);

        //更新rold列表
        List rolesusers = sysMenuService.queryListRedis();
        saveDictRedis(rolesusers,"userRoleList","MTM_CACHE:IMMELISTALL:USERROLELIST");
        return RestResponse.success();
    }

    /**
     * 更新redis异步
     */
    @Async
    public void saveDictRedis (List list,String keyname, String key){
        sysCacheService.saveDictRedis(list,keyname,key);
    }
    /**
     * 删除角色
     *
     * @param roleIds roleIds
     * @return RestResponse
     */
    @SysLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public RestResponse delete(@RequestBody String[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        return RestResponse.success();
    }
}
