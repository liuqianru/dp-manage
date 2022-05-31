/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberRightsConfigServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-30 09:23:01        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberRightsConfigDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberRightsConfigEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberRightsConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-04-30 09:23:01
 */
@Service("qkjvipMemberRightsConfigService")
public class QkjvipMemberRightsConfigServiceImpl extends ServiceImpl<QkjvipMemberRightsConfigDao, QkjvipMemberRightsConfigEntity> implements QkjvipMemberRightsConfigService {

    @Override
    public List<QkjvipMemberRightsConfigEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.CREATEON");
        params.put("asc", false);
        Page<QkjvipMemberRightsConfigEntity> page = new Query<QkjvipMemberRightsConfigEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberRightsConfigPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberRightsConfigEntity qkjvipMemberRightsConfig) {
        return this.save(qkjvipMemberRightsConfig);
    }

    @Override
    public boolean update(QkjvipMemberRightsConfigEntity qkjvipMemberRightsConfig) {
        return this.updateById(qkjvipMemberRightsConfig);
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
}
