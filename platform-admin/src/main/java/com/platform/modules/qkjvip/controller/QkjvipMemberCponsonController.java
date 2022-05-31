/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCponsonController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-17 14:46:26        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberCponsonEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberCponsonService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-12-17 14:46:26
 */
@RestController
@RequestMapping("qkjvip/membercponson")
public class QkjvipMemberCponsonController extends AbstractController {
    @Autowired
    private QkjvipMemberCponsonService qkjvipMemberCponsonService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberCponsonEntity> list = qkjvipMemberCponsonService.queryAll(params);

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
        Page page = qkjvipMemberCponsonService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberCponsonEntity qkjvipMemberCponson = qkjvipMemberCponsonService.getById(id);

        return RestResponse.success().put("membercponson", qkjvipMemberCponson);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberCponson qkjvipMemberCponson
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipMemberCponsonEntity qkjvipMemberCponson) {

        qkjvipMemberCponsonService.add(qkjvipMemberCponson);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberCponson qkjvipMemberCponson
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjvipMemberCponsonEntity qkjvipMemberCponson) {

        qkjvipMemberCponsonService.update(qkjvipMemberCponson);

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
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberCponsonService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 根据会员及优惠券id查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryCponByMandId")
    public RestResponse queryCponByMandId(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberCponsonEntity> list = qkjvipMemberCponsonService.queryAll(params);

        return RestResponse.success().put("list", list);
    }
}
