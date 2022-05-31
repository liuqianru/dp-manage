/*
 * 项目名称:platform-plus
 * 类名称:MemberPortraitsettingController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-28 09:46:33        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.MemberPortraitsettingEntity;
import com.platform.modules.qkjvip.service.MemberPortraitsettingService;
import com.platform.modules.sys.controller.AbstractController;
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
 * @date 2021-06-28 09:46:33
 */
@RestController
@RequestMapping("qkjvip/memberportraitsetting")
public class MemberPortraitsettingController extends AbstractController {
    @Autowired
    private MemberPortraitsettingService memberPortraitsettingService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
//    @RequiresPermissions("qkjvip:memberportraitsetting:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MemberPortraitsettingEntity> list = memberPortraitsettingService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
//    @RequiresPermissions("qkjvip:memberportraitsetting:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = memberPortraitsettingService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("qkjvip:memberportraitsetting:info")
    public RestResponse info(@PathVariable("id") String id) {
        MemberPortraitsettingEntity memberPortraitsetting = memberPortraitsettingService.getById(id);

        return RestResponse.success().put("memberportraitsetting", memberPortraitsetting);
    }

    /**
     * 新增
     *
     * @param memberPortraitsetting memberPortraitsetting
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
//    @RequiresPermissions("qkjvip:memberportraitsetting:save")
    public RestResponse save(@RequestBody MemberPortraitsettingEntity memberPortraitsetting) {
        memberPortraitsetting.setCreatedate(new Date());
        memberPortraitsetting.setCreateuser(getUserId());
        memberPortraitsettingService.add(memberPortraitsetting);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param memberPortraitsetting memberPortraitsetting
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
//    @RequiresPermissions("qkjvip:memberportraitsetting:update")
    public RestResponse update(@RequestBody MemberPortraitsettingEntity memberPortraitsetting) {
        memberPortraitsetting.setLmdate(new Date());
        memberPortraitsetting.setLmuser(getUserId());
        memberPortraitsettingService.update(memberPortraitsetting);

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
//    @RequiresPermissions("qkjvip:memberportraitsetting:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        memberPortraitsettingService.deleteBatch(ids);

        return RestResponse.success();
    }
}
