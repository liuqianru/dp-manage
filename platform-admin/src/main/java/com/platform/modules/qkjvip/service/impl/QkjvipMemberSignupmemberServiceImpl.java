/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupmemberServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-10-26 13:18:34        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberSignupmemberDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberSignupmemberEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberSignupmemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2020-10-26 13:18:34
 */
@Service("qkjvipMemberSignupmemberService")
public class QkjvipMemberSignupmemberServiceImpl extends ServiceImpl<QkjvipMemberSignupmemberDao, QkjvipMemberSignupmemberEntity> implements QkjvipMemberSignupmemberService {

    @Override
    public List<QkjvipMemberSignupmemberEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberSignupmemberEntity> queryTopOne(Map<String, Object> params) {
        return baseMapper.queryTopOne(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjvipMemberSignupmemberEntity> page = new Query<QkjvipMemberSignupmemberEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberSignupmemberPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember) {
        return this.save(qkjvipMemberSignupmember);
    }

    @Override
    public boolean update(QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember) {
        return this.updateById(qkjvipMemberSignupmember);
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
