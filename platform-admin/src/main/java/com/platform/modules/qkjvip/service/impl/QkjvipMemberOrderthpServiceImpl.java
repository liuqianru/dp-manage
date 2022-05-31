/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberOrderthpServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-01-13 09:27:21        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberOrderthpDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderthpEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrderthpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-01-13 09:27:21
 */
@Service("qkjvipMemberOrderthpService")
public class QkjvipMemberOrderthpServiceImpl extends ServiceImpl<QkjvipMemberOrderthpDao, QkjvipMemberOrderthpEntity> implements QkjvipMemberOrderthpService {

    @Override
    public List<QkjvipMemberOrderthpEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberOrderthpEntity> page = new Query<QkjvipMemberOrderthpEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberOrderthpPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberOrderthpEntity qkjvipMemberOrderthp) {
        return this.save(qkjvipMemberOrderthp);
    }

    @Override
    public boolean update(QkjvipMemberOrderthpEntity qkjvipMemberOrderthp) {
        return this.updateById(qkjvipMemberOrderthp);
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
    public int deleteBatchByOrder(List<QkjvipMemberOrderthpEntity> mbList) {
        return baseMapper.deleteBatchByOrder(mbList);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<QkjvipMemberOrderthpEntity> mbList) {
        this.saveBatch(mbList, 1000);
    }

}
