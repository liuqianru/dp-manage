/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitGivedetailController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-24 09:16:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberVisitGivedetailEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberVisitGivedetailService;
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
 * @date 2021-08-24 09:16:08
 */
@RestController
@RequestMapping("qkjvip/membervisitgivedetail")
public class QkjvipMemberVisitGivedetailController extends AbstractController {
    @Autowired
    private QkjvipMemberVisitGivedetailService qkjvipMemberVisitGivedetailService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
//    @RequiresPermissions("qkjvip:membervisitgivedetail:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberVisitGivedetailEntity> list = qkjvipMemberVisitGivedetailService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
//    @RequiresPermissions("qkjvip:membervisitgivedetail:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberVisitGivedetailService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("qkjvip:membervisitgivedetail:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberVisitGivedetailEntity qkjvipMemberVisitGivedetail = qkjvipMemberVisitGivedetailService.getById(id);

        return RestResponse.success().put("membervisitgivedetail", qkjvipMemberVisitGivedetail);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberVisitGivedetail qkjvipMemberVisitGivedetail
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
//    @RequiresPermissions("qkjvip:membervisitgivedetail:save")
    public RestResponse save(@RequestBody QkjvipMemberVisitGivedetailEntity qkjvipMemberVisitGivedetail) {
        qkjvipMemberVisitGivedetail.setAdduser(getUserId());
        qkjvipMemberVisitGivedetail.setAdddept(getOrgNo());
        qkjvipMemberVisitGivedetail.setAddtime(new Date());
        qkjvipMemberVisitGivedetailService.add(qkjvipMemberVisitGivedetail);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberVisitGivedetail qkjvipMemberVisitGivedetail
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
//    @RequiresPermissions("qkjvip:membervisitgivedetail:update")
    public RestResponse update(@RequestBody QkjvipMemberVisitGivedetailEntity qkjvipMemberVisitGivedetail) {

        qkjvipMemberVisitGivedetailService.update(qkjvipMemberVisitGivedetail);

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
//    @RequiresPermissions("qkjvip:membervisitgivedetail:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberVisitGivedetailService.deleteBatch(ids);

        return RestResponse.success();
    }
}
