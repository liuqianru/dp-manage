/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawAcitiityitemServiceImpl.java
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
import com.platform.modules.qkjluck.dao.QkjluckDrawAcitiityitemDao;
import com.platform.modules.qkjluck.entity.QkjluckDrawAcitiityitemEntity;
import com.platform.modules.qkjluck.service.QkjluckDrawAcitiityitemService;
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
@Service("qkjluckDrawAcitiityitemService")
public class QkjluckDrawAcitiityitemServiceImpl extends ServiceImpl<QkjluckDrawAcitiityitemDao, QkjluckDrawAcitiityitemEntity> implements QkjluckDrawAcitiityitemService {

    @Override
    public List<QkjluckDrawAcitiityitemEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.shownum");
        params.put("asc", true);
        Page<QkjluckDrawAcitiityitemEntity> page = new Query<QkjluckDrawAcitiityitemEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjluckDrawAcitiityitemPage(page, params));
    }

    @Override
    public boolean add(QkjluckDrawAcitiityitemEntity qkjluckDrawAcitiityitem) {
        return this.save(qkjluckDrawAcitiityitem);
    }

    @Override
    public boolean update(QkjluckDrawAcitiityitemEntity qkjluckDrawAcitiityitem) {
        return this.updateById(qkjluckDrawAcitiityitem);
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
    public int deleteBatchByMain(String mainid) {
        return baseMapper.deleteBatchByMain(mainid);
    }

    @Override
    public boolean update(String mainId) {
        return baseMapper.updateResultAddressByMainId(mainId);
    }
}
