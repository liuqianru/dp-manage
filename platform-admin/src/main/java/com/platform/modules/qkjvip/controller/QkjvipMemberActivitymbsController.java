/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymbsController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-18 13:35:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivitymbsEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActivitymbsService;
import com.platform.modules.util.ExportExcelUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-09-18 13:35:24
 */
@RestController
@RequestMapping("qkjvip/memberactivitymbs")
public class QkjvipMemberActivitymbsController extends AbstractController {
    @Autowired
    private QkjvipMemberActivitymbsService qkjvipMemberActivitymbsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberactivitymbs:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActivitymbsEntity> list = qkjvipMemberActivitymbsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberactivitymbs:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberActivitymbsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 分页查询根据活动id
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/listCount")
    public RestResponse listCount(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberActivitymbsService.queryPageCount(params);
        return RestResponse.success().put("page", page);
    }

    /**
     * 查询个人参加活动列表-liuqianru add
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/personalList")
    public RestResponse personalList(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberActivitymbsService.queryPageByPersonal(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 导出会员数据
     */
    @SysLog("导出结果")
    @RequestMapping("/export")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        try {
            List<QkjvipMemberActivitymbsEntity> list=qkjvipMemberActivitymbsService.queryAllResultExcel(params);
            ExportExcelUtils.exportExcel(list,"活动信息表","活动信息",QkjvipMemberActivitymbsEntity.class,"会员信息",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 分页查询根据活动id
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/listCountAll")
    public RestResponse listCountAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActivitymbsEntity> list  = qkjvipMemberActivitymbsService.queryAllCount(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberactivitymbs:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberActivitymbsEntity qkjvipMemberActivitymbs = qkjvipMemberActivitymbsService.getById(id);

        return RestResponse.success().put("memberactivitymbs", qkjvipMemberActivitymbs);
    }



    /**
     * 新增
     *
     * @param qkjvipMemberActivitymbs qkjvipMemberActivitymbs
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberactivitymbs:save")
    public RestResponse save(@RequestBody QkjvipMemberActivitymbsEntity qkjvipMemberActivitymbs) {

        qkjvipMemberActivitymbsService.add(qkjvipMemberActivitymbs);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberActivitymbs qkjvipMemberActivitymbs
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberactivitymbs:update")
    public RestResponse update(@RequestBody QkjvipMemberActivitymbsEntity qkjvipMemberActivitymbs) {

        qkjvipMemberActivitymbsService.update(qkjvipMemberActivitymbs);

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
    @RequiresPermissions("qkjvip:memberactivitymbs:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberActivitymbsService.deleteBatch(ids);

        return RestResponse.success();
    }
}
