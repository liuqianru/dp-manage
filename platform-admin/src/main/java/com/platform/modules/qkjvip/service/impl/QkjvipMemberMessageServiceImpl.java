/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberMessageServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-22 11:05:08        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberMessageDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageEntity;
import com.platform.modules.qkjvip.entity.QkjvipOptionsEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberMessageService;
import com.platform.modules.sys.entity.SysUserChannelEntity;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @date 2020-12-22 11:05:08
 */
@Service("qkjvipMemberMessageService")
public class QkjvipMemberMessageServiceImpl extends ServiceImpl<QkjvipMemberMessageDao, QkjvipMemberMessageEntity> implements QkjvipMemberMessageService {

    @Autowired
    private SysUserChannelService sysUserChannelService;

    @Override
    public List<QkjvipMemberMessageEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberMessageEntity> page = new Query<QkjvipMemberMessageEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberMessagePage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberMessageEntity qkjvipMemberMessage) {
        return this.save(qkjvipMemberMessage);
    }

    @Override
    public boolean update(QkjvipMemberMessageEntity qkjvipMemberMessage) {
        return this.updateById(qkjvipMemberMessage);
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
    public List<QkjvipOptionsEntity> queryChannels() {
        List<QkjvipOptionsEntity> channelList = new ArrayList<>();
        //获取公众号列表
        String resultPost = HttpClient.sendGet(Vars.APPID_GETLIST_URL);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("0".equals(resultObject.get("code").toString())) {  //调用成功
            channelList = JSON.parseArray(resultObject.getString("data"), QkjvipOptionsEntity.class);
        }
        return channelList;
    }
}
