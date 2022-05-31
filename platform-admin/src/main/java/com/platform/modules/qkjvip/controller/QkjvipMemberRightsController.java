/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberRightsController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 16:38:38        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.QkjvipMemberRightsResultEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberRightsEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberRightsService;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-04-26 16:38:38
 */
@RestController
@RequestMapping("qkjvip/memberrights")
public class QkjvipMemberRightsController extends AbstractController {
    @Autowired
    private QkjvipMemberRightsService qkjvipMemberRightsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberrights:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberRightsEntity> grouplist = qkjvipMemberRightsService.queryByGroup(null);
        params.put("sort", "T.MEMBERLEVEL desc, T.MEMBERRIGHT asc");
        List<QkjvipMemberRightsEntity> rightlist = qkjvipMemberRightsService.queryAll(params);
        List<QkjvipMemberRightsResultEntity> list = new ArrayList<>();
        for (int i = 0; i < grouplist.size(); i++) {
            List<QkjvipMemberRightsEntity> groupRightlist = new ArrayList<>();
            QkjvipMemberRightsResultEntity rightsResult = new QkjvipMemberRightsResultEntity();
            rightsResult.setMemberlevel(grouplist.get(i).getMemberlevel());
            rightsResult.setMemberlevelname(grouplist.get(i).getMemberlevelname());
            rightsResult.setRightvalue(grouplist.get(i).getRightvalue());
            List<Integer> checkedlist = new ArrayList<Integer>();
            for (int j = 0; j < rightlist.size(); j++) {
                if (grouplist.get(i).getMemberlevel() == rightlist.get(j).getMemberlevel()) {
                    groupRightlist.add(rightlist.get(j));
                    if (rightlist.get(j).getIshave() != null && rightlist.get(j).getIshave()) {
                        checkedlist.add(rightlist.get(j).getMemberright());
                    }
                }
            }
            rightsResult.setRightsList(groupRightlist);
            rightsResult.setCheckedList(checkedlist);
            list.add(rightsResult);
        }

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberrights:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberRightsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberrights:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberRightsEntity qkjvipMemberRights = qkjvipMemberRightsService.getById(id);

        return RestResponse.success().put("memberrights", qkjvipMemberRights);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberRights qkjvipMemberRights
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberrights:save")
    public RestResponse save(@RequestBody QkjvipMemberRightsEntity qkjvipMemberRights) {

        qkjvipMemberRightsService.add(qkjvipMemberRights);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberRights qkjvipMemberRights
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberrights:update")
    public RestResponse update(@RequestBody QkjvipMemberRightsEntity qkjvipMemberRights) {

        qkjvipMemberRightsService.update(qkjvipMemberRights);

        return RestResponse.success();
    }

    /**
     * 批量修改
     *
     * @param qkjvipMemberRights qkjvipMemberRights
     * @return RestResponse
     */
    @SysLog("批量修改")
    @RequestMapping("/updateByMemberlevel")
    @RequiresPermissions("qkjvip:memberrights:update")
    public RestResponse updateByMemberlevel(@RequestBody QkjvipMemberRightsEntity qkjvipMemberRights) {

        qkjvipMemberRightsService.updateByMemberlevel(qkjvipMemberRights);

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
    @RequiresPermissions("qkjvip:memberrights:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberRightsService.deleteBatch(ids);

        return RestResponse.success();
    }
}
