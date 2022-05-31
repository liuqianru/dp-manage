/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrguserServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 17:33:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberOrguserDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrguserEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrguserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-08-04 17:33:08
 */
@Service("qkjvipMemberOrguserService")
public class QkjvipMemberOrguserServiceImpl extends ServiceImpl<QkjvipMemberOrguserDao, QkjvipMemberOrguserEntity> implements QkjvipMemberOrguserService {

    @Override
    public List<QkjvipMemberOrguserEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberOrguserEntity> page = new Query<QkjvipMemberOrguserEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberOrguserPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberOrguserEntity qkjvipMemberOrguser) {
        return this.save(qkjvipMemberOrguser);
    }

    @Override
    public boolean update(QkjvipMemberOrguserEntity qkjvipMemberOrguser) {
        return this.updateById(qkjvipMemberOrguser);
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
