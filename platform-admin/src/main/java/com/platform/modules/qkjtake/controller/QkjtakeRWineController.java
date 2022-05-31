/*
 * 项目名称:platform-plus
 * 类名称:QkjtakeRWineController.java
 * 包名称:com.platform.modules.qkjtake.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-21 14:18:34             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjtake.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.datascope.ContextHelper;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;
import com.platform.modules.accesstoken.service.AccesstokenService;
import com.platform.modules.qkjInterface.entity.UserMsgEntity;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import com.platform.modules.qkjwine.service.QkjwineRInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import com.platform.modules.qkjtake.service.QkjtakeRWineService;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.util.DingMsg;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller
 *
 * @author 
 * @date 2022-02-21 14:18:34
 */
@RestController
@RequestMapping("qkjtake/rwine")
public class QkjtakeRWineController extends AbstractController {
    @Autowired
    private QkjtakeRWineService qkjtakeRWineService;
    @Autowired
    private QkjwineRInfoService qkjwineRInfoService;
    @Autowired
    private SysSmsLogService sysSmsLogService;
    @Autowired
    private AccesstokenService accesstokenService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjtakeRWineEntity> list = qkjtakeRWineService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjtake:rwine:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        // 仓库权限
        Set<String> warlist = new HashSet<>();
        warlist= ContextHelper.setWarelistsm("qkjvip:orderwarehouse:list",getUserId());
        if (warlist.size()>0) {
            params.put("warlists",warlist);
        }
        Page page = qkjtakeRWineService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjtake:rwine:info")
    public RestResponse info(@PathVariable("id") String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        List<QkjtakeRWineEntity> list = qkjtakeRWineService.queryAll(params);
        QkjtakeRWineEntity qkjtakeRWine = list.get(0);

        return RestResponse.success().put("rwine", qkjtakeRWine);
    }

    /**
     * 根据主键修改状态
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/updateStateById/{id}")
    public RestResponse updateStateById(@PathVariable("id") String id) {
        qkjtakeRWineService.updateStateById(id);

        return RestResponse.success();
    }

    /**
     * 新增
     *
     * @param qkjtakeRWine qkjtakeRWine
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse save(@RequestBody QkjtakeRWineEntity qkjtakeRWine) {
        if (qkjtakeRWine!=null && qkjtakeRWine.getWineid()!=null) {
            // 查询酒证是否已存在
            Map<String, Object> params = new HashMap<>();
            params.put("wineid",qkjtakeRWine.getWineid());
            List<QkjtakeRWineEntity> list = qkjtakeRWineService.queryAll(params);
            if (list == null || list.size() <= 0){
                qkjtakeRWineService.add(qkjtakeRWine);
                // 修改酒证的提货id
                QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
                wine.setId(qkjtakeRWine.getWineid());
                wine.setTakeid(qkjtakeRWine.getId());
                qkjwineRInfoService.updateTakeIdById(wine);
                // 短信钉钉通知
                UserMsgEntity userMsgEntity = new UserMsgEntity();
                userMsgEntity.setUrl("https://crm.qkj.com.cn/");
                userMsgEntity.setTitle("提货申请通知");
                userMsgEntity.setMsg("您好，"+qkjtakeRWine.getMembername()+"提交了新的提货申请，请及时操作。");
                userMsgEntity.setMobilelist("15202543760");
                userMsgEntity.setDinglist("102225031320339414");
                sendmsg(userMsgEntity);
            } else {
                return RestResponse.error("该酒证已申请提货，请勿重复提交！");
            }
        } else {
            return RestResponse.error("酒证不能为空！");
        }
        return RestResponse.success();
    }

    public void sendmsg(UserMsgEntity userMsgEntity) {
        if (userMsgEntity!=null && userMsgEntity.getMobilelist() != null && !userMsgEntity.getMobilelist().equals("")) {
            // 发短信
            SysSmsLogEntity smsLog = new SysSmsLogEntity();
            smsLog.setContent(userMsgEntity.getMsg() + userMsgEntity.getUrl());
            smsLog.setMobile(userMsgEntity.getMobilelist());
            SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSmsBach(smsLog);
        }

        if (userMsgEntity!=null && userMsgEntity.getDinglist()!= null && !userMsgEntity.getDinglist().equals("")) {
            //发钉钉消息
            Map<String, Object> params = new HashMap<>();
            List<AccesstokenEntity> aks = accesstokenService.queryAll(params);
            if (aks.size()>0) {
                AccesstokenEntity ak=aks.get(0);
                DingMsg demo=new DingMsg();
                String result = demo.sendLinkMessageResult(userMsgEntity.getTitle(),userMsgEntity.getMsg(), userMsgEntity.getDinglist(),"",ak.getAccessToken(),userMsgEntity.getUrl());
            }

        }
    }

    /**
     * 修改
     *
     * @param qkjtakeRWine qkjtakeRWine
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse update(@RequestBody QkjtakeRWineEntity qkjtakeRWine) {
        qkjtakeRWineService.update(qkjtakeRWine);

        // 修改酒证的提货id
        QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
        wine.setId(qkjtakeRWine.getWineid());
        wine.setTakeid(qkjtakeRWine.getId());
        qkjwineRInfoService.updateTakeIdById(wine);

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
    @RequiresPermissions("qkjtake:rwine:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjtakeRWineService.deleteBatch(ids);

        return RestResponse.success();
    }
}
