/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireRecordController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-21 10:06:03        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireRecordEntity;
import com.platform.modules.qkjvip.service.QkjvipQuestionnaireRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-07-21 10:06:03
 */
@RestController
@RequestMapping("qkjvip/questionnairerecord")
public class QkjvipQuestionnaireRecordController extends AbstractController {
    @Autowired
    private QkjvipQuestionnaireRecordService qkjvipQuestionnaireRecordService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipQuestionnaireRecordEntity> list = qkjvipQuestionnaireRecordService.queryAll(params);

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
        Page page = qkjvipQuestionnaireRecordService.queryPage(params);

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
        QkjvipQuestionnaireRecordEntity qkjvipQuestionnaireRecord = qkjvipQuestionnaireRecordService.getById(id);

        return RestResponse.success().put("questionnairerecord", qkjvipQuestionnaireRecord);
    }

    /**
     * 新增
     *
     * @param qkjvipQuestionnaireRecord qkjvipQuestionnaireRecord
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipQuestionnaireRecordEntity qkjvipQuestionnaireRecord) {
        qkjvipQuestionnaireRecord.setAddtime(new Date());
        qkjvipQuestionnaireRecordService.add(qkjvipQuestionnaireRecord);

        return RestResponse.success();
    }

    /**
     * 批量新增
     *
     * @param list list
     * @return RestResponse
     */
    @SysLog("批量新增")
    @RequestMapping("/addBatch")
    public RestResponse addBatch(@RequestBody List<QkjvipQuestionnaireRecordEntity> list) {
        qkjvipQuestionnaireRecordService.addBatch(list);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipQuestionnaireRecord qkjvipQuestionnaireRecord
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjvipQuestionnaireRecordEntity qkjvipQuestionnaireRecord) {

        qkjvipQuestionnaireRecordService.update(qkjvipQuestionnaireRecord);

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
        qkjvipQuestionnaireRecordService.deleteBatch(ids);

        return RestResponse.success();
    }
}
