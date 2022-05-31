/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberFansServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-09 14:02:22        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.modules.quartz.dao.QrtzMemberFansDao;
import com.platform.modules.quartz.entity.QrtzMemberFansEntity;
import com.platform.modules.quartz.service.QrtzMemberFansService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-09-09 14:02:22
 */
@Service("qrtzMemberFansService")
public class QrtzMemberFansServiceImpl extends ServiceImpl<QrtzMemberFansDao, QrtzMemberFansEntity> implements QrtzMemberFansService {

    @Override
    public List<QrtzMemberFansEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QrtzMemberFansEntity> queryList() {
        return baseMapper.queryList();
    }

    @Override
    public List<QrtzMemberFansEntity> queryByMemberMessageQuery(Map<String, Object> params) {
        return baseMapper.queryByMemberMessageQuery(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<QrtzMemberFansEntity> fanList) {
        this.saveBatch(fanList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(List<QrtzMemberFansEntity> fanList) {
        this.updateBatchById(fanList);
    }
}
