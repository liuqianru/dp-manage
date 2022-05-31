/*
 * 项目名称:platform-plus
 * 类名称:QkjsmsRInfoServiceImpl.java
 * 包名称:com.platform.modules.qkjsms.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-15 14:31:17        sun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjsms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjsms.dao.QkjsmsRInfoDao;
import com.platform.modules.qkjsms.entity.QkjsmsRInfoEntity;
import com.platform.modules.qkjsms.service.QkjsmsRInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author sun
 * @date 2022-03-15 14:31:17
 */
@Service("qkjsmsRInfoService")
public class QkjsmsRInfoServiceImpl extends ServiceImpl<QkjsmsRInfoDao, QkjsmsRInfoEntity> implements QkjsmsRInfoService {

    @Override
    public List<QkjsmsRInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjsmsRInfoEntity> page = new Query<QkjsmsRInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjsmsRInfoPage(page, params));
    }

    @Override
    public boolean add(QkjsmsRInfoEntity qkjsmsRInfo) {
        return this.save(qkjsmsRInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<QkjsmsRInfoEntity> qkjsmsRInfo) {
        this.saveBatch(qkjsmsRInfo, 100);
    }

    @Override
    public boolean update(QkjsmsRInfoEntity qkjsmsRInfo) {
        return this.updateById(qkjsmsRInfo);
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
