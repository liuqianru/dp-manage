/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymaterialServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-08 13:43:57        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberActivitymaterialDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivitymaterialEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberSignupaddressEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActivitymaterialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-09-08 13:43:57
 */
@Service("qkjvipMemberActivitymaterialService")
public class QkjvipMemberActivitymaterialServiceImpl extends ServiceImpl<QkjvipMemberActivitymaterialDao, QkjvipMemberActivitymaterialEntity> implements QkjvipMemberActivitymaterialService {

    @Override
    public List<QkjvipMemberActivitymaterialEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberActivitymaterialEntity> page = new Query<QkjvipMemberActivitymaterialEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberActivitymaterialPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberActivitymaterialEntity qkjvipMemberActivitymaterial) {
        return this.save(qkjvipMemberActivitymaterial);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<QkjvipMemberActivitymaterialEntity> materials) {
        this.saveBatch(materials, 100);
    }

    @Override
    public boolean update(QkjvipMemberActivitymaterialEntity qkjvipMemberActivitymaterial) {
        return this.updateById(qkjvipMemberActivitymaterial);
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
    public int deleteBatchByOrder(String activityId,Integer type) {
        return baseMapper.deleteBatchByOrder(activityId,type);
    }
}
