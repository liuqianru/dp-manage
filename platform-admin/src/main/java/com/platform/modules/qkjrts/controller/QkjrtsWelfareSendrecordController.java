/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareSendrecordController.java
 * 包名称:com.platform.modules.qkjrts.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareSendrecordEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareSendrecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@RestController
@RequestMapping("qkjrts/welfaresendrecord")
public class QkjrtsWelfareSendrecordController extends AbstractController {
    @Autowired
    private QkjrtsWelfareSendrecordService qkjrtsWelfareSendrecordService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjrts:welfaresendrecord:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjrtsWelfareSendrecordEntity> list = qkjrtsWelfareSendrecordService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjrts:welfaresendrecord:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        if (!getUser().getUserName().contains("admin")) {
            params.put("listorgno", ContextHelper.getPermitDepts("qkjvip:member:list"));
            params.put("currentuserid", getUserId());
        }
        Page page = qkjrtsWelfareSendrecordService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjrts:welfaresendrecord:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjrtsWelfareSendrecordEntity qkjrtsWelfareSendrecord = qkjrtsWelfareSendrecordService.getById(id);

        return RestResponse.success().put("welfaresendrecord", qkjrtsWelfareSendrecord);
    }

    /**
     * 新增
     *
     * @param qkjrtsWelfareSendrecord qkjrtsWelfareSendrecord
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjrts:welfaresendrecord:save")
    public RestResponse save(@RequestBody QkjrtsWelfareSendrecordEntity qkjrtsWelfareSendrecord) {

        qkjrtsWelfareSendrecordService.add(qkjrtsWelfareSendrecord);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjrtsWelfareSendrecord qkjrtsWelfareSendrecord
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjrts:welfaresendrecord:update")
    public RestResponse update(@RequestBody QkjrtsWelfareSendrecordEntity qkjrtsWelfareSendrecord) {

        qkjrtsWelfareSendrecordService.update(qkjrtsWelfareSendrecord);

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
    @RequiresPermissions("qkjrts:welfaresendrecord:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjrtsWelfareSendrecordService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 查看所有列表
     * 提供给小程序的接口
     * @param params 查询参数
     * @return RestResponse
     */
    @SysLog("根据memberid查询福利列表接口")
    @RequestMapping("/getWelfareList")
    public RestResponse getWelfareList(@RequestParam Map<String, Object> params) {
        if (!params.containsKey("memberid")) {
            return RestResponse.error("缺少参数：memberid");
        }
        if (StringUtils.isEmpty(params.get("memberid"))) {
            return RestResponse.error("参数（memberid）为空");
        }
        List<QkjrtsWelfareSendrecordEntity> list = qkjrtsWelfareSendrecordService.getWelfareList(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 更新领取状态
     * 提供给小程序的接口
     * @param params 查询参数
     * @return RestResponse
     */
    @SysLog("批量更新领取状态接口")
    @RequestMapping("/setWelfareStatus")
    public RestResponse setWelfareStatus(@RequestBody Map<String, List<String>> params) {
        if (!params.containsKey("id")) {
            return RestResponse.error("参数错误");
        }
        if (params.get("id") != null) {
            List<String> idList =  params.get("id");
            if (idList.size() > 1000) {
                int listSize = idList.size();
                int toIndex = 1000;
                for (int i = 0; i < idList.size(); i+=1000) {
                    if ((i + 1000) > listSize) {
                        toIndex = listSize - i;
                    }
                    List<String> newList = idList.subList(i, i + toIndex);
                    qkjrtsWelfareSendrecordService.setWelfareStatus(newList);
                }
            } else {
                qkjrtsWelfareSendrecordService.setWelfareStatus(idList);
            }
        }

        return RestResponse.success();
    }
}
