/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtThumbsupController.java
 * 包名称:com.platform.modules.cmnt.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:10        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.cmnt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.cmnt.entity.CmntMgmtThumbsupEntity;
import com.platform.modules.cmnt.service.CmntMgmtThumbsupService;
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
 * @date 2021-05-25 17:16:10
 */
@RestController
@RequestMapping("cmnt/mgmtthumbsup")
public class CmntMgmtThumbsupController extends AbstractController {
    @Autowired
    private CmntMgmtThumbsupService cmntMgmtThumbsupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("cmnt:mgmtthumbsup:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<CmntMgmtThumbsupEntity> list = cmntMgmtThumbsupService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("cmnt:mgmtthumbsup:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = cmntMgmtThumbsupService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cmnt:mgmtthumbsup:info")
    public RestResponse info(@PathVariable("id") String id) {
        CmntMgmtThumbsupEntity cmntMgmtThumbsup = cmntMgmtThumbsupService.getById(id);

        return RestResponse.success().put("mgmtthumbsup", cmntMgmtThumbsup);
    }

    /**
     * 新增
     *
     * @param cmntMgmtThumbsup cmntMgmtThumbsup
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
//    @RequiresPermissions("cmnt:mgmtthumbsup:save")
    public RestResponse save(@RequestBody CmntMgmtThumbsupEntity cmntMgmtThumbsup) {
        cmntMgmtThumbsup.setCreatedate(new Date());
        cmntMgmtThumbsupService.add(cmntMgmtThumbsup);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param cmntMgmtThumbsup cmntMgmtThumbsup
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("cmnt:mgmtthumbsup:update")
    public RestResponse update(@RequestBody CmntMgmtThumbsupEntity cmntMgmtThumbsup) {

        cmntMgmtThumbsupService.update(cmntMgmtThumbsup);

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
    @RequiresPermissions("cmnt:mgmtthumbsup:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        cmntMgmtThumbsupService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 删除点赞
     *
     * @param cmntMgmtThumbsup cmntMgmtThumbsup
     * @return RestResponse
     */
    @SysLog("删除点赞")
    @RequestMapping("/doDelete")
    public RestResponse doDelete(@RequestBody CmntMgmtThumbsupEntity cmntMgmtThumbsup) {
        cmntMgmtThumbsupService.doDelete(cmntMgmtThumbsup);

        return RestResponse.success();
    }
}
