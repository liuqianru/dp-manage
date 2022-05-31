/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberUnitnameController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-17 10:44:55        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberUnitnameEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberUnitnameService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-11-17 10:44:55
 */
@RestController
@RequestMapping("qkjvip/memberunitname")
public class QkjvipMemberUnitnameController extends AbstractController {
    @Autowired
    private QkjvipMemberUnitnameService qkjvipMemberUnitnameService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberUnitnameEntity> list = qkjvipMemberUnitnameService.queryAll(params);

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
        Page page = qkjvipMemberUnitnameService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param unitname 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{unitname}")
    public RestResponse info(@PathVariable("unitname") String unitname) {
        QkjvipMemberUnitnameEntity qkjvipMemberUnitname = qkjvipMemberUnitnameService.getById(unitname);

        return RestResponse.success().put("memberunitname", qkjvipMemberUnitname);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberUnitname qkjvipMemberUnitname
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipMemberUnitnameEntity qkjvipMemberUnitname) {

        qkjvipMemberUnitnameService.add(qkjvipMemberUnitname);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberUnitname qkjvipMemberUnitname
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjvipMemberUnitnameEntity qkjvipMemberUnitname) {

        qkjvipMemberUnitnameService.update(qkjvipMemberUnitname);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param unitnames unitnames
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    public RestResponse delete(@RequestBody String[] unitnames) {
        qkjvipMemberUnitnameService.deleteBatch(unitnames);

        return RestResponse.success();
    }
}
