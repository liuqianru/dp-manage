/*
 * 项目名称:platform-plus
 * 类名称:MemberPortraitsettingServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-06-28 09:46:33        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.MemberPortraitsettingDao;
import com.platform.modules.qkjvip.entity.MemberPortraitsettingEntity;
import com.platform.modules.qkjvip.service.MemberPortraitsettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-06-28 09:46:33
 */
@Service("memberPortraitsettingService")
public class MemberPortraitsettingServiceImpl extends ServiceImpl<MemberPortraitsettingDao, MemberPortraitsettingEntity> implements MemberPortraitsettingService {

    @Override
    public List<MemberPortraitsettingEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<MemberPortraitsettingEntity> page = new Query<MemberPortraitsettingEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMemberPortraitsettingPage(page, params));
    }

    @Override
    public boolean add(MemberPortraitsettingEntity memberPortraitsetting) {
        return this.save(memberPortraitsetting);
    }

    @Override
    public boolean update(MemberPortraitsettingEntity memberPortraitsetting) {
        return this.updateById(memberPortraitsetting);
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
