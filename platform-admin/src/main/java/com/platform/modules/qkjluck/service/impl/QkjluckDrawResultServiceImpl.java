/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawResultServiceImpl.java
 * 包名称:com.platform.modules.qkjluck.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 17:26:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjluck.dao.QkjluckDrawResultDao;
import com.platform.modules.qkjluck.entity.QkjluckDrawResultEntity;
import com.platform.modules.qkjluck.service.QkjluckDrawResultService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@Service("qkjluckDrawResultService")
public class QkjluckDrawResultServiceImpl extends ServiceImpl<QkjluckDrawResultDao, QkjluckDrawResultEntity> implements QkjluckDrawResultService {

    @Override
    public List<QkjluckDrawResultEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addtime");
        params.put("asc", false);
        Page<QkjluckDrawResultEntity> page = new Query<QkjluckDrawResultEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjluckDrawResultPage(page, params));
    }

    @Override
    public boolean add(QkjluckDrawResultEntity qkjluckDrawResult) {
        return this.save(qkjluckDrawResult);
    }

    @Override
    public boolean update(QkjluckDrawResultEntity qkjluckDrawResult) {
        return this.updateById(qkjluckDrawResult);
    }

    @Override
    public boolean updateByPama(QkjluckDrawResultEntity qkjluckDrawResult) {
        return baseMapper.updateByPama(qkjluckDrawResult);
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
