/*
 * 项目名称:platform-plus
 * 类名称:QkjserviceMemberSupportServiceImpl.java
 * 包名称:com.platform.modules.qkjservice.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-09 09:19:43        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjservice.dao.QkjserviceMemberSupportDao;
import com.platform.modules.qkjservice.entity.QkjserviceMemberSupportEntity;
import com.platform.modules.qkjservice.service.QkjserviceMemberSupportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-09-09 09:19:43
 */
@Service("qkjserviceMemberSupportService")
public class QkjserviceMemberSupportServiceImpl extends ServiceImpl<QkjserviceMemberSupportDao, QkjserviceMemberSupportEntity> implements QkjserviceMemberSupportService {

    @Override
    public List<QkjserviceMemberSupportEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjserviceMemberSupportEntity> page = new Query<QkjserviceMemberSupportEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjserviceMemberSupportPage(page, params));
    }

    @Override
    public boolean add(QkjserviceMemberSupportEntity qkjserviceMemberSupport) {
        return this.save(qkjserviceMemberSupport);
    }

    @Override
    public boolean update(QkjserviceMemberSupportEntity qkjserviceMemberSupport) {
        return this.updateById(qkjserviceMemberSupport);
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
