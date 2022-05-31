/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareMemberController.java
 * 包名称:com.platform.modules.qkjrts.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareMemberEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareMemberService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@RestController
@RequestMapping("qkjrts/welfaremember")
public class QkjrtsWelfareMemberController extends AbstractController {
    @Autowired
    private QkjrtsWelfareMemberService qkjrtsWelfareMemberService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrtsWelfareMemberEntity> list = qkjrtsWelfareMemberService.queryAll(params);

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
        Page page = qkjrtsWelfareMemberService.queryPage(params);

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
        QkjrtsWelfareMemberEntity qkjrtsWelfareMember = qkjrtsWelfareMemberService.getById(id);

        return RestResponse.success().put("welfaremember", qkjrtsWelfareMember);
    }

    /**
     * 新增
     *
     * @param qkjrtsWelfareMember qkjrtsWelfareMember
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjrtsWelfareMemberEntity qkjrtsWelfareMember) {

        qkjrtsWelfareMemberService.add(qkjrtsWelfareMember);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrtsWelfareMember qkjrtsWelfareMember
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjrtsWelfareMemberEntity qkjrtsWelfareMember) {

        qkjrtsWelfareMemberService.update(qkjrtsWelfareMember);

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
        qkjrtsWelfareMemberService.deleteBatch(ids);

        return RestResponse.success();
    }
}
