/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActotherbatchController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 15:07:44        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberActotherbatchEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActotherbatchService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-04-26 15:07:44
 */
@RestController
@RequestMapping("qkjvip/memberactotherbatch")
public class QkjvipMemberActotherbatchController extends AbstractController {
    @Autowired
    private QkjvipMemberActotherbatchService qkjvipMemberActotherbatchService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberactotherbatch:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActotherbatchEntity> list = qkjvipMemberActotherbatchService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberactotherbatch:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberActotherbatchService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param mainId 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{mainId}")
    @RequiresPermissions("qkjvip:memberactotherbatch:info")
    public RestResponse info(@PathVariable("mainId") String mainId) {
        QkjvipMemberActotherbatchEntity qkjvipMemberActotherbatch = qkjvipMemberActotherbatchService.getById(mainId);

        return RestResponse.success().put("memberactotherbatch", qkjvipMemberActotherbatch);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberActotherbatch qkjvipMemberActotherbatch
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberactotherbatch:save")
    public RestResponse save(@RequestBody QkjvipMemberActotherbatchEntity qkjvipMemberActotherbatch) {

        qkjvipMemberActotherbatchService.add(qkjvipMemberActotherbatch);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberActotherbatch qkjvipMemberActotherbatch
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberactotherbatch:update")
    public RestResponse update(@RequestBody QkjvipMemberActotherbatchEntity qkjvipMemberActotherbatch) {

        qkjvipMemberActotherbatchService.update(qkjvipMemberActotherbatch);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param mainIds mainIds
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:memberactotherbatch:delete")
    public RestResponse delete(@RequestBody String[] mainIds) {
        qkjvipMemberActotherbatchService.deleteBatch(mainIds);

        return RestResponse.success();
    }
}
