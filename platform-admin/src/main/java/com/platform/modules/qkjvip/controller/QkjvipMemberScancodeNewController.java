/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberScancodeNewController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-30 09:35:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.QkjvipMemberScancodeNewEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.service.QkjvipMemberScancodeNewService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author hanjie
 * @date 2022-03-30 09:35:18
 */
@RestController
@RequestMapping("qkjvip/memberscancodenew")
public class QkjvipMemberScancodeNewController extends AbstractController {
    @Autowired
    private QkjvipMemberScancodeNewService qkjvipMemberScancodeNewService;

    /**
     * 获取用户近一年的扫码记录
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/getScanList", method = RequestMethod.POST)
    public RestResponse getScanList(@RequestBody Map<String, Object> params) {
        if (!params.containsKey("unionid") || "".equals(params.get("unionid"))) {
            return RestResponse.error("unionid不允许为空");
        }
        List<QkjvipMemberScancodeNewEntity> list = qkjvipMemberScancodeNewService.getScanByYear(params);
        int cityCount = qkjvipMemberScancodeNewService.selectScanCityCount((String) params.get("unionid"));
        int totalCount = qkjvipMemberScancodeNewService.getScanCount((String) params.get("unionid"));
        return RestResponse.success().put("totalCount", totalCount).put("cityCount", cityCount).put("list", list);
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberscancodenew:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberScancodeNewEntity> list = qkjvipMemberScancodeNewService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberscancodenew:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberScancodeNewService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberscancodenew:info")
    public RestResponse info(@PathVariable("id") Long id) {
        QkjvipMemberScancodeNewEntity qkjvipMemberScancodeNew = qkjvipMemberScancodeNewService.getById(id);

        return RestResponse.success().put("memberscancodenew", qkjvipMemberScancodeNew);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberScancodeNew qkjvipMemberScancodeNew
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberscancodenew:save")
    public RestResponse save(@RequestBody QkjvipMemberScancodeNewEntity qkjvipMemberScancodeNew) {

        qkjvipMemberScancodeNewService.add(qkjvipMemberScancodeNew);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberScancodeNew qkjvipMemberScancodeNew
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberscancodenew:update")
    public RestResponse update(@RequestBody QkjvipMemberScancodeNewEntity qkjvipMemberScancodeNew) {

        qkjvipMemberScancodeNewService.update(qkjvipMemberScancodeNew);

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
    @RequiresPermissions("qkjvip:memberscancodenew:delete")
    public RestResponse delete(@RequestBody Long[] ids) {
        qkjvipMemberScancodeNewService.deleteBatch(ids);

        return RestResponse.success();
    }
}
