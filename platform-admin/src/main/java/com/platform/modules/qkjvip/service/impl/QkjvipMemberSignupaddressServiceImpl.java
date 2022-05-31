/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupaddressServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-10-15 17:21:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberSignupaddressDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberSignupaddressEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberSignupaddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-10-15 17:21:03
 */
@Service("qkjvipMemberSignupaddressService")
public class QkjvipMemberSignupaddressServiceImpl extends ServiceImpl<QkjvipMemberSignupaddressDao, QkjvipMemberSignupaddressEntity> implements QkjvipMemberSignupaddressService {

    @Override
    public List<QkjvipMemberSignupaddressEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberSignupaddressEntity> page = new Query<QkjvipMemberSignupaddressEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberSignupaddressPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberSignupaddressEntity qkjvipMemberSignupaddress) {
        return this.save(qkjvipMemberSignupaddress);
    }

    @Override
    public boolean update(QkjvipMemberSignupaddressEntity qkjvipMemberSignupaddress) {
        return this.updateById(qkjvipMemberSignupaddress);
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
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<QkjvipMemberSignupaddressEntity> qkjvipMemberActivitymbs) {
        this.saveBatch(qkjvipMemberActivitymbs, 100);
    }

    @Override
    public int deleteBatchByOrder(String activityId) {
        return baseMapper.deleteBatchByOrder(activityId);
    }
}
