/*
 * 项目名称:platform-plus
 * 类名称:QkjmHProcessServiceImpl.java
 * 包名称:com.platform.modules.qkjm.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-02 16:26:02        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjm.dao.QkjmHProcessDao;
import com.platform.modules.qkjm.entity.QkjmHProcessEntity;
import com.platform.modules.qkjm.service.QkjmHProcessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-11-02 16:26:02
 */
@Service("qkjmHProcessService")
public class QkjmHProcessServiceImpl extends ServiceImpl<QkjmHProcessDao, QkjmHProcessEntity> implements QkjmHProcessService {

    @Override
    public List<QkjmHProcessEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.uuid");
        params.put("asc", false);
        Page<QkjmHProcessEntity> page = new Query<QkjmHProcessEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjmHProcessPage(page, params));
    }

    @Override
    public boolean add(QkjmHProcessEntity qkjmHProcess) {
        return this.save(qkjmHProcess);
    }

    @Override
    public boolean update(QkjmHProcessEntity qkjmHProcess) {
        return this.updateById(qkjmHProcess);
    }

    @Override
    public boolean delete(String uuid) {
        return this.removeById(uuid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] uuids) {
        return this.removeByIds(Arrays.asList(uuids));
    }
}
