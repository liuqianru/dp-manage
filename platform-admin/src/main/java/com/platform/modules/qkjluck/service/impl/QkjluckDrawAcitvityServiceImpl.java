/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawAcitvityServiceImpl.java
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
import com.platform.modules.qkjluck.dao.QkjluckDrawAcitvityDao;
import com.platform.modules.qkjluck.entity.QkjluckDrawAcitvityEntity;
import com.platform.modules.qkjluck.service.QkjluckDrawAcitvityService;
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
@Service("qkjluckDrawAcitvityService")
public class QkjluckDrawAcitvityServiceImpl extends ServiceImpl<QkjluckDrawAcitvityDao, QkjluckDrawAcitvityEntity> implements QkjluckDrawAcitvityService {

    @Override
    public List<QkjluckDrawAcitvityEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addtime");
        params.put("asc", false);
        Page<QkjluckDrawAcitvityEntity> page = new Query<QkjluckDrawAcitvityEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjluckDrawAcitvityPage(page, params));
    }

    @Override
    public boolean add(QkjluckDrawAcitvityEntity qkjluckDrawAcitvity) {
        return this.save(qkjluckDrawAcitvity);
    }

    @Override
    public boolean update(QkjluckDrawAcitvityEntity qkjluckDrawAcitvity) {
        return this.updateById(qkjluckDrawAcitvity);
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
