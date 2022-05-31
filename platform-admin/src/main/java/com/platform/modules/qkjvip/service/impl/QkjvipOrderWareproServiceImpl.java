/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWareproServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-22 13:59:47        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipOrderWareproDao;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareproEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWareproService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-12-22 13:59:47
 */
@Service("qkjvipOrderWareproService")
public class QkjvipOrderWareproServiceImpl extends ServiceImpl<QkjvipOrderWareproDao, QkjvipOrderWareproEntity> implements QkjvipOrderWareproService {

    @Override
    public List<QkjvipOrderWareproEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createon");
        params.put("asc", false);
        Page<QkjvipOrderWareproEntity> page = new Query<QkjvipOrderWareproEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipOrderWareproPage(page, params));
    }

    @Override
    public Page queryPageListAll(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createon");
        params.put("asc", false);
        Page<QkjvipOrderWareproEntity> page = new Query<QkjvipOrderWareproEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipOrderWareproListPage(page, params));
    }

    @Override
    public boolean add(QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        return this.save(qkjvipOrderWarepro);
    }

    @Override
    public boolean update(QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        return this.updateById(qkjvipOrderWarepro);
    }

    @Override
    public boolean updateStateByProAndWare(QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        return baseMapper.updateStateByProAndWare(qkjvipOrderWarepro);
    }

    @Override
    public boolean updateDisabledByProAndWare(QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        return baseMapper.updateDisabledByProAndWare(qkjvipOrderWarepro);
    }

    @Override
    public boolean updateShiftWare(QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        return baseMapper.updateShiftWare(qkjvipOrderWarepro);
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
