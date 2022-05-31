/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWareprohistoryController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-22 13:59:47        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareprohistoryEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWareprohistoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-12-22 13:59:47
 */
@RestController
@RequestMapping("qkjvip/orderwareprohistory")
public class QkjvipOrderWareprohistoryController extends AbstractController {
    @Autowired
    private QkjvipOrderWareprohistoryService qkjvipOrderWareprohistoryService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:orderwareprohistory:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderWareprohistoryEntity> list = qkjvipOrderWareprohistoryService.queryAll(params);

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
        Page page = qkjvipOrderWareprohistoryService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:orderwareprohistory:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = qkjvipOrderWareprohistoryService.getById(id);

        return RestResponse.success().put("orderwareprohistory", qkjvipOrderWareprohistory);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderWareprohistory qkjvipOrderWareprohistory
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:orderwareprohistory:save")
    public RestResponse save(@RequestBody QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory) {
        qkjvipOrderWareprohistory.setCreateon(new Date());
        qkjvipOrderWareprohistory.setCreator(getUserId());
        qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderWareprohistory qkjvipOrderWareprohistory
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:orderwareprohistory:update")
    public RestResponse update(@RequestBody QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory) {

        qkjvipOrderWareprohistoryService.update(qkjvipOrderWareprohistory);

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
    @RequiresPermissions("qkjvip:orderwareprohistory:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipOrderWareprohistoryService.deleteBatch(ids);

        return RestResponse.success();
    }
}
