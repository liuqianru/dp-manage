/*
 * 项目名称:platform-plus
 * 类名称:QkjmHProcessController.java
 * 包名称:com.platform.modules.qkjm.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-02 16:26:02        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjm.entity.QkjmHProcessEntity;
import com.platform.modules.qkjm.service.QkjmHProcessService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-11-02 16:26:02
 */
@RestController
@RequestMapping("qkjm/hprocess")
public class QkjmHProcessController extends AbstractController {
    @Autowired
    private QkjmHProcessService qkjmHProcessService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjm:hprocess:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjmHProcessEntity> list = qkjmHProcessService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjm:hprocess:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjmHProcessService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param uuid 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{uuid}")
    @RequiresPermissions("qkjm:hprocess:info")
    public RestResponse info(@PathVariable("uuid") String uuid) {
        QkjmHProcessEntity qkjmHProcess = qkjmHProcessService.getById(uuid);

        return RestResponse.success().put("hprocess", qkjmHProcess);
    }

    /**
     * 新增
     *
     * @param qkjmHProcess qkjmHProcess
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjm:hprocess:save")
    public RestResponse save(@RequestBody QkjmHProcessEntity qkjmHProcess) {

        qkjmHProcessService.add(qkjmHProcess);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjmHProcess qkjmHProcess
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjm:hprocess:update")
    public RestResponse update(@RequestBody QkjmHProcessEntity qkjmHProcess) {

        qkjmHProcessService.update(qkjmHProcess);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param uuids uuids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjm:hprocess:delete")
    public RestResponse delete(@RequestBody String[] uuids) {
        qkjmHProcessService.deleteBatch(uuids);

        return RestResponse.success();
    }
}
