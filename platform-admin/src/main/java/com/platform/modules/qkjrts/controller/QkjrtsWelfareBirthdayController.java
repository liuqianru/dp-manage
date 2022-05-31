/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareBirthdayController.java
 * 包名称:com.platform.modules.qkjrts.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-13 12:20:09        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareBirthdayEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareBirthdayService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2022-04-13 12:20:09
 */
@RestController
@RequestMapping("qkjrts/welfarebirthday")
public class QkjrtsWelfareBirthdayController extends AbstractController {
    @Autowired
    private QkjrtsWelfareBirthdayService qkjrtsWelfareBirthdayService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrts:welfarebirthday:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrtsWelfareBirthdayEntity> list = qkjrtsWelfareBirthdayService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrts:welfarebirthday:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjrtsWelfareBirthdayService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrts:welfarebirthday:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday = qkjrtsWelfareBirthdayService.getById(id);

        return RestResponse.success().put("welfarebirthday", qkjrtsWelfareBirthday);
    }

    /**
     * 新增
     *
     * @param qkjrtsWelfareBirthday qkjrtsWelfareBirthday
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrts:welfarebirthday:save")
    public RestResponse save(@RequestBody QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday) {
        qkjrtsWelfareBirthday.setCreateuser(getUserId());
        qkjrtsWelfareBirthday.setCreatetime(new Date());
        qkjrtsWelfareBirthdayService.add(qkjrtsWelfareBirthday);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrtsWelfareBirthday qkjrtsWelfareBirthday
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrts:welfarebirthday:update")
    public RestResponse update(@RequestBody QkjrtsWelfareBirthdayEntity qkjrtsWelfareBirthday) {
        qkjrtsWelfareBirthdayService.update(qkjrtsWelfareBirthday);

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
    @RequiresPermissions("qkjrts:welfarebirthday:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrtsWelfareBirthdayService.deleteBatch(ids);

        return RestResponse.success();
    }

}
