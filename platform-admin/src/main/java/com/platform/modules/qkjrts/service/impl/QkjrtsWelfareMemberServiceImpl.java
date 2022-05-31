/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareMemberServiceImpl.java
 * 包名称:com.platform.modules.qkjrts.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrts.dao.QkjrtsWelfareMemberDao;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareMemberEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareMemberService;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@Service("qkjrtsWelfareMemberService")
public class QkjrtsWelfareMemberServiceImpl extends ServiceImpl<QkjrtsWelfareMemberDao, QkjrtsWelfareMemberEntity> implements QkjrtsWelfareMemberService {

    @Override
    public List<QkjrtsWelfareMemberEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipMemberMessageUserQueryEntity> queryByMainId(String mainid) {
        return baseMapper.queryByMainId(mainid);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjrtsWelfareMemberEntity> page = new Query<QkjrtsWelfareMemberEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrtsWelfareMemberPage(page, params));
    }

    @Override
    public boolean add(QkjrtsWelfareMemberEntity qkjrtsWelfareMember) {
        return this.save(qkjrtsWelfareMember);
    }

    @Override
    public void addBatch(List<QkjrtsWelfareMemberEntity> memberlist) {
        this.saveBatch(memberlist, 1000);
    }

    @Override
    public boolean update(QkjrtsWelfareMemberEntity qkjrtsWelfareMember) {
        return this.updateById(qkjrtsWelfareMember);
    }

    @Override
    public boolean updateByMainId(String mainId) {
        return baseMapper.updateByMainId(mainId);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public void deleteByMainId(String mainid) {
        baseMapper.deleteByMainId(mainid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
