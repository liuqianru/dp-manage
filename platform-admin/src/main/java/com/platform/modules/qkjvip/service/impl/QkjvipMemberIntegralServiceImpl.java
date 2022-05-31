/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegralServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-21 15:18:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import cn.emay.util.DateUtil;
import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.dao.QkjvipMemberIntegralDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegralEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegraluserEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegralService;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegraluserService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.ListToStringUtil;
import com.platform.modules.util.Vars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.*;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-12-21 15:18:24
 */
@Service("qkjvipMemberIntegralService")
public class QkjvipMemberIntegralServiceImpl extends ServiceImpl<QkjvipMemberIntegralDao, QkjvipMemberIntegralEntity> implements QkjvipMemberIntegralService {

    @Autowired
    private QkjvipMemberIntegraluserService qkjvipMemberIntegraluserService;

    @Override
    public List<QkjvipMemberIntegralEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.ADD_TIME");
        params.put("asc", false);
        Page<QkjvipMemberIntegralEntity> page = new Query<QkjvipMemberIntegralEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberIntegralPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(QkjvipMemberIntegralEntity qkjvipMemberIntegral) {

        this.save(qkjvipMemberIntegral);

        //批量保存会员
        saveIntegralUser(qkjvipMemberIntegral.getId(), qkjvipMemberIntegral.getMemberlist());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QkjvipMemberIntegralEntity qkjvipMemberIntegral) {
        this.updateById(qkjvipMemberIntegral);
        // 人员列表删除
        qkjvipMemberIntegraluserService.deleteByIntegralId(qkjvipMemberIntegral.getId());
        // 人员列表重新插入
        saveIntegralUser(qkjvipMemberIntegral.getId(), qkjvipMemberIntegral.getMemberlist());
    }

    @Override
    public void updateStatus(QkjvipMemberIntegralEntity qkjvipMemberIntegral) {
        baseMapper.updateStatus(qkjvipMemberIntegral);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendIntegral(QkjvipMemberIntegralEntity qkjvipMemberIntegral, List<String> memberids) throws IOException {
        baseMapper.updateStatus(qkjvipMemberIntegral);

        Map map = new HashMap();
        map.put("integral", qkjvipMemberIntegral.getIntegral());
        map.put("listmemberid", memberids);
        map.put("remark", "群发积分" + DateUtil.toString(new Date(), "yyyy-MM-dd"));
        Object obj = JSONArray.toJSON(map);

        String resultPost = HttpClient.sendPost(Vars.MEMBER_INTEGRAL_SEND_URL, JsonHelper.toJsonString(obj));
        JSONObject resultObject = JSON.parseObject(resultPost);
        if (!"200".equals(resultObject.get("resultcode").toString())) {  //调用不成功
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) {
        this.removeByIds(Arrays.asList(ids));
        // 同时批量删除人员列表数据
        qkjvipMemberIntegraluserService.deleteBatchByIntegralId(ids);
    }

    /**
     * 批量保存会员
     *
     * @param integralId integralId
     * @param memberlist memberlist
     * @return RestResponse
     */
    private void saveIntegralUser(String integralId, List<QkjvipMemberIntegraluserEntity> memberlist) {
        if (memberlist != null && memberlist.size() > 0) {
            List<QkjvipMemberIntegraluserEntity> integralusers = new ArrayList<>();
            for (QkjvipMemberIntegraluserEntity integraluser : memberlist) {
                integraluser.setIntegralId(integralId);
                integralusers.add(integraluser);
            }
            qkjvipMemberIntegraluserService.addBatch(integralusers);
        }
    }
}
