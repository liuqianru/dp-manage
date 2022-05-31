/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitMaterialController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-22 10:51:34        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitMaterialEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitMaterialService;
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
 * @date 2021-03-22 10:51:34
 */
@RestController
@RequestMapping("qkjvip/membervisitmaterial")
public class QkjvipMemberVisitMaterialController extends AbstractController {
    @Autowired
    private QkjvipMemberVisitMaterialService qkjvipMemberVisitMaterialService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:membervisitmaterial:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberVisitMaterialEntity> list = qkjvipMemberVisitMaterialService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membervisitmaterial:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberVisitMaterialService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 分页查询所有物料（包含活动产生的物料）
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/alllist")
    @RequiresPermissions("qkjvip:membervisitmaterial:list")
    public RestResponse alllist(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberVisitMaterialService.queryPageAll(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membervisitmaterial:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberVisitMaterialEntity qkjvipMemberVisitMaterial = qkjvipMemberVisitMaterialService.getById(id);

        return RestResponse.success().put("membervisitmaterial", qkjvipMemberVisitMaterial);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberVisitMaterial qkjvipMemberVisitMaterial
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:membervisitmaterial:save")
    public RestResponse save(@RequestBody QkjvipMemberVisitMaterialEntity qkjvipMemberVisitMaterial) {

        qkjvipMemberVisitMaterial.setAddUser(getUserId());
        qkjvipMemberVisitMaterial.setAddDept(getOrgNo());
        qkjvipMemberVisitMaterial.setAddTime(new Date());
        qkjvipMemberVisitMaterialService.add(qkjvipMemberVisitMaterial);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberVisitMaterial qkjvipMemberVisitMaterial
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membervisitmaterial:update")
    public RestResponse update(@RequestBody QkjvipMemberVisitMaterialEntity qkjvipMemberVisitMaterial) {

        qkjvipMemberVisitMaterialService.update(qkjvipMemberVisitMaterial);

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
    @RequiresPermissions("qkjvip:membervisitmaterial:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberVisitMaterialService.deleteBatch(ids);

        return RestResponse.success();
    }
}
