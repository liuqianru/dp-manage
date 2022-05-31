/*
 * 项目名称:platform-plus
 * 类名称:MemberServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/9 14:28            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service.impl;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.ShiroUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.pageCont.pageCount;
import com.platform.modules.qkjvip.dao.MemberDao;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberQueryEntity;
import com.platform.modules.qkjvip.service.MemberService;
import com.platform.modules.qkjvip.service.MemberTagsService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.ListToStringUtil;
import com.platform.modules.util.Vars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuqianru
 * @date 2020/3/9 14:28
 */

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberTagsService memberTagsService;

    @Override
    public List<MemberEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<MemberEntity> queryList(MemberQueryEntity memberQuery) throws IOException {
        if (memberQuery.getIslimitperm()) {  // 限制权限
            // 部门权限
            if (memberQuery.getIsconsultant() != null && memberQuery.getIsconsultant() == 2) {
                memberQuery.setListorgno(ContextHelper.getPermitDepts("qkjvip:memberbrandconsultant:list"));
            } else {
                memberQuery.setListorgno(ContextHelper.getPermitDepts("qkjvip:member:list"));
            }
            // 添加人、介绍人、维护人权限判断
            if (!ShiroUtils.getUserEntity().getUserName().contains("admin")) {  // 空默认是全部所有权限
                memberQuery.setCurrentmemberid(ShiroUtils.getUserId());
            }
        }
        String queryJsonStr = JsonHelper.toJsonString(memberQuery, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        List<MemberEntity> memberList = new ArrayList<>();
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            memberList = JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
        }
        return memberList;
    }

    @Override
    public List<MemberEntity> queryAllByMobile(Map<String, Object> params) {
        return baseMapper.queryAllByMobile(params);
    }

    @Override
    public List<MemberEntity> queryMemberInfo(String mobile) {
        return baseMapper.queryMemberInfo(mobile);
    }

    @Override
    public List<MemberEntity> selectMemberByOpenid(Map<String, Object> params) {
        return baseMapper.selectMemberByOpenid(params);
    }

    @Override
    public List<MemberEntity> selectMemberByUnionid(Map<String, Object> params) {
        return baseMapper.selectMemberByUnionid(params);
    }

    @Override
    public List<MemberEntity> selectMemberByJuruMemberid(Map<String, Object> params) {
        return baseMapper.selectMemberByJuruMemberid(params);
    }

    @Override
    public int selectQkhMemberById(String memberId) {
        return baseMapper.selectQkhMemberById(memberId);
    }

    @Override
    public pageCount selectMemberCount(Map<String, Object> params) {
        return baseMapper.selectMemberCount(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "m.add_time");
        params.put("asc", false);
        Page<MemberEntity> page = new Query<MemberEntity>(params).getPage();

        // 如果追加了会员标签的检索条件start
        if (params.get("conditionSql") != null && !"".equals(params.get("conditionSql").toString())) {
            page.setRecords(baseMapper.selectMemberList2(page, params));
        }
        // 如果追加了会员标签的检索条件end

        page.setRecords(baseMapper.selectMemberList(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public void add(MemberEntity member, Map<String, Object> params) {
        this.save(member);
    }

    @Override
    public void add(MemberEntity member) {
        this.save(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<MemberEntity> mList) {
        this.saveBatch(mList);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void update(MemberEntity member, Map<String, Object> params) throws IOException {
        //修改会员标签
//        memberTagsService.saveOrUpdate(member);  //先调用修改标签逻辑是避免调接口成功我这边失败时无法回滚接口那边的数据的问题,改为藕周红的接口处理2021-07-23
        MemberEntity oldStaff = this.getById(member.getMemberId());
        if (!member.equals(oldStaff)) {  // 不相等证明有修改
            member.setStatusflag(2);   // 锁住
            Object obj = JSONArray.toJSON(member);
            String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
            String resultPost = HttpClient.sendPost(Vars.MEMBER_UPDATE_URL, memberJsonStr);
            System.out.println("会员信息更新：" + memberJsonStr);
            JSONObject resultObject = JSON.parseObject(resultPost);
            if (!"200".equals(resultObject.get("resultcode").toString())) {  //修改不成功
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
//        this.updateById(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] memberIds) throws IOException {
//        this.removeByIds(Arrays.asList(memberIds));
//        baseMapper.removeByIds(memberIds);  //逻辑删除
        //同时删除会员对应的标签
        memberTagsService.deleteBatch(memberIds);
        Map map = new HashMap();
        map.put("listmemberid", ListToStringUtil.attrToString(memberIds, ","));
        map.put("remark", "删除会员");
        Object obj = JSONArray.toJSON(map);
        String memberJsonStr = JsonHelper.toJsonString(obj);
        String resultPost = HttpClient.sendPost(Vars.MEMBER_DELETE_URL, memberJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if (!"200".equals(resultObject.get("resultcode").toString())) {  //删除不成功
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public List<MemberEntity> queryMemByList(List<MemberEntity> mbList) {
        return baseMapper.queryMemByList(mbList);
    }

}
