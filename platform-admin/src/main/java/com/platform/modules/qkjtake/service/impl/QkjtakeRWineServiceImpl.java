/*
 * 项目名称:platform-plus
 * 类名称:QkjtakeRWineServiceImpl.java
 * 包名称:com.platform.modules.qkjtake.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-21 14:18:34             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjtake.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjtake.dao.QkjtakeRWineDao;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import com.platform.modules.qkjtake.service.QkjtakeRWineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 
 * @date 2022-02-21 14:18:34
 */
@Service("qkjtakeRWineService")
public class QkjtakeRWineServiceImpl extends ServiceImpl<QkjtakeRWineDao, QkjtakeRWineEntity> implements QkjtakeRWineService {

    @Override
    public List<QkjtakeRWineEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "t.takeDate");
        params.put("asc", false);
        Page<QkjtakeRWineEntity> page = new Query<QkjtakeRWineEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjtakeRWinePage(page, params));
    }

    @Override
    public boolean add(QkjtakeRWineEntity qkjtakeRWine) {
        return this.save(qkjtakeRWine);
    }

    @Override
    public boolean update(QkjtakeRWineEntity qkjtakeRWine) {
        return this.updateById(qkjtakeRWine);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean updateTakeStateById(QkjtakeRWineEntity qkjtakeRWine) {
        return baseMapper.updateTakeStateById(qkjtakeRWine);
    }

    @Override
    public boolean updateTakeByWareproid(QkjtakeRWineEntity qkjtakeRWine) {
        return baseMapper.updateTakeByWareproid(qkjtakeRWine);
    }

    @Override
    public void updateStateById(String Id) {
        baseMapper.updateStateById(Id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
