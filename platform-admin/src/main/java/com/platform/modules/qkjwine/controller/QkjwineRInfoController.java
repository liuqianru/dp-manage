/*
 * 项目名称:platform-plus
 * 类名称:QkjwineRInfoController.java
 * 包名称:com.platform.modules.qkjwine.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-16 09:16:11             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjwine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjsms.entity.QkjsmsRInfoEntity;
import com.platform.modules.qkjsms.service.QkjsmsRInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import com.platform.modules.qkjwine.service.QkjwineRInfoService;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.util.VideoUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 
 * @date 2022-02-16 09:16:11
 */
@RestController
@RequestMapping("qkjwine/rinfo")
public class QkjwineRInfoController extends AbstractController {
    @Autowired
    private QkjwineRInfoService qkjwineRInfoService;
    @Autowired
    private SysSmsLogService sysSmsLogService;
    @Autowired
    private QkjsmsRInfoService qkjsmsRInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjwineRInfoEntity> list = qkjwineRInfoService.queryAll(params);
        // 藏酒价值（取消赠送及已提货的酒证 孙珊珊 2022-03-25）
        double currenttotalprice = 0.00;
        if (params.containsKey("winevalue") && params.get("winevalue") != null && !"".equals(params.get("winevalue"))) {
            params.put("isStore","true");// 藏酒价值（取消赠送及已提货的酒证 孙珊珊 2022-03-25）
            currenttotalprice = qkjwineRInfoService.queryValue(params);
        }

        return RestResponse.success().put("list", list).put("currenttotalprice", currenttotalprice);
    }


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/selectSqlSimple")
    public RestResponse selectSqlSimple(@RequestParam Map<String, Object> params) {
        List<QkjwineRInfoEntity> list = qkjwineRInfoService.selectSqlSimple(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjwine:rinfo:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjwineRInfoService.queryPage(params);

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
        QkjwineRInfoEntity qkjwineRInfo = qkjwineRInfoService.getById(id);

        return RestResponse.success().put("rinfo", qkjwineRInfo);
    }

    /**
     * 新增
     *
     * @param qkjwineRInfo qkjwineRInfo
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjwineRInfoEntity qkjwineRInfo) {

        qkjwineRInfoService.add(qkjwineRInfo);

        return RestResponse.success().put("otherwineid", qkjwineRInfo.getId());
    }

    /**
     * 修改
     *
     * @param qkjwineRInfo qkjwineRInfo
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjwineRInfoEntity qkjwineRInfo) {
        if (qkjwineRInfo.getActiontype() != null) {
            if (qkjwineRInfo.getActiontype() == 1) {  // 赠送
                qkjwineRInfo.setGivedate(new Date());
            } else if (qkjwineRInfo.getActiontype() == 2) {  // 取消
                qkjwineRInfo.setCanceldate(new Date());
            }
        }

        qkjwineRInfoService.update(qkjwineRInfo);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjwineRInfo qkjwineRInfoorderwarehouse
     * @return RestResponse
     */
    @SysLog("修改入库时间")
    @RequestMapping("/updateScenetime")
    public RestResponse updateScenetime(@RequestBody QkjwineRInfoEntity qkjwineRInfo) {
        qkjwineRInfoService.updateScenetimeByWareporid(qkjwineRInfo);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjwineRInfo qkjwineRInfoorderwarehouse
     * @return RestResponse
     */
    @SysLog("修改封坛编号")
    @RequestMapping("/updateFtnum")
    public RestResponse updateFtnum(@RequestBody QkjwineRInfoEntity qkjwineRInfo) {
        qkjwineRInfoService.updateFtnumByWareporid(qkjwineRInfo);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjwineRInfo qkjwineRInfo
     * @return RestResponse
     */
    @SysLog("保存签名")
    @RequestMapping("/updateSign")
    public RestResponse updateSign(@RequestBody QkjwineRInfoEntity qkjwineRInfo) {
        String scenesign = "";
        if (!StringUtils.isEmpty(qkjwineRInfo.getSignbase64img())) {
            scenesign = VideoUtil.base64ToImage(qkjwineRInfo.getSignbase64img().replace("data:image/png;base64,", ""));
        }
        if (!"".equals(scenesign)) {
            qkjwineRInfo.setScenesign(scenesign);
        }
        qkjwineRInfoService.update(qkjwineRInfo);
        // 给客户发短信
        SysSmsLogEntity smsLog = new SysSmsLogEntity();
        // ；https://wxaurl.cn/K0vrkhelCbg
        smsLog.setContent("尊贵的" + qkjwineRInfo.getScenename() +"您好：专属于您的酒证已经生成，请微信登录青稞荟小程序，查看酒证");
        smsLog.setMobile(qkjwineRInfo.getScenephone());
        SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSms(smsLog);
        if (sysSmsLogEntity.getSendStatus() != null && sysSmsLogEntity.getSendStatus() == 0) {
            QkjsmsRInfoEntity qkjsmsRInfo = new QkjsmsRInfoEntity();
            qkjsmsRInfo.setWineid(qkjwineRInfo.getId());
            qkjsmsRInfo.setState(0);
            qkjsmsRInfoService.add(qkjsmsRInfo);
        }

        return RestResponse.success().put("scenesign", scenesign);
    }

    /**
     * 根据主键删除
     *
     * @param qkjwineRInfo ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjwine:rinfo:delete")
    public RestResponse delete(@RequestBody QkjwineRInfoEntity qkjwineRInfo) {
        qkjwineRInfoService.mdyWineDisableById(qkjwineRInfo);

        return RestResponse.success();
    }
}
