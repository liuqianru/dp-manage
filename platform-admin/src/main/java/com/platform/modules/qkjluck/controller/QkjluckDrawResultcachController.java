/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawResultcachController.java
 * 包名称:com.platform.modules.qkjluck.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-18 14:27:03             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjluck.entity.QkjluckDrawResultcachEntity;
import com.platform.modules.qkjluck.service.QkjluckDrawResultcachService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 
 * @date 2021-11-18 14:27:03
 */
@RestController
@RequestMapping("qkjluck/drawresultcach")
public class QkjluckDrawResultcachController extends AbstractController {
    @Autowired
    private QkjluckDrawResultcachService qkjluckDrawResultcachService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjluck:drawresultcach:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjluckDrawResultcachEntity> list = qkjluckDrawResultcachService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjluck:drawresultcach:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjluckDrawResultcachService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjluck:drawresultcach:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjluckDrawResultcachEntity qkjluckDrawResultcach = qkjluckDrawResultcachService.getById(id);

        return RestResponse.success().put("drawresultcach", qkjluckDrawResultcach);
    }

    /**
     * 新增
     *
     * @param qkjluckDrawResultcach qkjluckDrawResultcach
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjluck:drawresultcach:save")
    public RestResponse save(@RequestBody QkjluckDrawResultcachEntity qkjluckDrawResultcach) {

        qkjluckDrawResultcachService.add(qkjluckDrawResultcach);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjluckDrawResultcach qkjluckDrawResultcach
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjluck:drawresultcach:update")
    public RestResponse update(@RequestBody QkjluckDrawResultcachEntity qkjluckDrawResultcach) {

        qkjluckDrawResultcachService.update(qkjluckDrawResultcach);

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
    @RequiresPermissions("qkjluck:drawresultcach:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjluckDrawResultcachService.deleteBatch(ids);

        return RestResponse.success();
    }
}
