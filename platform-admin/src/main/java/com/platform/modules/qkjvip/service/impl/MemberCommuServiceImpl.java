/*
 * 项目名称:platform-plus
 * 类名称:MemberCommuServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 14:12            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.MemberCommuDao;
import com.platform.modules.qkjvip.entity.MemberAddressEntity;
import com.platform.modules.qkjvip.entity.MemberCommuEntity;
import com.platform.modules.qkjvip.service.MemberCommuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * MemberCommuServiceImpl
 *
 * @author liuqianru
 * @date 2020/3/13 14:12
 */
@Service("memberCommuService")
public class MemberCommuServiceImpl extends ServiceImpl<MemberCommuDao, MemberCommuEntity> implements MemberCommuService {
    @Override
    public List<MemberCommuEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "mc.add_time");
        params.put("asc", false);
        Page<MemberCommuEntity> page = new Query<MemberCommuEntity>(params).getPage();
        page.setRecords(baseMapper.selectMemberCommuList(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public void add(MemberCommuEntity mcommu, Map<String, Object> params) {
        this.save(mcommu);
    }

    @Override
    public void add(MemberCommuEntity mcommu) {
        this.save(mcommu);
    }

    @Override
    public void update(MemberCommuEntity mcommu, Map<String, Object> params) {
        this.updateById(mcommu);
    }

    @Override
    public void deleteBatch(String[] uuids) {
        this.removeByIds(Arrays.asList(uuids));
    }
}
