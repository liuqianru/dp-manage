/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActothersignServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 15:07:44        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberActothersignDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberActothersignEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActothersignService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-04-26 15:07:44
 */
@Service("qkjvipMemberActothersignService")
public class QkjvipMemberActothersignServiceImpl extends ServiceImpl<QkjvipMemberActothersignDao, QkjvipMemberActothersignEntity> implements QkjvipMemberActothersignService {

    @Override
    public List<QkjvipMemberActothersignEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberActothersignEntity> page = new Query<QkjvipMemberActothersignEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberActothersignPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberActothersignEntity qkjvipMemberActothersign) {
        return this.save(qkjvipMemberActothersign);
    }

    @Override
    public boolean update(QkjvipMemberActothersignEntity qkjvipMemberActothersign) {
        return this.updateById(qkjvipMemberActothersign);
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
