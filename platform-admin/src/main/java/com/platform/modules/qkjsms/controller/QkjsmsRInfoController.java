/*
 * 项目名称:platform-plus
 * 类名称:QkjsmsRInfoController.java
 * 包名称:com.platform.modules.qkjsms.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-15 14:31:17        sun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjsms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjsms.entity.QkjsmsRInfoEntity;
import com.platform.modules.qkjsms.service.QkjsmsRInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author sun
 * @date 2022-03-15 14:31:17
 */
@RestController
@RequestMapping("qkjsms/rinfo")
public class QkjsmsRInfoController extends AbstractController {
    @Autowired
    private QkjsmsRInfoService qkjsmsRInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjsms:rinfo:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjsmsRInfoEntity> list = qkjsmsRInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjsms:rinfo:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjsmsRInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjsms:rinfo:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjsmsRInfoEntity qkjsmsRInfo = qkjsmsRInfoService.getById(id);

        return RestResponse.success().put("rinfo", qkjsmsRInfo);
    }

    /**
     * 新增
     *
     * @param qkjsmsRInfo qkjsmsRInfo
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjsms:rinfo:save")
    public RestResponse save(@RequestBody QkjsmsRInfoEntity qkjsmsRInfo) {

        qkjsmsRInfoService.add(qkjsmsRInfo);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjsmsRInfo qkjsmsRInfo
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjsms:rinfo:update")
    public RestResponse update(@RequestBody QkjsmsRInfoEntity qkjsmsRInfo) {

        qkjsmsRInfoService.update(qkjsmsRInfo);

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
    @RequiresPermissions("qkjsms:rinfo:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjsmsRInfoService.deleteBatch(ids);

        return RestResponse.success();
    }
}
