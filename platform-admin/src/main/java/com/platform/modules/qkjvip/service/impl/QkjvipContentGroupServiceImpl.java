/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentGroupServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-24 15:41:39        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.common.utils.ShiroUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjvip.dao.QkjvipContentGroupDao;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import com.platform.modules.quartz.service.QrtzMemberFansService;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.ListToStringUtil;
import com.platform.modules.util.Vars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-03-24 15:41:39
 */
@Service("qkjvipContentGroupService")
public class QkjvipContentGroupServiceImpl extends ServiceImpl<QkjvipContentGroupDao, QkjvipContentGroupEntity> implements QkjvipContentGroupService {

    @Autowired
    private QkjvipContentGroupcontentService qkjvipContentGroupcontentService;
    @Autowired
    private QkjvipContentPushchannelService qkjvipContentPushchannelService;
    @Autowired
    private QkjvipContentGroupuserService qkjvipContentGroupuserService;
    @Autowired
    private QrtzMemberFansService qrtzMemberFansService;
    @Autowired
    private SysUserChannelService sysUserChannelService;

    @Override
    public List<QkjvipContentGroupEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjvipContentGroupEntity> page = new Query<QkjvipContentGroupEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipContentGroupPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public void add(QkjvipContentGroupEntity qkjvipContentGroup) {
        this.save(qkjvipContentGroup);
        // 批量保存分组内容
        saveGroupContent(qkjvipContentGroup);
        // 批量保存推送渠道
        savePushChannel(qkjvipContentGroup);
        // 批量保存人员
        if (qkjvipContentGroup.getPushtype() == 2) {  // 指定人员时保存
            saveContentGroupUser(qkjvipContentGroup.getId(), qkjvipContentGroup.getMemberList());
        }
    }

    @Override
    public void update(QkjvipContentGroupEntity qkjvipContentGroup) {
        this.updateById(qkjvipContentGroup);
        // 批量保存分组内容
        qkjvipContentGroupcontentService.deleteByGroupId(qkjvipContentGroup.getId());
        saveGroupContent(qkjvipContentGroup);
        // 批量保存推送渠道
        qkjvipContentPushchannelService.deleteByGroupId(qkjvipContentGroup.getId());
        // 人员列表删除
        qkjvipContentGroupuserService.deleteByGroupId(qkjvipContentGroup.getId());
        if (qkjvipContentGroup.getPushtype() == 2) {  // 指定人员时保存
            saveContentGroupUser(qkjvipContentGroup.getId(), qkjvipContentGroup.getMemberList());
        }

        savePushChannel(qkjvipContentGroup);
    }

    @Override
    public void update2(QkjvipContentGroupEntity qkjvipContentGroup) {
        this.updateById(qkjvipContentGroup);
    }

    @Override
    public void updateTaskNoById(QkjvipContentGroupEntity qkjvipContentGroup) {
        baseMapper.updateTaskNoById(qkjvipContentGroup);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    @Async
    public void batchesGetUser(QkjvipContentGroupEntity qkjvipContentGroup, String appidstr) throws IOException {
        // 将查询json转化一下类型
        MemberQueryEntity memberQuery = new MemberQueryEntity();
        memberQuery = JSON.parseObject(qkjvipContentGroup.getSearchquery(),MemberQueryEntity.class);
        memberQuery.setPageindex(1);
        memberQuery.setPagesize(100);
        memberQuery.setListorgno(ContextHelper.getPermitDepts("qkjvip:member:list"));
        if (!ShiroUtils.getUserEntity().getUserName().contains("admin")) {  // 空默认是全部所有权限
            memberQuery.setCurrentmemberid(ShiroUtils.getUserEntity().getUserId());
            memberQuery.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(ShiroUtils.getUserEntity().getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(ShiroUtils.getUserEntity().getUserId())); // 0代表选择的是全部渠道传-1
        } else {
            memberQuery.setListmemberchannel("-1");
        }
        String queryJsonStr = JsonHelper.toJsonString(memberQuery, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
        System.out.println("会员检索条件：" + queryJsonStr);
        //插入会员标签
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            List<QrtzMemberFansEntity> fansList = new ArrayList<>();
            List<MemberEntity> memberList = new ArrayList<>();
            memberList = JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
            Map queryMap = ListToStringUtil.entityToMap2(memberList);
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
                    sendWxContent(qkjvipContentGroup, fansList);
                }
            }
            long total = Long.parseLong(resultObject.get("totalcount").toString());
            int pages = (int)(Math.ceil(total / 100)); // 总页数
            for (int i = 1; i < pages; i++) {
                memberQuery.setPageindex(i + 1);
                queryJsonStr = JsonHelper.toJsonString(memberQuery, "yyyy-MM-dd HH:mm:ss");
                resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
                System.out.println("会员检索条件：" + queryJsonStr);
                if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                    memberList = JSON.parseArray(resultObject.getString("listmember"), MemberEntity.class);
                    queryMap = ListToStringUtil.entityToMap2(memberList);
                    memberidstr = queryMap.get("userStr").toString();
                    openidStr = queryMap.get("openidStr").toString();
                    if (!"".equals(appidstr) && (!"('')".equals(memberidstr) || !"('')".equals(openidStr))) {
                        Map map = new HashMap();
                        map.put("appidstr", appidstr);
                        map.put("memberidstr", memberidstr);
                        map.put("openidStr", openidStr);
                        fansList = qrtzMemberFansService.queryAll(map);
                        //调用赵月辉接口
                        if (fansList.size() > 0) {
                            sendWxContent(qkjvipContentGroup, fansList);
                        }
                    }
                }
            }
        }
    }

    @Override
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
            map.put("delayTime", qkjvipContentGroup.getFixedtime());
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
            String queryJsonStr = JsonHelper.toJsonString(map);
            String resultPost = HttpClient.sendPost(Vars.MESSAGE_SEND, queryJsonStr);
            return resultPost;
        }
        return "";
    }

    /**
     * 批量插入推送渠道
     *
     * @param qkjvipContentGroup qkjvipContentGroup
     * @return RestResponse
     */
    private void savePushChannel(QkjvipContentGroupEntity qkjvipContentGroup) {
        if (qkjvipContentGroup.getAppids() != null && qkjvipContentGroup.getAppids().size() > 0) {
            List<QkjvipContentPushchannelEntity> pushChannelList = new ArrayList<>();
            for (int i = 0; i < qkjvipContentGroup.getAppids().size(); i++) {
                QkjvipContentPushchannelEntity pushChannel = new QkjvipContentPushchannelEntity();
                for (QkjvipOptionsEntity option : qkjvipContentGroup.getChannelList()) {
                    if (option.getAppid().equals(qkjvipContentGroup.getAppids().get(i))) {
                        pushChannel.setAppid(option.getAppid());
                        pushChannel.setGroupid(qkjvipContentGroup.getId());
                        pushChannel.setAppname(option.getName());
                        pushChannelList.add(pushChannel);
                    }
                }
            }
            qkjvipContentPushchannelService.addBatch(pushChannelList);
        }
    }

    /**
     * 批量插入分组内容
     *
     * @param qkjvipContentGroup qkjvipContentGroup
     * @return RestResponse
     */
    private void saveGroupContent(QkjvipContentGroupEntity qkjvipContentGroup) {
        if (qkjvipContentGroup.getContentList() != null && qkjvipContentGroup.getContentList().size() > 0) {
            List<QkjvipContentGroupcontentEntity> groupContentList = new ArrayList<>();
            int cnt = 0;
            for (QkjvipContentEntity contentEntity : qkjvipContentGroup.getContentList()) {
                QkjvipContentGroupcontentEntity groupContent = new QkjvipContentGroupcontentEntity();
                groupContent.setGroupid(qkjvipContentGroup.getId());
                groupContent.setContentid(contentEntity.getId());
                groupContent.setSortvalue(cnt);
                groupContentList.add(groupContent);
                cnt++;
            }
            qkjvipContentGroupcontentService.addBatch(groupContentList);
        }
    }
    /**
     * 批量保存会员
     *
     * @param groupId groupId
     * @param memberlist memberlist
     * @return RestResponse
     */
    private void saveContentGroupUser(String groupId, List<QkjvipContentGroupuserEntity> memberlist) {
        if (memberlist != null && memberlist.size() > 0) {
            List<QkjvipContentGroupuserEntity> groupusers = new ArrayList<>();
            for (QkjvipContentGroupuserEntity groupuser : memberlist) {
                groupuser.setGroupid(groupId);
                groupusers.add(groupuser);
            }
            qkjvipContentGroupuserService.addBatch(groupusers);
        }
    }
}
