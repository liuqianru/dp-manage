/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentGroupController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-24 15:41:39        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import com.platform.modules.quartz.service.QrtzMemberFansService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.ListToStringUtil;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-03-24 15:41:39
 */
@RestController
@RequestMapping("qkjvip/contentgroup")
public class QkjvipContentGroupController extends AbstractController {
    @Autowired
    private QkjvipContentGroupService qkjvipContentGroupService;
    @Autowired
    private QkjvipContentPushchannelService qkjvipContentPushchannelService;
    @Autowired
    private QkjvipContentGroupuserService qkjvipContentGroupuserService;
    @Autowired
    private QkjvipContentService qkjvipContentService;
    @Autowired
    private QrtzMemberFansService qrtzMemberFansService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:contentgroup:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:contentgroup:list", "T.add_user", "T.add_dept"));
        List<QkjvipContentGroupEntity> list = qkjvipContentGroupService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:contentgroup:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:contentgroup:list", "T.add_user", "T.add_dept"));
        Page page = qkjvipContentGroupService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:contentgroup:info")
    public RestResponse info(@PathVariable("id") String id) {
        List<QkjvipContentEntity> contentList = new ArrayList<>();
        List<QkjvipContentPushchannelEntity> pushChannels = new ArrayList<>();
        List<QkjvipContentGroupuserEntity> memberList = new ArrayList<>();
        List<String> appids = new ArrayList<>();
        QkjvipContentGroupEntity qkjvipContentGroup = qkjvipContentGroupService.getById(id);

        contentList = qkjvipContentService.queryByGroupId(id);
        qkjvipContentGroup.setContentList(contentList);

        Map param = new HashMap();
        param.put("groupid", id);
        pushChannels = qkjvipContentPushchannelService.queryAll(param);
        for (QkjvipContentPushchannelEntity pushchannelEntity : pushChannels) {
            appids.add(pushchannelEntity.getAppid());
        }
        qkjvipContentGroup.setAppids(appids);

        memberList = qkjvipContentGroupuserService.queryAll(param);
        qkjvipContentGroup.setMemberList(memberList);

        return RestResponse.success().put("contentgroup", qkjvipContentGroup);
    }

    /**
     * 新增
     *
     * @param qkjvipContentGroup qkjvipContentGroup
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:contentgroup:save")
    public RestResponse save(@RequestBody QkjvipContentGroupEntity qkjvipContentGroup) {
        qkjvipContentGroup.setAddDept(getOrgNo());
        qkjvipContentGroup.setAddUser(getUserId());
        qkjvipContentGroup.setAddTime(new Date());
        qkjvipContentGroupService.add(qkjvipContentGroup);

        return RestResponse.success().put("contentgroup", qkjvipContentGroup);
    }

    /**
     * 修改
     *
     * @param qkjvipContentGroup qkjvipContentGroup
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:contentgroup:update")
    public RestResponse update(@RequestBody QkjvipContentGroupEntity qkjvipContentGroup) {
        qkjvipContentGroup.setLmUser(getUserId());
        qkjvipContentGroup.setLmDept(getOrgNo());
        qkjvipContentGroup.setLmTime(new Date());
        qkjvipContentGroupService.update(qkjvipContentGroup);

        return RestResponse.success().put("contentgroup", qkjvipContentGroup);
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:contentgroup:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipContentGroupService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 推送文章
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/pushToApp/{id}")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse pushToApp(@PathVariable("id") String id) throws IOException {
        QkjvipContentGroupEntity qkjvipContentGroup = qkjvipContentGroupService.getById(id);

        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();  //设置回滚点
        // 更新文章分组推送状态
        qkjvipContentGroup.setPushstatus(1); // 已推送
        qkjvipContentGroupService.update2(qkjvipContentGroup);

        List<QkjvipContentEntity> contentList = new ArrayList<>();
        contentList = qkjvipContentService.queryByGroupId(id);   // 取出文章
        qkjvipContentGroup.setContentList(contentList);

        // 批量更新文章推送状态
        for (QkjvipContentEntity contentEntity : contentList) {
            contentEntity.setPushstatus(1);
        }
        if (contentList.size() > 0) {
            qkjvipContentService.updateBatch(contentList);
        }

        Map param = new HashMap();
        param.put("groupid", id);
        List<QkjvipContentPushchannelEntity> pushChannels = new ArrayList<>();
        pushChannels = qkjvipContentPushchannelService.queryAll(param);
        List<String> appids = new ArrayList<>();
        for (QkjvipContentPushchannelEntity pushchannelEntity : pushChannels) {
            appids.add(pushchannelEntity.getAppid());  // 取出推送的渠道
        }
        qkjvipContentGroup.setAppids(appids);

        String result = "";
        List<QrtzMemberFansEntity> fansList = new ArrayList<>();
        String appidstr = ListToStringUtil.listToString(appids);
        if (qkjvipContentGroup.getPushtype() == 2) {  // 推送给指定人
            List<QkjvipMemberMessageUserQueryEntity> selectedUserList = new ArrayList<>();
            selectedUserList = qkjvipContentGroupuserService.queryByGroupId(id);
            Map queryMap = ListToStringUtil.entityToMap(selectedUserList);
            String memberidstr = queryMap.get("userStr").toString();
            String openidStr = queryMap.get("openidStr").toString();
            if (!"".equals(appidstr) && (!"('')".equals(memberidstr) || !"('')".equals(openidStr))) {
                Map map = new HashMap();
                map.put("appidstr", appidstr);
                map.put("memberidstr", memberidstr);
                map.put("openidStr", openidStr);
                fansList = qrtzMemberFansService.queryAll(map);
                //调用赵月辉接口
                if (fansList.size() > 0) {
                    result = qkjvipContentGroupService.sendWxContent(qkjvipContentGroup, fansList);
                }
            }
        } else if (qkjvipContentGroup.getPushtype() == 3) {  // 指定条件
            qkjvipContentGroupService.batchesGetUser(qkjvipContentGroup, appidstr);
        } else {
            result = qkjvipContentGroupService.sendWxContent(qkjvipContentGroup, fansList);
        }
        if ("".equals(result)) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return RestResponse.error("推送人员为空！");
        } else {
            JSONObject resultObject = JSON.parseObject(result);
            if (!"0".equals(resultObject.get("code").toString())) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return RestResponse.error("图文消息发送失败！");
            } else {
                if (!StringUtils.isEmpty(resultObject.get("data"))) {
                    qkjvipContentGroup.setTaskno(resultObject.get("data").toString());
                    qkjvipContentGroupService.updateTaskNoById(qkjvipContentGroup);
                }
            }
        }

        return RestResponse.success();
    }

    /**
     * 取消定时推送文章
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/cancelToPush/{id}")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse cancelToPush(@PathVariable("id") String id) throws IOException {
        QkjvipContentGroupEntity qkjvipContentGroup = qkjvipContentGroupService.getById(id);

        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();  //设置回滚点

        String taskNo = qkjvipContentGroup.getTaskno();
        if (!StringUtils.isEmpty(taskNo)) {
            // 更新文章分组推送状态
            qkjvipContentGroup.setPushstatus(0); // 未推送
            qkjvipContentGroup.setTaskno(null);
            qkjvipContentGroup.setFixedtime(null);
            qkjvipContentGroupService.update2(qkjvipContentGroup);

            String resultPost = HttpClient.doPost(Vars.MESSAGE_CANCEL_SEND + "?taskNo=" + taskNo);

            JSONObject resultObject = JSON.parseObject(resultPost);
            if (!"0".equals(resultObject.get("code").toString())) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return RestResponse.error("定时群发图文取消失败！");
            }
        }

        return RestResponse.success();
    }

    /**
     * 群发图文(暂时废弃，用service层的方法)
     * @param qkjvipContentGroup
     * @param fansList 微信用户列表
     */
    public String sendWxContent(QkjvipContentGroupEntity qkjvipContentGroup, List<QrtzMemberFansEntity> fansList) throws IOException {
        Map map = new HashMap();
        Map sonMap = new HashMap();
        List<String> appidList = qkjvipContentGroup.getAppids();
        List<Object> targetUsers = new ArrayList<>();
        List<Object> articles = new ArrayList<>();
        for (int i = 0; i < appidList.size(); i++) {
            List<String> openIds = new ArrayList<>();
            sonMap = new HashMap();
            sonMap.put("appId", appidList.get(i));
            for (int j = 0; j < fansList.size(); j++) {
                if (appidList.get(i) != null && appidList.get(i).equals(fansList.get(j).getAppid())) {
                    if (fansList.get(j).getOpenid() != null && !"".equals(fansList.get(j).getOpenid())) {
                        openIds.add(fansList.get(j).getOpenid());
                    }
                }
            }
            if (qkjvipContentGroup.getPushtype() == 2) {
                if (openIds != null && openIds.size() > 1) {  //一个appid下至少有2个人才可以发送图文消息
                    sonMap.put("openIds", openIds);
                    targetUsers.add(sonMap);
                }
            } else {
                sonMap.put("openIds", openIds);
                targetUsers.add(sonMap);
            }
        }
        if (targetUsers != null && targetUsers.size() > 0) {
            map.put("targetUsers", targetUsers);
            map.put("isToAll", (qkjvipContentGroup.getPushtype() == 2 || qkjvipContentGroup.getPushtype() == 3) ? false : true);
            for(QkjvipContentEntity contentEntity : qkjvipContentGroup.getContentList()) {
                sonMap = new HashMap();
                sonMap.put("title", contentEntity.getTitle());
                sonMap.put("coverUrl", contentEntity.getCoverimage());
                String tmpContent = "<img src='http://images.qkjebiz.qkjchina.com/scrm/mp-content-header.png' />" + contentEntity.getContent() + "<img src='https://images.qkjebiz.qkjchina.com/scrm/mp-content-footer.png' />";
                sonMap.put("content", tmpContent);
                sonMap.put("sourceUrl", "http://crm.qkj.com.cn/#/content-info?id=" + contentEntity.getId());
                articles.add(sonMap);
            }

            map.put("articles", articles);
            if (qkjvipContentGroup.getPushtype() == 3) {
                map.put("TargetCondition", qkjvipContentGroup.getSearchquery());
            }
            String queryJsonStr = JsonHelper.toJsonString(map);
            String resultPost = HttpClient.sendPost(Vars.MESSAGE_SEND, queryJsonStr);
            JSONObject resultObject = JSON.parseObject(resultPost);
            return resultObject.get("code").toString();
        }
        return "0";
    }
}
