/*
 * 项目名称:platform-plus
 * 类名称:QkjwineRInfoServiceImpl.java
 * 包名称:com.platform.modules.qkjwine.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-16 09:16:11             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjwine.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjwine.dao.QkjwineRInfoDao;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import com.platform.modules.qkjwine.service.QkjwineRInfoService;
import org.apache.poi.hpsf.Decimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 
 * @date 2022-02-16 09:16:11
 */
@Service("qkjwineRInfoService")
public class QkjwineRInfoServiceImpl extends ServiceImpl<QkjwineRInfoDao, QkjwineRInfoEntity> implements QkjwineRInfoService {

    @Override
    public List<QkjwineRInfoEntity> queryAll(Map<String, Object> params) {
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjwineRInfoEntity> selectSqlSimple(Map<String, Object> params) {
        return baseMapper.selectSqlSimple(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.add_time");
        params.put("asc", false);
        Page<QkjwineRInfoEntity> page = new Query<QkjwineRInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjwineRInfoPage(page, params));
    }

    @Override
    public Double queryValue(Map<String, Object> params) {
        return baseMapper.queryValue(params);
    }

    @Override
    public boolean add(QkjwineRInfoEntity qkjwineRInfo) {
        return this.save(qkjwineRInfo);
    }

    @Override
    public boolean update(QkjwineRInfoEntity qkjwineRInfo) {
        return this.updateById(qkjwineRInfo);
    }

    @Override
    public boolean updateWineInfo(QkjwineRInfoEntity qkjwineRInfo) {
        return baseMapper.updateWineInfo(qkjwineRInfo);
    }

    @Override
    public boolean updateTakeIdById(QkjwineRInfoEntity qkjwineRInfo) {
        return baseMapper.updateTakeIdById(qkjwineRInfo);
    }

    @Override
    public boolean updateScenetimeByWareporid(QkjwineRInfoEntity qkjwineRInfo) {
        return baseMapper.updateScenetimeByWareporid(qkjwineRInfo);
    }

    public boolean updateFtnumByWareporid(QkjwineRInfoEntity qkjwineRInfo) {
        return baseMapper.updateFtnumByWareporid(qkjwineRInfo);
    }
    @Override
    public boolean mdyWineDisableById(QkjwineRInfoEntity qkjwineRInfo) {
        return baseMapper.mdyWineDisableById(qkjwineRInfo);
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
