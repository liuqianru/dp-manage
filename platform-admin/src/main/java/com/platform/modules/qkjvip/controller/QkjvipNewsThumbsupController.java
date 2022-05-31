/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsThumbsupController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-04 11:35:48        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.cmnt.entity.CmntMgmtThumbsupEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipNewsThumbsupEntity;
import com.platform.modules.qkjvip.service.QkjvipNewsThumbsupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-06-04 11:35:48
 */
@RestController
@RequestMapping("qkjvip/newsthumbsup")
public class QkjvipNewsThumbsupController extends AbstractController {
    @Autowired
    private QkjvipNewsThumbsupService qkjvipNewsThumbsupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipNewsThumbsupEntity> list = qkjvipNewsThumbsupService.queryAll(params);

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
        Page page = qkjvipNewsThumbsupService.queryPage(params);

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
        QkjvipNewsThumbsupEntity qkjvipNewsThumbsup = qkjvipNewsThumbsupService.getById(id);

        return RestResponse.success().put("newsthumbsup", qkjvipNewsThumbsup);
    }

    /**
     * 新增
     *
     * @param qkjvipNewsThumbsup qkjvipNewsThumbsup
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipNewsThumbsupEntity qkjvipNewsThumbsup) {
        qkjvipNewsThumbsup.setCreatedate(new Date());
        qkjvipNewsThumbsupService.add(qkjvipNewsThumbsup);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipNewsThumbsup qkjvipNewsThumbsup
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjvipNewsThumbsupEntity qkjvipNewsThumbsup) {

        qkjvipNewsThumbsupService.update(qkjvipNewsThumbsup);

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
        qkjvipNewsThumbsupService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 删除点赞
     *
     * @param qkjvipNewsThumbsup qkjvipNewsThumbsup
     * @return RestResponse
     */
    @SysLog("删除点赞")
    @RequestMapping("/doDelete")
    public RestResponse doDelete(@RequestBody QkjvipNewsThumbsupEntity qkjvipNewsThumbsup) {
        qkjvipNewsThumbsupService.doDelete(qkjvipNewsThumbsup);

        return RestResponse.success();
    }
}
