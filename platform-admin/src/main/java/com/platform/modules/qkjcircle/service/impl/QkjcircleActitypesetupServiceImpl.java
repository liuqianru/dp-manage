/*
 * 项目名称:platform-plus
 * 类名称:QkjcircleActitypesetupServiceImpl.java
 * 包名称:com.platform.modules.qkjcircle.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-11 11:21:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcircle.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjcircle.dao.QkjcircleActitypesetupDao;
import com.platform.modules.qkjcircle.entity.QkjcircleActitypesetupEntity;
import com.platform.modules.qkjcircle.service.QkjcircleActitypesetupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-08-11 11:21:03
 */
@Service("qkjcircleActitypesetupService")
public class QkjcircleActitypesetupServiceImpl extends ServiceImpl<QkjcircleActitypesetupDao, QkjcircleActitypesetupEntity> implements QkjcircleActitypesetupService {

    @Override
    public List<QkjcircleActitypesetupEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjcircleActitypesetupEntity> page = new Query<QkjcircleActitypesetupEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjcircleActitypesetupPage(page, params));
    }

    @Override
    public boolean add(QkjcircleActitypesetupEntity qkjcircleActitypesetup) {
        return this.save(qkjcircleActitypesetup);
    }

    @Override
    public boolean update(QkjcircleActitypesetupEntity qkjcircleActitypesetup) {
        return this.updateById(qkjcircleActitypesetup);
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
