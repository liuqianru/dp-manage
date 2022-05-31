/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawResultcachServiceImpl.java
 * 包名称:com.platform.modules.qkjluck.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-18 14:27:03             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjluck.dao.QkjluckDrawResultcachDao;
import com.platform.modules.qkjluck.entity.QkjluckDrawResultcachEntity;
import com.platform.modules.qkjluck.service.QkjluckDrawResultcachService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 
 * @date 2021-11-18 14:27:03
 */
@Service("qkjluckDrawResultcachService")
public class QkjluckDrawResultcachServiceImpl extends ServiceImpl<QkjluckDrawResultcachDao, QkjluckDrawResultcachEntity> implements QkjluckDrawResultcachService {

    @Override
    public List<QkjluckDrawResultcachEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjluckDrawResultcachEntity> page = new Query<QkjluckDrawResultcachEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjluckDrawResultcachPage(page, params));
    }

    @Override
    public boolean add(QkjluckDrawResultcachEntity qkjluckDrawResultcach) {
        return this.save(qkjluckDrawResultcach);
    }
    @Override
    public boolean addluck(String key,String val) {
        QkjluckDrawResultcachEntity qkjluckDrawResultcach = new QkjluckDrawResultcachEntity();
        qkjluckDrawResultcach.setCkey(key);
        qkjluckDrawResultcach.setCvalue(val);
        return this.save(qkjluckDrawResultcach);
    }

    @Override
    public boolean update(QkjluckDrawResultcachEntity qkjluckDrawResultcach) {
        return this.updateById(qkjluckDrawResultcach);
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
