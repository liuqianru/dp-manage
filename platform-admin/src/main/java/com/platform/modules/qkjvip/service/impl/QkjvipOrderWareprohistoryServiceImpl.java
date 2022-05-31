/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWareprohistoryServiceImpl.java
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
import com.platform.modules.qkjvip.dao.QkjvipOrderWareprohistoryDao;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareprohistoryEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWareprohistoryService;
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
@Service("qkjvipOrderWareprohistoryService")
public class QkjvipOrderWareprohistoryServiceImpl extends ServiceImpl<QkjvipOrderWareprohistoryDao, QkjvipOrderWareprohistoryEntity> implements QkjvipOrderWareprohistoryService {

    @Override
    public List<QkjvipOrderWareprohistoryEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipOrderWareprohistoryEntity> queryMagAll(Map<String, Object> params) {
        return baseMapper.queryMagAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createon");
        params.put("asc", false);
        Page<QkjvipOrderWareprohistoryEntity> page = new Query<QkjvipOrderWareprohistoryEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipOrderWareprohistoryPage(page, params));
    }

    @Override
    public boolean add(QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory) {
        return this.save(qkjvipOrderWareprohistory);
    }

    @Override
    public boolean update(QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory) {
        return this.updateById(qkjvipOrderWareprohistory);
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
