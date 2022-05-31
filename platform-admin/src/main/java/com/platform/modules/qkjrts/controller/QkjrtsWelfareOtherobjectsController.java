/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareOtherobjectsController.java
 * 包名称:com.platform.modules.qkjrts.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-04-22 14:44:54        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.ShiroUtils;
import com.platform.common.utils.StringUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareDetailsEntity;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareObjectEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareDetailsService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareMemberService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareOtherobjectsEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareOtherobjectsService;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2022-04-22 14:44:54
 */
@RestController
@RequestMapping("qkjrts/welfareotherobjects")
public class QkjrtsWelfareOtherobjectsController extends AbstractController {
    @Autowired
    private QkjrtsWelfareOtherobjectsService qkjrtsWelfareOtherobjectsService;
    @Autowired
    private QkjrtsWelfareMemberService qkjrtsWelfareMemberService;
    @Autowired
    private QkjrtsWelfareDetailsService qkjrtsWelfareDetailsService;
    @Autowired
    private SysDictService sysDictService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrts:welfareotherobjects:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrtsWelfareOtherobjectsEntity> list = qkjrtsWelfareOtherobjectsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrts:welfareotherobjects:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        if (!getUser().getUserName().contains("admin")) {
            if (StringUtils.isEmpty(ShiroUtils.getUserEntity().getDistributerid())) {  // 内部管理员
                params.put("objecttype", 2);
                params.put("currentuserid", getUserId());
            } else {  // 核心店/团购商
                params.put("objecttype", 1);
                params.put("listorgno", ContextHelper.getPermitDepts("qkjrts:welfareotherobjects:list"));
            }
        }
        Page page = qkjrtsWelfareOtherobjectsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrts:welfareotherobjects:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects = qkjrtsWelfareOtherobjectsService.getById(id);

        // 会员列表
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("mainid", id);
        qkjrtsWelfareOtherobjects.setMemberlist(qkjrtsWelfareMemberService.queryAll(map));

        // 福利列表
        map.clear();
        map.put("mainid", qkjrtsWelfareOtherobjects.getMainid());
        List<QkjrtsWelfareDetailsEntity> welfareDetails = new ArrayList<>();
        welfareDetails = qkjrtsWelfareDetailsService.queryAll(map);

        map.clear();
        map.put("code", "WELFARETYPE");
        List<SysDictEntity> dictList = new ArrayList<>();
        dictList = sysDictService.queryByCode(map);

        Integer integral = 0;
        List<QkjrtsWelfareObjectEntity> welfareObjects = new ArrayList<>();
        for (SysDictEntity dict : dictList) {
            QkjrtsWelfareObjectEntity welfareObject = new QkjrtsWelfareObjectEntity();
            List<QkjrtsWelfareDetailsEntity> welfaresonlist = new ArrayList<>();
            welfareObject.setType(Integer.parseInt(dict.getValue()));
            welfareObject.setTypename(dict.getName());
            for (QkjrtsWelfareDetailsEntity welfareDetail : welfareDetails) {
                // 福利类型相同
                if (welfareDetail.getWelfaretype() != null && ((welfareDetail.getWelfaretype() + "").equals(dict.getValue()))) {
                    welfaresonlist.add(welfareDetail);
                    if ("2".equals(dict.getValue())) {  // 积分的情况下
                        integral = welfareDetail.getIntegral();
                    }
                }
            }
            welfareObject.setItemchecked(welfaresonlist.size() > 0 ? true : false);
            welfareObject.setWelfaresonlist(welfaresonlist);
            welfareObjects.add(welfareObject);
        }
        return RestResponse.success().put("welfareotherobjects", qkjrtsWelfareOtherobjects).put("welfareObjects", welfareObjects).put("integral", integral);
    }
    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/getInfo")
    @RequiresPermissions("qkjrts:welfareotherobjects:info")
    public RestResponse getInfo(@RequestParam("id") String id, @RequestParam("type") String type) {
        QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects = qkjrtsWelfareOtherobjectsService.getById(id);

        // 会员列表
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("mainid", id);
        qkjrtsWelfareOtherobjects.setMemberlist(qkjrtsWelfareMemberService.queryAll(map));

        if (type != null && "view".equals(type)) {  // 点击查看时
            // 福利列表
            map.clear();
            map.put("mainid", qkjrtsWelfareOtherobjects.getMainid());
            List<QkjrtsWelfareDetailsEntity> welfareDetails = new ArrayList<>();
            welfareDetails = qkjrtsWelfareDetailsService.queryAll(map);

            map.clear();
            map.put("code", "WELFARETYPE");
            List<SysDictEntity> dictList = new ArrayList<>();
            dictList = sysDictService.queryByCode(map);

            Integer integral = 0;
            List<QkjrtsWelfareObjectEntity> welfareObjects = new ArrayList<>();
            for (SysDictEntity dict : dictList) {
                QkjrtsWelfareObjectEntity welfareObject = new QkjrtsWelfareObjectEntity();
                List<QkjrtsWelfareDetailsEntity> welfaresonlist = new ArrayList<>();
                welfareObject.setType(Integer.parseInt(dict.getValue()));
                welfareObject.setTypename(dict.getName());
                for (QkjrtsWelfareDetailsEntity welfareDetail : welfareDetails) {
                    // 福利类型相同
                    if (welfareDetail.getWelfaretype() != null && ((welfareDetail.getWelfaretype() + "").equals(dict.getValue()))) {
                        welfaresonlist.add(welfareDetail);
                        if ("2".equals(dict.getValue())) {  // 积分的情况下
                            integral = welfareDetail.getIntegral();
                        }
                    }
                }
                welfareObject.setItemchecked(welfaresonlist.size() > 0 ? true : false);
                welfareObject.setWelfaresonlist(welfaresonlist);
                welfareObjects.add(welfareObject);
            }
            return RestResponse.success().put("welfareotherobjects", qkjrtsWelfareOtherobjects).put("welfareObjects", welfareObjects).put("integral", integral);
        }
        return RestResponse.success().put("welfareotherobjects", qkjrtsWelfareOtherobjects);
    }

    /**
     * 新增
     *
     * @param qkjrtsWelfareOtherobjects qkjrtsWelfareOtherobjects
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrts:welfareotherobjects:save")
    public RestResponse save(@RequestBody QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects) {

        qkjrtsWelfareOtherobjectsService.add(qkjrtsWelfareOtherobjects);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrtsWelfareOtherobjects qkjrtsWelfareOtherobjects
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrts:welfareotherobjects:update")
    public RestResponse update(@RequestBody QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects) {

        qkjrtsWelfareOtherobjectsService.update(qkjrtsWelfareOtherobjects);

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
    @RequiresPermissions("qkjrts:welfareotherobjects:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrtsWelfareOtherobjectsService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 发送福利
     */
    @SysLog("发送福利")
    @RequestMapping("/send")
    public RestResponse send(@RequestBody QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects)throws IOException {
        qkjrtsWelfareOtherobjectsService.send(qkjrtsWelfareOtherobjects);
        return RestResponse.success();
    }
}
