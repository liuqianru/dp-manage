/*
 * 项目名称:platform-plus
 * 类名称:QkjvipTaglibsController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 14:20:07        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.service.MemberTagsService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipTaglibsEntity;
import com.platform.modules.qkjvip.service.QkjvipTaglibsService;
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
 * @date 2020-08-26 14:20:07
 */
@RestController
@RequestMapping("qkjvip/taglibs")
public class QkjvipTaglibsController extends AbstractController {
    @Autowired
    private QkjvipTaglibsService qkjvipTaglibsService;
    @Autowired
    private MemberTagsService memberTagsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
//    @RequiresPermissions("qkjvip:taglibs:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipTaglibsEntity> list = qkjvipTaglibsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:taglibs:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipTaglibsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param labelId 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{labelId}")
    @RequiresPermissions("qkjvip:taglibs:info")
    public RestResponse info(@PathVariable("labelId") String labelId) {
        QkjvipTaglibsEntity qkjvipTaglibs = qkjvipTaglibsService.getById(labelId);

        return RestResponse.success().put("taglibs", qkjvipTaglibs);
    }

    /**
     * 新增
     *
     * @param qkjvipTaglibs qkjvipTaglibs
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:taglibs:save")
    public RestResponse save(@RequestBody QkjvipTaglibsEntity qkjvipTaglibs) {

        qkjvipTaglibs.setAddUser(getUserId());
        qkjvipTaglibs.setAddDept(getOrgNo());
        qkjvipTaglibs.setAddTime(new Date());
        qkjvipTaglibsService.add(qkjvipTaglibs);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipTaglibs qkjvipTaglibs
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:taglibs:update")
    public RestResponse update(@RequestBody QkjvipTaglibsEntity qkjvipTaglibs) {

        qkjvipTaglibsService.update(qkjvipTaglibs);

        return RestResponse.success();
    }

    /**
     * 删除
     *
     * @param labelId 主键
     * @return RestResponse
     */
    @SysLog("删除")
    @PostMapping("/delete/{labelId}")
    @RequiresPermissions("qkjvip:taglibs:delete")
    public RestResponse delete(@PathVariable("labelId") String labelId) {

        qkjvipTaglibsService.delete(labelId);

        //同时删除会员标签对应关系表中的数据
        memberTagsService.delete(labelId);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param labelIds labelIds
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:taglibs:delete")
    public RestResponse delete(@RequestBody String[] labelIds) {
        qkjvipTaglibsService.deleteBatch(labelIds);

        return RestResponse.success();
    }
}
