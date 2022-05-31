/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireQuestionController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-16 10:27:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireQuestionEntity;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireQuestionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-07-16 10:27:08
 */
@RestController
@RequestMapping("qkjvip/questionnairequestion")
public class QkjvipQuestionnaireQuestionController extends AbstractController {
    @Autowired
    private QkjvipQuestionnaireQuestionService qkjvipQuestionnaireQuestionService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:questionnairequestion:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipQuestionnaireQuestionEntity> list = qkjvipQuestionnaireQuestionService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:questionnairequestion:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipQuestionnaireQuestionService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:questionnairequestion:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipQuestionnaireQuestionEntity qkjvipQuestionnaireQuestion = qkjvipQuestionnaireQuestionService.getById(id);

        return RestResponse.success().put("questionnairequestion", qkjvipQuestionnaireQuestion);
    }

    /**
     * 新增
     *
     * @param qkjvipQuestionnaireQuestion qkjvipQuestionnaireQuestion
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:questionnairequestion:save")
    public RestResponse save(@RequestBody QkjvipQuestionnaireQuestionEntity qkjvipQuestionnaireQuestion) {

        qkjvipQuestionnaireQuestionService.add(qkjvipQuestionnaireQuestion);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipQuestionnaireQuestion qkjvipQuestionnaireQuestion
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:questionnairequestion:update")
    public RestResponse update(@RequestBody QkjvipQuestionnaireQuestionEntity qkjvipQuestionnaireQuestion) {

        qkjvipQuestionnaireQuestionService.update(qkjvipQuestionnaireQuestion);

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
    @RequiresPermissions("qkjvip:questionnairequestion:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipQuestionnaireQuestionService.deleteBatch(ids);

        return RestResponse.success();
    }
}
