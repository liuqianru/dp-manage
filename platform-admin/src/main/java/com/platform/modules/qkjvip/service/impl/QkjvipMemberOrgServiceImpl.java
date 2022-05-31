/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrgServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-24 09:57:29        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberOrgDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrgEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-09-24 09:57:29
 */
@Service("qkjvipMemberOrgService")
public class QkjvipMemberOrgServiceImpl extends ServiceImpl<QkjvipMemberOrgDao, QkjvipMemberOrgEntity> implements QkjvipMemberOrgService {

    @Override
    public List<QkjvipMemberOrgEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.sort");
        params.put("asc", true);
        Page<QkjvipMemberOrgEntity> page = new Query<QkjvipMemberOrgEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberOrgPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberOrgEntity qkjvipMemberOrg) {
        return this.save(qkjvipMemberOrg);
    }

    @Override
    public boolean update(QkjvipMemberOrgEntity qkjvipMemberOrg) {
        return this.updateById(qkjvipMemberOrg);
    }

    @Override
    public boolean delete(String orgNo) {
        return this.removeById(orgNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] orgNos) {
        return this.removeByIds(Arrays.asList(orgNos));
    }
}
