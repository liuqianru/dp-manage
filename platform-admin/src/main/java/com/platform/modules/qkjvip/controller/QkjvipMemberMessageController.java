/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberMessageController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-22 11:05:08        李鹏军     初版做成
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
import com.platform.modules.qkjrts.entity.QkjrtsWelfareEntity;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareOtherobjectsEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareMemberService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareOtherobjectsService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareService;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import com.platform.modules.quartz.service.QrtzMemberFansService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
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
 * @date 2020-12-22 11:05:08
 */
@RestController
@RequestMapping("qkjvip/membermessage")
public class QkjvipMemberMessageController extends AbstractController {
    @Autowired
    private QkjvipMemberMessageService qkjvipMemberMessageService;
    @Autowired
    private QkjvipMemberIntegraluserService qkjvipMemberIntegraluserService;
    @Autowired
    private QrtzMemberFansService qrtzMemberFansService;
    @Autowired
    private SysSmsLogService sysSmsLogService;
    @Autowired
    private QkjvipMemberIntegralService qkjvipMemberIntegralService;
    @Autowired
    private QkjvipMemberCponsonService qkjvipMemberCponsonService;
    @Autowired
    private QkjvipMemberCponService qkjvipMemberCponService;
    @Autowired
    private QkjvipMemberActivitymbsService qkjvipMemberActivitymbsService;
    @Autowired
    private QkjvipMemberActivityService qkjvipMemberActivityService;
    @Autowired
    private QkjvipMemberVisitGivedetailService qkjvipMemberVisitGivedetailService;
    @Autowired
    private QkjvipMemberVisitGivewineService qkjvipMemberVisitGivewineService;
    @Autowired
    private QkjrtsWelfareMemberService qkjrtsWelfareMemberService;
    @Autowired
    private QkjrtsWelfareService qkjrtsWelfareService;
    @Autowired
    private QkjrtsWelfareOtherobjectsService qkjrtsWelfareOtherobjectsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:membermessage:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberMessageEntity> list = qkjvipMemberMessageService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membermessage:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberMessageService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membermessage:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberMessageEntity qkjvipMemberMessage = qkjvipMemberMessageService.getById(id);

        return RestResponse.success().put("membermessage", qkjvipMemberMessage);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberMessage qkjvipMemberMessage
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:membermessage:save")
    public RestResponse save(@RequestBody QkjvipMemberMessageEntity qkjvipMemberMessage) {

        qkjvipMemberMessageService.add(qkjvipMemberMessage);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberMessage qkjvipMemberMessage
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membermessage:update")
    public RestResponse update(@RequestBody QkjvipMemberMessageEntity qkjvipMemberMessage) {

        qkjvipMemberMessageService.update(qkjvipMemberMessage);

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
    @RequiresPermissions("qkjvip:membermessage:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberMessageService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 取出所有公众号
     *
     * @return RestResponse
     */
    @PostMapping("/getChannels")
    public RestResponse getChannels() {
        List<QkjvipOptionsEntity> channelList = new ArrayList<>();
        channelList = qkjvipMemberMessageService.queryChannels();
        return RestResponse.success().put("channelList", channelList);
    }

    /**
     * 取出所选人分布在每个公众号的人数
     *
     * @param qkjvipMemberMessage 活动/积分/优惠券的
     * @return RestResponse
     */
    @PostMapping("/init")
    public RestResponse init(@RequestBody QkjvipMemberMessageEntity qkjvipMemberMessage) {
        List<QkjvipOptionsEntity> options = new ArrayList<>();
        List<QkjvipMemberMessageUserQueryEntity> selectedUserList = new ArrayList<>();
        List<String> appids = new ArrayList<>();
        String userStr = "";
        String openidStr = "";
        int cnt = 0;
        //获取公众号列表
        String resultPost = HttpClient.sendGet(Vars.APPID_GETLIST_URL);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("0".equals(resultObject.get("code").toString())) {  //调用成功
            options = JSON.parseArray(resultObject.getString("data"), QkjvipOptionsEntity.class);
            //获取被选人分布在每个公众号的人数
            List<QrtzMemberFansEntity> fansList = new ArrayList<>();
            if (qkjvipMemberMessage.getCategoryType() != null) {
                if ("1".equals(qkjvipMemberMessage.getCategoryType())) {  //活动
                    selectedUserList = qkjvipMemberActivitymbsService.queryByActivityId(qkjvipMemberMessage.getCategoryId());
                } else if ("2".equals(qkjvipMemberMessage.getCategoryType())) {  //积分
                    selectedUserList = qkjvipMemberIntegraluserService.queryByIntegralId(qkjvipMemberMessage.getCategoryId());
                } else if ("3".equals(qkjvipMemberMessage.getCategoryType())) {  //优惠券
                    selectedUserList = qkjvipMemberCponsonService.queryByCponId(qkjvipMemberMessage.getCategoryId());
                } else if ("4".equals(qkjvipMemberMessage.getCategoryType())) {  // 节日赠礼
                    selectedUserList = qkjvipMemberVisitGivedetailService.queryByMainId(qkjvipMemberMessage.getCategoryId());
                } else if ("5".equals(qkjvipMemberMessage.getCategoryType()) || "6".equals(qkjvipMemberMessage.getCategoryType())) {  // 客户关怀发放、客户关怀代发
                    selectedUserList = qkjrtsWelfareMemberService.queryByMainId(qkjvipMemberMessage.getCategoryId());
                }
                Map map = ListToStringUtil.entityToMap(selectedUserList);
                fansList = qrtzMemberFansService.queryByMemberMessageQuery(map);
            }

            // 查询有手机号的人数
            for (QkjvipMemberMessageUserQueryEntity selectedUser : selectedUserList) {
                if (StringUtils.isNotBlank(selectedUser.getMobile())) {
                    cnt++;
                }
            }
            for (QkjvipOptionsEntity option : options) {
                for(QrtzMemberFansEntity fans : fansList){
                    if (option.getAppid() != null && option.getAppid().equals(fans.getAppid())) {
                        option.setName(option.getName() + "(" + fans.getMembernum() + "个人)");
                        appids.add(option.getAppid());
                        break;
                    }
                }
            }
            if (cnt > 0) {
                QkjvipOptionsEntity option = new QkjvipOptionsEntity();
                option.setAppid("012345678987654321");
                option.setName("短信" + "(" + cnt + "个人)");
                options.add(option);
                appids.add(option.getAppid());
            }
        }
        Map map = new HashMap();
        map.put("appids", appids);
        map.put("options", options);
        return RestResponse.success().put("selectedMap", map);
    }

    /**
     * 发送消息
     *
     * @param qkjvipMemberMessage 活动/积分/优惠券的
     * @return RestResponse
     */
    @PostMapping("/sendMsg")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse sendMsg(@RequestBody QkjvipMemberMessageEntity qkjvipMemberMessage) throws IOException {
        Map map = new HashMap();
        List<QrtzMemberFansEntity> fansList = new ArrayList<>();
        if (qkjvipMemberMessage.getCategoryType() != null) {
            String[] appidAttr = qkjvipMemberMessage.getChannels().split(",");
            List<String> appidList = new ArrayList<String>(Arrays.asList(appidAttr));
            appidList.remove("012345678987654321");
            qkjvipMemberMessage.setAppidList(appidList);
            String appidstr = ListToStringUtil.listToString(appidList);
            List<QkjvipMemberMessageUserQueryEntity> selectedUserList = new ArrayList<>();
            String memberidstr = "";
            String openidStr = "";
            String msg = "";
            Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();  //设置回滚点
            if ("1".equals(qkjvipMemberMessage.getCategoryType())) {  //活动
                QkjvipMemberActivityEntity qkjvipMemberCpon=new QkjvipMemberActivityEntity();
                qkjvipMemberCpon = qkjvipMemberActivityService.getById(qkjvipMemberMessage.getCategoryId());
                qkjvipMemberCpon.setStatus(2);
                qkjvipMemberActivityService.update(qkjvipMemberCpon);
                selectedUserList = qkjvipMemberActivitymbsService.queryByActivityId(qkjvipMemberMessage.getCategoryId());
            } else if ("2".equals(qkjvipMemberMessage.getCategoryType())) {  //积分
                QkjvipMemberIntegralEntity qkjvipMemberIntegral = new QkjvipMemberIntegralEntity();
                qkjvipMemberIntegral.setSendStatus(2); //状态修改为通知已发送
                qkjvipMemberIntegral.setId(qkjvipMemberMessage.getCategoryId());
                qkjvipMemberIntegralService.updateStatus(qkjvipMemberIntegral);
                selectedUserList = qkjvipMemberIntegraluserService.queryByIntegralId(qkjvipMemberMessage.getCategoryId());
            } else if ("3".equals(qkjvipMemberMessage.getCategoryType())) {  //优惠券
                QkjvipMemberCponEntity qkjvipMemberCpon=new QkjvipMemberCponEntity();
                qkjvipMemberCpon = qkjvipMemberCponService.getById(qkjvipMemberMessage.getCategoryId());
                qkjvipMemberCpon.setStatus(2);
                qkjvipMemberCponService.update(qkjvipMemberCpon);
                selectedUserList = qkjvipMemberCponsonService.queryByCponId(qkjvipMemberMessage.getCategoryId());
            } else if ("4".equals(qkjvipMemberMessage.getCategoryType())) {  // 节日赠酒
                QkjvipMemberVisitGivewineEntity qkjvipMemberVisitGivewine = new QkjvipMemberVisitGivewineEntity();
                qkjvipMemberVisitGivewine = qkjvipMemberVisitGivewineService.getById(qkjvipMemberMessage.getCategoryId());
                qkjvipMemberVisitGivewine.setStatus(2);
                qkjvipMemberVisitGivewineService.update(qkjvipMemberVisitGivewine);
                selectedUserList = qkjvipMemberVisitGivedetailService.queryByMainId(qkjvipMemberMessage.getCategoryId());
            } else if ("5".equals(qkjvipMemberMessage.getCategoryType())) {  // 客户关怀发放
                QkjrtsWelfareEntity qkjrtsWelfare = new QkjrtsWelfareEntity();
                qkjrtsWelfare = qkjrtsWelfareService.getById(qkjvipMemberMessage.getCategoryId());
                qkjrtsWelfare.setSendstatus(2);
                qkjrtsWelfareService.mdy(qkjrtsWelfare);
                selectedUserList = qkjrtsWelfareMemberService.queryByMainId(qkjvipMemberMessage.getCategoryId());
            } else if ("6".equals(qkjvipMemberMessage.getCategoryType())) {  // 客户关怀代发
                QkjrtsWelfareOtherobjectsEntity qkjrtsWelfareOtherobjects = new QkjrtsWelfareOtherobjectsEntity();
                qkjrtsWelfareOtherobjects = qkjrtsWelfareOtherobjectsService.getById(qkjvipMemberMessage.getCategoryId());
                if (qkjrtsWelfareOtherobjects.getSendstatus() == 2) {  // 全部发放的情况下
                    qkjrtsWelfareOtherobjects.setSendstatus(3);
                    qkjrtsWelfareOtherobjectsService.update(qkjrtsWelfareOtherobjects);
                }
                selectedUserList = qkjrtsWelfareMemberService.queryByMainId(qkjvipMemberMessage.getCategoryId());
            }

            //保存发放记录
            qkjvipMemberMessage.setAddUser(getUserId());
            qkjvipMemberMessage.setAddDept(getOrgNo());
            qkjvipMemberMessage.setAddTime(new Date());
            qkjvipMemberMessageService.add(qkjvipMemberMessage);
            //群发发短信
            if (qkjvipMemberMessage.getChecktype().contains("短信")) {
                if (qkjvipMemberMessage.getChannels().contains("012345678987654321")) {  //包含短信修改为群发孙珊
//                    if ("4".equals(qkjvipMemberMessage.getCategoryType())) {  // 节日赠酒
                        StringBuffer mobiles = new StringBuffer();
                        if (!StringUtils.isEmpty(qkjvipMemberMessage.getDxContent())) {
                            for(QkjvipMemberMessageUserQueryEntity selectedUser : selectedUserList){
                                if (selectedUser != null && StringUtils.isNotBlank(selectedUser.getMobile())) {
                                    mobiles.append(selectedUser.getMobile()+",");
                                }
                            }
                            SysSmsLogEntity smsLog = new SysSmsLogEntity();
                            smsLog.setContent(qkjvipMemberMessage.getDxContent());
                            smsLog.setMobile(mobiles.toString());
                            SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSmsBach(smsLog);
                            if (sysSmsLogEntity.getSendStatus() == 1) {
                                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                                return RestResponse.error("短信通知发送失败！");
                            }
                        }
//                    }
//                    else {
//                        StringBuffer mobiles = new StringBuffer();
//                        msg = qkjvipMemberMessage.getDxContent() + " 请点击以下链接进入青稞荟小程序查看：" + QkjCommonUtil.getShortAddress(qkjvipMemberMessage.getAppletsurl(), qkjvipMemberMessage.getAppletsparam());
//                        for(QkjvipMemberMessageUserQueryEntity selectedUser : selectedUserList){
//                            if (selectedUser != null && StringUtils.isNotBlank(selectedUser.getMobile())) {
//                                mobiles.append(selectedUser.getMobile()+",");
//                            }
//                        }
//                        SysSmsLogEntity smsLog = new SysSmsLogEntity();
//                        smsLog.setContent(msg);
//                        smsLog.setMobile(mobiles.toString());
//                        SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSmsBach(smsLog);
//                        if (sysSmsLogEntity.getSendStatus() == 1) {
//                            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
//                            return RestResponse.error("短信通知发送失败！");
//                        }
//                    }
                }
            }

            //群发微信
            if (qkjvipMemberMessage.getChecktype().contains("微信")) {
                Map queryMap = ListToStringUtil.entityToMap(selectedUserList);
                memberidstr = queryMap.get("userStr").toString();
                openidStr = queryMap.get("openidStr").toString();
                if (!"".equals(appidstr) && (!"('')".equals(memberidstr) || !"('')".equals(openidStr))) {
                    map.clear();
                    map.put("appidstr", appidstr);
                    map.put("memberidstr", memberidstr);
                    map.put("openidStr", openidStr);
                    fansList = qrtzMemberFansService.queryAll(map);
                    //调用赵月辉接口
                    if (fansList.size() > 0) {
                        String result = this.sendWxMsg(qkjvipMemberMessage, fansList);
                        if (!"0".equals(result)) {
                            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                            return RestResponse.error("微信通知发送失败！");
                        }
                    }
                }
            }
            // 客户关怀发放和客户关怀代发更新通知状态
            if ("5".equals(qkjvipMemberMessage.getCategoryType()) || "6".equals(qkjvipMemberMessage.getCategoryType())) {
                qkjrtsWelfareMemberService.updateByMainId(qkjvipMemberMessage.getCategoryId());  // 更新通知状态
            }
        }

        return RestResponse.success();
    }

    /**
     * 群发微信
     * @param qkjvipMemberMessage
     * @param fansList 微信用户列表
     */
    public String sendWxMsg(QkjvipMemberMessageEntity qkjvipMemberMessage, List<QrtzMemberFansEntity> fansList) throws IOException {
        Map map = new HashMap();
        Map sonMap = new HashMap();
        List<String> appidList = qkjvipMemberMessage.getAppidList();
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
            if (openIds != null && openIds.size() > 1) {  //一个appid下至少有2个人才可以发送图文消息
                sonMap.put("openIds", openIds);
                targetUsers.add(sonMap);
            }
        }
        if (targetUsers != null && targetUsers.size() > 0) {
            map.put("targetUsers", targetUsers);
            map.put("isToAll", false);
            sonMap = new HashMap();
            sonMap.put("title", qkjvipMemberMessage.getTitle());
            sonMap.put("coverUrl", qkjvipMemberMessage.getCoverImage());
            StringBuilder sb = new StringBuilder();
            sb.append(qkjvipMemberMessage.getWxContent());
            String tmpUrl = qkjvipMemberMessage.getAppletsurl() + "?" + qkjvipMemberMessage.getAppletsparam();
//            sb.append("<a class=\"weapp_text_link\" style=\"font-size:17px;\" data-miniprogram-appid=\"wxacf5db8e2b0ef6a5\" data-miniprogram-path=\"" + tmpUrl + "\" data-miniprogram-nickname=\"青稞荟\" href=\"\" data-miniprogram-type=\"text\" data-miniprogram-servicetype=\"\">点击打开小程序</a>");
            sb.append("<p>点击图片查看详情</p>");
            if (qkjvipMemberMessage.getLinkImage() != null) {
                sb.append("<a class=\"weapp_image_link\" data-miniprogram-appid=\"wxacf5db8e2b0ef6a5\" data-miniprogram-path=\"" + tmpUrl + "\" data-miniprogram-nickname=\"青稞荟\" href=\"\" data-miniprogram-type=\"image\" data-miniprogram-servicetype=\"\"><img src='" + qkjvipMemberMessage.getLinkImage() + "'></a>");
            }
            sonMap.put("content", sb.toString());
            sonMap.put("sourceUrl", qkjvipMemberMessage.getUrl());
            articles.add(sonMap);
            map.put("articles", articles);
            String queryJsonStr = JsonHelper.toJsonString(map);
            String resultPost = HttpClient.sendPost(Vars.MESSAGE_SEND, queryJsonStr);
            JSONObject resultObject = JSON.parseObject(resultPost);
            System.out.println("发送微信消息返回结果code：" + resultObject.get("code"));
            System.out.println("发送微信消息返回结果msg：" + resultObject.get("msg"));
            return resultObject.get("code").toString();
        }
        return "0";
    }
}
